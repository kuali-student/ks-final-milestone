package org.kuali.student.lum.lu.ui.course.client.configuration;

import org.kuali.student.common.ui.client.configurable.mvc.views.VerticalSectionView;
import org.kuali.student.lum.common.client.lo.LUConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;

public class CdmCourseConfigurer extends CourseConfigurer{
    public SectionView generateCourseInfoSection() {
        VerticalSectionView section = initSectionView(CourseSections.COURSE_INFO, LUConstants.INFORMATION_LABEL_KEY);
        addField(section, PROPOSAL_TITLE_PATH, generateMessageInfo(LUConstants.PROPOSAL_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + COURSE_TITLE, generateMessageInfo(LUConstants.COURSE_TITLE_LABEL_KEY));
        addField(section, COURSE + "/" + TRANSCRIPT_TITLE, generateMessageInfo(LUConstants.SHORT_TITLE_LABEL_KEY));

        //addField(section, COURSE + "/" + "changeCategory", generateMessageInfo("cluChangeCategory"), new KSCheckBox(getLabel("cluChangeCategoryText")));
        addField(section, COURSE + "/" + "changeCategory", generateMessageInfo("cluChangeCategory"));
        section.addSection(generateCourseNumberSection());
        FieldDescriptor instructorsFd = addField(section, COURSE + "/" + INSTRUCTORS, generateMessageInfo(LUConstants.INSTRUCTORS_LABEL_KEY));
        instructorsFd.setWidgetBinding(new KeyListModelWigetBinding("personId"));
        section.addSection(generateDescriptionRationaleSection());

        return section;
    }
}
