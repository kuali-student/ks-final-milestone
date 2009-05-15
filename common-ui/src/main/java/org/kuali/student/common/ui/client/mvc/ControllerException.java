package org.kuali.student.common.ui.client.mvc;

/**
 * Thrown by the controller in situations where an error is caused by a configuration/code problem. (e.g. calling showView()
 * for a view that is not registered with a given controller.)
 * 
 * @author Kuali Student Team
 */
public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = 3080242531292487209L;

    public ControllerException() {
        super();
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

}
