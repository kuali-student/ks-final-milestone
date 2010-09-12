/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.configurable;

import java.util.ArrayList;

import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

import com.google.gwt.user.client.ui.Composite;

@Deprecated
public abstract class LayoutSection<T extends Idable> extends Composite {
    private String sectionTitle = null;
    private String instructions = null;
    private ConfigurableLayout<T> parentLayout = null;
    private ArrayList<LayoutSection> layoutSectionList = new ArrayList<LayoutSection>();

    public ArrayList<LayoutSection> getLayoutSectionList(){ 
      return layoutSectionList;
    }
    public String getSectionTitle() {
        return sectionTitle;
    }

    public LayoutSection<T> setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
        return this;
    }

    public String getInstructions() {
        return instructions;
    }

    public LayoutSection<T> setInstructions(String instructions) {
        this.instructions = instructions;
        return this;
    }

    public LayoutSection<T> setParentLayout(ConfigurableLayout<T> parentLayout) {
        this.parentLayout = parentLayout;
        return this;
    }

    public ConfigurableLayout<T> getParentLayout() {
        return parentLayout;
    }

    public ConfigurableLayoutSection<T> addSection(ConfigurableLayoutSection<T> section) {
        layoutSectionList.add(section);
        return null;
    }
    public void populateChildSection(){
        for (LayoutSection layoutSection : layoutSectionList) {
            layoutSection.populate();
        }
        
    }
    public void validateChildSection(Callback<ValidationResultInfo.ErrorLevel> callback) {
        for (LayoutSection layoutSection : layoutSectionList) {
            layoutSection.validate(callback);
        }
    }

    public abstract void validate(Callback<ValidationResultInfo.ErrorLevel> callback);

    public abstract void populate();

    public abstract void updateObject();

}
