package org.kuali.rice.kim.bo.role.dto;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * NOTE: This class only exists as workaround to calling PermissionService see rice bug https://jira.kuali.org/browse/KULRICE-5957.
 * 
 * This class should be removed once the bug is fixed.
 * 
 * @author Kuali Student Team
 *
 */
@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public KimPermissionInfo createKimPermissionInfo() {
        return new KimPermissionInfo();
    }

    public KimPermissionTemplateInfo createKimPermissionTemplateInfo() {
        return new KimPermissionTemplateInfo();
    }
}
