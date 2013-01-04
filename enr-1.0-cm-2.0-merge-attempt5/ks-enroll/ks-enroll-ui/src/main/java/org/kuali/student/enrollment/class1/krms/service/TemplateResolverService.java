package org.kuali.student.enrollment.class1.krms.service;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/11/29
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateResolverService {

    public String getTermSpecificationForType(String type);

    public String getOperationForType(String type);

    public String getValueForType(String type);

    public String getComponentForTermSpec(String termSpecId);
}
