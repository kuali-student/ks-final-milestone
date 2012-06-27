package org.kuali.student.common.ui.client.security;

/**
 * 
 * The implementing university should override this class if they
 * want to generically handle failure during async calls.
 * <p>
 *
 */
public class AsyncCallbackFailureHandler {

    /**
     * 
     * Override this method to handle an async failure.
     * <p>
     * Return 'true' if KS should handle the exception
     * <p>
     * Return 'false' if you handled the exception and KS 
     * should NOT process it.
     * 
     * @param caught
     * @return
     */
    public boolean handleFailure(Throwable caught) {
        // By default we return true, which tells the KS code to
        // process it.
        return true;   
    }
}
