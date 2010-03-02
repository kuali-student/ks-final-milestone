/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author simstu
 */
public interface ProposalAsync {
    public void myMethod(String s, AsyncCallback<String> callback);
}
