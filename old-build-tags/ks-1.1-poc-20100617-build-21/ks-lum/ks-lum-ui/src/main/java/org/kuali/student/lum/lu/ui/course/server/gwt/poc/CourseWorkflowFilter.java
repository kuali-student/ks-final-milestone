package org.kuali.student.lum.lu.ui.course.server.gwt.poc;

import org.kuali.student.core.assembly.transform.WorkflowFilter;
import org.kuali.student.lum.course.dto.CourseInfo;

public class CourseWorkflowFilter extends WorkflowFilter{

	@Override
	public String deriveAppId(Object data) {
		CourseInfo course = (CourseInfo)data;
		return course.getId();
	}

	@Override
	public String deriveDocContentFromData(Object data) {
		CourseInfo course = (CourseInfo)data;
		
		return null;
	}

	@Override
	public Class<?> getType() {
		return CourseInfo.class;
	}

}
