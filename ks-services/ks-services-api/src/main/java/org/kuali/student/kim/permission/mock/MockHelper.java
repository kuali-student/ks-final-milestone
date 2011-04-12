/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.kim.bo.group.dto.GroupInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.bo.types.dto.KimTypeInfo;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.support.KimRoleTypeService;
import org.kuali.rice.kim.service.support.KimTypeService;

/**
 *
 * @author nwright
 */
public class MockHelper {

    public AttributeSet copy(AttributeSet info) {
        if (info == null) {
            return null;
        }
        AttributeSet copy = new AttributeSet(info);
        return copy;
    }

    public GroupInfo copy(GroupInfo info) {
        GroupInfo copy = new GroupInfo();
        copy.setActive(info.isActive());
        copy.setAttributes(this.copy(info.getAttributes()));
        copy.setGroupDescription(info.getGroupDescription());
        copy.setGroupId(info.getGroupId());
        copy.setGroupName(info.getGroupName());
        copy.setKimTypeId(info.getKimTypeId());
        copy.setNamespaceCode(info.getNamespaceCode());
        return copy;
    }
}
