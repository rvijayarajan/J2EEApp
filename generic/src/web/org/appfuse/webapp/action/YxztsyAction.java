package org.appfuse.webapp.action;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
//import net.mlw.vlh.web.ValueListRequestUtil;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import java.util.HashMap;
import java.util.Map;
import org.appfuse.service.YxztsyManager;
import org.appfuse.webapp.util.SearchMap;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.appfuse.webapp.util.NowDate;

import org.appfuse.Constants;
import org.appfuse.model.Yxztsy;

import org.appfuse.webapp.form.YxztsyForm;

/**
 * Action class to handle CRUD on a Yxztsy object
 *
 * @struts.action name="yxztsyForm" path="/yxztsys" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="yxztsyForm" path="/editYxztsy" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="yxztsyForm" path="/saveYxztsy" scope="request"
 *  validate="true" parameter="method" input="edit"
 * 
 * @struts.action-forward name="edit" path="/WEB-INF/pages/yxztsyForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/yxztsyList.jsp"
 * @struts.action-forward name="find" path="/WEB-INF/pages/yxztsyFind.jsp"
 * @struts.action-forward name="view" path="/WEB-INF/pages/yxztsyView.jsp"
 */
public final class YxztsyAction extends BaseAction {

    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
	 YxztsyForm yxztsyForm = (YxztsyForm) form;

	 resetYxztsy(yxztsyForm); //FormBean复位

