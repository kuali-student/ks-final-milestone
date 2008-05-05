/**
 *
 */
package org.kuali.student.rules.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class SumConstraint<E> extends ConstraintStrategy {
    
    ComparisonOperator operator;
    List<? extends Number> factSet;
    Number requiredLunits;

    public SumConstraint() {
        super();
    }

    public SumConstraint(String constraintID, String requestClassName, String requestMethodName, ComparisonOperator operator, Number requiredLunits) {
        super(constraintID, requestClassName, requestMethodName);
        this.operator = operator;
        this.requiredLunits = requiredLunits;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.drools.util.Constraint#apply()
     */
    @Override
    public Boolean apply(String propVar) {
        System.out.println("applying sum constraint ");
        populateFactSet();

        boolean result;
        Integer neededUnits = 0;
        int totalUnits = sumLunits();
        int constraintLunits = requiredLunits.intValue();
        
        System.out.println("total units : " + totalUnits);
        System.out.println("required units : " + constraintLunits);
        
        switch (operator){
            case EQUAL_TO:
                result = totalUnits == constraintLunits;
                cacheAdvice("The lunits are not equal", neededUnits, result, propVar);
                System.out.println("in EQUAL_TO switch - result is : " + result);
                return result;
            
            case NOT_EQUAL_TO:  
                result = totalUnits != constraintLunits;
                cacheAdvice("The lunits are equal", neededUnits, result, propVar);
                System.out.println("in NOT_EQUAL_TO switch - result is : " + result);
                return result;
            
            case GREATER_THAN:  
                result = totalUnits > constraintLunits;
                neededUnits = (constraintLunits+1) - totalUnits;
                cacheAdvice("%d more lunit(s) is still required", neededUnits, result, propVar);
                System.out.println("in GREATER_THAN switch - result is : " + result);
                return result;
            
            case LESS_THAN:     
                result = totalUnits < constraintLunits;
                neededUnits = (totalUnits+1) - constraintLunits;
                cacheAdvice("You have %d to many lunits", neededUnits, result, propVar);
                System.out.println("in LESS_THAN switch - result is : " + result);
                return result;
            
            case GREATER_THAN_OR_EQUAL_TO:  
                result = totalUnits >= constraintLunits;
                neededUnits = constraintLunits - totalUnits;
                cacheAdvice("%d more lunit(s) is still required", neededUnits, result, propVar);
                System.out.println("in GREATER_THAN_OR_EQUAL_TO switch - result is : " + result);
                return result;
            
            case LESS_THAN_OR_EQUAL_TO:
                result = totalUnits <= constraintLunits;
                neededUnits = totalUnits - constraintLunits;
                cacheAdvice("You have %d to many lunits", neededUnits, result, propVar);
                System.out.println("in LESS_THAN_OR_EQUAL_TO switch - result is : " + result);
                return result;
            
            default:
                return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    protected void cacheAdvice(String format, Object... args) {
        //Integer totalUnits = (Integer) args[0];
        Integer needed = (Integer) args[0];
        Boolean result = (Boolean) args[1];
        String propVar = (String) args[2];
        
        if (result) {
            Propositions.setFailureMessage(propVar, "sum constraint fulfilled");
            return;
        }

        // Is the format a problem ? going to test
        String advice = String.format(format, needed.intValue(), requiredLunits.intValue());
        Propositions.setFailureMessage(propVar, advice);

    }

    /**
     * Returns the intersection of the fact set with the criteria set.
     * 
     * @return the intersection
     */
    public int sumLunits() {
        int rval=0;
        for(Number lunits : factSet){
            rval += lunits.intValue();
        }
        return rval;
    }

    /**
     * @return the factSet
     */
    public List<?> getFactSet() {
        return factSet;
    }

    /**
     * @param factSet
     *            the factSet to set
     */
    public void setFactSet(List<? extends Number> factSet) {
        this.factSet = new ArrayList<Number>(factSet);
        
/*        for (Number t : factSet) {
            System.out.println("The intgers " + t.toString());
        }*/
    }

    private void populateFactSet() {

        try {
            Method m = requestMethodNameAccessor();

            List<? extends Number> facts = (List<? extends Number>) m.invoke(request);
            setFactSet(facts);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
