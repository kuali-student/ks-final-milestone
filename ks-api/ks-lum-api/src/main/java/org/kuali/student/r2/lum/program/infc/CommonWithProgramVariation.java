package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.Date;
import java.util.List;

/*
 * fields that are common with program variation
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CommonWithProgramVariation extends CommonWithCoreProgram {

    /**
     * Indicates if the program is full time, part time, both etc
     * 
     * @name Intensity
     */
    public String getIntensity();

    /**
     * CIP 2000 Code for the Program
     */
    public String getCip2000Code();

    /**
     * CIP 2010 Code for the Program
     */
    public String getCip2010Code();

    /**
     * HEGIS Code for the Program
     */
    public String getHegisCode();

    /**
     * Specifies if the Major is Selective Major, Limited Enrollment program or
     * Selective Admissions
     */
    public String getSelectiveEnrollmentCode();

    /**
     * Date this program became effective
     * 
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * Title to print on the diploma
     * 
     * @name Diploma Title 
     */
    public String getDiplomaTitle();

    /**
     * Places where this program might be offered
     * 
     * @name Campus Locations
     */
    public List<String> getCampusLocations();

    /**
     * Result Option for the Program
     * 
     * This indicates the degrees that can be awarded from completing this program.
     * 
     * @name Result Options
     */
    public List<String> getResultOptions();

    /**
     * Standard Duration of the Program 
     * 
     * Typically expressed in years or semesters
     * 
     * @name Standard Duration
     */
    public TimeAmount getStdDuration();

    /**
     * Division Deployment for the program variation
     * 
     * @name Divisions Deployment
     */
    public List<String> getDivisionsDeployment();

    /**
     * @return
     */
    public List<String> getDivisionsFinancialResources();

    /**
     * @name Divisions Financial COntrol
     */
    public List<String> getDivisionsFinancialControl();

    /**
     * @name Units Deployment
     */
    public List<String> getUnitsDeployment();

    /**
     * @name Units Financial Resources
     */
    public List<String> getUnitsFinancialResources();

    /**
     * @name Units Financial Control
     */
    public List<String> getUnitsFinancialControl();
}
