package org.appfuse.webapp.action;
import javax.servlet.http.HttpSession;

import org.appfuse.model.Jbxx;
import org.appfuse.service.JbxxManager;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
//import net.mlw.vlh.web.ValueListRequestUtil;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import java.util.HashMap;
import java.util.Map;
import org.appfuse.service.XshdxsjlManager;
import org.appfuse.webapp.util.SearchMap;

import org.springframework.beans.BeansException;

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
import org.appfuse.model.Xshdxsjl;

import org.appfuse.webapp.form.XshdxsjlForm;

/**
 * Action class to handle CRUD on a Xshdxsjl object
 *
 * @struts.action name="xshdxsjlForm" path="/xshdxsjls" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="xshdxsjlForm" path="/editXshdxsjl" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="xshdxsjlForm" path="/saveXshdxsjl" scope="request"
 *  validate="true" parameter="method" input="edit"
 * 
 * @struts.action-forward name="edit" path="/WEB-INF/pages/xshdxsjlForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/xshdxsjlList.jsp"
 * @struts.action-forward name="find" path="/WEB-INF/pages/xshdxsjlFind.jsp"
 * @struts.action-forward name="view" path="/WEB-INF/pages/xshdxsjlView.jsp"
 */
public final class XshdxsjlAction extends BaseAction {
   	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		XshdxsjlForm xshdxsjlForm = (XshdxsjlForm) form;

		resetXshdxsjl(xshdxsjlForm); //FormBean复位

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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = null;

		XshdxsjlManager mgr = (XshdxsjlManager) getBean("xshdxsjlManager");

		ActionMessages messages = new ActionMessages();

		XshdxsjlForm xshdxsjlForm = (XshdxsjlForm) form;

