package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.Date;
import java.util.List;

/**
 * Detailed information about major program variations
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface ProgramVariation extends ProgramAttributes {

    /**
     * Indicates if the program is full time, part time, both etc
     * 
     * @name Intensity
     */
    public String getIntensity();

    /**
     * An URL for additional information about the Variation.
     * 
     * @name Reference URL
     */
    public String getReferenceURL();

    /**
     * 
     * @name Effective Date
     */
    public Date getEffectiveDate();

    /**
     * Result Option for the Program Variation
     * 
     * @name Result Options
     */
    public List<String> getResultOptions();

    /**
     * Standard Duration of the Program  Variation.
     * 
     * @name Standard Duration
     */
    public TimeAmount getStdDuration();

    /**
     * Places where this Variation might be offered
     * 
     * @name Campus Locations
     */
    public List<String> getCampusLocations();

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
