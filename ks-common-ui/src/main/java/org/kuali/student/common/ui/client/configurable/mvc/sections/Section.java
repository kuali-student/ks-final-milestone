package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasLayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.RequiredEnum;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Widget;

public interface Section extends HasLayoutController{
	public static enum SectionState{NORMAL, ERROR, HIGHLIGHT};
	public void addField(FieldDescriptor field);
	public List<FieldDescriptor> getUnnestedFields();
	public void addSection(BaseSection section);
	public void addWidget(Widget w);
	public void setInstructions(String instructions);
	public void setMessage(String text, boolean show);
	public void showMessage(boolean show);
	public void clearValidation();
	public void clearValidationMessage(String fieldPath);
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results);
	public void setHighlight(SectionState state);
	public void clearHighlight();
	public void showValidationMessage(String fieldPath);
	public List<FieldDescriptor> getFields();
	public List<Section> getSections();
	public void setRequiredState(RequiredEnum required);
	public RequiredEnum getRequiredState();
	public SectionTitle getSectionTitle();
	public void setSectionTitle(SectionTitle sectionTitle);
	public void clear();
	public void redraw();
	public void resetFieldInteractionFlags();
	public void setFieldHasHadFocusFlags(boolean hadFocus);
	public void updateView(DataModel model);
	public void updateModel(DataModel model);
	public void setSectionValidationPanel(ValidationMessagePanel validationPanel);
	public void removeSection(BaseSection section);
	public void enableValidation(boolean enableValidation);
}
