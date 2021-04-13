/*
 * �������� 2006-4-7
 *
 * TODO Ҫ���Ĵ����ɵ��ļ���ģ�壬��ת��
 * ���� �� ��ѡ�� �� Java �� ������ʽ �� ����ģ��
 */

package org.appfuse.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @struts.action name="KillSession" path="/KillSession" scope="request"
*                parameter="method"
* 
*/
public class KillSession extends DispatchAction{
	public ActionForward kill(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{	if(request.getSession()!=null)
				request.getSession().invalidate();
		else return null;
			}
		catch(Exception e){
			
			return null;	
		}
		return null;
			
		
		
		
		
	}
	
	   /**
     * ���δָ��method������ø÷���
     */
    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return kill(mapping, form, request, response);
    }
}
