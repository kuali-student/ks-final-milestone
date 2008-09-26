/**
 *
 */
package org.kuali.student.rules.internal.common.statement.propositions;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;

/**
 * A constraint that compares a constrained property (fact) to a given criterion. The threshold may be defined as minimum or
 * maximum.
 * 
 * @param <E>
 *            the type being constrained.
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public class SimpleComparableProposition<T> extends AbstractProposition<T> {

    // ~ Instance fields --------------------------------------------------------

    T fact;

    // ~ Constructors -----------------------------------------------------------

    public SimpleComparableProposition() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SimpleComparableProposition(String propositionName, ComparisonOperator operator, String expectedValue, T fact) {
        super(propositionName, operator, expectedValue);

        this.fact = fact;
    }

    // ~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
    * @see org.kuali.rules.constraint.Constraint#apply()
    */
    @Override
    public Boolean apply() {
        sanityCheck();

        // TODO: Type unsafe. Should be changed to a conversion based on the type value of LHS
        T expectedValue = (T) expectedValueAsString;

        result = checkTruthValue(fact, expectedValue);

        cacheReport("%s NOT %s %s");

        return result;
    }

    /* (non-Javadoc)
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    protected void cacheReport(String format, Object... args) {

        if (result) {
            report.setSuccessMessage("comparison met");
        } else {
            String rpt = String.format(format, fact, operator, expectedValueAsString);
            report.setFailureMessage(rpt);
        }

    }

    private void sanityCheck() {

        if (fact == null)
            throw new IllegalStateException(getClass().getName() + ":  no fact to compare");
    }

    /**
     * @return the fact
     */
    public T getFact() {
        return fact;
    }

    /**
     * @param fact
     *            the fact to set
     */
    public void setFact(T fact) {
        this.fact = fact;
    }
}
