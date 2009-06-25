package org.kuali.student.common.ui.client.mvc;

/**
 * Passed to ModelRequestCallback when the model is not found.
 * 
 * @author Kuali Student Team
 */
public class ModelNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1631884923519293731L;
    private final Class<?> requestedType;

    /**
     * This constructs a ModelNotFoundException
     * 
     * @param message
     * @param requestedType
     *            the type associated with the model request
     */
    public ModelNotFoundException(String message, Class<?> requestedType) {
        super(message);
        this.requestedType = requestedType;
    }

    /**
     * Returns the type associated with the model request
     * 
     * @return the type associated with the model request
     */
    public Class<?> getRequestedType() {
        return requestedType;
    }

}
