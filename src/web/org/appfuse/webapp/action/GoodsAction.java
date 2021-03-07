package org.appfuse.webapp.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mlw.vlh.ValueList;
import net.mlw.vlh.ValueListHandler;
import net.mlw.vlh.ValueListInfo;
import net.mlw.vlh.web.mvc.ValueListHandlerHelper;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.appfuse.Constants;



import org.appfuse.model.Goods;





import org.appfuse.service.GoodsManager;





import org.appfuse.webapp.form.GoodsForm;
import org.appfuse.webapp.util.NowDate;
import org.appfuse.webapp.util.SearchMap;
import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * 
 * @author ycs
 * @struts.action name="goodsForm" path="/goods" scope="request"
 *  validate="false" parameter="method" 
 *   
 *  
 * @struts.action name="goodsForm" path="/goodss" scope="request"
 * validate="false" parameter="method" input="mainMenu"
 * @struts.action name="goodsForm" path="/editgoods" scope="request"
 *  validate="false" parameter="method" input="list"
 * @struts.action name="goodsForm" path="/savegoods" scope="request"
 *  validate="true" parameter="method" input="edit"
 *  
 *  
 *  
 *  
 *  
 * @struts.action-forward name="edit" path="/WEB-INF/pages/basedata/goodsForm.jsp"
 * @struts.action-forward name="view" path="/WEB-INF/pages/basedata/goodsView.jsp"
 * @struts.action-forward name="find" path="/WEB-INF/pages/basedata/goodsFind.jsp"
  * @struts.action-forward name="list" path="/WEB-INF/pages/basedata/goodsList.jsp"
 */

