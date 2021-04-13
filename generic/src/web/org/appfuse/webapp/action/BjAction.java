package org.appfuse.webapp.action;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
//import net.mlw.vlh.web.ValueListRequestUtil;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;
import java.util.HashMap;
import java.util.Map;
import org.appfuse.service.BjManager;
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
import org.appfuse.model.Bj;

import org.appfuse.webapp.form.BjForm;

/**
 * Action class to handle CRUD on a Bj object
 *
 * @struts.action name="bjForm" path="/bjs" scope="request"
 *  validate="false" parameter="method" input="mainMenu"
 * @struts.action name="bjForm" path="/editBj" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="bjForm" path="/saveBj" scope="request"
 *  validate="true" parameter="method" input="edit"
 * 
 * @struts.action-forward name="edit" path="/WEB-INF/pages/bjForm.jsp"
 * @struts.action-forward name="list" path="/WEB-INF/pages/bjList.jsp"
 * @struts.action-forward name="find" path="/WEB-INF/pages/bjFind.jsp"
 * @struts.action-forward name="view" path="/WEB-INF/pages/bjView.jsp"
 */
public final class BjAction extends BaseAction {

    public ActionForward cancel(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
	 BjForm bjForm = (BjForm) form;

	 resetBj(bjForm); //FormBean��λ

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
                                HttpServletRequest request,
                                HttpServletResponse response)
    throws Exception {
		HttpSession session = null;

		BjManager mgr = (BjManager) getBean("bjManager");

		ActionMessages messages = new ActionMessages();

		BjForm bjForm = (BjForm) form;

		String id = null;
		//��ȡɾ������Դ�������"del_many"���Ǵ�list������ֱ������ɾ��
		//���Ϊdel_many_one,���������޸��еĵ����޸�ɾ��,���������޸��еĲ鿴ʱ���ɾ��
		//���Ϊdel,��Ϊ�����鿴,���ߵ����޸�ɾ��
		String type = getType(request);

		if ("del_many".equals(type)) { //����Ƕ���ɾ��
			return delMany(mapping, form, request, response, mgr, messages,
					type);

		} else { //�������ɾ��
			return delOne(mapping, form, request, response, mgr,
					messages, type);

		}

    }
	/**
	 * ɾ������
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
			BjManager mgr, ActionMessages messages, String type)
			throws Exception {
		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {
			resetBj((BjForm)form);
			saveToken(request);
		} else {
			resetToken(request);
			String[] sel_ids = request.getParameterValues(Constants.IDS);

			if (sel_ids != null) { //�����ɾ����������ѯ���
				for (int i = 0; i < sel_ids.length; i++) {
					String id = sel_ids[i];
					try{
						mgr.removeBj(id); //ɾ��һ����¼
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
			BjManager mgr, ActionMessages messages,
			String type) throws Exception {
		HttpSession session = request.getSession(); //�õ�Session����Session�в����Ƿ���δ�����id
		String id = null;
		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {  
			resetBj((BjForm)form);//����Ѿ�ִ�й���,�����뱾����
			saveToken(request);
		} else {
			resetToken(request);
			id = request.getParameter("id");
			try{
				mgr.removeBj(id);  //ɾ����¼
			} catch (RuntimeException e) {
				getError(session, e, "error.bj.deleted", id);
			}
		}

		if (Constants.DEL.equals(type)) { //�����ɾ����ͨ�ĵ�����¼,��ת����ѯ���ҳ��
			saveMsg(request, messages, "bj.deleted");
			return search(mapping, form, request, response);
		} else {  //����������޸ĵ��޸Ļ��߲鿴ҳ��ĵ���ɾ��
			String[] sel_ids = (String[])session.getAttribute(Constants.SEL_IDS);  //��ȡSession�е�����ѡ�е�id������

			int pos = ((Integer) session.getAttribute(Constants.POS))
					.intValue(); 
			if (sel_ids != null && pos >= sel_ids.length - 1) { //���Ϊ���һ����¼
				decPos(session, pos);   //��pos��1
			}
			sel_ids = delID(id, session, sel_ids);//ɾ��SEL_IDS�����еĵ�ǰ��id

			//��ȡɾ�������һ������һ����ͼ��
			return getDelImgState(mapping, form, request, response, messages,
					session, sel_ids);

		}

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
		BjForm bjForm = new BjForm();
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
		String bjlist = Constants.BJ_LIST;
		String bjid = Constants.BJ_ID;
		return do_find(mapping, form, request, bjlist, bjid);
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
		String bjlist = Constants.BJ_LIST;
		String bjid = Constants.BJ_ID;
		return do_Search(mapping, request, bjlist, bjid);
	}

	/**
	 * edit������������½��������һ����form��
	 * ���򣬽����޸�form
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

        //��ֹ�ظ��ύ
		saveToken(request);

		String type = getType(request);

		HttpSession session = request.getSession();
		String id = null;

		String img_state = null;
		BjForm bjForm = (BjForm) form;

		boolean isNew = ("".equals(bjForm.getId()) || bjForm.getId() == null);

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
					|| type.equals(Constants.DEL_MANY)||type.equals(Constants.DEl_MANY_ONE)) { //�����LIST�������ģ���϶��������޸�
				String[] sel_ids = (String[]) session
						.getAttribute(Constants.SEL_IDS); //��ȡid������

				Integer pos = (Integer) session.getAttribute(Constants.POS); //��ȡ��ǰλ��
				if (sel_ids.length > 0 && pos.intValue() <= sel_ids.length){
					id = sel_ids[pos.intValue()]; //��ǰid
				}else{
					return search(mapping, form, request, response);
				}

			} else {
				//�����ÿ�е�ͼ�괫����
				//����Ǵ�viewҳ��ת������
				img_state = Constants.NONE;
				request.setAttribute(Constants.IMG_STATE, img_state);

				id = request.getParameter("id"); //���ȳ��Դ�request�л�ȡid
			}

			BjManager mgr = (BjManager) getBean("bjManager");
			 try {
                getBj(mapping, request, id, mgr);
            } catch (RuntimeException e) {
                getError(session, e, "error.bj.selected", id);

                return search(mapping, form, request, response);
            }

			if (type.equals(Constants.VIEW)) { //����Ǵ�VIEW����
				return mapping.findForward("view");
			} else { //����Ǵ�EDIT����
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
    private void getBj(ActionMapping mapping, HttpServletRequest request, String id, BjManager mgr) throws Exception {
        BjForm bjForm;
        Bj bj;
        bj = mgr.getBj(id);
        bjForm = (BjForm) convert(bj);
        updateFormBean(mapping, request, bjForm);

    }
        /**
	 * ִ��search
	 * @param mapping
	 * @param request
	 * @param bjlist
	 * @param bjid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_Search(ActionMapping mapping, HttpServletRequest request, String bjlist, String bjid) throws BeansException {
		String type = getType(request);

		HttpSession session = request.getSession();

		//�õ���ѯʱ��form��Ϣ
		BjForm bj ;
		try {
			bj = (BjForm) session.getAttribute("form");
		} catch (RuntimeException e) {
			e.printStackTrace();
			 bj = null;
		}
		//�õ�ValueList��ص�HandlerHelper
		ValueListHandlerHelper vlHelper = getValueListHelper();

		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);

		Map map = new HashMap();

		//��valueListInfo�еõ���ǰҳ��ţ�
		ValueListInfo oldInfo = vlHelper.getValueListInfo(request,
				Constants.BJ_ID);
		//��ȡ�ӵڼ�ҳ��ת����
		int pageno = oldInfo.getPagingPage();

		map = SearchMap.getBjMap(request, bj); //�õ�map

		ValueListInfo info = new ValueListInfo(map);
		//������ʾ���ڼ�ҳ
		if (!(Constants.DEL_MANY.equals(type) || (Constants.DEL.equals(type) || Constants.DEl_MANY_ONE.equals(type)))) {
			info.setPagingPage(pageno);
		}

		ValueList valueList = vlh.getValueList(bjlist, info);
		vlHelper.backupAndSet(request, valueList, bjlist, bjid);

		//����ظ��ύ������
		saveToken(request);

		return mapping.findForward("list");
	}

	/**
	 * ��listҳ�������޸ġ�ͼ�꣬���ø÷���
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
		saveToken(request);  //��ֹ�ظ��ύ
		String type = getType(request); //�õ�type

		String id = null;

		String img_state = null; //��һ������һ��ͼ�����ʾ��־
		Integer pos = new Integer(0); //id�����λ�õĳ�ʼ��

		//ȡ��ѡ���id�����飬��������ͬ��ǰid����session��
		HttpSession session = request.getSession();

		BjForm bjForm = (BjForm) form;

		//ȡ�ö�����¼id���飡
		String[] sel_ids = request.getParameterValues(Constants.IDS);//��ȡ�ύ��id������

		if(sel_ids == null){
		    sel_ids = (String[])session.getAttribute(Constants.SEL_IDS); //ȡ��ѡ���ID����
		    pos = (Integer)session.getAttribute(Constants.POS);    //ȡ�������λ��
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
			}
		}

		if (id == null) { //����û�ûѡ��id,ͬʱ������ر���javascript�Ĺ���
			return search(mapping, form, request, response);
		} else {
			BjManager mgr = (BjManager) getBean("bjManager");
            try {
                getBj(mapping, request, id, mgr); //����FormBean
            } catch (RuntimeException e) {
                getError(session, e, "error.bj.selected", id);

                return search(mapping, form, request, response);
            }
			setImgState(request, pos.intValue(), sel_ids);
			return mapping.findForward("edit"); //�����������ת������edit������
		}
	}

	/**
	 * ���淽��
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
		BjForm bjForm = (BjForm) form;
		//��ֹ�ظ��ύ
		if (!isTokenValid(request)) {
			resetBj((BjForm) form);
			saveToken(request);
			return search(mapping, form, request, response);
		} 
		resetToken(request);
		saveToken(request);

		//�õ��޸���
		bjForm.setXgr(getXgr(request));
		bjForm.setXgsj(NowDate.getNowStrIsNullOrEmpty(bjForm.getXgsj()));//��ӡ�޸�ʱ�䡣

		BjManager mgr = (BjManager) getBean("bjManager");
		Bj bj = (Bj) convert(bjForm);

		String id = null;

		HttpSession session = request.getSession();

		String img_state = null;

		boolean isNew = ("".equals(bjForm.getId()) || bjForm.getId() == null);
		if (isNew) { //�½���ʱ����һ������һ����ͼ�궼����ʾ
			img_state = Constants.NONE;
		} else { //�޸ĵ�ʱ��Ҫ�ж���һ������һ��ͼ���Ƿ���ʾ
			img_state = getUptImgState(request, session);
		}

		request.setAttribute(Constants.IMG_STATE, img_state);

		try {
			id = mgr.saveBj(bj); //������Ϣ

			bjForm.setId(id);

			saveMsg(request, messages, "bj.saved");

			return mapping.findForward("view");
		} catch (RuntimeException e) {
			e.printStackTrace();
			saveMsg(request, messages, "error.bj.saved");

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
	public ActionForward batch_ChangeID(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session, int i)
			throws Exception {
	    String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); //��Session�л�ȡѡ���id������

		Integer pos = new Integer(i); //��ǰλ�õ�������Session�д洢��id����ĵ�ǰ��¼λ��

		session.setAttribute(Constants.POS, pos); //�ѵ�ǰλ�ü�¼��session��

		String id;
		try {
            id = sel_ids[i]; //��ȡ��ǰid
        } catch (RuntimeException e) {
            e.printStackTrace();
            return search(mapping, form, request, response);
        }

		BjManager mgr = (BjManager) getBean("bjManager");
        try {
            getBj(mapping, request, id, mgr);	
        } catch (RuntimeException e1) {
            getError(session, e1, "error.bj.selected", id);
        }
		setImgState(request, i, sel_ids);

		return mapping.findForward("edit");
	}

    /**
	 * ��bjForm�����ֶθ�λ
	 * @param bjForm
	 * 
	 */
	private void resetBj(BjForm bjForm) {
		Bj bj = new Bj();
		//�˴�������Apache��BeanUtils���copyProperties����,�÷�����һ��������Ŀ����󣬺���Ĳ�����Դ����
		try {
			BeanUtils.copyProperties(bjForm, bj);
		} catch (IllegalAccessException e1) {
			log.debug("resetBj ����-IllegalAccessException");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			log.debug("resetBj ����-InvocationTargetException");
			e1.printStackTrace();
		}
	}

