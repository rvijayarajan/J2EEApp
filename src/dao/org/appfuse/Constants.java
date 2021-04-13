package org.appfuse; 




/**
 * Constant values used throughout the application.
 *
 * <p>
 * <a href="Constants.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class Constants { 
    //~ Static fields/initializers =============================================

    /**
     * The application scoped attribute for persistence engine and class that
     * implements it
     */
    public static final String DAO_TYPE = "daoType";
    public static final String DAO_TYPE_HIBERNATE = "hibernate";

    /** Application scoped attribute for authentication url */
    public static final String AUTH_URL = "authURL";

    /** Application scoped attributes for SSL Switching */
    public static final String HTTP_PORT = "httpPort";
    public static final String HTTPS_PORT = "httpsPort";

    /** The application scoped attribute for indicating a secure login */
    public static final String SECURE_LOGIN = "secureLogin";

    /** The encryption algorithm key to be used for passwords */
    public static final String ENC_ALGORITHM = "algorithm";

    /** A flag to indicate if passwords should be encrypted */
    public static final String ENCRYPT_PASSWORD = "encryptPassword";

    /** File separator from System properties */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /** User home from System properties */
    public static final String USER_HOME =
        System.getProperty("user.home") + FILE_SEP;

    /**
     * The session scope attribute under which the breadcrumb ArrayStack is
     * stored
     */
    public static final String BREADCRUMB = "breadcrumbs";

    /**
     * The session scope attribute under which the User object for the
     * currently logged in user is stored.
     */
    public static final String USER_KEY = "currentUserForm";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_EDIT_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";

    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "admin";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "tomcat";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * Name of cookie for "Remember Me" functionality.
     */
    public static final String LOGIN_COOKIE = "sessionId";

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";
    
    
    //add new vary

     public static final String SEL_IDS = "sel_ids"; 

    public static final String POS = "pos"; 

    public static final String IMG_STATE = "img_state"; 
    
    public static final String ID = "id"; 

    public static final String IDS = "ids"; 

    public static final String NEXT = "next"; 

    public static final String PRIOR = "prior"; 

    public static final String NONE = "none"; 

    public static final String BOTH = "both"; 

    public static final String FROM = "from"; 

    public static final String DEL = "del"; 

    public static final String VIEW = "view";

    public static final String LIST = "list";

    public static final String DEL_MANY = "del_many"; 
    
    public static final String DEl_MANY_ONE = "del_many_one"; 

    public static final String VIEW_MANY = "view_many";

    public static final String LIST_MANY = "list_many";

    public static final String EDIT = "edit";

    public static final String EDIT_MANY = "edit_many";
    
    public static final String LIKE = "%";  
    
    public static final String PID = "pid"; 
    
    public static final String FORM = "form"; 
    
     
	public static final String before = "10";

	public static final String after = "3";
	

	
	public static final String LIST_GOODS="list_goods";
	public static final String goodsID="goodsID";
	
	
}