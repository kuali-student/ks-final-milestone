package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseLearningResultsConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LRBuilder extends Composite {

	public static final String COURSE = "course";
	
    private boolean WITH_DIVIDER = true;
    private boolean NO_DIVIDER = false;

    private static String type;
    private static String state;
    private static String groupName;

    private final DataModelDefinition modelDefinition;
	
	public LRBuilder(String luType, String luState, String luGroup, final DataModelDefinition modelDefinition) {
        super();
		
        this.modelDefinition = modelDefinition;
        type = luType;
        state = luState;
        groupName = luGroup;
	}

    public VerticalSection generateLearningResultsSection() {

    	VerticalSection learningResultsSection = initSection(getH3Title(LUConstants.LEARNING_RESULTS_LABEL_KEY), WITH_DIVIDER);
    	addField(learningResultsSection, COURSE + "/" + CreditCourseConstants.GRADING_OPTIONS, getLabel(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY));
        addField(learningResultsSection, COURSE + "/" + CreditCourseConstants.OUTCOME_OPTIONS, getLabel(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY), new LearningResultOutcomeList(COURSE + "/" + CreditCourseConstants.OUTCOME_OPTIONS));
        
        return learningResultsSection;
    }

    public class LearningResultOutcomeList extends UpdatableMultiplicityComposite {
    	private final String parentPath;
        public LearningResultOutcomeList(String parentPath){
        	super(StyleType.TOP_LEVEL);
        	this.parentPath = parentPath;
            setAddItemLabel(getLabel(LUConstants.ADD_LEARNING_RESULT_OUTCOME_LABEL_KEY));
            setItemLabel(getLabel(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY));
            setMinEmptyItems(1);
        }

        public Widget createItem() {
            String path = QueryPath.concat(parentPath, String.valueOf(getAddItemKey())).toString();
            GroupSection lrSection = new GroupSection();

        	addField(lrSection, CreditCourseLearningResultsConstants.OUTCOME_TYPE, getLabel(LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY),null, path);
            lrSection.nextLine();
          //  KSTextBox creditValueTextbox = new KSTextBox();
          //  addField(lrSection, CreditCourseLearningResultsConstants.OUTCOME_CREDIT_VALUE, getLabel(LUConstants.LEARNING_RESULT_OUTCOME_CREDIT_VALUE_LABEL_KEY), creditValueTextbox, path);
          //  lrSection.nextLine();
            
            return lrSection;
        }
    }

    private VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          title.addStyleName("ks-heading-page-section");
          section.setSectionTitle(title);
        }
        section.addStyleName(LUConstants.STYLE_SECTION);
        if (withDivider)
            section.addStyleName(LUConstants.STYLE_SECTION_DIVIDER);
        return section;
    }
    
    private String getLabel(String labelKey) {
        return Application.getApplicationContext().getUILabel(groupName, type, state, labelKey);
    }

    private SectionTitle getH3Title(String labelKey) {
        return SectionTitle.generateH1Title(getLabel(labelKey));
    }

    private FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(section, fieldKey, null, null, null);
    }    
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel) {
    	return addField(section, fieldKey, fieldLabel, null, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget) {
    	return addField(section, fieldKey, fieldLabel, widget, null);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, String parentPath) {
        return addField(section, fieldKey, fieldLabel, null, parentPath);
    }
    private FieldDescriptor addField(Section section, String fieldKey, String fieldLabel, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), fieldLabel, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }
}
