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
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

/**
 * Wrapper class for <code>KeyDateInfo</code> dto.
 *
 * @author Kuali Student Team
 */
public class KeyDateWrapper extends TimeSetWrapper{

    private String keyDateType;
    private String keyDateNameUI;

    private KeyDateInfo keyDateInfo;
    private TypeInfo typeInfo;

    public KeyDateWrapper(){
        setAllDay(true);
        setDateRange(false);
        keyDateInfo = new KeyDateInfo();
        keyDateInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("test");
        keyDateInfo.setDescr(desc);
    }

    public KeyDateWrapper(KeyDateInfo keydate,boolean isCopy){

        this.setStartDate(keydate.getStartDate());
        this.setEndDate(keydate.getEndDate());
        this.setAllDay(keydate.getIsAllDay());
        this.setDateRange(keydate.getIsDateRange());
        this.setKeyDateType(keydate.getTypeKey());

        if (isCopy){
            this.setKeyDateInfo(new KeyDateInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(keydate.getTypeKey());
            getKeyDateInfo().setDescr(desc);
            getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        }else{
            this.setKeyDateInfo(keydate);
        }

        buildDateAndTime();
    }

    public String getKeyDateType() {
        return keyDateType;
    }

    public void setKeyDateType(String keyDateType) {
        this.keyDateType = keyDateType;
    }

    public KeyDateInfo getKeyDateInfo() {
        return keyDateInfo;
    }

    public void setKeyDateInfo(KeyDateInfo keyDateInfo) {
        this.keyDateInfo = keyDateInfo;
    }

    public String getKeyDateNameUI() {
        return keyDateNameUI;
    }

    public void setKeyDateNameUI(String keyDateNameUI) {
        this.keyDateNameUI = keyDateNameUI;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public boolean isNew() {
        return StringUtils.isBlank(keyDateInfo.getId());
    }

    //This is for UI display purpose
    public String getStartDateUI(){
        return formatStartDateUI(keyDateInfo.getStartDate());
    }

    //This is for UI display purpose
    public String getEndDateUI(){
        return formatEndDateUI(keyDateInfo.getEndDate());
    }

    @Override
    public boolean isAllDay(){
        if(!super.isAllDay()&&!this.isDateRange()){
            this.setAllDay(true);
        }
        return super.isAllDay();
    }

}