	/**
	 * ִ��find����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param bjlist
	 * @param bjid
	 * @return
	 * @throws BeansException
	 */
	public ActionForward do_find(ActionMapping mapping, ActionForm form, HttpServletRequest request, String bjlist, String bjid) throws BeansException {
		HttpSession session = request.getSession();
		//���Session�д��е���Ϣ
		session.removeAttribute(Constants.SEL_IDS);
		String type = getType(request);

		BjForm bj = (BjForm) form;
		//�������ֶ���ص�form��Ϣ
		session.setAttribute("form",bj);

		//��ȡValueList��صĶ���
		WebApplicationContext context = WebApplicationContextUtils
				.getWebApplicationContext(getServlet().getServletContext());
		ValueListHandler vlh = (ValueListHandler) context.getBean(
				"valueListHandler", ValueListHandler.class);

		Map map = new HashMap();

		map = SearchMap.getBjMap(request, bj); //�õ�map //getMap(request, bj); //�õ�����Map

		ValueListHandlerHelper vlHelper = getValueListHelper();
		ValueList valueList = vlh.getValueList(bjlist, new ValueListInfo(
				map));
		vlHelper.backupAndSet(request, valueList, bjlist, bjid);

		//�����ض�����,�Ӷ����Excel�ļ�
		String excelFileName = realPath + "bj��ѯ���.xls"; //ע�⣺Ҫ�޸ĸ��ļ���
		String listname = bjlist;

		setExcelInfo(request, valueList, excelFileName, listname);

		return mapping.findForward("list");
	}

