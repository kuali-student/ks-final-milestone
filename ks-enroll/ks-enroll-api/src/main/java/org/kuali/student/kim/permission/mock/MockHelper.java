/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.kim.api.group.Group;

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

    public Group copy(Group info) {
        Group.Builder copy = Group.Builder.create(info);
        copy.setId(null);
        return copy.build();
    }
}