        return gotoFind(mapping, form, request, response); //进入查询页面
    }
         /**
	 * 删除方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
		HttpSession session = null;

		YxztsyManager mgr = (YxztsyManager) getBean("yxztsyManager");

		ActionMessages messages = new ActionMessages();

		YxztsyForm yxztsyForm = (YxztsyForm) form;

		String id = null;
		//获取删除的来源，如果是"del_many"则是从list中来的直接批量删除
		//如果为del_many_one,则是批量修改中的单条修改删除,或者批量修改中的查看时候的删除
		//如果为del,则为单条查看,或者单条修改删除
		String type = getType(request);

		if ("del_many".equals(type)) { //如果是多条删除
			return delMany(mapping, form, request, response, mgr, messages,
					type);

		} else { //如果单条删除
			return delOne(mapping, form, request, response, mgr,
					messages, type);

		}

    }
	/**
	 * 删除多条
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param mgr
	 * @param messages
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private ActionForward delMany(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			YxztsyManager mgr, ActionMessages messages, String type)
			throws Exception {
		//防止重复提交
		if (!isTokenValid(request)) {
			resetYxztsy((YxztsyForm)form);
			saveToken(request);
		} else {
			resetToken(request);
			String[] sel_ids = request.getParameterValues(Constants.IDS);

			if (sel_ids != null) { //如果是删除多条，查询结果
				for (int i = 0; i < sel_ids.length; i++) {
					String id = sel_ids[i];
					try{
						mgr.removeYxztsy(id); //删除一条记录
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return goToSearch(mapping, form, request, response, messages); //调转到Search方法
	}

	/**
	 * 删除一条记录
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param sel_ids
	 * @param mgr
	 * @param messages
	 * @param type
	 * @return
	 * @throws Exception
	 */
	private ActionForward delOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			YxztsyManager mgr, ActionMessages messages,
			String type) throws Exception {
		HttpSession session = request.getSession(); //得到Session，从Session中查找是否有未处理的id
		String id = null;
		//防止重复提交
		if (!isTokenValid(request)) {  
			resetYxztsy((YxztsyForm)form);//如果已经执行过了,则跳入本部分
			saveToken(request);
		} else {
			resetToken(request);
			id = request.getParameter("id");
			try{
				mgr.removeYxztsy(id);  //删除记录
			} catch (RuntimeException e) {
				getError(session, e, "error.yxztsy.deleted", id);
			}
		}

		if (Constants.DEL.equals(type)) { //如果是删除普通的单条记录,跳转到查询结果页面
			saveMsg(request, messages, "yxztsy.deleted");
			return search(mapping, form, request, response);
		} else {  //如果是批量修改的修改或者查看页面的单条删除
			String[] sel_ids = (String[])session.getAttribute(Constants.SEL_IDS);  //获取Session中的批量选中的id的数组

			int pos = ((Integer) session.getAttribute(Constants.POS))
					.intValue(); 
			if (sel_ids != null && pos >= sel_ids.length - 1) { //如果为最后一条记录
				decPos(session, pos);   //将pos减1
			}
			sel_ids = delID(id, session, sel_ids);//删除SEL_IDS数组中的当前的id

			//获取删除后的上一条、下一条的图标
			return getDelImgState(mapping, form, request, response, messages,
					session, sel_ids);

		}

	}
	/**
	 * 进入查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoFind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		YxztsyForm yxztsyForm = new YxztsyForm();
		return mapping.findForward("find");
	}

	/**
	 * 根据条件返回查询结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String yxztsylist = Constants.YXZTSY_LIST;
		String yxztsyid = Constants.YXZTSY_ID;
		return do_find(mapping, form, request, yxztsylist, yxztsyid);
	}

	/**
	 * 根据条件返回查询结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String yxztsylist = Constants.YXZTSY_LIST;
		String yxztsyid = Constants.YXZTSY_ID;
		return do_Search(mapping, request, yxztsylist, yxztsyid);
	}

	/**
	 * edit方法，如果是新建，则进入一个空form，
	 * 否则，进入修改form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {

        //防止重复提交
		saveToken(request);

		String type = getType(request);

		HttpSession session = request.getSession();
		String id = null;

		String img_state = null;
		YxztsyForm yxztsyForm = (YxztsyForm) form;

		boolean isNew = ("".equals(yxztsyForm.getId()) || yxztsyForm.getId() == null);

		if (isNew) { //当选择新建时，删除图标为灰色，功能不可使用
			//清楚Session中的SEL_IDS
			session.removeAttribute(Constants.SEL_IDS);

			img_state = Constants.NONE; //当选择新建时，没有上一条、下一条图标

			request.setAttribute(Constants.IMG_STATE, img_state);
			return mapping.findForward("edit");
		} else {
			//1.从每行的修改图标来的修改，传入id,getParameter();
			//2.从View页面来的修改,传入id,getParameter();
			//3.批量修改，getAttribute(POS);
			if (type.equals(Constants.EDIT_MANY)
					|| type.equals(Constants.DEL_MANY)||type.equals(Constants.DEl_MANY_ONE)) { //如果从LIST传过来的，则肯定是批量修改
				String[] sel_ids = (String[]) session
						.getAttribute(Constants.SEL_IDS); //获取id的数组

				Integer pos = (Integer) session.getAttribute(Constants.POS); //获取当前位置
				if (sel_ids.length > 0 && pos.intValue() <= sel_ids.length){
					id = sel_ids[pos.intValue()]; //当前id
				}else{
					return search(mapping, form, request, response);
				}

			} else {
				//如果从每行的图标传过来
				//如果是从view页面转过来的
				img_state = Constants.NONE;
				request.setAttribute(Constants.IMG_STATE, img_state);

				id = request.getParameter("id"); //首先尝试从request中获取id
			}

			YxztsyManager mgr = (YxztsyManager) getBean("yxztsyManager");
			 try {
                getYxztsy(mapping, request, id, mgr);
            } catch (RuntimeException e) {
                getError(session, e, "error.yxztsy.selected", id);

                return search(mapping, form, request, response);
            }

			if (type.equals(Constants.VIEW)) { //如果是从VIEW来的
				return mapping.findForward("view");
			} else { //如果是从EDIT来的
				return mapping.findForward("edit");
			}

		}

    }

         /**
     * @param mapping
     * @param request
     * @param id
     * @param mgr
     * @throws Exception
     */
    private void getYxztsy(ActionMapping mapping, HttpServletRequest request, String id, YxztsyManager mgr) throws Exception {
        YxztsyForm yxztsyForm;
        Yxztsy yxztsy;
        yxztsy = mgr.getYxztsy(id);
        yxztsyForm = (YxztsyForm) convert(yxztsy);
        updateFormBean(mapping, request, yxztsyForm);

    }
        /**
	 * 执行search
	 * @param mapping
	 * @param request
	 * @param yxztsylist
	 * @param yxztsyid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_Search(ActionMapping mapping, HttpServletRequest request, String yxztsylist, String yxztsyid) throws BeansException {
		String type = getType(request);

		HttpSession session = request.getSession();

		//得到查询时的form信息
		YxztsyForm yxztsy ;
		try {
			yxztsy = (YxztsyForm) session.getAttribute("form");
		} catch (RuntimeException e) {
			e.printStackTrace();
			 yxztsy = null;
		}
		//得到ValueList相关的HandlerHelper
		ValueListHandlerHelper vlHelper = getValueListHelper();

		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);

		Map map = new HashMap();

		//从valueListInfo中得到当前页码号！
		ValueListInfo oldInfo = vlHelper.getValueListInfo(request,
				Constants.YXZTSY_ID);
		//获取从第几页跳转来的
		int pageno = oldInfo.getPagingPage();

		map = SearchMap.getYxztsyMap(request, yxztsy); //得到map

		ValueListInfo info = new ValueListInfo(map);
		//设置显示到第几页
		if (!(Constants.DEL_MANY.equals(type) || (Constants.DEL.equals(type) || Constants.DEl_MANY_ONE.equals(type)))) {
			info.setPagingPage(pageno);
		}

		ValueList valueList = vlh.getValueList(yxztsylist, info);
		vlHelper.backupAndSet(request, valueList, yxztsylist, yxztsyid);

		//解决重复提交的问题
		saveToken(request);

		return mapping.findForward("list");
	}

	/**
	 * 从list页面点击“修改”图标，调用该方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit_many(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request);  //防止重复提交
		String type = getType(request); //得到type

		String id = null;

		String img_state = null; //上一条、下一条图标的显示标志
		Integer pos = new Integer(0); //id数组的位置的初始化

		//取得选择的id的数组，并将其连同当前id存入session中
		HttpSession session = request.getSession();

		YxztsyForm yxztsyForm = (YxztsyForm) form;

		//取得多条记录id数组！
		String[] sel_ids = request.getParameterValues(Constants.IDS);//获取提交的id的数组

		if(sel_ids == null){
		    sel_ids = (String[])session.getAttribute(Constants.SEL_IDS); //取得选择的ID数组
		    pos = (Integer)session.getAttribute(Constants.POS);    //取得数组的位置
		}

		if (sel_ids == null) { //如果是多条批量修改，然后从form中点击“修改”按钮
			id = request.getParameter("id"); //
		} else {
			//如果提交的id为0个，则跳转到查询页面
			if (sel_ids.length == 0) {
				return goToSearch(mapping, form, request, response, null);
			} else {
				//将得到的id数组|当前记录在数组中的位置存入session
				session.setAttribute(Constants.SEL_IDS, sel_ids);
				session.setAttribute(Constants.POS, pos);
				id = sel_ids[pos.intValue()]; //取得当前索引的id，其实是第一条id，索引为0
			}
		}

		if (id == null) { //如果用户没选择id,同时浏览器关闭了javascript的功能
			return search(mapping, form, request, response);
		} else {
			YxztsyManager mgr = (YxztsyManager) getBean("yxztsyManager");
            try {
                getYxztsy(mapping, request, id, mgr); //更新FormBean
            } catch (RuntimeException e) {
                getError(session, e, "error.yxztsy.selected", id);

                return search(mapping, form, request, response);
            }
			setImgState(request, pos.intValue(), sel_ids);
			return mapping.findForward("edit"); //经过这里的跳转，进入edit方法。
		}
	}

	/**
	 * 保存方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
    throws Exception {
		ActionMessages messages = new ActionMessages();
		YxztsyForm yxztsyForm = (YxztsyForm) form;
		//防止重复提交
		if (!isTokenValid(request)) {
			resetYxztsy((YxztsyForm) form);
			saveToken(request);
			return search(mapping, form, request, response);
		} 
		resetToken(request);
		saveToken(request);

		//得到修改人
		yxztsyForm.setXgr(getXgr(request));
		yxztsyForm.setXgsj(NowDate.getNowStrIsNullOrEmpty(yxztsyForm.getXgsj()));//打印修改时间。

		YxztsyManager mgr = (YxztsyManager) getBean("yxztsyManager");
		Yxztsy yxztsy = (Yxztsy) convert(yxztsyForm);

		String id = null;

		HttpSession session = request.getSession();

		String img_state = null;

		boolean isNew = ("".equals(yxztsyForm.getId()) || yxztsyForm.getId() == null);
		if (isNew) { //新建的时候，上一条、下一条的图标都不显示
			img_state = Constants.NONE;
		} else { //修改的时候，要判断上一条、下一条图标是否显示
			img_state = getUptImgState(request, session);
		}

		request.setAttribute(Constants.IMG_STATE, img_state);

		try {
			id = mgr.saveYxztsy(yxztsy); //保存信息

			yxztsyForm.setId(id);

			saveMsg(request, messages, "yxztsy.saved");

			return mapping.findForward("view");
		} catch (RuntimeException e) {
			e.printStackTrace();
			saveMsg(request, messages, "error.yxztsy.saved");

			return mapping.findForward("edit");
		}
    }
	/**
	 * 批量翻页
	 * 
	 * @param mapping
	 * @param request
	 * @param session
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public ActionForward batch_ChangeID(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session, int i)
			throws Exception {
	    String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //从Session中获取选择的id的数组

		Integer pos = new Integer(i); //当前位置的索引，Session中存储的id数组的当前记录位置

		session.setAttribute(Constants.POS, pos); //把当前位置记录到session中

		String id;
		try {
            id = sel_ids[i]; //获取当前id
        } catch (RuntimeException e) {
            e.printStackTrace();
            return search(mapping, form, request, response);
        }

		YxztsyManager mgr = (YxztsyManager) getBean("yxztsyManager");
        try {
            getYxztsy(mapping, request, id, mgr);	
        } catch (RuntimeException e1) {
            getError(session, e1, "error.yxztsy.selected", id);
        }
		setImgState(request, i, sel_ids);

		return mapping.findForward("edit");
	}

    /**
	 * 将yxztsyForm所有字段复位
	 * @param yxztsyForm
	 * 
	 */
	private void resetYxztsy(YxztsyForm yxztsyForm) {
		Yxztsy yxztsy = new Yxztsy();
		//此处适用了Apache的BeanUtils类的copyProperties方法,该方法第一个参数是目标对象，后面的参数是源对象
		try {
			BeanUtils.copyProperties(yxztsyForm, yxztsy);
		} catch (IllegalAccessException e1) {
			log.debug("resetYxztsy 出错-IllegalAccessException");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			log.debug("resetYxztsy 出错-InvocationTargetException");
			e1.printStackTrace();
		}
	}

	/**
	 * 执行find方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param yxztsylist
	 * @param yxztsyid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_find(ActionMapping mapping, ActionForm form, HttpServletRequest request, String yxztsylist, String yxztsyid) throws BeansException {
		HttpSession session = request.getSession();
		//清除Session中存有的信息
		session.removeAttribute(Constants.SEL_IDS);
		String type = getType(request);

		YxztsyForm yxztsy = (YxztsyForm) form;
		//备份与字段相关的form信息
		session.setAttribute("form",yxztsy);

		//获取ValueList相关的东西
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);

		Map map = new HashMap();

		map = SearchMap.getYxztsyMap(request, yxztsy); //得到map //getMap(request, yxztsy); //得到条件Map

		ValueListHandlerHelper vlHelper = getValueListHelper();
		ValueList valueList = vlh.getValueList(yxztsylist, new ValueListInfo(
				map));
		vlHelper.backupAndSet(request, valueList, yxztsylist, yxztsyid);

		//导出特定参数,从而完成Excel文件
		String excelFileName = realPath + "yxztsy查询结果.xls"; //注意：要修改该文件名
		String listname = yxztsylist;

		setExcelInfo(request, valueList, excelFileName, listname);

		return mapping.findForward("list");
	}

	/**
	*如果未指定method，则调用该方法
	*/
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return gotoFind(mapping, form, request, response);
	}

	    //李铁峰添加的方法
	/**
	 * 获取在修改状态下的上一条、下一条图标是否显示
	 * 
	 * @param request
	 * @return
	 */
	public String getUptImgState(HttpServletRequest request,
			HttpSession session) {
		//如果从单条记录修改，然后保存，则不显示上一条、下一条图标

		//否则，要判断是否还有下一条，如果有，显示下一条图标，否则，不显示下一条图标
		//判断是否是第一条记录，如果是，则不显示上一条图标
		String img_state = null;
		String type = getType(request); //得到type

		if ("save".equals(type)) {
			img_state = Constants.NONE;
		} else {
			String[] sel_ids = (String[]) session
					.getAttribute(Constants.SEL_IDS);

			Integer pos = (Integer) session.getAttribute(Constants.POS);

			int i = pos.intValue();

			img_state = getImgState(i, sel_ids); //获取上一条、下一条的图标的状态
		}
		return img_state;
	}
	/**
	 * 获取from的值
	 * @param request
	 * @return
	 */
	/**
	public String getType(HttpServletRequest request) {
		String type = request.getParameter(Constants.FROM);

		if (type == null) {
			type = "";
		}

		return type;
	}
	*/

	//编辑下一条记录
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() + 1; //得到当前id的pos的值

		return batch_ChangeID(mapping, form, request, response, session, i);
	}

	//编辑上一条记录！
	public ActionForward prior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() - 1;

		return batch_ChangeID(mapping, form, request, response, session, i);
	}

	/**
	 * 跳转到Edit方法，跳转前，做一些处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages) throws Exception {
		saveMsg(request, messages, "yxztsy.deleted");

		return edit(mapping, form, request, response);
	}

	/**
	 * 判断删除后的情况： 如果有下一条，则进入下一条修改页面 如果没有下一条、有上一条，进入上一条的修改页面
	 * 如果没有上一条、没有下一条，进入查询结果页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param messages
	 * @param session
	 * @param sel_ids
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDelImgState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages,
			HttpSession session, String[] sel_ids) throws Exception {

		String id;
		if (sel_ids == null || sel_ids.length == 0) { //如果选择的ID的数组为null或者数组长度为0，则说明没有上一条、下一条记录了，则进入Search()方法。
			return goToSearch(mapping, form, request, response, messages);
		} else {
			String img_state = null; //记录图标的状态，显示上一页、下一页、或者都显示
			int pos = ((Integer) session.getAttribute(Constants.POS))
					.intValue(); //当前id在数组中的索引(位置),该位置在批量修改进入时写入为0,在进入单条修改页面后，则修改该数值。

			return com_edit(mapping, form, request, response, messages,
					session, sel_ids, pos);
		}
	}

	/**
	 * 通用的edit方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param messages
	 * @param session
	 * @param sel_ids
	 * @param pos
	 * @return
	 * @throws Exception
	 */
	public ActionForward com_edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages, HttpSession session, String[] sel_ids,
			int pos) throws Exception {
		String img_state;
		//然后将Session中的POS的位置更新为当前的位置.
		session.setAttribute(Constants.POS, new Integer(pos));

		img_state = getImgState(pos, sel_ids); //获取上一条、下一条图标的状态

		request.setAttribute(Constants.IMG_STATE, img_state);

		//跳转到指定id的修改页面
		return goToEdit(mapping, form, request, response, messages);
	}
	/**
	 * 跳转到search方法，跳转前，做一些处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages) throws Exception {
		saveMsg(request, messages, "yxztsy.deleted");

		return search(mapping, form, request, response);
	}

}

