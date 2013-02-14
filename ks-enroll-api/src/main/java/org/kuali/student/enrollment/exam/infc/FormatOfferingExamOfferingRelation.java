/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab (mezba.mahtab@utoronto.ca)
 * Date: 2/7/13
 * Time: 11:47 AM
 * Represents a relationship between a FormatOffering and an ExamOffering.
 */
package org.kuali.student.enrollment.exam.infc;

import org.kuali.student.r2.common.infc.Relationship;

import java.util.List;

/**
 * Detailed information about a FormatOffering to ExamOffering relationship.
 *
 * @Version 2.0
 * @Author Mezba Mahtab mezba.mahtab@utoronto.ca
 */
public interface FormatOfferingExamOfferingRelation extends Relationship {

    /**
     * Unique identifier for a Format Offering.
     * @required
     */
    public String getFormatOfferingId();

    /**
     * Unique identifier for an exam offering record.
     * @required
     */
    public String getExamOfferingId();

    /**
     * Gets a list of the activity offering ids associated with this relationship.
     */
    public List<String> getActivityOfferingIds();

    /**
     * Gets a list of the population ids associated with this relationship.
     */
    public List<String> getPopulationIds();

}
