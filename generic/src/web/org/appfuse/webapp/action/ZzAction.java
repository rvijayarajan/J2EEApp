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
import org.appfuse.service.ZzManager;
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
import org.appfuse.model.Zz;

import org.appfuse.webapp.form.ZzForm;

/**
 * Action class to handle CRUD on a Zz object
 *
 * @struts.action name="zzForm" path="/zzs" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="zzForm" path="/editZz" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="zzForm" path="/saveZz" scope="request"
 *  validate="true" parameter="method" input="edit"
 * 
 * @struts.action-forward name="edit" path="/WEB-INF/pages/zzForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/zzList.jsp"
 * @struts.action-forward name="find" path="/WEB-INF/pages/zzFind.jsp"
 * @struts.action-forward name="view" path="/WEB-INF/pages/zzView.jsp"
 */
public final class ZzAction extends BaseAction {
   	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ZzForm zzForm = (ZzForm) form;

		resetZz(zzForm); //FormBean��λ

		return gotoFind(mapping, form, request, response); //�����ѯҳ��
	}

	/**
	 * ɾ������
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

		ZzManager mgr = (ZzManager) getBean("zzManager");

		ActionMessages messages = new ActionMessages();

		ZzForm zzForm = (ZzForm) form;

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
	private ActionForward delReturn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, ZzManager mgr, ActionMessages messages) throws Exception {
		//��ȡɾ������Դ�������"del_many"���Ǵ�list������ֱ������ɾ��
		//���Ϊdel_many_one,���������޸��еĵ����޸�ɾ��,���������޸��еĲ鿴ʱ���ɾ��
		//���Ϊdel,��Ϊ�����鿴,���ߵ����޸�ɾ��
		String type = getType(request);

		if ("del_many".equals(type)) { //����Ƕ���ɾ��
			return delMany(mapping, form, request, response, mgr, messages,
					type);
		} else { //�������ɾ��
			return delOne(mapping, form, request, response, mgr, messages, type);

		}
	}

	/**
	 * ɾ������
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
			ZzManager mgr, ActionMessages messages, String type)
			throws Exception {
		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {
			resetZz((ZzForm) form);
			saveToken(request);
		} else {
			resetToken(request);
			String[] sel_ids = request.getParameterValues(Constants.IDS);

			if (sel_ids != null) { //�����ɾ����������ѯ���
				for (int i = 0; i < sel_ids.length; i++) {
					String id = sel_ids[i];
					try {
						mgr.removeZz(id); //ɾ��һ����¼
					} catch (RuntimeException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return goToSearch(mapping, form, request, response, messages); //��ת��Search����
	}

	/**
	 * ɾ��һ����¼
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
			ZzManager mgr, ActionMessages messages, String type)
			throws Exception {
		HttpSession session = request.getSession(); //�õ�Session����Session�в����Ƿ���δ�����id
		String id = null;
		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {
			resetZz((ZzForm) form);//����Ѿ�ִ�й���,�����뱾����
			saveToken(request);
		} else {
			resetToken(request);
			id = request.getParameter("id");
			if (!isNullOrSpace(id)) {
				try {
					mgr.removeZz(id); //ɾ����¼
				} catch (RuntimeException e) {
					e.printStackTrace();
					getError(session, e, "error.zz.deleted", id);
				}
			}
		}

		if (Constants.DEL.equals(type)) { //�����ɾ����ͨ�ĵ�����¼,��ת����ѯ���ҳ��
			saveMsg(request, messages, "zz.deleted");
			return search(mapping, form, request, response);
		} else { //����������޸ĵ��޸Ļ��߲鿴ҳ��ĵ���ɾ��
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
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //��ȡSession�е�����ѡ�е�id������

		sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //SEL_IDS��ֵ����listҳ����������޸�ʱ�ύ�ģ����û�ѡ����һ������ID����������޸ġ���ť���������Щֵд��SEL_IDS�С�

		int pos = ((Integer) session.getAttribute(Constants.POS)).intValue();
		try {
			if (pos >= sel_ids.length - 1) { //���Ϊ���һ����¼
				//			if (sel_ids.length > 1) {
				//��Ϊ�����һ����¼����������ĳ���>1�����ԣ�pos�϶����ǵ�һ������pos > 0
				decPos(session, pos); //��pos��1
				//			} else { //�������ĳ���Ϊ1�������һ��Ҳ�ǵ�һ��
				//				decPos(session, pos); //��pos��1
				//				return goToEdit(mapping, form, request, response, messages);
				// //��ת��Search����
				//			}
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return search(mapping, form, request, response);
		}

		//���next = "f",���Ǵ�list�й�����,���򣬴�jbxxlist������
		if ((request.getParameter("next") != null && request.getParameter(
				"next").equals("f"))) {
			sel_ids = delID(id, session, sel_ids);//ɾ��SEL_IDS�����еĵ�ǰ��id
		}

		//��ȡɾ�������һ������һ����ͼ��
		return getDelImgState(mapping, form, request, response, messages,
				session, sel_ids);
	}

	/**
	 * �����ѯҳ��
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
		ZzForm zzForm = new ZzForm();
		return mapping.findForward("find");
	}

	/**
	 * �����������ز�ѯ���
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
		String zzlist = Constants.ZZ_LIST;
		String zzid = Constants.ZZ_ID;
		return do_find(mapping, form, request, zzlist, zzid);
	}

	/**
	 * �����������ز�ѯ���
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
		String zzlist = Constants.ZZ_LIST;
		String zzid = Constants.ZZ_ID;
		return do_Search(mapping, request, zzlist, zzid);
	}

	/**
	 * edit������������½��������һ����form�� ���򣬽����޸�form
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
		//�������ֶ�����id�Ĳ���
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}

		//��ֹ�ظ��ύ
		saveToken(request);

		String type = getType(request);

		String id = null;

		String img_state = null;
		ZzForm zzForm = (ZzForm) form;
		//�ж����½������޸�
		boolean isNew = ("".equals(zzForm.getId()) || zzForm.getId() == null);

		if (isNew) { //��ѡ���½�ʱ��ɾ��ͼ��Ϊ��ɫ�����ܲ���ʹ��
			//���Session�е�SEL_IDS
			session.removeAttribute(Constants.SEL_IDS);
			img_state = Constants.NONE; //��ѡ���½�ʱ��û����һ������һ��ͼ��
			request.setAttribute(Constants.IMG_STATE, img_state);
			return mapping.findForward("edit");
		} else {
			//1.��ÿ�е��޸�ͼ�������޸ģ�����id,getParameter();
			//2.��Viewҳ�������޸�,����id,getParameter();
			//3.�����޸ģ�getAttribute(POS);
			if (type.equals(Constants.EDIT_MANY)
					|| type.equals(Constants.DEL_MANY)
					|| type.equals(Constants.DEl_MANY_ONE)) { //�����LIST�������ģ���϶��������޸�
				String[] sel_ids = (String[]) session
						.getAttribute(Constants.SEL_IDS); //��ȡid������

				Integer pos = (Integer) session.getAttribute(Constants.POS); //��ȡ��ǰλ��
				if (sel_ids.length > 0 && pos.intValue() <= sel_ids.length) {
					id = sel_ids[pos.intValue()]; //��ǰid
				} else {
					return search(mapping, form, request, response);
				}

			} else {
				//�����ÿ�е�ͼ�괫����
				//����Ǵ�viewҳ��ת������
				img_state = Constants.NONE;
				request.setAttribute(Constants.IMG_STATE, img_state);

				id = request.getParameter("id"); //���ȳ��Դ�request�л�ȡid
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
					resetZz(zzForm); //��zzForm���
				}
			} else {
				Zz zz; //����id���в�ѯ
				try {
					ZzManager mgr = (ZzManager) getBean("zzManager");
					zz = mgr.getZz(id);
					zzForm = (ZzForm) convert(zz);
					request.setAttribute(Constants.PID, zz.getJbxxid()); //����ǰ��pid����request��
				} catch (Exception e) {
					e.printStackTrace();
					return search(mapping, form, request, response);
				}

			}

			updateFormBean(mapping, request, zzForm);
			if (type.equals(Constants.VIEW)) { //����Ǵ�VIEW����
				return mapping.findForward("view");
			} else { //����Ǵ�EDIT����
				return mapping.findForward("edit");
			}

		}

	}

	/**
	 * @return
	 */
	private String getId(HttpSession session, String id,
			HttpServletRequest request) {
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //��ȡSession�е�����ѡ�е�id������
		Integer i = (Integer) session.getAttribute(Constants.POS);
		int pos = 0;
		if (i != null) {
			pos = i.intValue();
		}

		//��ȡɾ�������һ������һ����ͼ��
		String img_state = getImgState(pos, sel_ids); //��ȡ��һ������һ��ͼ���״̬

		request.setAttribute(Constants.IMG_STATE, img_state);
		if (sel_ids != null) {
			return sel_ids[pos];
		} else {
			return request.getParameter("id");
		}

	}

	/**
	 * ִ��search
	 * 
	 * @param mapping
	 * @param request
	 * @param zzlist
	 * @param zzid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_Search(ActionMapping mapping,
			HttpServletRequest request, String zzlist, String zzid)
			throws BeansException {
		String type = getType(request);

		HttpSession session = request.getSession();

		//�õ���ѯʱ��form��Ϣ
		ZzForm zz;
		try {
			zz = (ZzForm) session.getAttribute("form");
		} catch (RuntimeException e) {
			e.printStackTrace();
			zz = null;
		}

		//�õ���ǰ����
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS);

		//�õ�ValueList��ص�HandlerHelper
		ValueListHandlerHelper vlHelper = getValueListHelper();

		ValueListHandler vlh = getValueListHandler();

		Map map = new HashMap();

		//��valueListInfo�еõ���ǰҳ��ţ�
		ValueListInfo oldInfo = vlHelper.getValueListInfo(request,
				Constants.ZZ_ID);
		//��ȡ�ӵڼ�ҳ��ת����
		int pageno = oldInfo.getPagingPage();

		map = SearchMap.getZzMap(request, zz); //�õ�map

		ValueListInfo info = new ValueListInfo(map);
		//������ʾ���ڼ�ҳ
		if (!(Constants.DEL_MANY.equals(type) || (Constants.DEL.equals(type) || Constants.DEl_MANY_ONE
				.equals(type)))) {
			info.setPagingPage(pageno);
		}

		ValueList valueList = vlh.getValueList(zzlist, info);

		vlHelper.backupAndSet(request, valueList, zzlist, zzid);

		//����ظ��ύ������
		saveToken(request);
		return mapping.findForward("list");
	}

	/**
	 * ���淽��
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

		ZzForm zzForm = (ZzForm) form;
		//�˴��õ�����������������listҳ��.
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}

		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {
			resetZz((ZzForm) form);
			saveToken(request);
			return search(mapping, form, request, response);
		}

		resetToken(request);
		saveToken(request);

		//�õ��޸��˺��޸�ʱ��
		zzForm.setXgr(getXgr(request));
		zzForm.setXgsj(NowDate.getNowStrIsNullOrEmpty(zzForm.getXgsj()));//��ӡ�޸�ʱ�䡣

		ZzManager mgr = (ZzManager) getBean("zzManager");
		Zz zz = (Zz) convert(zzForm);

		HttpSession session = request.getSession();

		//������һ������һ����ͼ��
		String img_state = getUptImgState(request, session);
		//��ͼ��״̬����request��
		request.setAttribute(Constants.IMG_STATE, img_state);

		ActionMessages messages = new ActionMessages();

		try {
			String id = mgr.saveZz(zz); //������Ϣ
			zzForm.setId(id);
			//д�뱣��ɹ���Ϣ
			saveMsg(request, messages, "zz.saved");

			return mapping.findForward("view");
		} catch (RuntimeException e) {
			e.printStackTrace();
			//д�뱣��ʧ����Ϣ
			//			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			//					"error.zz.saved"));
			//			saveMessages(session, messages);

			saveMsg(request, messages, "error.zz.saved");
			request.setAttribute("pid", zzForm.getJbxxid()); //����ǰ��idд��request��Attribute��
			return mapping.findForward("edit");
		}
	}

	/**
	 * ������ҳ
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
		String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //��Session�л�ȡѡ���id������
		Integer pos = new Integer(i); //��ǰλ�õ�������Session�д洢��id����ĵ�ǰ��¼λ��

		session.setAttribute(Constants.POS, pos); //�ѵ�ǰλ�ü�¼��session��

		request.setAttribute("isnext", "yes");

		try {
			//��ȡ�Ƿ��ǵ�������һ��������
			String id = sel_ids[i]; //��ȡ��ǰid
			getNext(mapping, request, id);
		} catch (Exception e) {
			e.printStackTrace();
			return search(mapping, form, request, response);
		}

		setImgState(request, i, sel_ids);

		return mapping.findForward("edit");
	}

	/**
	 * ��zzForm�����ֶθ�λ
	 * 
	 * @param zzForm
	 *  
	 */
	private void resetZz(ZzForm zzForm) {
		Zz zz = new Zz();
		//�˴�������Apache��BeanUtils���copyProperties����,�÷�����һ��������Ŀ����󣬺���Ĳ�����Դ����
		try {
			BeanUtils.copyProperties(zzForm, zz);
		} catch (IllegalAccessException e1) {
			log.debug("resetZz ����-IllegalAccessException");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			log.debug("resetZz ����-InvocationTargetException");
			e1.printStackTrace();
		}
	}

	/**
	 * ִ��find����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param zzlist
	 * @param zzid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, String zzlist, String zzid)
			throws BeansException {
		HttpSession session = request.getSession();
		//���Session�д��е���Ϣ
		session.removeAttribute(Constants.SEL_IDS);
		String type = getType(request);

		ZzForm zz = (ZzForm) form;

		ValueListHandler vlh = getValueListHandler();

		Map map = new HashMap();

		map = SearchMap.getZzMap(request, zz); //�õ�map //getMap(request, zz);
		// //�õ�����Map

		ValueListHandlerHelper vlHelper = getValueListHelper();
		ValueList valueList = vlh.getValueList(zzlist, new ValueListInfo(map));
		vlHelper.backupAndSet(request, valueList, zzlist, zzid);

		//�����ض�����,�Ӷ����Excel�ļ�
		String excelFileName = realPath + "zz��ѯ���.xls"; //ע�⣺Ҫ�޸ĸ��ļ���
		String listname = zzlist;

		setExcelInfo(request, valueList, excelFileName, listname);
		return mapping.findForward("list");
	}

	/**
	 * ���δָ��method������ø÷���
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return gotoFind(mapping, form, request, response);
	}

	/**
	 * ��ȡ���޸�״̬�µ���һ������һ��ͼ���Ƿ���ʾ
	 * 
	 * @param request
	 * @return
	 */
	public String getUptImgState(HttpServletRequest request, HttpSession session) {
		//����ӵ�����¼�޸ģ�Ȼ�󱣴棬����ʾ��һ������һ��ͼ��

		//����Ҫ�ж��Ƿ�����һ��������У���ʾ��һ��ͼ�꣬���򣬲���ʾ��һ��ͼ��
		//�ж��Ƿ��ǵ�һ����¼������ǣ�����ʾ��һ��ͼ��
		String img_state = null;
		String type = getType(request); //�õ�type

		if ("save".equals(type)) {
			img_state = Constants.NONE;
		} else {
			String[] sel_ids = (String[]) session
					.getAttribute(Constants.SEL_IDS);

			Integer pos = (Integer) session.getAttribute(Constants.POS);

			int i = pos.intValue();

			img_state = getImgState(i, sel_ids); //��ȡ��һ������һ����ͼ���״̬
		}
		return img_state;
	}

	//�༭��һ����¼
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() + 1; //�õ���ǰid��pos��ֵ

		return batch_ChangeID(mapping, request, form, response, session, i);
	}

	//�༭��һ����¼��
	public ActionForward prior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() - 1;

		return batch_ChangeID(mapping, request, form, response, session, i);
	}

	/**
	 * ��ת��Edit��������תǰ����һЩ����
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

		saveMsg(request, messages, "zz.deleted");

		return edit(mapping, form, request, response);
	}

	/**
	 * �ж�ɾ���������� �������һ�����������һ���޸�ҳ�� ���û����һ��������һ����������һ�����޸�ҳ��
	 * ���û����һ����û����һ���������ѯ���ҳ��
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
		if (sel_ids == null || sel_ids.length == 0) { //���ѡ���ID������Ϊnull�������鳤��Ϊ0����˵��û����һ������һ����¼�ˣ������Search()������
			return goToSearch(mapping, form, request, response, messages);
		} else {
			String img_state = null; //��¼ͼ���״̬����ʾ��һҳ����һҳ�����߶���ʾ
			int pos = ((Integer) session.getAttribute(Constants.POS))
					.intValue(); //��ǰid�������е�����(λ��),��λ���������޸Ľ���ʱд��Ϊ0,�ڽ��뵥���޸�ҳ������޸ĸ���ֵ��

			return com_edit(mapping, form, request, response, messages,
					session, sel_ids, pos);
		}
	}

	/**
	 * ͨ�õ�edit����
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

		//Ȼ��Session�е�POS��λ�ø���Ϊ��ǰ��λ��.
		session.setAttribute(Constants.POS, new Integer(i));

		setImgState(request, i, sel_ids);

		//��ת��ָ��id���޸�ҳ��
		return goToEdit(mapping, form, request, response, messages);
	}

	/**
	 * ��ת��search��������תǰ����һЩ����
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
		//				"zz.deleted"));
		//
		//		saveMessages(request.getSession(), messages);

		saveMsg(request, messages, "zz.deleted");

		return search(mapping, form, request, response);
	}

	public ActionForward edit_many(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		saveToken(request); //��ֹ�ظ��ύ
		String type = getType(request); //�õ�type

		String id = null;

		String img_state = null; //��һ������һ��ͼ�����ʾ��־
		Integer pos = new Integer(0); //id�����λ�õĳ�ʼ��

		//ȡ��ѡ���id�����飬��������ͬ��ǰid����session��
		HttpSession session = request.getSession();

		//ȡ�ö�����¼id���飡
		String[] sel_ids = request.getParameterValues(Constants.IDS);//��ȡ�ύ��id������

		//�˴��õ�����������������listҳ��.
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
			if (sel_ids == null) {
				sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //ȡ��ѡ���ID����
				pos = (Integer) session.getAttribute(Constants.POS); //ȡ�������λ��
			}
		} 

		if (sel_ids == null) { //����Ƕ��������޸ģ�Ȼ���form�е�����޸ġ���ť
			id = request.getParameter("id"); //
		} else {
			//����ύ��idΪ0��������ת����ѯҳ��
			if (sel_ids.length == 0) {
				return goToSearch(mapping, form, request, response, null);
			} else {
				//���õ���id����|��ǰ��¼�������е�λ�ô���session
				session.setAttribute(Constants.SEL_IDS, sel_ids);
				session.setAttribute(Constants.POS, pos);
				id = sel_ids[pos.intValue()]; //ȡ�õ�ǰ������id����ʵ�ǵ�һ��id������Ϊ0
				request.setAttribute("islist", "yes");
			}
		}

		if (id == null) { //����û�ûѡ��id,ͬʱ������ر���javascript�Ĺ���
			return search(mapping, form, request, response);
		} else {
			try {
				//ȷ���Ƿ��ǵ������һ��������
				getNext(mapping, request, id);
			} catch (Exception e) {
				e.printStackTrace();
				return search(mapping, form, request, response);
			}
			//ȷ����ʾ��һ������һ����״̬
			img_state = getImgState(pos.intValue(), sel_ids); //��ȡ��һ����һ��ͼ�����ʾ״̬

			request.setAttribute(Constants.IMG_STATE, img_state); //����һ������һ����״̬д��request��

			return mapping.findForward("edit"); //�����������ת������edit������
		}
	}

	/**
	 * @param request
	 * @param zzForm
	 * @param id
	 * @return
	 * @throws Exception
	 *             ActionMapping mapping, ActionForm form, HttpServletRequest
	 *             request, HttpServletResponse response
	 */
	private void getNext(ActionMapping mapping, HttpServletRequest request,
			String id) throws Exception {
		ZzForm zzForm = null;
		String module = request.getParameter("module");
		//�������ֶ�����id�Ĳ���
		if (!isNullOrSpace(request.getParameter("next"))) {
			request.setAttribute("next", request.getParameter("next"));
		}
		String islist = (String) request.getAttribute("islist"); //�Ƿ��������޸Ĺ�����
		String isjbxxlist = request.getParameter("isjbxxlist");
		String isnext = (String) request.getAttribute("isnext"); //��һ��
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
			ZzManager mgr = (ZzManager) getBean("zzManager");
			Zz zz = mgr.getZz(id); //����id���в�ѯ
			zzForm = (ZzForm) convert(zz);
			request.setAttribute(Constants.PID, zz.getJbxxid()); //����ǰ��pid����request��
		}
		updateFormBean(mapping, request, zzForm);
	}

}