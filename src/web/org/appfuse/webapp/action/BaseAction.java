package org.appfuse.webapp.action;


import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.appfuse.Constants;
import org.appfuse.NowDate;
 
import org.appfuse.model.User;
import org.appfuse.util.ConvertUtil;
import org.appfuse.util.CurrencyConverter;
import org.appfuse.util.DateConverter;
import org.appfuse.webapp.util.SslUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Implementation of <strong>Action </strong> that contains base methods for
 * logging and conducting pre/post perform actions. This class is intended to be
 * a base class for all Struts actions. <p/><a href="BaseAction.java.html">
 * <i>View Source </i> </a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible </a>
 */
public class BaseAction extends LookupDispatchAction {
	protected static String realPath = "c:\\ltf";
	
    protected static int MaxMbs=60;
    
	protected static final String TEMPLET = "templet"; //模板路径

	private static final String reportDir = "bb";

	private static final String reportDqsDir = "bb/dqs";

	protected static final String SAVEFN = "SAVEFN";

	protected static final String EXCELNAME = "EXCELNAME";

	protected static final String PATH = "PATH";

	protected transient final Log log = LogFactory.getLog(getClass());

	private static final String SECURE = "secure";

	private static ApplicationContext ctx = null;

	private static Long defaultLong = null;

	public static String suffixName = ".xls";

	/**
	 * NEW: added by Jaap
	 * 
	 * Message (key) name of default locale to message key lookup.
	 */
	protected Map defaultKeyNameKeyMap = null;

	static {
		ConvertUtils.register(new CurrencyConverter(), Double.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new DateConverter(), String.class);
		ConvertUtils.register(new LongConverter(defaultLong), Long.class);
		ConvertUtils.register(new IntegerConverter(defaultLong), Integer.class);
	}

