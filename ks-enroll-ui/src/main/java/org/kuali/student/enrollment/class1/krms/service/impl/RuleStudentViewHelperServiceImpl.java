package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class1.krms.service.RuleStudentViewHelperService;
import org.kuali.student.enrollment.class1.krms.service.TemplateResolverService;
import org.kuali.student.enrollment.class1.krms.util.KsKrmsRepositoryServiceLocator;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleStudentViewHelperServiceImpl extends ViewHelperServiceImpl implements RuleStudentViewHelperService{

    @Override
    public String getTermSpecificationForType(String type) {
        return KsKrmsRepositoryServiceLocator.getTemplateResolverService().getTermSpecificationForType(type);
    }

    @Override
    public String getOperationForType(String type) {
        return KsKrmsRepositoryServiceLocator.getTemplateResolverService().getOperationForType(type);
    }

    @Override
    public String getValueForType(String type) {
        return KsKrmsRepositoryServiceLocator.getTemplateResolverService().getValueForType(type);
    }

}
