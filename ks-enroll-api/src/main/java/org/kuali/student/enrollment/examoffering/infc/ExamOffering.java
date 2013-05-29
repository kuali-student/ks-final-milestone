package org.kuali.student.enrollment.examoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab (mezba.mahtab@utoronto.ca)
 * Date: 1/18/13
 * Time: 9:39 AM
 * Represents an ExamOffering in the ExamOffering Service. An ExamOffering is an
 * instantiation of a canonical exam, that is scheduled to be offered in an exam period.
 *
 * IMPLEMENTATION NOTE: A Clu requires a start date. The start date for the canonical
 * exam can be the date it's created.
 */
public interface ExamOffering extends IdEntity {

    /**
     * A term (exam period) this exam offering is tied to
     * @return a term id of the exam period this exam is offered for
     * @readonly
     * @required
     */
    public String getExamPeriodId();

    /**
     * The canonical exam referred to by this exam offering.
     * @readonly
     * @return an identifier for the canonical exam this offering is created from
     * @required
     */
    public String getExamId();

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
