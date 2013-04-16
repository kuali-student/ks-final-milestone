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
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Wrapper class for <code>TermInfo</code> dto.
 *
 * @author Kuali Student Team
 */
public class AcademicTermWrapper {

    private TermInfo termInfo;

    private String name;
    private int instructionalDays;
    private String termType;
    private Date startDate;
    private Date endDate;

    private String termNameForUI;

    private TypeInfo typeInfo;

    private List<KeyDatesGroupWrapper> keyDatesGroupWrappers;
    private List<KeyDateWrapper> keyDatesToDeleteOnSave;

    /**
     * This constructor sets all the default values.
     */
    public AcademicTermWrapper(){
        keyDatesGroupWrappers = new ArrayList();
        keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();
        termInfo = new TermInfo();
        termInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("Test");
        termInfo.setDescr(desc);
    }

    /**
     * Copy constructor. Populates data from the passed in term dto.
     *
     * @param termInfo source dto to copy from
     * @param isCopy whether to copy or not
     */
    public AcademicTermWrapper(TermInfo termInfo,boolean isCopy){

        this.startDate = termInfo.getStartDate();
        this.endDate = termInfo.getEndDate();
        this.termType = termInfo.getTypeKey();
        this.keyDatesGroupWrappers = new ArrayList();
        this.keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();

        if (isCopy){
            setTermInfo(new TermInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(termInfo.getTypeKey());
            getTermInfo().setDescr(desc);
            getTermInfo().setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        } else{
           setTermInfo(termInfo);
           this.name = termInfo.getName();
        }

    }

    /**
     * See <code>setName()</code>
     *
     * @return the name of the term
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for display purpose. This would display a term as <p>"Spring 2013"</p>.
     *
     * @param name to display
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * See <code>setTermType()</code>
     *
     * @return term type
     */
    public String getTermType() {
        return termType;
    }

    /**
     * Sets the term type from the drop down list. This is being
     * used only at the add line which allows user to pick an available term type
     *
     * @param termType term type
     */
    public void setTermType(String termType) {
        this.termType = termType;
    }

    /**
     * Returns the term start date
     *
     * @return start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * sets the term start date.
     *
     * @param startDate start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * See <code>setEndDate()</code>
     *
     * @return end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the term end date.
     *
     * @param endDate end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * See <code>setTermInfo()</code>
     *
     * @return term dto
     */
    public TermInfo getTermInfo() {
        return termInfo;
    }

    /**
     * <code>TermInfo</code> DTO associated with a term.
     *
     * @param termInfo term dto
     */
    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
    }

    /**
     * See <code>setInstructionalDays()</code>
     *
     * @return the no of instructional days in a term
     */
    @SuppressWarnings("unused")
    public int getInstructionalDays() {
        return instructionalDays;
    }

    /**
     * Sets the number of instructional days for ui display. It's just for display purpose and this is being
     * calculated at the service.
     *
     * @param instructionalDays instructional days
     */
    public void setInstructionalDays(int instructionalDays) {
        this.instructionalDays = instructionalDays;
    }

    /**
     * Term name for ui display purpose
     *
     * @return term name
     */
    public String getTermNameForUI() {
        return termNameForUI;
    }

    /**
     * Sets the term name for the ui display purpose
     *
     * @param termNameForUI term name
     */
    public void setTermNameForUI(String termNameForUI) {
        this.termNameForUI = termNameForUI;
    }

    /**
     * See <code>setKeyDatesGroupWrappers()</code>
     *
     * @return a list of associated key date groups
     */
    public List<KeyDatesGroupWrapper> getKeyDatesGroupWrappers() {
        return keyDatesGroupWrappers;
    }

    /**
     * Collection of KeyDate groups associated with a term.
     *
     * @param keyDatesGroupWrappers key date groups
     */
    public void setKeyDatesGroupWrappers(List<KeyDatesGroupWrapper> keyDatesGroupWrappers) {
        this.keyDatesGroupWrappers = keyDatesGroupWrappers;
    }

    /**
     * See <code>setKeyDatesToDeleteOnSave()</code>
     *
     * @return the terms marked for deletion which is already exists in the DB
     */
    public List<KeyDateWrapper> getKeyDatesToDeleteOnSave() {
        return keyDatesToDeleteOnSave;
    }

    /**
     * This collection is used to hold the terms which are marked for deletion on save. The terms exists
     * in this collection will be already present in the DB.
     *
     * @param keyDatesToDeleteOnSave marked key dates to delete on save
     */
    public void setKeyDatesToDeleteOnSave(List<KeyDateWrapper> keyDatesToDeleteOnSave) {
        this.keyDatesToDeleteOnSave = keyDatesToDeleteOnSave;
    }

    /**
     * Resets the form fields.
     */
    public void clear(){
        setEndDate(null);
        setStartDate(null);
        setTermType(null);
        setName(null);
        setTypeInfo(null);
        keyDatesToDeleteOnSave.clear();
    }

    /**
     * Type Info
     *
     * @return type info
     */
    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    /**
     * Type DTO for the term type.
     *
     * @param typeInfo type info
     */
    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    /**
     * Returns whether the term is official or not. This is being used at the UI to render fields.
     * There would be no reference for this method in the code as it has it's reference at the
     *
     * @return true if the term is official
     */
    @SuppressWarnings("unused")
    public boolean isOfficial() {
        return StringUtils.equals(termInfo.getStateKey(), AcademicCalendarServiceConstants.TERM_OFFICIAL_STATE_KEY);
    }

    /**
     * Returns true if the Acal DTO is new. If it's new, it doesnt have an id.
     *
     * @return
     */
    public boolean isNew() {
        return StringUtils.isBlank(termInfo.getId());
    }

    /**
     * This method validates whether the key date group already exists or not. This is being use to validate
     * users not to add duplicates
     *
     * @param keydateGroupTypeKey
     * @return
     */
    public boolean isKeyDateGroupExists(String keydateGroupTypeKey){
        for(KeyDatesGroupWrapper wrapper : keyDatesGroupWrappers){
            if (StringUtils.equalsIgnoreCase(wrapper.getKeyDateGroupType(),keydateGroupTypeKey)){
                return true;
            }
        }
        return false;
    }

}
