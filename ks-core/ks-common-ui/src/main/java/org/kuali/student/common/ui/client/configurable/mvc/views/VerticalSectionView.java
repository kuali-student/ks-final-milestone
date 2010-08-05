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

package org.kuali.student.common.ui.client.configurable.mvc.views;

import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.history.HistoryStackFrame;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;

import com.google.gwt.user.client.Window;

public class VerticalSectionView extends SectionView{

	
    public VerticalSectionView(Enum<?> viewEnum, String name, String modelId) {     
        super(viewEnum, name);
        this.modelId = modelId;
        SectionTitle sectionTitle = SectionTitle.generateH2Title(getName());
        layout = new VerticalFieldLayout(sectionTitle);
        this.add(layout);
    }

	
    /**
     * This updates the model
     * 
     * @see org.kuali.student.common.ui.client.mvc.View#updateModel()
     */
    @Override
    @SuppressWarnings("unchecked")
    public void updateModel() {
        if(model!=null && isValidationEnabled()){
            super.updateModel(model);
        }
    }


	
    @Override
    public void collectHistory(HistoryStackFrame frame) {
        // do nothing
    }

    @Override
    public void onHistoryEvent(HistoryStackFrame frame) {
        // do nothing
    }

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
