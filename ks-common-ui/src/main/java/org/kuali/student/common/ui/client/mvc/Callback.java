package org.kuali.student.common.ui.client.mvc;
/**
 * 
 * Generic callback used in place of return values, in cases where a response may be asynchronous, or may 
 * require a call to another method that is asynchronous.
 * @author Kuali Student Team
 *
 */
public interface Callback <T extends Object> { 
    public void exec(T result);
}
