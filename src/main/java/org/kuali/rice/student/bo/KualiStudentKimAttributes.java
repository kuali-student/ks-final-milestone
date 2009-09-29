/**
 * 
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

	public static final String QUALIFICATION_PROPOSAL_ID   = "proposalId";
	public static final String QUALIFICATION_CLU_ID        = "cluId";
	public static final String QUALIFICATION_DEPARTMENT_ID = "departmentId";
	public static final String QUALIFICATION_DEPARTMENT    = "department";
	public static final String QUALIFICATION_DIVISION      = "division";
	public static final String QUALIFICATION_DIVISION_ID   = "divisionId";
	public static final String QUALIFICATION_COLLEGE       = "college";
	public static final String QUALIFICATION_COLLEGE_ID    = "collegeId";
	public static final String QUALIFICATION_ORG_ID        = "orgId";
	public static final String QUALIFICATION_ORG           = "org";

	protected String department;
	protected String college;
	protected String division;

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
	
	@Override
	protected LinkedHashMap toStringMapper() {
		LinkedHashMap m = new LinkedHashMap();
		return m;
	}



}