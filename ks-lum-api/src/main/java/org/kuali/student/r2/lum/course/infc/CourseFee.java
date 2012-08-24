package org.kuali.student.r2.lum.course.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Information about a fee related to a course.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
public interface CourseFee extends IdEntity {
    /**
     * A code that identifies the type of the fee. For example: Lab Fee or
     * Tuition Fee or CMF for Course Materials Fee.
     */
    public String getFeeType();

    /**
     * Indicates the structure and interpretation of the fee amounts, i.e.
     * Fixed, Variable, Multiple.
     */
    public String getRateType();

    /**
     * The amount or amounts associated with the fee. The number fee amounts and
     * interpretation depends on the rate type.
     */
    public List<? extends CurrencyAmount> getFeeAmounts();
}
