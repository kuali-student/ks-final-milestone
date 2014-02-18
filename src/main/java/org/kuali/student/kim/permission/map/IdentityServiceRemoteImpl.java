/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.map;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.student.r2.common.util.constants.KimIdentityServiceConstants;

/**
 *
 * @author nwright
 */
public class IdentityServiceRemoteImpl extends IdentityServiceDecorator {

    private String hostUrl;

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
        if (hostUrl == null) {
            this.setNextDecorator(null);
            return;
        }
        URL wsdlURL;
        try {
            String urlStr = hostUrl + "/services/soap/kim/v2_0/" + KimIdentityServiceConstants.SERVICE_NAME_LOCAL_PART + "?wsdl";
            wsdlURL = new URL(urlStr);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        }
        QName qname = new QName(KimIdentityServiceConstants.NAMESPACE, KimIdentityServiceConstants.SERVICE_NAME_LOCAL_PART);
        Service factory = Service.create(wsdlURL, qname);
        IdentityService port = factory.getPort(IdentityService.class);
        this.setNextDecorator(port);
    }
}
