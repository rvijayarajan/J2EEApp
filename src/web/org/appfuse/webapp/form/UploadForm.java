package org.appfuse.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;


/**
 * This class is modeled after the UploadForm from the struts-upload example
 * application. For more information on implementation details, please
 * see that application.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @version $Revision: 1.1 $ $Date: 2010/08/02 01:52:15 $
 * 
 * @struts.form name="uploadForm"
 */
public class UploadForm extends BaseForm {
    
    public static final String ERROR_PROPERTY_MAX_LENGTH_EXCEEDED =
        "MaxLengthExceeded";

    /** The value of the text the user has sent as form data */
    protected String name;

    /** The file that the user has uploaded */
    protected FormFile file;

    /**
     * Retrieve the name the user has given the uploaded file
     *
     * @return the file's name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the uploaded file (by the user)
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve a representation of the file the user has uploaded
     *
     * @return FormFile the uploaded file
     */
    public FormFile getFile() {
        return file;
    }

    /**
     * Set a representation of the file the user has uploaded
     *
     * @param file the file to upload
     */
    public void setFile(FormFile file) {
        this.file = file;
    }

    /**
     * Check to make sure the client hasn't exceeded the maximum allowed upload size inside of this
     * validate method.
     */
     // Commented out to avoid: Unhandled Exception thrown: class java.lang.NullPointerException
     public ActionErrors validate(ActionMapping mapping,
                                  HttpServletRequest request) {
         ActionErrors errors = null;
         // has the maximum length been exceeded?
         Boolean maxLengthExceeded =
             (Boolean) request.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
         if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue())) {
             errors = new ActionErrors();
             errors.add(ERROR_PROPERTY_MAX_LENGTH_EXCEEDED,
                        new ActionMessage("maxLengthExceeded"));
         }
         return errors;
     }
}