public class GoodsAction extends BaseAction {
	 public ActionForward cancel(ActionMapping mapping, ActionForm form,
             HttpServletRequest request,
             HttpServletResponse response)
throws Exception {
    GoodsForm goodsForm = (GoodsForm) form;

    resetGoods(goodsForm); //FormBean复位

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
			//HttpSession session = null;

	    	GoodsManager mgr = (GoodsManager) getBean("goodsManager");

			ActionMessages messages = new ActionMessages();

			//BzForm bzForm = (BzForm) form;

			//String id = null;
			String type = getType(request);

			if ("del_many".equals(type)) { 
				return delMany(mapping, form, request, response, mgr, messages,
						type);

			} else { 
				return delOne(mapping, form, request, response, mgr,
						messages, type);

			}

	    }
		/**
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
				GoodsManager mgr, ActionMessages messages, String type)
				throws Exception {
			if (!isTokenValid(request)) {
				resetGoods((GoodsForm)form);
				saveToken(request);
			} else {
				resetToken(request);
				String[] sel_ids = request.getParameterValues(Constants.IDS);

				if (sel_ids != null) { 
					for (int i = 0; i < sel_ids.length; i++) {
						String id = sel_ids[i];
						try{
							mgr.removeGoods(id); 
						} catch (RuntimeException e) {
							e.printStackTrace();
						}
					}

				}
			}
			return goToSearch(mapping, form, request, response, messages); 
		}

		/**
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
				GoodsManager mgr, ActionMessages messages,
				String type) throws Exception {
			HttpSession session = request.getSession(); 
			String id = null;
			if (!isTokenValid(request)) {  
				resetGoods((GoodsForm)form);
				saveToken(request);
			} else {
				resetToken(request);
				id = request.getParameter("id");
				try{
					mgr.removeGoods(id);  
				} catch (RuntimeException e) {
					getError(session, e, "error.goods.deleted", id);
				}
			}

			if (Constants.DEL.equals(type)) { 
				saveMsg(request, messages, ".deleted");
				return search(mapping, form, request, response);
			} else {  
				String[] sel_ids = (String[])session.getAttribute(Constants.SEL_IDS);  

				int pos = ((Integer) session.getAttribute(Constants.POS))
						.intValue(); 
				if (sel_ids != null && pos >= sel_ids.length - 1) { 
					decPos(session, pos);   
				}
				sel_ids = delID(id, session, sel_ids);

				return getDelImgState(mapping, form, request, response, messages,
						session, sel_ids);

			}

		}
		/**
		 * 
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
			String list_goods = Constants.LIST_GOODS;
			String goodsID = Constants.goodsID;
			return do_Search(mapping, request, list_goods, goodsID);
		}
		
		public ActionForward do_Search(ActionMapping mapping, HttpServletRequest request, String list_goods, String goodsID) throws BeansException {
			String type = getType(request);

			HttpSession session = request.getSession();

			GoodsForm goods ;
			try {
				goods = (GoodsForm) session.getAttribute("form");
			} catch (RuntimeException e) {
				e.printStackTrace();
				goods = null;
				 return do_SearchError(mapping);
			}
			ValueListHandlerHelper vlHelper = getValueListHelper();

			WebApplicationContext context = WebApplicationContextUtils
					.getWebApplicationContext(getServlet().getServletContext());
			ValueListHandler vlh = (ValueListHandler) context.getBean(
					"valueListHandler", ValueListHandler.class);

			Map map = new HashMap();

			ValueListInfo oldInfo = vlHelper.getValueListInfo(request,
					Constants.goodsID);
			int pageno = oldInfo.getPagingPage();

			map = SearchMap.getGoodsMap(request, goods); 

			ValueListInfo info = new ValueListInfo(map);
			if (!(Constants.DEL_MANY.equals(type) || (Constants.DEL.equals(type) || Constants.DEl_MANY_ONE.equals(type)))) {
				info.setPagingPage(pageno);
			}

			ValueList valueList = vlh.getValueList(list_goods, info);
			vlHelper.backupAndSet(request, valueList, list_goods, goodsID);

			saveToken(request);

			return mapping.findForward("list");
		}

		
		
		/**
		 * 
		 * 
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

	       saveToken(request);

			String type = getType(request);

			HttpSession session = request.getSession();
			String id = null;

			String img_state = null;
			GoodsForm goodsForm = (GoodsForm) form;

			boolean isNew = ("".equals(goodsForm.getGoodsID()) || goodsForm.getGoodsID() == null);

			if (isNew) { 
				session.removeAttribute(Constants.SEL_IDS);

				img_state = Constants.NONE; 

				request.setAttribute(Constants.IMG_STATE, img_state);
				return mapping.findForward("edit");
			} else {
				if (type.equals(Constants.EDIT_MANY)
						|| type.equals(Constants.DEL_MANY)||type.equals(Constants.DEl_MANY_ONE)) { 
					String[] sel_ids = (String[]) session
							.getAttribute(Constants.SEL_IDS); 

					Integer pos = (Integer) session.getAttribute(Constants.POS); 
					if (sel_ids.length > 0 && pos.intValue() <= sel_ids.length){
						id = sel_ids[pos.intValue()]; 
					}else{
						return search(mapping, form, request, response);
					}

				} else {
					img_state = Constants.NONE;
					request.setAttribute(Constants.IMG_STATE, img_state);

					id = request.getParameter("id");
				}

				GoodsManager mgr = (GoodsManager) getBean("goodsManager");
				 try {
	                getGoods(mapping, request, id, mgr);
	            } catch (RuntimeException e) {
	                getError(session, e, "error.goods.selected", id);

	                return search(mapping, form, request, response);
	            }

				if (type.equals(Constants.VIEW)) { 
					return mapping.findForward("view");
				} else { 
					return mapping.findForward("edit");
				}

			}

	    }
		public ActionForward edit_many(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			saveToken(request);  
			//String type = getType(request); 

			String id = null;

			//String img_state = null; 
			Integer pos = new Integer(0); 

			HttpSession session = request.getSession();

			//BzForm bzForm = (BzForm) form;

			String[] sel_ids = request.getParameterValues(Constants.IDS);

			if(sel_ids == null){
			    sel_ids = (String[])session.getAttribute(Constants.SEL_IDS); 
			    pos = (Integer)session.getAttribute(Constants.POS);    
			}

			if (sel_ids == null) { 
				id = request.getParameter("id"); //
			} else {
				if (sel_ids.length == 0) {
					return goToSearch(mapping, form, request, response, null);
				} else {
					session.setAttribute(Constants.SEL_IDS, sel_ids);
					session.setAttribute(Constants.POS, pos);
					id = sel_ids[pos.intValue()]; 
				}
			}

			if (id == null) { 
				return search(mapping, form, request, response);
			} else {
				GoodsManager mgr = (GoodsManager) getBean("goodsManager");
	            try {
	                getGoods(mapping, request, id, mgr); 
	            } catch (RuntimeException e) {
	                getError(session, e, "error.goods.selected", id);

	                return search(mapping, form, request, response);
	            }
				setImgState(request, pos.intValue(), sel_ids);
				return mapping.findForward("edit"); 
			}
		}

		/**
		 * 
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
		    String[] sel_ids = (String[]) session.getAttribute(Constants.SEL_IDS); 

			Integer pos = new Integer(i); 

			session.setAttribute(Constants.POS, pos); 

			String id;
			try {
	            id = sel_ids[i]; 
	        } catch (RuntimeException e) {
	            e.printStackTrace();
	            return search(mapping, form, request, response);
	        }

			GoodsManager mgr = (GoodsManager) getBean("goodsManager");
	        try {
	            getGoods(mapping, request, id, mgr);	
	        } catch (RuntimeException e1) {
	            getError(session, e1, "error.goods.selected", id);
	        }
			setImgState(request, i, sel_ids);

			return mapping.findForward("edit");
		}


	    
	    
	    
	    
	    
	        /**
		 * 
		 * @param mapping
		 * @param request
		 * @param bzlist
		 * @param bzid
		 * @return
		 * @throws BeansException
		 */
	 
	 
	
	
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'add' method");
		}
		// add else do
		Goods goods = new Goods();
		GoodsForm goodfrm=(GoodsForm) convert(goods);
		updateFormBean(mapping, request, goodfrm);

		return mapping.findForward("edit");
	}
    
    public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	
    	Goods goods = new Goods();
      	GoodsForm frmgoods =( GoodsForm)form;
      	frmgoods.setGoodsID("");
      	goods=(Goods)convert(frmgoods);
       	
    	GoodsManager mgr=(GoodsManager)getBean("goodsManager");
        try { 
        	mgr.saveGoods(goods);
     //   	System.out.print(l);
        }catch(Exception e){
        	e.printStackTrace();
        	
        }
    	
    	return mapping.findForward("view");
    	
    }
    	
   
    
    public ActionForward gotoFind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'find' method");
		}
		// add else do
		Goods goods = new Goods();
		GoodsForm goodfrm=(GoodsForm) convert(goods);
		updateFormBean(mapping, request, goodfrm);

		return mapping.findForward("find");
	}
    
    public ActionForward find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		sessionInfo(request, session); 	
		
    	GoodsForm frmgoods =( GoodsForm)form;
    	
    	session.setAttribute(Constants.FORM, frmgoods);
    	
    	ValueListHandler vlh = getValueListHandler();
		Map map = new HashMap();
		
		map = SearchMap.getGoodsMap(request, frmgoods);
    	
		ValueListHandlerHelper vlHelper = getValueListHelper();
		ValueList valueList = vlh.getValueList(Constants.LIST_GOODS, new ValueListInfo(map));
		vlHelper.backupAndSet(request, valueList, Constants.LIST_GOODS, Constants.goodsID);
		
    	return mapping.findForward("list");
    }
    
    /**
	 * 
	 * @param bzForm
	 * 
	 */
	private void resetGoods(GoodsForm goodsForm) {
		Goods goods = new Goods();
		try {
			BeanUtils.copyProperties(goodsForm, goods);
		} catch (IllegalAccessException e1) {
			log.debug("resetGoods -IllegalAccessException");
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			log.debug("resetGoods -InvocationTargetException");
			e1.printStackTrace();
		}
	}
	
	public ActionForward getDelImgState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionMessages messages,
			HttpSession session, String[] sel_ids) throws Exception {

		//String id;
		if (sel_ids == null || sel_ids.length == 0) { 
			return goToSearch(mapping, form, request, response, messages);
		} else {
			//String img_state = null; 
			int pos = ((Integer) session.getAttribute(Constants.POS))
					.intValue(); 

			return com_edit(mapping, form, request, response, messages,
					session, sel_ids, pos);
		}
	}
	public ActionForward com_edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			ActionMessages messages, HttpSession session, String[] sel_ids,
			int pos) throws Exception {
		String img_state;
		session.setAttribute(Constants.POS, new Integer(pos));

		img_state = getImgState(pos, sel_ids); 

		request.setAttribute(Constants.IMG_STATE, img_state);

		return goToEdit(mapping, form, request, response, messages);
	}
	/**
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
		saveMsg(request, messages, "goods.deleted");

		return edit(mapping, form, request, response);
	}
	
	
	
    /**
* @param mapping
* @param request
* @param id
* @param mgr
* @throws Exception
*/
private void getGoods(ActionMapping mapping, HttpServletRequest request, String id, GoodsManager mgr) throws Exception {
   GoodsForm goodsForm;
   Goods goods;
   goods = mgr.getGood(id);
   goodsForm = (GoodsForm) convert(goods);
   updateFormBean(mapping, request, goodsForm);

}

