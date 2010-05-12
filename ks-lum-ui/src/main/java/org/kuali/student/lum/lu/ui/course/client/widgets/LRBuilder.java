/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.configurable.mvc.multiplicity.UpdatableMultiplicityComposite;
import org.kuali.student.common.ui.client.configurable.mvc.sections.GroupSection;
import org.kuali.student.common.ui.client.configurable.mvc.sections.Section;
import org.kuali.student.common.ui.client.configurable.mvc.sections.VerticalSection;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.field.layout.element.MessageKeyInfo;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.impl.SimpleListItems;
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
    	
    	addField(learningResultsSection, COURSE + "/" + CreditCourseConstants.GRADING_OPTIONS, generateMessageInfo(LUConstants.LEARNING_RESULT_ASSESSMENT_SCALE_LABEL_KEY));
        addField(learningResultsSection, COURSE + "/" + CreditCourseConstants.OUTCOME_OPTIONS, generateMessageInfo(LUConstants.LEARNING_RESULT_OUTCOME_LABEL_KEY), new LearningResultOutcomeList(COURSE + "/" + CreditCourseConstants.OUTCOME_OPTIONS));
        
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

        	addField(lrSection, CreditCourseLearningResultsConstants.OUTCOME_TYPE, generateMessageInfo(LUConstants.LEARNING_RESULT_OUTCOME_TYPE_LABEL_KEY),null, path);
            lrSection.nextLine();
          //  KSTextBox maxCreditsTextbox = new KSTextBox();
          //  addField(lrSection, CreditCourseLearningResultsConstants.OUTCOME_MAX_CREDITS, getLabel(LUConstants.LEARNING_RESULT_OUTCOME_MAX_CREDITS_LABEL_KEY), maxCreditsTextbox, path);
          //  lrSection.nextLine();
            
            return lrSection;
        }
    }
    
    protected MessageKeyInfo generateMessageInfo(String labelKey) {
        return new MessageKeyInfo(groupName, type, state, labelKey);
    }

    private VerticalSection initSection(SectionTitle title, boolean withDivider) {
        VerticalSection section = new VerticalSection();
        if (title !=  null) {
          title.addStyleName("ks-heading-page-section");
          section.getLayout().setLayoutTitle(title);
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

    protected FieldDescriptor addField(Section section, String fieldKey) {
    	return addField(section, fieldKey, null, null, null);
    }    
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey) {
    	return addField(section, fieldKey, messageKey, null, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget) {
    	return addField(section, fieldKey, messageKey, widget, null);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, String parentPath) {
        return addField(section, fieldKey, messageKey, null, parentPath);
    }
    protected FieldDescriptor addField(Section section, String fieldKey, MessageKeyInfo messageKey, Widget widget, String parentPath) {
        QueryPath path = QueryPath.concat(parentPath, fieldKey);
    	Metadata meta = modelDefinition.getMetadata(path);

    	FieldDescriptor fd = new FieldDescriptor(path.toString(), messageKey, meta);
    	if (widget != null) {
    		fd.setFieldWidget(widget);
    	}
    	section.addField(fd);
    	return fd;
    }
}
