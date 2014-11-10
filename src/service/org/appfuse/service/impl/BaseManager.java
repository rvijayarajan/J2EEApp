package org.appfuse.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.DAO;
import org.appfuse.service.Manager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * 
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseManager implements Manager {
    protected final Log log = LogFactory.getLog(getClass());
    protected DAO dao = null;
    /***/
    protected static ApplicationContext ctx;
    /**
     * Convenience method to bind objects in Managers
     * @param name
     * @return Object
     */
    public Object getBean(String name) {
//        File currentPath = new File("test.txt");
//        String s = currentPath.getAbsolutePath();
//        System.out.println(s);
//        System.out.println(System.getProperty("user.dir"));
//        System.out.println(this.getClass().getResource(""));
        if (ctx == null) {
            String filePath = this.getClass().getClassLoader().getResource("").toString();
            int flag = filePath.indexOf("classes");
            filePath = filePath.substring(0,flag); 
            System.out.println(filePath);

            String[] paths = {filePath+"applicationContext-manager.xml",
            filePath+"applicationContext-hibernate.xml"};
            ctx = new ClassPathXmlApplicationContext(paths);
        }
        return ctx.getBean(name);
    }
    
    /**
     * @see org.appfuse.service.Manager#setDAO(org.appfuse.dao.DAO)
     */
    public void setDAO(DAO dao) {
        this.dao = dao;
    }
    
    /**
     * @see org.appfuse.service.Manager#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject(Class clazz, Serializable id) {
        return dao.getObject(clazz, id);
    }
    
    /**
     * @see org.appfuse.service.Manager#getObjects(java.lang.Class)
     */
    public List getObjects(Class clazz) {
        return dao.getObjects(clazz);
    }
    
    /**
     * @see org.appfuse.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject(Class clazz, Serializable id) {
        dao.removeObject(clazz, id);
    }
    
    /**
     * @see org.appfuse.service.Manager#saveObject(java.lang.Object)
     */
    public void saveObject(Object o) {
        dao.saveObject(o);
    }
    /**
     * 判断一个字符串是否为null或者""
     * @param str
     * @return boolean
     */
    public boolean isNullOrSpace(String str) {
        if(str != null && !str.trim().equals("") && !str.trim().equals("null")){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 判断一个字符串是否为null或者""
     * @param temp
     * @return boolean
     */
    public boolean isNullOrSpace(Long temp){
        if(temp != null && !temp.toString().equals("")){
            return false;
        }else{
            return true;
        }
    }
}
