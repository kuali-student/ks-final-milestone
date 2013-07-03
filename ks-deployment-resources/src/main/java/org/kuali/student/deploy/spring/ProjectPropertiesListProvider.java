package org.kuali.student.deploy.spring;

import java.util.List;

import org.kuali.common.util.property.ProjectProperties;

public interface ProjectPropertiesListProvider {

    List<ProjectProperties> getProjectProperties();

}
