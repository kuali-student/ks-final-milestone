/**
 *
 */
package org.kuali.student.rules.statement;




/**
 * A constraint that compares a constrained  property (fact) to a given criterion.
 * The threshold may be defined as minimum or maximum.
 *
 * @param <E> the type being constrained.
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 *
 */
public class SimpleComparableProposition<T extends Comparable<? super T>>
    extends AbstractProposition<T> {

    //~ Instance fields --------------------------------------------------------

    T fact;

    //~ Constructors -----------------------------------------------------------

    public SimpleComparableProposition() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SimpleComparableProposition(String propositionName,
        T fact) {
        super(propositionName);

        this.fact = fact;
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
    * @see org.kuali.rules.constraint.Constraint#apply()
    */
    public Boolean apply(ComparisonOperator operator, T exptectedValue) {
        super.apply(operator, expectedValue);
        sanityCheck();

        result = checkTruthValue(fact);

        cacheReport("%s NOT %s %s");

        return result;
    }

    /* (non-Javadoc)
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override protected void cacheReport(String format, Object... args) {

        if (result) {
            report.setSuccessMessage("comparison met");
        } else {
            String rpt = String.format(format, fact, operator, expectedValue);
            report.setFailureMessage(rpt);
        }

    }

    private void sanityCheck() {

        if (fact == null)
            throw new IllegalStateException(getClass().getName() +
                ":  no fact to compare");
    }


    /**
     * @return the fact
     */
    public T getFact() {
        return fact;
    }

    /**
     * @param fact the fact to set
     */
    public void setFact(T fact) {
        this.fact = fact;
    }
}
