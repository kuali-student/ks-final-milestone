/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.kuali.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import org.kuali.client.ProposalService;

/**
 *
 * @author simstu
 */
public class ProposalImpl extends RemoteServiceServlet implements ProposalService {
    public String myMethod(String s) {
        // Do something interesting with 's' here on the server.
        return "Server says: " + s;
    }
}
