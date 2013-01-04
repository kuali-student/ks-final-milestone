package org.kuali.student.r2.common.assembler;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Charles
 * Date: 7/9/12
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HasDynamicAttributes<DtoType> {
    Map<String, DynamicAttrWrapper<DtoType>> getDynAttrMap();
}
