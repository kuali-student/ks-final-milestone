/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.permission.map;

import java.util.LinkedHashMap;
import java.util.Map;
import org.kuali.rice.kim.api.group.Group;

/**
 *
 * @author nwright
 */
public class MapHelper {

    public Map<String,String> copy(Map<String,String> info) {
        if (info == null) {
            return null;
        }
        Map<String,String> copy = new LinkedHashMap<String,String>(info);
        return copy;
    }

    public Group copy(Group info) {
        Group.Builder copy = Group.Builder.create(info);
        copy.setId(null);
        return copy.build();
    }
}
