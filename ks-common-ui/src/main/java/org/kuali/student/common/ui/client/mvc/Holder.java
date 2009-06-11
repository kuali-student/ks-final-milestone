package org.kuali.student.common.ui.client.mvc;

/**
 * 
 * Used to hold a reference to an object in cases where the declared reference needs to be final.
 * For example, when using anonymous classes for asynchronous callbacks, a Holder can be used for
 * a mutable value within the callback.
 * 
 * @author Kuali Student Team
 *
 */
public class Holder<T extends Object> {
    private T value = null;
    public Holder() {
    	super();
    }
    public Holder(T value) {
    	this.value = value;
    }
    public T get() {
        return value;
    }
    public void set(T value) {
        this.value = value;
    }
}
