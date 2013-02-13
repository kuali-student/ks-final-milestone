package org.kuali.student.krms.service;

import org.kuali.student.krms.dto.TemplateInfo;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/11/29
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateResolverService {

    public TemplateInfo getTemplateForType(String type);

    public String getTermSpecNameForType(String type);

    public String getOperationForType(String type);

    public String getValueForType(String type);

}
