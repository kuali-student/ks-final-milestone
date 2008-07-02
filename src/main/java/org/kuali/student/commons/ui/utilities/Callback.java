package org.kuali.student.commons.ui.utilities;

/**
 * Generic callback to be used when a callback is needed but it is not desirable to create a new class.
 * 
 * @param <T>
 *            the type of object to be returned when the callback is called
 */
public interface Callback<T extends Object> {
    /**
     * Called the when callback is fired
     * 
     * @param result
     *            the result of the call which required a callback
     */
    public void onResult(T result);
}