	/**
	*���δָ��method������ø÷���
	*/
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return gotoFind(mapping, form, request, response);
	}

	    //��������ӵķ���
	/**
	 * ��ȡ���޸�״̬�µ���һ������һ��ͼ���Ƿ���ʾ
	 * 
	 * @param request
	 * @return
	 */
	public String getUptImgState(HttpServletRequest request,
			HttpSession session) {
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
	/**
	 * ��ȡfrom��ֵ
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

	//�༭��һ����¼
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() + 1; //�õ���ǰid��pos��ֵ

		return batch_ChangeID(mapping, form, request, response, session, i);
	}

	//�༭��һ����¼��
	public ActionForward prior(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();
		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue() - 1;

		return batch_ChangeID(mapping, form, request, response, session, i);
	}

	/**
	 * ��ת��Edit��������תǰ����һЩ����
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
		saveMsg(request, messages, "bj.deleted");

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
	public ActionForward getDelImgState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages,
			HttpSession session, String[] sel_ids) throws Exception {

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
		//Ȼ��Session�е�POS��λ�ø���Ϊ��ǰ��λ��.
		session.setAttribute(Constants.POS, new Integer(pos));

		img_state = getImgState(pos, sel_ids); //��ȡ��һ������һ��ͼ���״̬

		request.setAttribute(Constants.IMG_STATE, img_state);

		//��ת��ָ��id���޸�ҳ��
		return goToEdit(mapping, form, request, response, messages);
	}
	/**
	 * ��ת��search��������תǰ����һЩ����
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
		saveMsg(request, messages, "bj.deleted");

		return search(mapping, form, request, response);
	}

}

