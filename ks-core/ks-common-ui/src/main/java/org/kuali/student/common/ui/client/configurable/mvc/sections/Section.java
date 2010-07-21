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

package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.HasLayoutController;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.FieldLayout;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

import com.google.gwt.user.client.ui.Widget;

public interface Section extends HasLayoutController{
	public String addField(FieldDescriptor field);
	public String addSection(Section section);
	public String addSection(String key, Section section);
	public FieldLayout getLayout();
	public void removeField(String fieldKey);
	public void removeSection(String sectionKey);
	public void removeSection(Section section);
	public void removeField(FieldDescriptor field);
	public FieldDescriptor getField(String fieldKey);
	public Section getSection(String sectionKey);
	public List<FieldDescriptor> getUnnestedFields();
	public String addWidget(Widget widget);
	public void removeWidget(Widget widget);
	public void removeWidget(String key);
	public void resetFieldInteractionFlags();
	public void setFieldHasHadFocusFlags(boolean hadFocus);
	public void updateWidgetData(DataModel model);
	public void updateModel(DataModel model);
	public List<FieldDescriptor> getFields();
	public List<Section> getSections();
	public void enableValidation(boolean enableValidation);
	public boolean isValidationEnabled();
	public ErrorLevel processValidationResults(List<ValidationResultInfo> results);
	public boolean isDirty();
	public void resetDirtyFlags();
}
