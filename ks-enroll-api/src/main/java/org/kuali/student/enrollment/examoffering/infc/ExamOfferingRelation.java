/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab (mezba.mahtab@utoronto.ca)
 * Date: 2/7/13
 * Time: 11:47 AM
 * Represents a relationship between a FormatOffering and an ExamOffering.
 */
package org.kuali.student.enrollment.examoffering.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.List;

/**
 * Detailed information about a relationship between a FormatOffering and an ExamOffering.
 *
 * @Version 2.0
 * @Author Mezba Mahtab mezba.mahtab@utoronto.ca
 */
public interface ExamOfferingRelation extends Relationship {

    /**
     * Unique identifier for a Format Offering.
     * @required
     * @readonly
     */
    public String getFormatOfferingId();

    /**
     * Unique identifier for an exam offering record.
     * @required
     * @readonly
     */
    public String getExamOfferingId();

    /**
     * Gets the list of ActivityOfferings that relate to this exam offering for
     * this format offering instance.
     */
    public List<String> getActivityOfferingIds();

    /**
     * Gets the list of Populations that relate to this exam offering for
     * this format offering instance.
     */
    public List<String> getPopulationIds();

}