	/**
	 * Convenience method to bind objects in Actions
	 * 
	 * @param name
	 * @return
	 */
	public Object getBean(String name) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servlet
							.getServletContext());
		}
		return ctx.getBean(name);
	}

	/**
	 * Provides the mapping from resource key to method name
	 * 
	 * @return Resource key / method name map
	 */
	public Map getKeyMethodMap() {
		Map map = new HashMap();

		String pkg = this.getClass().getPackage().getName();
		ResourceBundle methods = ResourceBundle.getBundle(pkg
				+ ".LookupMethods");

		Enumeration keys = methods.getKeys();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			map.put(key, methods.getString(key));
		}

		return map;
	}

	/**
	 * @see org.appfuse.util.ConvertUtil#convert(java.lang.Object)
	 */
	protected Object convert(Object o) throws Exception {
		return ConvertUtil.convert(o);
	}

	/**
	 * @see org.appfuse.util.ConvertUtil#convertLists(java.lang.Object)
	 */
	protected Object convertLists(Object o) throws Exception {
		return ConvertUtil.convertLists(o);
	}

	/**
	 * Convenience method to initialize messages in a subclass.
	 * 
	 * @param request
	 *            the current request
	 * @return the populated (or empty) messages
	 */
	public ActionMessages getMessages(HttpServletRequest request) {
		ActionMessages messages = null;
		HttpSession session = request.getSession();

		if (request.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) request
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
		} else if (session.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) session
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
			session.removeAttribute(Globals.MESSAGE_KEY);
		} else {
			messages = new ActionMessages();
		}

		return messages;
	}

	/**
	 * Override the execute method in LookupDispatchAction to parse URLs and
	 * forward to methods without parameters. Also will forward to unspecified
	 * method when no parameter is present. <p/>This is based on the following
	 * system: <p/>
	 * <ul>
	 * <li>edit*.html -> edit method</li>
	 * <li>save*.html -> save method</li>
	 * <li>view*.html -> search method</li>
	 * </ul>
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @return Describes where and how control should be forwarded.
	 * @throws Exception
	 *             if an error occurs
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (isCancelled(request)) {
			ActionForward af = cancelled(mapping, form, request, response);

			if (af != null) {
				return af;
			}
		}

		MessageResources resources = getResources(request);

		// Identify the localized message for the cancel button
		String edit = resources.getMessage(Locale.ENGLISH, "button.edit")
				.toLowerCase();
		String save = resources.getMessage(Locale.ENGLISH, "button.save")
				.toLowerCase();
		String search = resources.getMessage(Locale.ENGLISH, "button.search")
				.toLowerCase();
		String view = resources.getMessage(Locale.ENGLISH, "button.view")
				.toLowerCase();
		String[] rules = { edit, save, search, view };

		// Identify the request parameter containing the method name
		// 得到字符串method，制定变量method中存放的是方法名称
		String parameter = mapping.getParameter();

		// don't set keyName unless it's defined on the action-mapping
		// no keyName -> unspecified will be called
		String keyName = null;

		if (parameter != null) {
			// 从变量method中取出方法名
			keyName = request.getParameter(parameter);
		}

		if ((keyName == null) || (keyName.length() == 0)) {
			for (int i = 0; i < rules.length; i++) {
				// apply the rules for automatically appending the method name
				// 根据请求路径中的信息，来选择处理方法。
				if (request.getServletPath().indexOf(rules[i]) > -1) {
					return dispatchMethod(mapping, form, request, response,
							rules[i]);
				}
			}

			return this.unspecified(mapping, form, request, response);
		}

		// Identify the string to lookup
		String methodName = getMethodName(mapping, form, request, response,
				parameter);

		return dispatchMethod(mapping, form, request, response, methodName);
	}

	/**
	 * Convenience method for getting an action form base on it's mapped scope.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @return ActionForm the form from the specifies scope, or null if nothing
	 *         found
	 */
	protected ActionForm getActionForm(ActionMapping mapping,
			HttpServletRequest request) {
		ActionForm actionForm = null;

		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				actionForm = (ActionForm) request.getAttribute(mapping
						.getAttribute());
			} else {
				HttpSession session = request.getSession();

				actionForm = (ActionForm) session.getAttribute(mapping
						.getAttribute());
			}
		}

		return actionForm;
	}

	/**
	 * Convenience method to get the userForm from the session
	 * 
	 * @param session
	 *            the current user's session
	 * @return the user's populated form from the session
	 */
	protected User getUser(HttpSession session) {
		// get the user form from the session
		return (User) session.getAttribute(Constants.USER_KEY);
	}

	/**
	 * Convenience method to get the Configuration HashMap from the servlet
	 * context.
	 * 
	 * @return the user's populated form from the session
	 */
	public Map getConfiguration() {
		Map config = (HashMap) getServlet().getServletContext().getAttribute(
				Constants.CONFIG);
		// so unit tests don't puke when nothing's been set
		if (config == null) {
			return new HashMap();
		}
		return config;
	}

	// --------------------------------------------------------- Public Methods
	// Don't use class variables in Action objects. These are not session safe.

	/**
	 * Method to check and see if https is required for this resource
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return boolean true if redirection to SSL is needed
	 */
	protected boolean checkSsl(ActionMapping mapping,
			HttpServletRequest request, HttpServletResponse response) {
		String redirectString = SslUtil.getRedirectString(request, getServlet()
				.getServletContext(), SECURE.equals(mapping.getParameter()));

		if (redirectString != null) {
			log.debug("protocol switch needed, redirecting...");

			try {
				// Redirect the page to the desired URL
				response.sendRedirect(response
						.encodeRedirectURL(redirectString));

				return true;
			} catch (Exception ioe) {
				log.error("redirect to new protocol failed...");

				// Handle appropriately
			}
		}

		return false;
	}

	/**
	 * Convenience method for removing the obsolete form bean.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 */
	protected void removeFormBean(ActionMapping mapping,
			HttpServletRequest request) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.removeAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();

				session.removeAttribute(mapping.getAttribute());
			}
		}
	}

	/**
	 * Convenience method to update a formBean in it's scope
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @param form
	 *            The ActionForm
	 */
	protected void updateFormBean(ActionMapping mapping,
			HttpServletRequest request, ActionForm form) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.setAttribute(mapping.getAttribute(), form);
			} else {
				HttpSession session = request.getSession();

				session.setAttribute(mapping.getAttribute(), form);
			}
		}
	}

	/**
	 * NEW: added by Jaap <p/>Lookup the key name corresponding to the client
	 * request's locale. This method is overwritten here because the key names
	 * are hardcoded in the global forwards of the struts-config.xml file. This
	 * in turn is required to make the StrutsMenu tags work (amongst others).
	 * <p/>Whatever the reason, this hard-coding is breaking the default
	 * i18n-mechanism used by the LookupDispatchAction finds the Action-method
	 * name. <p/>What I am doing now is to first call the
	 * LookupDispatchAction.getLookupMapName method, catching any
	 * ServletException that might arise. There are two reasons why
	 * <code>getLookupMapName</code> would throw an exception: first because
	 * the keyName cannot be found in the resourcebundle associated with the
	 * User's Localea and second because no method name is specified for the
	 * corresponding key. We're only interested in the first exception, but
	 * catching the second doesn't do any harm, it will just get re-thrown.
	 * 
	 * @see org.apache.struts.actions.LookupDispatchAction#getLookupMapName(HttpServletRequest,String,ActionMapping)
	 */
	protected String getLookupMapName(HttpServletRequest request,
			String keyName, ActionMapping mapping) throws ServletException {

		if (log.isDebugEnabled()) {
			log.debug("BaseAction: getLookupMapName( keyName = " + keyName
					+ " )");
		}

		String methodName = null;

		try {
			this.setLocale(request, request.getLocale()); // MR - added to
			// default to JSTL
			methodName = super.getLookupMapName(request, keyName, mapping);
		} catch (ServletException ex) {
			if (log.isDebugEnabled()) {
				log
						.debug("BaseAction: keyName not found in resource bundle with locale "
								+ request.getLocale());
			}

			// the keyname is not available in the resource bundle associated
			// with the user's locale
			// --> get find the key name in the default locale's resource bundle
			if (defaultKeyNameKeyMap == null) {
				defaultKeyNameKeyMap = this.initDefaultLookupMap(request);
			}

			// Find the key for the resource
			String key = (String) defaultKeyNameKeyMap.get(keyName);
			if (key == null) {
				if (log.isDebugEnabled()) {
					log.debug("keyName '" + keyName
							+ "' not found in resource bundle with locale "
							+ request.getLocale());
				}
				return keyName;
				//String message = messages.getMessage("dispatch.resource",
				// mapping.getPath(), keyName);
				//throw new ServletException(message);
			}

			// Find the method name
			methodName = (String) keyMethodMap.get(key);
			if (methodName == null) {
				String message = messages.getMessage("dispatch.lookup", mapping
						.getPath(), key);
				throw new ServletException(message);
			}

		}

		return methodName;
	}

	/**
	 * NEW: added by Jaap
	 * 
	 * This is the first time the default Locale is used so build the reverse
	 * lookup Map. Search for message keys in all configured MessageResources
	 * for the current module.
	 */
	protected Map initDefaultLookupMap(HttpServletRequest request) {
		Map lookupMap = new HashMap();
		this.keyMethodMap = this.getKeyMethodMap();

		ModuleConfig moduleConfig = (ModuleConfig) request
				.getAttribute(Globals.MODULE_KEY);

		MessageResourcesConfig[] mrc = moduleConfig
				.findMessageResourcesConfigs();

		// Look through all module's MessageResources
		for (int i = 0; i < mrc.length; i++) {
			MessageResources resources = this.getResources(request, mrc[i]
					.getKey());

			// Look for key in MessageResources
			Iterator iter = this.keyMethodMap.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String text = resources.getMessage(Locale.ENGLISH, key);

				// Found key and haven't added to Map yet, so add the text
				if ((text != null) && !lookupMap.containsKey(text)) {
					lookupMap.put(text, key);
				}
			}
		}

		return lookupMap;
	}

	//  ==============================================================================================

	//private static final String MemFolder = "Mem"; //保存记忆文件的文件夹

	private static final String Comma = ",";

	//返回当前操作员de 用户姓名
	protected static String getXgr(javax.servlet.http.HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute(Constants.USER_KEY);
		String username = user.getFirstName();
		return username;
	}

	/**
	 * 判断一个字符串是否为null或者“” 
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNullOrSpace(String s) {
		if (s != null && !s.trim().equals("") && !s.trim().equals("null")) {
			return false;
		} else
			return true;
	}
    

	/**
	 * 复制数组，将某数组中的指定id项去掉
	 * 
	 * @param String[]
	 *            cc
	 * @param String
	 *            ccid 当前记录的id值
	 * @return String[] mod
	 */
	public String[] copyArray(String[] sel_ids, String id) {
		String[] sa = null;
		if (sel_ids.length > 1) {
			sa = new String[sel_ids.length - 1];
			int i = 0;
			for (int j = 0; j < sel_ids.length; j++) {
				if (!sel_ids[j].equals(id)) {
					if (i < sa.length) {
						sa[i] = sel_ids[j];
					}

					i++;
				}
			}
		}

		return sa;
	}

	/**
	 * 获取from的值
	 * 
	 * @param request
	 * @return
	 */
	public String getType(HttpServletRequest request) {
		//Constants.FROM = from 记录返回到哪里，如果0，则返回list页面，否则，返回其他
		String type = request.getParameter(Constants.FROM);

		if (type == null) {
			type = "";
		}

		return type;
	}

	/**
	 * 将当前id在数组中的索引值－1
	 * 
	 * @param session
	 * @param pos
	 */
	public void decPos(HttpSession session, int pos) {
		if (pos != 0) {
			pos--;
		}
		session.setAttribute(Constants.POS, new Integer(pos));
	}

	/**
	 * 删除Session中存储的批量操作的ID的数组中的被删除的记录的id
	 * 
	 * @param id
	 * @param session
	 * @param sel_ids
	 */
	public String[] delID(String id, HttpSession session, String[] sel_ids) {
		String[] sa = null;
		if (sel_ids != null) {
			sa = copyArray(sel_ids, id); //获取删除了被删除的记录的id后的数组
			session.setAttribute(Constants.SEL_IDS, sa); //将选中的可以处理的id的数组写入Session
		}

		return sa;
	}

	/**
	 * 获取ValueListHandleHelper
	 */
	public ValueListHandlerHelper getValueListHelper() {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		return (ValueListHandlerHelper) context.getBean("valueListHelper",
				ValueListHandlerHelper.class);

	}

	
	/**
	 * 添加前后的%
	 * 
	 * @author ltf
	 */
	public static String addLike(String s) {
		String str = "";
		if (!isNullOrSpace(s)) {
			str = Constants.LIKE + s + Constants.LIKE;
		}

		return str;
	}

	//5-15
	/**
	 * @return
	 * @throws BeansException
	 */
	public ValueListHandler getValueListHandler() throws BeansException {
		//获取ValueList相关的东西
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);
		return vlh;
	}

	/** 
	 * 获取上一条、下一条图标的显示标记
	 * 
	 * @param i
	 * @param sel_ids
	 * @return
	 */
	public String getImgState(int i, String[] sel_ids) {
		String img_state = null; //上一条、下一条图标的显示标志
		//如果选择的id的数组为null或者长度为0
		if (sel_ids == null || sel_ids.length == 0) {
			img_state = Constants.NONE;
		} else {
			if (i == 0) { //如果是第一条
				if (sel_ids.length == 1) { //如果共一条记录，则没有上一条和下一条图标
					img_state = Constants.NONE;
				} else { //如果有多条记录，则肯定有下一条记录
					img_state = Constants.NEXT;
				}

			} else { //如果不是第一条记录
				if (i == sel_ids.length - 1) { //如果是最后一条,则此时不是第一条，则肯定有上一条记录
					img_state = Constants.PRIOR;
				} else { //如果不是最后一条，在这里则也不是第一条,所以这里上一条、下一条都显示
					img_state = Constants.BOTH;
				}
			}
		}

		return img_state;
	}

	//5-19
	/**
	 * 设置导出Excel的参数
	 * 
	 * @param request
	 * @param valueList
	 * @param excelFileName
	 * @param listname
	 */
	protected void setExcelInfo(HttpServletRequest request,
			ValueList valueList, String excelFileName, String listname) {
		request.getSession().setAttribute("listname", listname);
		request.getSession().setAttribute("valueListForExport", valueList);

		request.setAttribute(PATH, getXgr(request));//存储文件的路径
		request.setAttribute(EXCELNAME, excelFileName); //存储生成的文件名
	}

	/**
	 * 导出Excel
	 * 
	 * @param request
	 * @param bxlist
	 * @param valueList
	 */
	protected void expExcel(HttpServletRequest request, String bxlist,
			ValueList valueList, String excelFileName) {
		//导出特定参数,从而完成Excel文件
		String listname = bxlist;

		setExcelInfo(request, valueList, excelFileName, listname);
	}

	/**
	 * 设置上一条，下一条的图标状态
	 * 
	 * @param request
	 * @param i
	 * @param sel_ids
	 */
	public void setImgState(HttpServletRequest request, int i, String[] sel_ids) {
		String img_state = getImgState(i, sel_ids); //获取上一条、下一条图标状态

		request.setAttribute(Constants.IMG_STATE, img_state);
	}

	/**
	 * 得到查询一条记录失败的错误提示。
	 * 
	 * @param session
	 * @param e
	 * @param hintMsg
	 *            可以为任意的提示信息，如id或者名称等
	 */
	public void getError(HttpSession session, RuntimeException e,
			String errorKey, String hintMsg) {
		e.printStackTrace();
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		saveMessages(session, messages);
	}

	/**
	 * 得到查询一条记录的提示。
	 * 
	 * @param session
	 * @param e
	 * @param hintMsg
	 *            可以为任意的提示信息，如id或者名称等
	 */
	public void saveMsg(HttpServletRequest request, ActionMessages messages,
			String msgKey) {
		if (messages.size() < 1) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					msgKey));
			saveMessages(request.getSession(), messages);
		}
	}

	//5-28 ltf
	//  获取服务器的系统日期
	public static String getSysDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		return ReturnSapce(df.format(date));
	}

	//如果输入值是null，则返回“”
	public static String ReturnSapce(String V) {
		return (V == null ? "" : (V.equals("null") ? "" : V.trim()));
	}

	//如果输入值是null，则返回“”
	public static String ReturnBlankStringWhileNull(String V) {
		return (V == null ? "" : (V.trim().equals("null") ? "" : V.trim()));
	}

	//这个方法是公用的打印调试信息的方法。
	public static void PrintStr(String S) {
		System.out.println(S);
	}

