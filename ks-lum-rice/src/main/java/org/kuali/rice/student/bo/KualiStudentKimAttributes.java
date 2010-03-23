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
package org.kuali.rice.student.bo;

import java.util.LinkedHashMap;

import org.kuali.rice.kns.bo.TransientBusinessObjectBase;

/**
 * @author delyea
 * 
 */
public class KualiStudentKimAttributes extends TransientBusinessObjectBase {

	private static final long serialVersionUID = 6969156403877595025L;

	public static final String QUALIFICATION_CLU_ID            = "cluId";
	public static final String QUALIFICATION_DEPARTMENT_ID     = "departmentId";
	public static final String QUALIFICATION_DEPARTMENT        = "department";
	public static final String QUALIFICATION_DIVISION          = "division";
	public static final String QUALIFICATION_DIVISION_ID       = "divisionId";
	public static final String QUALIFICATION_COLLEGE           = "college";
	public static final String QUALIFICATION_COLLEGE_ID        = "collegeId";
	public static final String QUALIFICATION_ORG_ID            = "orgId";
	public static final String QUALIFICATION_ORG               = "org";

	protected String department;
	protected String college;
	protected String division;

	protected String dtoName;
	protected String dtoFieldKey;
	protected String fieldAccessLevel;

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
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
	 * @param college
	 *            the college to set
	 */
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getDivision() {
		return division;
	}

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

	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();
		return m;
	}



}
