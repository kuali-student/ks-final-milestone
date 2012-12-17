package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krms.framework.engine.expression.ComparisonOperator;
import org.kuali.student.enrollment.class1.krms.service.TemplateResolverService;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/11/29
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class TempResolverServiceMockImpl implements TemplateResolverService {

    public String getTermSpecificationForType(String type){
        if( "10016".equals(type)){
            return "10002";
        } else if ( "10018".equals(type)){
            return "10002";
        }
        return "10002";
    }

    public String getOperationForType(String type){
        if( "20000".equals(type)){
            return ComparisonOperator.GREATER_THAN_EQUAL.getCode();
        }
        return ComparisonOperator.EQUALS.getCode();
    }

    public String getValueForType(String type){
        if( "10016".equals(type)){
            return "true";
        } else if ( "10018".equals(type)){
            return "?";
        }
        return "true";
    }

    public String getComponentForTermSpec(String termSpecId){
        if ("20000".equals(termSpecId)){
            return "courseList";
        } else if ("20001".equals(termSpecId)){
            return "org";
        }
        return "course";
    }
}