		return delReturn(mapping, form, request, response, mgr, messages);

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param mgr
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	private ActionForward delReturn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, XshdxsjlManager mgr, ActionMessages messages) throws Exception {
		//获取删除的来源，如果是"del_many"则是从list中来的直接批量删除
		//如果为del_many_one,则是批量修改中的单条修改删除,或者批量修改中的查看时候的删除
		//如果为del,则为单条查看,或者单条修改删除
		String type = getType(request);

		if ("del_many".equals(type)) { //如果是多条删除
			return delMany(mapping, form, request, response, mgr, messages,
					type);
		} else { //如果单条删除
			return delOne(mapping, form, request, response, mgr, messages, type);

		}
	}

	/**
	 * 删除多条
	 * 
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
			XshdxsjlManager mgr, ActionMessages messages, String type)
			throws Exception {
		//防止重复提交
		if (!isTokenValid(request)) {
			resetXshdxsjl((XshdxsjlForm) form);
			saveToken(request);
		} else {
			resetToken(request);
			String[] sel_ids = request.getParameterValues(Constants.IDS);

			if (sel_ids != null) { //如果是删除多条，查询结果
				for (int i = 0; i < sel_ids.length; i++) {
					String id = sel_ids[i];
					try {
						mgr.removeXshdxsjl(id); //删除一条记录
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
	 * 
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
			XshdxsjlManager mgr, ActionMessages messages, String type)
			throws Exception {
		HttpSession session = request.getSession(); //得到Session，从Session中查找是否有未处理的id
		String id = null;
		//防止重复提交
		if (!isTokenValid(request)) {
			resetXshdxsjl((XshdxsjlForm) form);//如果已经执行过了,则跳入本部分
			saveToken(request);
		} else {
			resetToken(request);
			id = request.getParameter("id");
			if (!isNullOrSpace(id)) {
				try {
					mgr.removeXshdxsjl(id); //删除记录
				} catch (RuntimeException e) {
					e.printStackTrace();
					getError(session, e, "error.xshdxsjl.deleted", id);
				}
			}
		}

		if (Constants.DEL.equals(type)) { //如果是删除普通的单条记录,跳转到查询结果页面
			saveMsg(request, messages, "xshdxsjl.deleted");
			return search(mapping, form, request, response);
		} else { //如果是批量修改的修改或者查看页面的单条删除
			return delEditOrView(mapping, form, request, response, messages,
					session, id);

		}

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param messages
	 * @param session
	 * @param id
	 * @return
	 * @throws Exception
	 */
	private ActionForward delEditOrView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages, HttpSession session, String id)
			throws Exception {
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //获取Session中的批量选中的id的数组

		sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //SEL_IDS的值是在list页面进行批量修改时提交的，当用户选择了一个或多个ID，并点击“修改”按钮后，则程序将这些值写入SEL_IDS中。

		int pos = ((Integer) session.getAttribute(Constants.POS)).intValue();
		try {
			if (pos >= sel_ids.length - 1) { //如果为最后一条记录
				//			if (sel_ids.length > 1) {
				//因为是最后一条记录，并且数组的长度>1，所以，pos肯定不是第一条，即pos > 0
				decPos(session, pos); //将pos减1
				//			} else { //如果数组的长度为1，则最后一条也是第一条
				//				decPos(session, pos); //将pos减1
				//				return goToEdit(mapping, form, request, response, messages);
				// //调转到Search方法
				//			}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return search(mapping, form, request, response);
		}

		//如果next = "f",则是从list中过来的,否则，从jbxxlist过来的
		if ((request.getParameter("next") != null && request.getParameter(
				"next").equals("f"))) {
			sel_ids = delID(id, session, sel_ids);//删除SEL_IDS数组中的当前的id
		}

		//获取删除后的上一条、下一条的图标
		return getDelImgState(mapping, form, request, response, messages,
				session, sel_ids);
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
		XshdxsjlForm xshdxsjlForm = new XshdxsjlForm();
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
		String xshdxsjllist = Constants.XSHDXSJL_LIST;
		String xshdxsjlid = Constants.XSHDXSJL_ID;
		return do_find(mapping, form, request, xshdxsjllist, xshdxsjlid);
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
		String xshdxsjllist = Constants.XSHDXSJL_LIST;
		String xshdxsjlid = Constants.XSHDXSJL_ID;
		return do_Search(mapping, request, xshdxsjllist, xshdxsjlid);
	}

	/**
	 * edit方法，如果是新建，则进入一个空form， 否则，进入修改form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		//用于区分对两类id的查找
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}

		//防止重复提交
		saveToken(request);

		String type = getType(request);

		String id = null;

		String img_state = null;
		XshdxsjlForm xshdxsjlForm = (XshdxsjlForm) form;
		//判断是新建还是修改
		boolean isNew = ("".equals(xshdxsjlForm.getId()) || xshdxsjlForm.getId() == null);

		if (isNew) { //当选择新建时，删除图标为灰色，功能不可使用
			//请出Session中的SEL_IDS
			session.removeAttribute(Constants.SEL_IDS);
			img_state = Constants.NONE; //当选择新建时，没有上一条、下一条图标
			request.setAttribute(Constants.IMG_STATE, img_state);
			return mapping.findForward("edit");
		} else {
			//1.从每行的修改图标来的修改，传入id,getParameter();
			//2.从View页面来的修改,传入id,getParameter();
			//3.批量修改，getAttribute(POS);
			if (type.equals(Constants.EDIT_MANY)
					|| type.equals(Constants.DEL_MANY)
					|| type.equals(Constants.DEl_MANY_ONE)) { //如果从LIST传过来的，则肯定是批量修改
				String[] sel_ids = (String[]) session
						.getAttribute(Constants.SEL_IDS); //获取id的数组

				Integer pos = (Integer) session.getAttribute(Constants.POS); //获取当前位置
				if (sel_ids.length > 0 && pos.intValue() <= sel_ids.length) {
					id = sel_ids[pos.intValue()]; //当前id
				} else {
					return search(mapping, form, request, response);
				}

			} else {
				//如果从每行的图标传过来
				//如果是从view页面转过来的
				img_state = Constants.NONE;
				request.setAttribute(Constants.IMG_STATE, img_state);

				id = request.getParameter("id"); //首先尝试从request中获取id
			}

			String module = request.getParameter("module");
			if (!isNullOrSpace(module)
					&& (request.getParameter("next") == null || (!request
							.getParameter("next").equals("f")))) {
				id = getId(session, id, request);
				JbxxManager mgr = (JbxxManager) getBean("jbxxManager");

				try {
					Jbxx jbxx = mgr.getJbxx(id);

					request.setAttribute("jbxx", jbxx);
					request.setAttribute(Constants.PID, id);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}

				String from = request.getParameter("from");
				if (!isNullOrSpace(from) && from.equals("del_many_one")) {
					resetXshdxsjl(xshdxsjlForm); //将xshdxsjlForm清空
				}
			} else {
				Xshdxsjl xshdxsjl; //按照id进行查询
				try {
					XshdxsjlManager mgr = (XshdxsjlManager) getBean("xshdxsjlManager");
					xshdxsjl = mgr.getXshdxsjl(id);
					xshdxsjlForm = (XshdxsjlForm) convert(xshdxsjl);
					request.setAttribute(Constants.PID, xshdxsjl.getJbxxid()); //将当前的pid放入request中
				} catch (Exception e) {
					e.printStackTrace();
					return search(mapping, form, request, response);
				}

			}

			updateFormBean(mapping, request, xshdxsjlForm);
			if (type.equals(Constants.VIEW)) { //如果是从VIEW来的
				return mapping.findForward("view");
			} else { //如果是从EDIT来的
				return mapping.findForward("edit");
			}

		}

	}

	/**
	 * @return
	 */
	private String getId(HttpSession session, String id,
			HttpServletRequest request) {
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //获取Session中的批量选中的id的数组
		Integer i = (Integer) session.getAttribute(Constants.POS);
		int pos = 0;
		if (i != null) {
			pos = i.intValue();
		}

		//获取删除后的上一条、下一条的图标
		String img_state = getImgState(pos, sel_ids); //获取上一条、下一条图标的状态

		request.setAttribute(Constants.IMG_STATE, img_state);
		if (sel_ids != null) {
			return sel_ids[pos];
		} else {
			return request.getParameter("id");
		}

	}

	/**
	 * 执行search
	 * 
	 * @param mapping
	 * @param request
	 * @param xshdxsjllist
	 * @param xshdxsjlid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_Search(ActionMapping mapping,
			HttpServletRequest request, String xshdxsjllist, String xshdxsjlid)
			throws BeansException {
		String type = getType(request);

		HttpSession session = request.getSession();

		//得到查询时的form信息
		XshdxsjlForm xshdxsjl;
		try {
			xshdxsjl = (XshdxsjlForm) session.getAttribute("form");
		} catch (RuntimeException e) {
			e.printStackTrace();
			xshdxsjl = null;
		}

		//得到当前数组
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS);

		//得到ValueList相关的HandlerHelper
		ValueListHandlerHelper vlHelper = getValueListHelper();

		ValueListHandler vlh = getValueListHandler();

		Map map = new HashMap();

		//从valueListInfo中得到当前页码号！
		ValueListInfo oldInfo = vlHelper.getValueListInfo(request,
				Constants.XSHDXSJL_ID);
		//获取从第几页跳转来的
		int pageno = oldInfo.getPagingPage();

		map = SearchMap.getXshdxsjlMap(request, xshdxsjl); //得到map

		ValueListInfo info = new ValueListInfo(map);
		//设置显示到第几页
		if (!(Constants.DEL_MANY.equals(type) || (Constants.DEL.equals(type) || Constants.DEl_MANY_ONE
				.equals(type)))) {
			info.setPagingPage(pageno);
		}

		ValueList valueList = vlh.getValueList(xshdxsjllist, info);

		vlHelper.backupAndSet(request, valueList, xshdxsjllist, xshdxsjlid);

		//解决重复提交的问题
		saveToken(request);
		return mapping.findForward("list");
	}

	/**
	 * 保存方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		XshdxsjlForm xshdxsjlForm = (XshdxsjlForm) form;
		//此处得到参数用于区分两类list页面.
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}

		//防止重复提交
		if (!isTokenValid(request)) {
			resetXshdxsjl((XshdxsjlForm) form);
			saveToken(request);
			return search(mapping, form, request, response);
		}

		resetToken(request);
		saveToken(request);

		//得到修改人和修改时间
		xshdxsjlForm.setXgr(getXgr(request));
		xshdxsjlForm.setXgsj(NowDate.getNowStrIsNullOrEmpty(xshdxsjlForm.getXgsj()));//打印修改时间。

		XshdxsjlManager mgr = (XshdxsjlManager) getBean("xshdxsjlManager");
		Xshdxsjl xshdxsjl = (Xshdxsjl) convert(xshdxsjlForm);

		HttpSession session = request.getSession();

		//设置上一条、下一条的图标
		String img_state = getUptImgState(request, session);
		//将图标状态存入request中
		request.setAttribute(Constants.IMG_STATE, img_state);

		ActionMessages messages = new ActionMessages();

		try {
			String id = mgr.saveXshdxsjl(xshdxsjl); //保存信息
			xshdxsjlForm.setId(id);
			//写入保存成功信息
			saveMsg(request, messages, "xshdxsjl.saved");

			return mapping.findForward("view");
		} catch (RuntimeException e) {
			e.printStackTrace();
			//写入保存失败信息
			//			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			//					"error.xshdxsjl.saved"));
			//			saveMessages(session, messages);

			saveMsg(request, messages, "error.xshdxsjl.saved");
			request.setAttribute("pid", xshdxsjlForm.getJbxxid()); //将当前的id写入request的Attribute中
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
	public ActionForward batch_ChangeID(ActionMapping mapping,
			HttpServletRequest request, ActionForm form,
			HttpServletResponse response, HttpSession session, int i)
			throws Exception {
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //从Session中获取选择的id的数组
		Integer pos = new Integer(i); //当前位置的索引，Session中存储的id数组的当前记录位置

		session.setAttribute(Constants.POS, pos); //把当前位置记录到session中

		request.setAttribute("isnext", "yes");

		try {
			//获取是否是单击“下一步”来的
			String id = sel_ids[i]; //获取当前id
			getNext(mapping, request, id);
		} catch (Exception e) {
			e.printStackTrace();
			return search(mapping, form, request, response);
		}

		setImgState(request, i, sel_ids);

		return mapping.findForward("edit");
	}

	/**
	 * 将xshdxsjlForm所有字段复位
	 * 
	 * @param xshdxsjlForm
	 *  
	 */
	private void resetXshdxsjl(XshdxsjlForm xshdxsjlForm) {
		Xshdxsjl xshdxsjl = new Xshdxsjl();
		//此处适用了Apache的BeanUtils类的copyProperties方法,该方法第一个参数是目标对象，后面的参数是源对象
		try {
			BeanUtils.copyProperties(xshdxsjlForm, xshdxsjl);
		} catch (IllegalAccessException e1) {
			log.debug("resetXshdxsjl 出错-IllegalAccessException");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			log.debug("resetXshdxsjl 出错-InvocationTargetException");
			e1.printStackTrace();
		}
	}

	/**
	 * 执行find方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param xshdxsjllist
	 * @param xshdxsjlid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, String xshdxsjllist, String xshdxsjlid)
			throws BeansException {
		HttpSession session = request.getSession();
		//清除Session中存有的信息
		session.removeAttribute(Constants.SEL_IDS);
		String type = getType(request);

		XshdxsjlForm xshdxsjl = (XshdxsjlForm) form;

		ValueListHandler vlh = getValueListHandler();

		Map map = new HashMap();

		map = SearchMap.getXshdxsjlMap(request, xshdxsjl); //得到map //getMap(request, xshdxsjl);
		// //得到条件Map

		ValueListHandlerHelper vlHelper = getValueListHelper();
		ValueList valueList = vlh.getValueList(xshdxsjllist, new ValueListInfo(map));
		vlHelper.backupAndSet(request, valueList, xshdxsjllist, xshdxsjlid);

		//导出特定参数,从而完成Excel文件
		String excelFileName = realPath + "xshdxsjl查询结果.xls"; //注意：要修改该文件名
		String listname = xshdxsjllist;

		setExcelInfo(request, valueList, excelFileName, listname);
		return mapping.findForward("list");
	}

	/**
	 * 如果未指定method，则调用该方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return gotoFind(mapping, form, request, response);
	}

	/**
	 * 获取在修改状态下的上一条、下一条图标是否显示
	 * 
	 * @param request
	 * @return
	 */
	public String getUptImgState(HttpServletRequest request, HttpSession session) {
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

	//编辑下一条记录
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() + 1; //得到当前id的pos的值

		return batch_ChangeID(mapping, request, form, response, session, i);
	}

	//编辑上一条记录！
	public ActionForward prior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() - 1;

		return batch_ChangeID(mapping, request, form, response, session, i);
	}

	/**
	 * 跳转到Edit方法，跳转前，做一些处理
	 * 
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
		//		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		//				module + ".deleted"));
		//
		//		saveMessages(request.getSession(), messages);

		saveMsg(request, messages, "xshdxsjl.deleted");

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
	public ActionForward getDelImgState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages, HttpSession session, String[] sel_ids)
			throws Exception {

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
	 * 
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
			int i) throws Exception {

		//然后将Session中的POS的位置更新为当前的位置.
		session.setAttribute(Constants.POS, new Integer(i));

		setImgState(request, i, sel_ids);

		//跳转到指定id的修改页面
		return goToEdit(mapping, form, request, response, messages);
	}

	/**
	 * 跳转到search方法，跳转前，做一些处理
	 * 
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
		//		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		//				"xshdxsjl.deleted"));
		//
		//		saveMessages(request.getSession(), messages);

		saveMsg(request, messages, "xshdxsjl.deleted");

		return search(mapping, form, request, response);
	}

	public ActionForward edit_many(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request); //防止重复提交
		String type = getType(request); //得到type

		String id = null;

		String img_state = null; //上一条、下一条图标的显示标志
		Integer pos = new Integer(0); //id数组的位置的初始化

		//取得选择的id的数组，并将其连同当前id存入session中
		HttpSession session = request.getSession();

		//取得多条记录id数组！
		String[] sel_ids = request.getParameterValues(Constants.IDS);//获取提交的id的数组

		//此处得到参数用于区分两类list页面.
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
			if (sel_ids == null) {
				sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //取得选择的ID数组
				pos = (Integer) session.getAttribute(Constants.POS); //取得数组的位置
			}
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
				request.setAttribute("islist", "yes");
			}
		}

		if (id == null) { //如果用户没选择id,同时浏览器关闭了javascript的功能
			return search(mapping, form, request, response);
		} else {
			try {
				//确定是否是点击“下一步”来的
				getNext(mapping, request, id);
			} catch (Exception e) {
				e.printStackTrace();
				return search(mapping, form, request, response);
			}
			//确定显示上一条、下一条的状态
			img_state = getImgState(pos.intValue(), sel_ids); //获取上一条下一条图标的显示状态

			request.setAttribute(Constants.IMG_STATE, img_state); //将上一条、下一条的状态写入request中

			return mapping.findForward("edit"); //经过这里的跳转，进入edit方法。
		}
	}

	/**
	 * @param request
	 * @param xshdxsjlForm
	 * @param id
	 * @return
	 * @throws Exception
	 *             ActionMapping mapping, ActionForm form, HttpServletRequest
	 *             request, HttpServletResponse response
	 */
	private void getNext(ActionMapping mapping, HttpServletRequest request,
			String id) throws Exception {
		XshdxsjlForm xshdxsjlForm = null;
		String module = request.getParameter("module");
		//用于区分对两类id的查找
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}
		String islist = (String) request.getAttribute("islist"); //是否是批量修改过来的
		String isjbxxlist = request.getParameter("isjbxxlist");
		String isnext = (String) request.getAttribute("isnext"); //下一步
		if (!isNullOrSpace(module)
				&& (request.getParameter("next") == null || (!request
						.getParameter("next").equals("f")))
				&& ((!isNullOrSpace(islist) && !islist.equals("yes"))
						|| (!isNullOrSpace(isnext) && isnext.equals("yes")) || (!isNullOrSpace(isjbxxlist) && isjbxxlist
						.equals("yes")))) { // && "add".equals(module)
			JbxxManager mgr = (JbxxManager) getBean("jbxxManager");
			Jbxx jbxx = mgr.getJbxx(id);
			request.setAttribute("jbxx", jbxx);
			request.setAttribute(Constants.PID, id);
		} else {
			XshdxsjlManager mgr = (XshdxsjlManager) getBean("xshdxsjlManager");
			Xshdxsjl xshdxsjl = mgr.getXshdxsjl(id); //按照id进行查询
			xshdxsjlForm = (XshdxsjlForm) convert(xshdxsjl);
			request.setAttribute(Constants.PID, xshdxsjl.getJbxxid()); //将当前的pid放入request中
		}
		updateFormBean(mapping, request, xshdxsjlForm);
	}

}