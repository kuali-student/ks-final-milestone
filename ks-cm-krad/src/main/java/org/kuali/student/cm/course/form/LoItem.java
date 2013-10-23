/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form;

public class LoItem {
    
    private int indentLevel = 0;
    
    private boolean selected;

    private LoDisplayInfoWrapper loWrapper;
    
    public void indent() {
      indentLevel++;
    }

    public void outdent() {
      indentLevel--;
    }

    public int getIndentLevel() {
      return indentLevel;
    }
    
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }    

    public LoDisplayInfoWrapper getLoWrapper() {
        return loWrapper;
    }

    public void setLoWrapper(LoDisplayInfoWrapper loWrapper) {
        this.loWrapper = loWrapper;
    }

}