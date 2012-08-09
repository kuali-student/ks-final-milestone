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

package org.kuali.student.lum.common.client.lo;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.layout.VerticalFlowPanel;
import org.kuali.student.common.ui.client.widgets.list.HasSelectionChangeHandlers;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeEvent;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends HorizontalPanel implements HasSelectionChangeHandlers{
	KSLabel countLabel = new KSLabel();
    KSTextArea loTextArea;
    VerticalFlowPanel vp = new VerticalFlowPanel();
    LOCategoryBuilder loCategoryBuilder;
    Data metaInfoData; // temporary storage of metaInfo data this is needed when LO is updated
    public LOPicker(String messageGroup, String type, String state, String loRepoKey, int maxLength) {
        super();
        this.loTextArea = new KSTextArea(countLabel, maxLength);
        loCategoryBuilder = new LOCategoryBuilder(messageGroup, type, state, loRepoKey);
        loTextArea.removeStyleName("ks-form-module-elements");
        loTextArea.addStyleName("KS-LOTextArea");
        countLabel.addStyleName("ks-form-module-elements-help-text");
        countLabel.addStyleName("loText-count-label");
        vp.add(loTextArea);
        vp.add(countLabel);
        super.add(vp);
        super.add(loCategoryBuilder);
        
        loTextArea.addValueChangeHandler(new ValueChangeHandler<String>(){
			public void onValueChange(ValueChangeEvent<String> event) {
				fireChangeEvent();
			}        	
        });
        
        loCategoryBuilder.addValueChangeHandler(new ValueChangeHandler<List<LoCategoryInfo>>(){
			public void onValueChange(
					ValueChangeEvent<List<LoCategoryInfo>> event) {
				fireChangeEvent();
			}        	
        });
    }
    public void setLOCategories(List<LoCategoryInfo> categories){
        loCategoryBuilder.setValue(categories);
    }
    public List<LoCategoryInfo> getLoCategories(){
        return loCategoryBuilder.getValue();
    }
    public String getLOText(){
        return this.loTextArea.getText();
    }
    public void setLOText(String value){
        this.loTextArea.setText(value);
    }
    public Data getMetaInfoData() {
        return metaInfoData;
    }
    public void setMetaInfoData(Data metaInfoData) {
        this.metaInfoData = metaInfoData;
    }
	
    @Override
	public HandlerRegistration addSelectionChangeHandler(SelectionChangeHandler handler) {
    	return addHandler(handler, SelectionChangeEvent.getType());
    }
    
    public boolean hasChangeHandler(){
    	return this.getHandlerCount(SelectionChangeEvent.getType()) > 0;
    }
	
    private void fireChangeEvent(){
    	SelectionChangeEvent.fire(this);
    }
    @Override
    protected void onEnsureDebugId(String baseID) {
        super.onEnsureDebugId(baseID);
        loTextArea.ensureDebugId(baseID);
        loCategoryBuilder.ensureDebugId(baseID);
    }
    
    
}
