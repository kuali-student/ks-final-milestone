package org.kuali.rice.student.kew.xml;

import java.io.InputStream;

import org.kuali.rice.core.exception.RiceRuntimeException;
import org.kuali.rice.kew.xml.UserXmlServiceImpl;

public class KSUserXmlServiceImpl extends UserXmlServiceImpl {
    @Override
	public void loadXml(InputStream inputStream, String principalId) {
        KSUserXmlParser parser = new KSUserXmlParser();
        try {
            parser.parseUsers(inputStream);
        } catch (Exception e) {
            throw new RiceRuntimeException("Error loading xml file", e);
        }
    }
}
