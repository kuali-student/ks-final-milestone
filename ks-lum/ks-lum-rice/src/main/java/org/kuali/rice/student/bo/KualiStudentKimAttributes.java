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

package org.kuali.rice.student.bo;

import java.util.LinkedHashMap;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.bo.ExternalizableBusinessObject;
import org.kuali.rice.krad.bo.TransientBusinessObjectBase;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;

/**
 * Data Dictionary entries for Kuali Student attributes needed by KIM
 * 
 */
public class KualiStudentKimAttributes implements ExternalizableBusinessObject {

	private static final long serialVersionUID = 6969156403877595025L;

	public static final String DOCUMENT_TYPE_NAME                   = KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME;

	public static final String QUALIFICATION_DEPARTMENT_ID          = "departmentId";
	public static final String QUALIFICATION_DIVISION_ID            = "divisionId";
	public static final String QUALIFICATION_COLLEGE_ID             = "collegeId";
//	public static final String QUALIFICATION_ORG_ID                 = "orgId";
    public static final String QUALIFICATION_ORG_ID                 = "org";
    public static final String QUALIFICATION_SUBJECT_AREA           = "subjectArea";
    public static final String QUALIFICATION_OFFERING_ADMIN_ORG_ID  = "offeringAdminOrgId";

	public static final String QUALIFICATION_DATA_ID                = StudentIdentityConstants.QUALIFICATION_DATA_ID;
	public static final String QUALIFICATION_CLU_ID                 = "cluId";
	public static final String QUALIFICATION_DTO_NAME               = "dtoName";
	public static final String QUALIFICATION_DTO_FIELD_KEY          = "dtoFieldKey";
	public static final String QUALIFICATION_FIELD_ACCESS_LEVEL     = "fieldAccessLevel";
	public static final String QUALIFICATION_SCREEN_COMPONENT       = "screenComponent";
	public static final String QUALIFICATION_SECTION_ID             = "sectionId";
    public static final String DESCEND_HIERARCHY                    = "descendHierarchy";
    public static final String KS_REFERENCE_TYPE_KEY                = "ksReferenceTypeKey";

    public static final String KS_CO_STATE_KEY                      = "coStateKey";
    public static final String KS_SOC_STATE_KEY                     = "socStateKey";

    public static final String KS_PERMISSION_EXPRESSION             = "permissionExpression";

	protected String dataId;
	protected String org;
	protected String department;
	protected String college;
	protected String division;
    protected String subjectArea;
    protected String offeringAdminOrgId;

	protected String dtoName;
	protected String dtoFieldKey;
	protected String fieldAccessLevel;
	protected String screenComponent;
	protected String sectionId;
    protected Boolean descendHierarchy;
    protected String ksReferenceTypeKey;

    protected String coStateKey;
    protected String socStateKey;

    protected String permissionExpression;

	/**
     * @return the dataId
     */
    public String getDataId() {
    	return dataId;
    }

	/**
     * @param dataId the dataId to set
     */
    public void setDataId(String dataId) {
    	this.dataId = dataId;
    }

	/**
     * @return the org
     */
    public String getOrg() {
    	return org;
    }

	/**
     * @param org the org to set
     */
    public void setOrg(String org) {
    	this.org = org;
    }

	/**
     * @return the department
     */
    public String getDepartment() {
    	return department;
    }

	/**
     * @param department the department to set
     */
    public void setDepartment(String department) {
    	this.department = department;
    }

	/**
     * @return the college
     */
    public String getCollege() {
    	return college;
    }

	/**
     * @param college the college to set
     */
    public void setCollege(String college) {
    	this.college = college;
    }

	/**
     * @return the division
     */
    public String getDivision() {
    	return division;
    }

	/**
     * @param division the division to set
     */
    public void setDivision(String division) {
    	this.division = division;
    }

	/**
     * @return the dtoName
     */
    public String getDtoName() {
    	return dtoName;
    }

	/**
     * @param dtoName the dtoName to set
     */
    public void setDtoName(String dtoName) {
    	this.dtoName = dtoName;
    }

	/**
     * @return the dtoFieldKey
     */
    public String getDtoFieldKey() {
    	return dtoFieldKey;
    }

	/**
     * @param dtoFieldKey the dtoFieldKey to set
     */
    public void setDtoFieldKey(String dtoFieldKey) {
    	this.dtoFieldKey = dtoFieldKey;
    }

	/**
     * @return the fieldAccessLevel
     */
    public String getFieldAccessLevel() {
    	return fieldAccessLevel;
    }

	/**
     * @param fieldAccessLevel the fieldAccessLevel to set
     */
    public void setFieldAccessLevel(String fieldAccessLevel) {
    	this.fieldAccessLevel = fieldAccessLevel;
    }

	/**
     * @return the screenComponent
     */
    public String getScreenComponent() {
    	return screenComponent;
    }

	/**
     * @param screenComponent the screenComponent to set
     */
    public void setScreenComponent(String screenComponent) {
    	this.screenComponent = screenComponent;
    }

	/**
     * @return the sectionId
     */
    public String getSectionId() {
    	return sectionId;
    }

	/**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(String sectionId) {
    	this.sectionId = sectionId;
    }

    /**
     * Gets the descendHierarchy attribute.
     * 
     * @return Returns the descendHierarchy.
     */
    public Boolean isDescendHierarchy() {
        return descendHierarchy;
    }

    /**
     * Sets the descendHierarchy attribute value.
     * 
     * @param descendHierarchy The descendHierarchy to set.
     */
    public void setDescendHierarchy(Boolean descendHierarchy) {
        this.descendHierarchy = descendHierarchy;
    }

    /**
     * @return the ksReferenceTypeKey
     */
    public String getKsReferenceTypeKey() {
        return ksReferenceTypeKey;
    }

    /**
     * @param ksReferenceTypeKey the ksReferenceTypeKey to set
     */
    public void setKsReferenceTypeKey(String ksReferenceTypeKey) {
        this.ksReferenceTypeKey = ksReferenceTypeKey;
    }


    public String getSocStateKey() {
        return socStateKey;
    }

    public void setSocStateKey(String socStateKey) {
        this.socStateKey = socStateKey;
    }

    public String getCoStateKey() {
        return coStateKey;
    }

    public void setCoStateKey(String coStateKey) {
        this.coStateKey = coStateKey;
    }

    public String getPermissionExpression() {
        return permissionExpression;
    }

    public void setPermissionExpression(String permissionExpression) {
        this.permissionExpression = permissionExpression;
    }

    public String getSubjectArea() {
        return subjectArea;
    }

    public void setSubjectArea(String subjectArea) {
        this.subjectArea = subjectArea;
    }

    public String getOfferingAdminOrgId() {
        return offeringAdminOrgId;
    }

    public void setOfferingAdminOrgId(String offeringAdminOrgId) {
        this.offeringAdminOrgId = offeringAdminOrgId;
    }

    @Override
    public void refresh() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        return "Fill Me Out";  //To change body of implemented methods use File | Settings | File Templates.
    }
}
