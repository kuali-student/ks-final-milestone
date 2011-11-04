package org.kuali.student.r2.lum.program.infc;

import org.kuali.student.common.dto.MetaInfo;
import org.kuali.student.common.dto.RichTextInfo;
import org.kuali.student.common.dto.TimeAmountInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.infc.TimeAmount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sambitpatnaik
 * Date: 10/27/11
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ProgramVariation  extends IdEntity{

        /**
     * Indicates if the program is full time, part time, both etc
     */
    public String getIntensity() ;

    /**
     * An URL for additional information about the Variation.
     */
    public String getReferenceURL();

    /**
     * The composite string that is used to officially reference or publish the Variation. Note it may have an internal structure that each Institution may want to enforce.
     */
    public String getCode() ;

    /**
     * CIP 2000 Code for the Program
     */
    public String getCip2000Code() ;

    /**
     * CIP 2010 Code for the Program
     */
    public String getCip2010Code();

    /**
     * HEGIS Code for the Program
     */
    public String getHegisCode() ;

    /**
     * University specific classification e.g Major(Bacc), Specialization
     */
    public String getUniversityClassification() ;

    /**
     * Specifies if the Variation is Limited Enrollment program or Selective Admissions
     */
    public String getSelectiveEnrollmentCode() ;


    public List<String> getResultOptions() ;

    public TimeAmount getStdDuration() ;

    /**
     * The first academic time period that this Variation would be effective. This may not reflect the first "real" academic time period for this Variation.
     */
    public String getStartTermKey();
    /**
     * The last academic time period that this Variation would be effective.
     */
    public String getEndTermKey() ;

    /**
     * The last academic time period that this Variation would be available for enrollment. This may not reflect the last "real" academic time period for this Variation.
     */
    public String getEndProgramEntryTermKey();

    /**
     * Date and time the Variation became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() ;

    /**
     * Abbreviated name of the Variation
     */
    public String getShortTitle() ;
    /**
     * Full name of the Variation Discipline
     */
    public String getLongTitle() ;

    /**
     * Information related to the official identification of the Variation, typically in human readable form. Used to officially reference or publish.
     */
    public String getTranscriptTitle();

    /**
     *
     */
    public String getDiplomaTitle() ;
    /**
     * Narrative description of the Variation that will show up in Catalog
     */
    public RichText getCatalogDescr() ;

    /**
     * List of catalog targets where program variation information will be published.
     */
    public List<String> getCatalogPublicationTargets() ;

    /**
     * Learning Objectives associated with this Variation.
     */
    public List<LoDisplayInfo> getLearningObjectives();

    /**
     * Places where this Variation might be offered
     */
    public List<String> getCampusLocations() ;

    /**
     * Program Variation Requirements.
     */
    public List<String> getProgramRequirements() ;

    /**
     *
     * @return
     */
    public List<String> getDivisionsContentOwner() ;

    /**
     *
     * @return
     */
    public List<String> getDivisionsStudentOversight() ;

    /**
     *
     * @return
     */
    public List<String> getDivisionsDeployment() ;

    /**
     *
     * @return
     */
    public List<String> getDivisionsFinancialResources();

    /**
     *
     * @return
     */
    public List<String> getDivisionsFinancialControl();

    /**
     *
     * @return
     */
    public List<String> getUnitsContentOwner() ;

    /**
     *
     * @return
     */
    public List<String> getUnitsStudentOversight();

    /**
     *
     * @return
     */
    public List<String> getUnitsDeployment() ;

    /**
     *
     * @return
     */
    public List<String> getUnitsFinancialResources();

    /**
     *
     * @return
     */
    public List<String> getUnitsFinancialControl();

}
