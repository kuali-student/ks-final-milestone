package org.kuali.rice.krms.service;

import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.TemplateInfo;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/11/29
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface TemplateRegistry {

    public TemplateInfo getTemplateForType(String type);

    public String getTermSpecNameForType(String type);

    public String getOperationForType(String type);

    public String getValueForType(String type);

    public ComponentBuilder getComponentBuilderForType(String type);

}
