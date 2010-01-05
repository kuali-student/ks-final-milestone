/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LOPicker extends HorizontalPanel{ 
    TextBox loTextBox = new TextBox();
    LOCategoryBuilder loCategoryBuilder;
    public LOPicker(String messageGroup, String type, String state) {
        super();
        loCategoryBuilder = new LOCategoryBuilder(messageGroup, type, state);
        
        loTextBox.setPixelSize(200, 50);
        super.add(loTextBox);
        super.add(loCategoryBuilder);
    }
    public void setLOCategory(ModelDTOValue modelDTOValue){
        loCategoryBuilder.setValue(modelDTOValue);
    }
    public ModelDTOValue getLoCategory(){
        return loCategoryBuilder.getValue();
    }
    public String getLOText(){
        return loTextBox.getText();
    }
    public void setLOText(String value){
        loTextBox.setText(value);
    }
    
}