public ActionForward do_find(ActionMapping mapping, ActionForm form, HttpServletRequest request, String goodslist, String goodsid) throws BeansException {
	HttpSession session = request.getSession();
	session.removeAttribute(Constants.SEL_IDS);
	//String type = getType(request);

	GoodsForm goods = (GoodsForm) form;
	session.setAttribute("form",goods);

	WebApplicationContext context = WebApplicationContextUtils
			.getWebApplicationContext(getServlet().getServletContext());
	ValueListHandler vlh = (ValueListHandler) context.getBean(
			"valueListHandler", ValueListHandler.class);

	Map map = new HashMap();


	map = SearchMap.getGoodsMap(request, goods); 

	ValueListHandlerHelper vlHelper = getValueListHelper();
	ValueList valueList = vlh.getValueList(goodslist, new ValueListInfo(
			map));
	vlHelper.backupAndSet(request, valueList, goodslist, goodsid);

	String excelFileName = "goods.xls"; 
	String listname = goodslist;

	setExcelInfo(request, valueList, excelFileName, listname);

	return mapping.findForward("list");
}

/**
*
*/
public ActionForward unspecified(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	return gotoFind(mapping, form, request, response);
}

 
/**
 * 
 * 
 * @param request
 * @return
 */
public String getUptImgState(HttpServletRequest request,
		HttpSession session) {
	String img_state = null;
	String type = getType(request); 

	if ("save".equals(type)) {
		img_state = Constants.NONE;
	} else {
		String[] sel_ids = (String[]) session
				.getAttribute(Constants.SEL_IDS);

		Integer pos = (Integer) session.getAttribute(Constants.POS);

		int i = pos.intValue();

		img_state = getImgState(i, sel_ids); 
	}
	return img_state;
}
/**
 * 
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

//
public ActionForward next(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	HttpSession session = request.getSession();
	Integer pos = (Integer) session.getAttribute(Constants.POS);

	int i = pos.intValue() + 1; 

	return batch_ChangeID(mapping, form, request, response, session, i);
}


public ActionForward prior(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

	HttpSession session = request.getSession();
	Integer pos = (Integer) session.getAttribute(Constants.POS);

	int i = pos.intValue() - 1;

	return batch_ChangeID(mapping, form, request, response, session, i);
}




	
	
	/**
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
		saveMsg(request, messages, "goods.deleted");

		return search(mapping, form, request, response);
	}

    
}
