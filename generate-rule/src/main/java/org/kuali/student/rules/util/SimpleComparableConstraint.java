/**
 *
 */
package org.kuali.student.rules.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * A constraint that compares a constrained  property (fact) to a given criterion.
 * 
 *
 * @param <E> the type being constrained.
 * @see org.kuali.student.rules.util.ComparisonOperator
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 *
 */
public class SimpleComparableConstraint<E extends Comparable>
    extends AbstractConstraint {

    //~ Instance fields --------------------------------------------------------

    Comparable fact;
    Comparable criterion;
    ComparisonOperator operation = ComparisonOperator.EQUAL_TO;

    //~ Constructors -----------------------------------------------------------

    public SimpleComparableConstraint() {
        super();
        // TODO Auto-generated constructor stub
    }

    public SimpleComparableConstraint(String constraintName,
        String requestClassName, String constrainedProperty,
        Comparable<E> criterion, ComparisonOperator operation) {
        super(constraintName, requestClassName, constrainedProperty);

        this.criterion = criterion;
        this.operation = operation;
    }

    //~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
    * @see org.kuali.rules.constraint.Constraint#apply()
    */
    public Boolean apply() {

        populateFact();
        sanityCheck();

        Boolean rval = Boolean.FALSE;

        switch (operation) {

        case EQUAL_TO:
            rval = fact.compareTo(criterion) == 0;

            break;

        case NOT_EQUAL_TO:
            rval = fact.compareTo(criterion) != 0;

            break;

        case GREATER_THAN:
            rval = fact.compareTo(criterion) == 1;

            break;

        case LESS_THAN:
            rval = fact.compareTo(criterion) == -1;

            break;

        case GREATER_THAN_OR_EQUAL_TO:
            rval = fact.compareTo(criterion) > -1;

            break;

        case LESS_THAN_OR_EQUAL_TO:
            rval = fact.compareTo(criterion) < 1;

            break;
        }

        cacheAdvice("%s NOT %s %s", rval);

        return rval;
    }

    /* (non-Javadoc)
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override protected void cacheAdvice(String format, Object... args) {
        Boolean rval = (Boolean) args[0];
        String dbg = String.format("rval: %s (fact: %s %s criterion: %s)", rval, fact, operation, criterion);
        System.out.println("---####--- SimpleComparator: " + dbg);
        if (rval.equals(Boolean.TRUE)) {
            setAdvice("comparison met");

            return;
        }

        String rpt = String.format(format, fact, operation, criterion);
        System.out.println("####### SimpleComparator: " + rpt);
        setAdvice(rpt);

    }

    private void populateFact() {

        Method accessor = getConstrainedPropertyAccessor();

        try {
            fact = (Comparable) accessor.invoke(request);
        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private void sanityCheck() {

        if (fact == null)
            throw new IllegalStateException(getClass().getName() +
                ":  no fact to constrain");

        if (criterion == null)
            throw new IllegalStateException(getClass().getName() +
                ":  no constraining criterion");

    }


    /* (non-Javadoc)
     * @see org.kuali.rules.constraint.AbstractConstraint#clone()
     */
    @Override public Object clone() {
        SimpleComparableConstraint rval = (SimpleComparableConstraint) super
            .clone();
        rval.fact = fact;
        rval.criterion = criterion;
        rval.operation = operation;

        return rval;
    }

    /**
     * @return the fact
     */
    public Comparable<E> getFact() {
        return fact;
    }

    /**
     * @param fact the fact to set
     */
    public void setFact(Comparable<E> fact) {
        this.fact = fact;
    }

    /**
     * @return the criterion
     */
    public Comparable<E> getCriterion() {
        return criterion;
    }

    /**
     * @param criterion the criterion to set
     */
    public void setCriterion(Comparable<E> criterion) {
        this.criterion = criterion;
    }

    /**
     * @return the operation
     */
    public ComparisonOperator getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(ComparisonOperator operation) {
        this.operation = operation;
    }

}
