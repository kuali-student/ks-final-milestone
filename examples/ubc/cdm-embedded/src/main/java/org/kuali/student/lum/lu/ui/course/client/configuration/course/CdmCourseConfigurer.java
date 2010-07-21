package org.kuali.student.lum.lu.ui.course.client.configuration.course;

import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

	
public class CdmCourseConfigurer extends CourseConfigurer{
    protected VerticalSection generateInstructorsSection() {
        VerticalSection instructors = initSection(getH3Title("UBC Instructor"), WITH_DIVIDER);
        // FIXME wilj: do we need to set the instructor's orgId? or can we default it at the assembler level?
        addField(instructors, COURSE + "/" + PRIMARY_INSTRUCTOR);
        return instructors;
	}
}