//	//判断字符串类型的值是否是null或者是“”
//	public static boolean isNullOrSpace(String S) {
//		return (S == null || S.trim().equals(""));
//	}

	//获得要插入数据库中日期类型字段的字符串的值
	public static String getDateStr(Object O) {
		if (O != null) {
			if (!isNullOrSpace(O.toString())) {
				return "to_date('" + O.toString() + "','yyyy-mm-dd')";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	//获得要插入数据库中日期类型字段的字符串的值
	public static String getDateStrFN(String FN) {
		if (!isNullOrSpace(FN)) {
			return "to_date(" + FN + ",'yyyy-mm-dd')";
		} else {
			return null;
		}
	}

	//为指定的字符串加“"”
	public static String getQuotedStr(String S) {
		return "'" + S + "'";
	}

	//为指定的字符串前加“,”
	public static String getAddCommaStr(String S) {
		return Comma + S;
	}

	//创建文件夹
	public static boolean createDir(String DirName) {
		File f = new File(DirName);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}
	/**
	 * 清空目录，如果目录不存在，则创建空目录
	 * @param path
	 */
	public static void deleteDir(String path){
		try{
		File file = new File(path);
		if (!file.exists())	createDir(path);
		if(file!=null){
			String [] fileNames = file.list();
			for (int i=0; i<fileNames.length;i++){
				File delFile = new File(path + fileNames[i]);
				delFile.delete();
			}
		}

			
		}catch (Exception e){
			return;
		}
	}

	//得到当前月份
	public static String getMonth() {
		Date now = new Date();
		SimpleDateFormat mm = new SimpleDateFormat("M");
		return mm.format(now); //月份
	}

	//得到当前年份
	public static String getYear() {
		Date now = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy"); //年份
		return y.format(now);
	}

	//判断如果传入的参数为null或“”，则返回传入的默认值
	public static String getDefaultValue(String Str, String DefaultValue) {
		if (isNullOrSpace(Str)) {
			return ReturnSapce(DefaultValue);
		} else {
			return Str;
		}
	}

	//将Unicode->GBK
	public static String UnicodeToChinese(String Str) {
		String strChinese = "";
		try {
			if ((Str != null) && (Str.length() > 0)) {
				strChinese = new String(Str.getBytes("ISO-8859-1"), "GB2312");
			}
		} catch (java.io.UnsupportedEncodingException e) {
			System.out.println("Unicode to Chinese: " + e.getMessage());
			strChinese = "";
		}

		return strChinese;
	}

	//GBK->Unicode
	public static String ChineseToUnicode(String Str) {
		String strUnicode = "";
		try {
			if ((Str != null) && (Str.length() > 0)) {
				strUnicode = new String(Str.getBytes("GB2312"), "ISO-8859-1");
			}
		} catch (java.io.UnsupportedEncodingException e) {
			System.out.println("Chinese to Unicode: " + e.getMessage());
			strUnicode = "";
		}

		return strUnicode;
	}

	//为字符串添加方括号
	public static String getaddBracketStr(String str) {
		if (isNullOrSpace(str)) {
			return "";
		}
		return "[" + str + "]";
	}

	//得到英文姓名
	public static String getYWXM(String x, String m) {
		String xm = ReturnSapce(m) + "." + ReturnSapce(x);
		if (xm.equals(".")) {
			return "";
		}
		return xm;
	}

	//将截取从数据库中取得的日期类型的数据的前10位
	public static String getDateFromStr(String str) {
		if (!isNullOrSpace(str)) {
			if (str.length() >= 10) {
				return str.substring(0, 10);
			}
			return "";
		}
		return "";

	}

	//判断一个数字是否介于输入的2个数值之间(包含＝),如果在该范围内则返回true,否则返回false
	public static boolean isBetween(int V, int Max, int Min) {
		if ((V >= Min) && (V <= Max)) {
			return true;
		} else
			return false;
	}

	/**
	 * @return
	 */
	protected String getReportDir(String path) {
		return getPath(getPath(path) +  reportDir) ;
	}

	protected String getReportDqsDir(String path) {
		return getPath(getReportDir(path)+"dqs") ;
		
		//return path + "\\" + reportDqsDir + "\\";
	}

	/**
	 * 将List转换为数组
	 * 
	 * @param fnList
	 * @return
	 */
	protected String[] listToArray(List fnList) {
		String[] FN = new String[fnList.size()];
		int i = 0;
		for (Iterator iter = fnList.iterator(); iter.hasNext();) {
			String str = (String) iter.next();

			FN[i] = str;
			i++;
		}

		return FN;
	}

	/**
	 * 得到
	 * 
	 * @param object
	 * @return
	 */
	protected String getString(Object object) {

		try {
			if (object != null && !isNullOrSpace(((String) object))) {
				return (String) object;
			} else {
				return "";
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "";
		}

	}

	//本函数的功能是将传入的路径添加“\\”,如果结尾不是“\\”，则要添加“\\”
	public static String getPath(String Path) {
		StringBuffer sf = new StringBuffer(Path); //接收输入的路径
	
		if(isWin()){
		if (!Path.endsWith("\\")) {
			sf.append("\\");
		}
		}
		else if
			(Path.endsWith("\\")){
			int startIndex = sf.indexOf("\\");
			sf.replace(startIndex,startIndex+1,"");
		}
		else if(!Path.endsWith("/")){
		sf.append("/");	
		}
		createDir(Path); //如果该目录不存在，则创建

		return sf.toString();

	}

	/**
	 * 得到当前程序所在的路径
	 * 
	 * @return
	 */
	public String getDir(HttpServletRequest request) {
		return getPath(request.getSession().getServletContext()
				.getRealPath("/"));
	}

	/**
	 * 得到当前程序模板所在的路径
	 * 
	 * @return
	 */
	public String getTempletDir(HttpServletRequest request) {
		return getPath(getDir(request) + TEMPLET);
	}

	/**
	 * 得到当前用户保存文件所在的路径
	 * 
	 * @return
	 */
	public String getUserDir(HttpServletRequest request) {
		String path = getPath(getDir(request) + getXgr(request));
		createDir(path); //如果指定的路径不存在，则创建
		return path;
	}

	/**
	 * 返回带有后缀的Excel的名字
	 * 
	 * @param excelName
	 * @return
	 */
	public static String getExcelName(String excelName) {

		return excelName + suffixName;
	}

	/*以下部分代码用户生成下拉列表List*/
    /**
     * 生成上报索引下拉列表需要的LabelValueBean
     * */


	/**
	 * 清除Session的信息，并放入需要的对象
	 * 
	 * @param request
	 * @param session
	 */
	protected void sessionInfo(HttpServletRequest request, HttpSession session) {
		//清除Session中存有的信息
		session.removeAttribute(Constants.SEL_IDS); //清除选中的ID的数组
//已经不存在jbxx这个概念了。
		session.removeAttribute("jbxx"); //从session中删除jbxx

		//getJbxxFromReq(request); //得到基本信息，然后把基本信息放入Session中

	}

	public static Long strToLong(String s) {
		try{
			long l = Long.parseLong(s);
			return new Long(l);			
		}catch(NumberFormatException e){
			return null;
		}
	}

	public static Long getLongValue(HttpServletRequest request, String fieldName){
		Long result;
		String s;
		
		s = request.getParameter(fieldName + "id");
		result = strToLong(s);
		if (result == null){
			s = request.getParameter(fieldName);
			result = strToLong(s);
		}
		
		return result;
	}
	/**
	 * 删除文件
	 * @param fn
	 */
	public void delFile(String[] FileNames, String Path) {
		for (int i = 0; i < FileNames.length; i++) {
			System.out.println("添加文件: " + FileNames[i]);

			String FileName = Path + FileNames[i]; //文件全名（带路径）
			
			File f = new File(FileName);
			
			f.delete();
		}
		
	}
 
    public ActionForward  do_SearchError(ActionMapping mapping){
        return mapping.findForward("newModify");
    
    }
     
    private static boolean isWin() {
    	  String OS = System.getProperty("os.name").toLowerCase();
    	  if(OS.indexOf("windows")>-1)
    		  return true;
    	  else return false;
    }
    /**
     * 该方法用于从ValueListInfo中获取需要的KEY-VALUE对并组合成为对应的List对象.
     * */
    protected List changeSql(Map whereClause, String queryReturn, String sqlreturn) {
        List mapList = new ArrayList();
        String a= new String();
        for (int i = 0, end = 0, start =queryReturn.toString().indexOf('{'); ((start = queryReturn.toString().indexOf('{', end)) >= 0); i++)
        {
            end = queryReturn.toString().indexOf('}', start);
            String key = queryReturn.substring(start + 1, end);
            Object value = whereClause.get(key);
            if (value instanceof String && (((String) value).length() == 0)){
                value = null;
            }
            if(value !=null){
            a = String.valueOf(value);
            mapList.add(a);
            }
        }
        return mapList;
    }
    /**
     * 用于获取合法SQL
     * @param sqlReturn
     * @param conditionList
     * */
    protected String getSQLValid(String sqlReturn,List conditionList){

    	StringBuffer sqlValid = new StringBuffer(sqlReturn);
        int index=0;
        while(sqlValid.indexOf("?")>0){
            sqlValid.replace(sqlValid.indexOf("?"),sqlValid.indexOf("?")+1,(String)conditionList.get(index));
            index++;
        }
        return sqlValid.toString();
    }


}   
	



