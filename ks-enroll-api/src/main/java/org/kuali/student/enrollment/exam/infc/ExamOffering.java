package org.kuali.student.enrollment.exam.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab (mezba.mahtab@utoronto.ca)
 * Date: 1/18/13
 * Time: 9:39 AM
 * Represents an ExamOffering in the ExamOffering Service. An ExamOffering is an
 * instantiation of a canonical exam, that is scheduled to be offered in an exam period.
 */
public interface ExamOffering extends IdEntity {

    /**
     * A term this exam offering is tied to
     * @return a term id of the term this exam is offered for
     * @required
     */
    public String getTermId();

    /**
     * A term this exam offering is tied to
     * @return a term code of the term this exam is offered for
     * @required
     */
    public String getTermCode();

    /**
     * A term this exam offering is tied to
     * @return a term code of the term this exam is offered for
     * @required
     */
    public String getCanonicalExamId();

    /**
     * A format offering this exam offering is tied to
     * @return a format offering id if this exam is tied to one, else null
     */
    public String getFormatOfferingId();

    /**
     * A course offering this exam offering is tied to
     * @return a course offering id if this exam is tied to one, else null
     */
    public String getCourseOfferingId();

    /**
     * A schedule id if this exam offering is scheduled and thus has a schedule.
     *
     * No value will exist until the scheduling process has been completed for this exam offering.
     *
     * @return a schedule id if this exam is scheduled, else null
     */
    public String getScheduleId();

    /**
     * Indicates where this exam offering is in the scheduling process.
     *
     * @return the scheduling state type key
     * @readonly
     * @impl The scheduling state is a calculated field
     */
    public String getSchedulingStateKey();

}
