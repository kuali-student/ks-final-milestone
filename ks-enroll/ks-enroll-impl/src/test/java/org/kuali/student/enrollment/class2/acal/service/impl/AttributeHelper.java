/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.acal.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.kuali.student.r2.common.dto.AttributeInfo;

/**
 * Helps create a dynamic 
 * @author nwright
 */
public class AttributeHelper {
    
    public AttributeInfo toAttribute(String key, String value) {
        if (key == null) {
            return null;
        }
        AttributeInfo info = new AttributeInfo();
        info.setKey(key);
        info.setValue(value);
        return info;
    }
    
    public List<AttributeInfo> findAttributes(List<AttributeInfo> attrs, String key) {
        List<AttributeInfo> list = new ArrayList<AttributeInfo>();
        for (AttributeInfo info : attrs) {
            if (key.equals(info.getKey())) {
                list.add(info);
            }
        }
        return list;
    }
   
}
