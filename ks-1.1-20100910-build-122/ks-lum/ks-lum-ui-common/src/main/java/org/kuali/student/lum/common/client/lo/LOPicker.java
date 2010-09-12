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

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends HorizontalPanel{ 
    TextArea loTextArea = new TextArea();
    LOCategoryBuilder loCategoryBuilder;
    Data metaInfoData; // temporary storage of metaInfo data this is needed when LO is updated
    public LOPicker(String messageGroup, String type, String state, String loRepoKey) {
        super();
        loCategoryBuilder = new LOCategoryBuilder(messageGroup, type, state, loRepoKey);
        loTextArea.removeStyleName("ks-form-module-elements");
        loTextArea.addStyleName("KS-LOTextArea");
        super.add(loTextArea);
        super.add(loCategoryBuilder);
    }
    public void setLOCategories(List<LoCategoryInfo> categories){
        loCategoryBuilder.setValue(categories);
    }
    public List<LoCategoryInfo> getLoCategories(){
        return loCategoryBuilder.getValue();
    }
    public String getLOText(){
        return loTextArea.getText();
    }
    public void setLOText(String value){
        loTextArea.setText(value);
    }
    public Data getMetaInfoData() {
        return metaInfoData;
    }
    public void setMetaInfoData(Data metaInfoData) {
        this.metaInfoData = metaInfoData;
    }
    
}
