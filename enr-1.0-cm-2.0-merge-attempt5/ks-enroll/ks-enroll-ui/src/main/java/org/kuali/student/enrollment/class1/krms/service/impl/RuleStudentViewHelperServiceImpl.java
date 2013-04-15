package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.krms.service.RuleStudentViewHelperService;
import org.kuali.student.enrollment.class1.krms.service.TemplateResolverService;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleStudentViewHelperServiceImpl extends ViewHelperServiceImpl implements RuleStudentViewHelperService{

    private TemplateResolverService templateResolverService = new TempResolverServiceMockImpl();

    @Override
    public String getTermSpecificationForType(String type) {
        return templateResolverService.getTermSpecificationForType(type);
    }

    @Override
    public String getOperationForType(String type) {
        return templateResolverService.getOperationForType(type);
    }

    @Override
    public String getValueForType(String type) {
        return templateResolverService.getValueForType(type);
    }

    @Override
    public String getComponentForTermSpec(String termSpecId) {
        return templateResolverService.getComponentForTermSpec(termSpecId);
    }
}
