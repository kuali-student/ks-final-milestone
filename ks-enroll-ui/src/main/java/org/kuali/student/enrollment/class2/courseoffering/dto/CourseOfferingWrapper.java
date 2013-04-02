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
 * @author Kuali Student Team
 */

package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.CourseCrossListing;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* TODO : see https://jira.kuali.org/browse/KSENROLL-5931
*/

/**
 * Base class for all the wrappers around CourseOfferingInfo.
 *
 * This will be extended by the different Course Offering Wrappers (for now, it's Create and Edit)
 */
public class CourseOfferingWrapper implements Serializable{

    private CourseInfo course;
    private CourseOfferingInfo courseOfferingInfo;
    private TermInfo term;

    private String courseOfferingCode;
    private String courseOfferingDesc;

    private String courseOfferingId;
    private boolean isCrossListed;
    private List<String> alternateCOCodes;
    private List<String> ownerAliases;
    private String ownerCode;
    private String coOwningDeptName;

    private boolean isColocatedAoToDelete;
    /**
     * Usage of this property only in create and edit CO screens. Basically, this is whether to
     * allow the user to select cross lists or not. If it's false, then the cross
     * lists will be added to all the alternate codes.
     *
     * @see #isSelectCrossListingAllowed()
     */
    private Boolean selectCrossListingAllowed;

    /**
     * Added as a hack fix - the group dialog is expecting this property
     */
    protected String dialogExplanation;

    public CourseOfferingWrapper(){
        alternateCOCodes = new ArrayList<String>();
        ownerAliases = new ArrayList<String>();
    }

    /**
     * This is a normal wrapper for {link:#CourseOfferingInfo}
     * @param courseOfferingInfo
     */
    public CourseOfferingWrapper(CourseOfferingInfo courseOfferingInfo){
        this.courseOfferingInfo = courseOfferingInfo;
        this.alternateCOCodes = new ArrayList<String>();
        this.ownerAliases = new ArrayList<String>();
    }

    /**
     * This is based on Good citizen pattern. No setters for the cross listed properties. Those needs to be passed in
     * at the time of instance creation.
     *
     * @param isCrossListed whether this wrapper is for a cross listed course.
     * @param courseOfferingCode
     * @param courseOfferingDesc
     * @param alternateCOCodes
     * @param courseOfferingId
     */
    public CourseOfferingWrapper(boolean isCrossListed, String courseOfferingCode, String courseOfferingDesc,List<String> alternateCOCodes,String courseOfferingId){
        this.isCrossListed = isCrossListed;
        this.courseOfferingCode = courseOfferingCode;
        this.courseOfferingDesc = courseOfferingDesc;
        if (alternateCOCodes == null){
            this.alternateCOCodes = new ArrayList<String>();
        } else {
            this.alternateCOCodes = alternateCOCodes;
        }
        this.courseOfferingId = courseOfferingId;
    }

    public CourseOfferingWrapper(boolean isCrossListed, String courseOfferingCode, String courseOfferingDesc,List<String> alternateCOCodes,String courseOfferingId, String ownerCode, List<String> ownerAliases){
        this.isCrossListed = isCrossListed;
        this.courseOfferingCode = courseOfferingCode;
        this.courseOfferingDesc = courseOfferingDesc;
        if (alternateCOCodes == null){
            this.alternateCOCodes = new ArrayList<String>();
        } else {
            this.alternateCOCodes = alternateCOCodes;
        }
        this.courseOfferingId = courseOfferingId;
        this.ownerCode = ownerCode;
        if (ownerAliases == null){
            this.ownerAliases = new ArrayList<String>();
        } else {
            this.ownerAliases = ownerAliases;
        }
    }

    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(CourseInfo course) {
        this.course = course;
    }

    public CourseOfferingInfo getCourseOfferingInfo() {
        return courseOfferingInfo;
    }

    public void setCourseOfferingInfo(CourseOfferingInfo coInfo) {
        this.courseOfferingInfo = coInfo;
    }

    public TermInfo getTerm() {
        return term;
    }

    public void setTerm(TermInfo term) {
        this.term = term;
    }

    /**
     * @return
     */
    public String getCourseOfferingCode() {
        if (StringUtils.isNotBlank(courseOfferingCode)){
            return courseOfferingCode;
        } else {
            return courseOfferingInfo.getCourseOfferingCode();
        }

    }

    /**
     * @return
     */
    public String getCourseOfferingDesc() {
        if (isCrossListed()){
            return courseOfferingDesc;
        } else {
            return courseOfferingInfo.getCourseOfferingTitle();
        }
    }

    /**
     * @return
     */
    public String getCourseOfferingTitle() {
        return getCourseOfferingDesc();
    }

