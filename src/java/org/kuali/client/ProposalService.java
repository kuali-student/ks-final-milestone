/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author simstu
 */
@RemoteServiceRelativePath("proposal")
public interface ProposalService extends RemoteService {
    public String myMethod(String s);
}
