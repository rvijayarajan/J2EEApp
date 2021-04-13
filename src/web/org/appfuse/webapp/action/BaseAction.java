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
    
	protected static final String TEMPLET = "templet"; //ģ��·��

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
		// �õ��ַ���method���ƶ�����method�д�ŵ��Ƿ�������
		String parameter = mapping.getParameter();

		// don't set keyName unless it's defined on the action-mapping
		// no keyName -> unspecified will be called
		String keyName = null;

		if (parameter != null) {
			// �ӱ���method��ȡ��������
			keyName = request.getParameter(parameter);
		}

		if ((keyName == null) || (keyName.length() == 0)) {
			for (int i = 0; i < rules.length; i++) {
				// apply the rules for automatically appending the method name
				// ��������·���е���Ϣ����ѡ��������
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

	//private static final String MemFolder = "Mem"; //��������ļ����ļ���

	private static final String Comma = ",";

	//���ص�ǰ����Աde �û�����
	protected static String getXgr(javax.servlet.http.HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute(Constants.USER_KEY);
		String username = user.getFirstName();
		return username;
	}

	/**
	 * �ж�һ���ַ����Ƿ�Ϊnull���ߡ��� 
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
	 * �������飬��ĳ�����е�ָ��id��ȥ��
	 * 
	 * @param String[]
	 *            cc
	 * @param String
	 *            ccid ��ǰ��¼��idֵ
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
	 * ��ȡfrom��ֵ
	 * 
	 * @param request
	 * @return
	 */
	public String getType(HttpServletRequest request) {
		//Constants.FROM = from ��¼���ص�������0���򷵻�listҳ�棬���򣬷�������
		String type = request.getParameter(Constants.FROM);

		if (type == null) {
			type = "";
		}

		return type;
	}

	/**
	 * ����ǰid�������е�����ֵ��1
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
	 * ɾ��Session�д洢������������ID�������еı�ɾ���ļ�¼��id
	 * 
	 * @param id
	 * @param session
	 * @param sel_ids
	 */
	public String[] delID(String id, HttpSession session, String[] sel_ids) {
		String[] sa = null;
		if (sel_ids != null) {
			sa = copyArray(sel_ids, id); //��ȡɾ���˱�ɾ���ļ�¼��id�������
			session.setAttribute(Constants.SEL_IDS, sa); //��ѡ�еĿ��Դ����id������д��Session
		}

		return sa;
	}

	/**
	 * ��ȡValueListHandleHelper
	 */
	public ValueListHandlerHelper getValueListHelper() {
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		return (ValueListHandlerHelper) context.getBean("valueListHelper",
				ValueListHandlerHelper.class);

	}

	
	/**
	 * ���ǰ���%
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
		//��ȡValueList��صĶ���
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);
		return vlh;
	}

	/** 
	 * ��ȡ��һ������һ��ͼ�����ʾ���
	 * 
	 * @param i
	 * @param sel_ids
	 * @return
	 */
	public String getImgState(int i, String[] sel_ids) {
		String img_state = null; //��һ������һ��ͼ�����ʾ��־
		//���ѡ���id������Ϊnull���߳���Ϊ0
		if (sel_ids == null || sel_ids.length == 0) {
			img_state = Constants.NONE;
		} else {
			if (i == 0) { //����ǵ�һ��
				if (sel_ids.length == 1) { //�����һ����¼����û����һ������һ��ͼ��
					img_state = Constants.NONE;
				} else { //����ж�����¼����϶�����һ����¼
					img_state = Constants.NEXT;
				}

			} else { //������ǵ�һ����¼
				if (i == sel_ids.length - 1) { //��������һ��,���ʱ���ǵ�һ������϶�����һ����¼
					img_state = Constants.PRIOR;
				} else { //����������һ������������Ҳ���ǵ�һ��,����������һ������һ������ʾ
					img_state = Constants.BOTH;
				}
			}
		}

		return img_state;
	}

	//5-19
	/**
	 * ���õ���Excel�Ĳ���
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

		request.setAttribute(PATH, getXgr(request));//�洢�ļ���·��
		request.setAttribute(EXCELNAME, excelFileName); //�洢���ɵ��ļ���
	}

	/**
	 * ����Excel
	 * 
	 * @param request
	 * @param bxlist
	 * @param valueList
	 */
	protected void expExcel(HttpServletRequest request, String bxlist,
			ValueList valueList, String excelFileName) {
		//�����ض�����,�Ӷ����Excel�ļ�
		String listname = bxlist;

		setExcelInfo(request, valueList, excelFileName, listname);
	}

	/**
	 * ������һ������һ����ͼ��״̬
	 * 
	 * @param request
	 * @param i
	 * @param sel_ids
	 */
	public void setImgState(HttpServletRequest request, int i, String[] sel_ids) {
		String img_state = getImgState(i, sel_ids); //��ȡ��һ������һ��ͼ��״̬

		request.setAttribute(Constants.IMG_STATE, img_state);
	}

	/**
	 * �õ���ѯһ����¼ʧ�ܵĴ�����ʾ��
	 * 
	 * @param session
	 * @param e
	 * @param hintMsg
	 *            ����Ϊ�������ʾ��Ϣ����id�������Ƶ�
	 */
	public void getError(HttpSession session, RuntimeException e,
			String errorKey, String hintMsg) {
		e.printStackTrace();
		ActionMessages messages = new ActionMessages();
		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorKey));
		saveMessages(session, messages);
	}

	/**
	 * �õ���ѯһ����¼����ʾ��
	 * 
	 * @param session
	 * @param e
	 * @param hintMsg
	 *            ����Ϊ�������ʾ��Ϣ����id�������Ƶ�
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
	//  ��ȡ��������ϵͳ����
	public static String getSysDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		return ReturnSapce(df.format(date));
	}

	//�������ֵ��null���򷵻ء���
	public static String ReturnSapce(String V) {
		return (V == null ? "" : (V.equals("null") ? "" : V.trim()));
	}

	//�������ֵ��null���򷵻ء���
	public static String ReturnBlankStringWhileNull(String V) {
		return (V == null ? "" : (V.trim().equals("null") ? "" : V.trim()));
	}

	//��������ǹ��õĴ�ӡ������Ϣ�ķ�����
	public static void PrintStr(String S) {
		System.out.println(S);
	}

//	//�ж��ַ������͵�ֵ�Ƿ���null�����ǡ���
//	public static boolean isNullOrSpace(String S) {
//		return (S == null || S.trim().equals(""));
//	}

	//���Ҫ�������ݿ������������ֶε��ַ�����ֵ
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

	//���Ҫ�������ݿ������������ֶε��ַ�����ֵ
	public static String getDateStrFN(String FN) {
		if (!isNullOrSpace(FN)) {
			return "to_date(" + FN + ",'yyyy-mm-dd')";
		} else {
			return null;
		}
	}

	//Ϊָ�����ַ����ӡ�"��
	public static String getQuotedStr(String S) {
		return "'" + S + "'";
	}

	//Ϊָ�����ַ���ǰ�ӡ�,��
	public static String getAddCommaStr(String S) {
		return Comma + S;
	}

	//�����ļ���
	public static boolean createDir(String DirName) {
		File f = new File(DirName);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}
	/**
	 * ���Ŀ¼�����Ŀ¼�����ڣ��򴴽���Ŀ¼
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

	//�õ���ǰ�·�
	public static String getMonth() {
		Date now = new Date();
		SimpleDateFormat mm = new SimpleDateFormat("M");
		return mm.format(now); //�·�
	}

	//�õ���ǰ���
	public static String getYear() {
		Date now = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy"); //���
		return y.format(now);
	}

	//�ж��������Ĳ���Ϊnull�򡰡����򷵻ش����Ĭ��ֵ
	public static String getDefaultValue(String Str, String DefaultValue) {
		if (isNullOrSpace(Str)) {
			return ReturnSapce(DefaultValue);
		} else {
			return Str;
		}
	}

	//��Unicode->GBK
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

	//Ϊ�ַ�����ӷ�����
	public static String getaddBracketStr(String str) {
		if (isNullOrSpace(str)) {
			return "";
		}
		return "[" + str + "]";
	}

	//�õ�Ӣ������
	public static String getYWXM(String x, String m) {
		String xm = ReturnSapce(m) + "." + ReturnSapce(x);
		if (xm.equals(".")) {
			return "";
		}
		return xm;
	}

	//����ȡ�����ݿ���ȡ�õ��������͵����ݵ�ǰ10λ
	public static String getDateFromStr(String str) {
		if (!isNullOrSpace(str)) {
			if (str.length() >= 10) {
				return str.substring(0, 10);
			}
			return "";
		}
		return "";

	}

	//�ж�һ�������Ƿ���������2����ֵ֮��(������),����ڸ÷�Χ���򷵻�true,���򷵻�false
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
	 * ��Listת��Ϊ����
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
	 * �õ�
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

	//�������Ĺ����ǽ������·����ӡ�\\��,�����β���ǡ�\\������Ҫ��ӡ�\\��
	public static String getPath(String Path) {
		StringBuffer sf = new StringBuffer(Path); //���������·��
	
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
		createDir(Path); //�����Ŀ¼�����ڣ��򴴽�

		return sf.toString();

	}

	/**
	 * �õ���ǰ�������ڵ�·��
	 * 
	 * @return
	 */
	public String getDir(HttpServletRequest request) {
		return getPath(request.getSession().getServletContext()
				.getRealPath("/"));
	}

	/**
	 * �õ���ǰ����ģ�����ڵ�·��
	 * 
	 * @return
	 */
	public String getTempletDir(HttpServletRequest request) {
		return getPath(getDir(request) + TEMPLET);
	}

	/**
	 * �õ���ǰ�û������ļ����ڵ�·��
	 * 
	 * @return
	 */
	public String getUserDir(HttpServletRequest request) {
		String path = getPath(getDir(request) + getXgr(request));
		createDir(path); //���ָ����·�������ڣ��򴴽�
		return path;
	}

	/**
	 * ���ش��к�׺��Excel������
	 * 
	 * @param excelName
	 * @return
	 */
	public static String getExcelName(String excelName) {

		return excelName + suffixName;
	}

	/*���²��ִ����û����������б�List*/
    /**
     * �����ϱ����������б���Ҫ��LabelValueBean
     * */


	/**
	 * ���Session����Ϣ����������Ҫ�Ķ���
	 * 
	 * @param request
	 * @param session
	 */
	protected void sessionInfo(HttpServletRequest request, HttpSession session) {
		//���Session�д��е���Ϣ
		session.removeAttribute(Constants.SEL_IDS); //���ѡ�е�ID������
//�Ѿ�������jbxx��������ˡ�
		session.removeAttribute("jbxx"); //��session��ɾ��jbxx

		//getJbxxFromReq(request); //�õ�������Ϣ��Ȼ��ѻ�����Ϣ����Session��

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
	 * ɾ���ļ�
	 * @param fn
	 */
	public void delFile(String[] FileNames, String Path) {
		for (int i = 0; i < FileNames.length; i++) {
			System.out.println("����ļ�: " + FileNames[i]);

			String FileName = Path + FileNames[i]; //�ļ�ȫ������·����
			
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
     * �÷������ڴ�ValueListInfo�л�ȡ��Ҫ��KEY-VALUE�Բ���ϳ�Ϊ��Ӧ��List����.
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
     * ���ڻ�ȡ�Ϸ�SQL
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
	