    /**
     * Returns the state key
     * @return
     */
    public String getStateKey(){
        return courseOfferingInfo.getStateKey();
    }

    /**
     * Returns the credit count
     */
    public String getCreditCnt(){
        return courseOfferingInfo.getCreditCnt();
    }

    /**
     *
     * @param alternateCOCodes
     */
    public void setAlternateCOCodes(List<String> alternateCOCodes) {
        this.alternateCOCodes = alternateCOCodes;
    }

    /**
     * @return
     */
    public List<String> getAlternateCOCodes() {
        return alternateCOCodes;
    }

    /**
     * @return
     */
    public boolean isCrossListed() {
        return isCrossListed;
    }

    /**
    * This method returns a list of crosslisted/official course code for a course as comma seperated
    * string
    *
     * @see #getAlternateCOCodesUITooltip()
    * @return
    */
   @SuppressWarnings("unused")
   public String getAlternateCOCodesUIList(){
       StringBuffer buffer = new StringBuffer();
       for (String code : alternateCOCodes){
           buffer.append(code + ", ");
       }

       return StringUtils.removeEnd(buffer.toString(), ", ");
   }

    /**
    * This method returns a list of crosslisted/official course code for a course. This will
    * be displayed as the tooltip (if crosslisted cos exists) at Manage CO screen.
    *
    * @see #getAlternateCOCodesUIList()
    * @return
    */
   @SuppressWarnings("unused")
   public String getAlternateCOCodesUITooltip(){
       StringBuffer buffer = new StringBuffer();
       for (String code : alternateCOCodes){
           buffer.append(code + "<br>");
       }
       return StringUtils.removeEnd(buffer.toString(), "<br>");
   }

    public List<String> getOwnerAliases() {
        return ownerAliases;
    }

    public void setOwnerAliases(List<String> ownerAliases) {
        this.ownerAliases = ownerAliases;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerAliasesUIList(){
        StringBuffer buffer = new StringBuffer();

        if(ownerAliases!=null){
            for (String code : ownerAliases){
                buffer.append(code + ", ");
            }
        }

        return StringUtils.removeEnd(buffer.toString(), ", ");
     }

    /**
     * This method returns the course offering id.
     *
     * @return
     */
    public String getCourseOfferingId() {
        if (StringUtils.isNotBlank(courseOfferingId)){
            return courseOfferingId;
        } else {
            return getCourseOfferingInfo().getId();
        }
    }

    public String getId() {
        return getCourseOfferingInfo().getId();
    }

    /**
     *
     * @see #setCoOwningDeptName(String)
     * @return
     */
    @SuppressWarnings("unused")
    public String getCoOwningDeptName() {
        return coOwningDeptName;
    }

    /**
     * This property is used to indicate the user which department owns the owner course
     * of a cross listed course.
     *
     * @param coOwningDeptName
     */
    public void setCoOwningDeptName(String coOwningDeptName) {
        this.coOwningDeptName = coOwningDeptName;
    }

    /**
     * This method returns a list of comma seperated alternate course codes.
     * This is used in create and edit course offerings screen.
     * @return
     */
    @SuppressWarnings("unused")
    public String getCrossListedCourseCodes(){
        StringBuilder builder = new StringBuilder();
        if (course != null){
            for (CourseCrossListing crossListing : course.getCrossListings()){
                builder.append(crossListing.getCode() + ", ");
            }
        }
        return StringUtils.removeEnd(builder.toString(), ", ");
    }

    public boolean isColocatedAoToDelete() {
        return isColocatedAoToDelete;
    }

    public void setColocatedAoToDelete(boolean colocatedAoToDelete) {
        isColocatedAoToDelete = colocatedAoToDelete;
    }

    /**
     * This method returns whether alternate code selection is allowed or not.
     * This method reads the configuration property <code>kuali.ks.enrollment.options.selective-crossListing-allowed</code>
     * and returns its value (true/false). This is to allow the institutional configuration to decide whether the users
     * should be allowed to pick alternate codes or just select all the alternate codes by default.
     *
     * @return boolean based on the configured property
     */
    public boolean isSelectCrossListingAllowed() {
         if (selectCrossListingAllowed == null) {
             String selectiveColocationAllowed = ConfigContext.getCurrentContextConfig().getProperty("kuali.ks.enrollment.options.selective-crossListing-allowed");
             if("false".equalsIgnoreCase(selectiveColocationAllowed)) {
                 selectCrossListingAllowed = false;
             } else {
                 selectCrossListingAllowed = true;
             };
         }

        return true;
    }

    public String getDialogExplanation() {
        return dialogExplanation;
    }

    public void setDialogExplanation(String dialogExplanation) {
        this.dialogExplanation = dialogExplanation;
    }

}
