/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This is to group all the related KeyDates in a term.
 *
 * @author Kuali Student Team
 */
public class KeyDatesGroupWrapper {

    private String keyDateGroupType;
    private String keyDateGroupNameUI;
    private List<KeyDateWrapper> keydates;

    private TypeInfo typeInfo;

    public KeyDatesGroupWrapper(){
        keydates = new ArrayList<KeyDateWrapper>();
    }

    public KeyDatesGroupWrapper(String keydateGroupType,String nameUI){
        this.keydates = new ArrayList<KeyDateWrapper>();
        this.keyDateGroupType = keydateGroupType;
        this.keyDateGroupNameUI = nameUI;
    }

    public String getKeyDateGroupNameUI() {
        return keyDateGroupNameUI;
    }

    public void setKeyDateGroupNameUI(String keyDateGroupNameUI) {
        this.keyDateGroupNameUI = keyDateGroupNameUI;
    }

    public String getKeyDateGroupType() {
        return keyDateGroupType;
    }

    public void setKeyDateGroupType(String keyDateGroupType) {
        this.keyDateGroupType = keyDateGroupType;
    }

    public List<KeyDateWrapper> getKeydates() {
        return keydates;
    }

    /**
     * This is a list of keydates grouped by a type
     *
     * @param keydates
     */
    public void setKeydates(List<KeyDateWrapper> keydates) {
        this.keydates = keydates;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public boolean isKeyDateExists(String keyDateType){
        for (KeyDateWrapper keydate : keydates) {
            if (StringUtils.equals(keyDateType,keydate.getKeyDateType())){
                return true;
            }
        }
        return false;
    }

}
