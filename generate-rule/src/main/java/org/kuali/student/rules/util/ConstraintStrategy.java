/**
 *
 */
package org.kuali.student.rules.util;

import java.lang.reflect.Method;

/**
 * Abstract superclass that implements common constraint property management. Also wraps
 * <code>CloneNotSupportedException</code> with RuntimeException.
 * 
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 */
public abstract class ConstraintStrategy {

    // ~ Instance fields --------------------------------------------------------

    protected String constraintID;
    protected String requestMethodName;
    protected Class<Object> requestClass;

    protected Object request;

    // ~ Constructors -----------------------------------------------------------

    /**
     * No arg constructor.
     */
    public ConstraintStrategy() {
        super();
    }

    /**
     * Construct from fields.
     * 
     * @param constraintID
     * @param requestClassName
     * @param requestMethodName
     */
    public ConstraintStrategy(String constraintID, String requestClassName, String requestMethodName) {

        super();
        this.constraintID = constraintID;
        this.requestMethodName = requestMethodName;

        try {
            requestClass = (Class<Object>) Class.forName(requestClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // ~ Methods ----------------------------------------------------------------

    /**
     * Subclasses should implement in the most efficient manner. This would usually entail invoking cacheAdvice from apply()
     * 
     * @param format
     *            printf style format
     * @param args
     *            varargs list
     * @see org.kuali.rules.constraint.Constraint#apply()
     */
    protected abstract void cacheAdvice(String format, Object... args);

    /*
     * (non-Javadoc)
     * 
     * @see org.kuali.rules.constraint.Constraint#apply()
     */
    public abstract Boolean apply(String propVar);

    /**
     * @return the request
     */
    public Object getRequest() {
        return request;
    }

    /**
     * @param request
     *            the request to set
     */
    public void setRequest(Object request) {
        this.request = request;
    }

    /**
     * @return the constraintID
     */
    public String getConstraintID() {
        return constraintID;
    }

    /**
     * @param constraintID
     *            the constraintID to set
     */
    public void setConstraintID(String constraintID) {
        this.constraintID = constraintID;
    }

    /**
     * Return the accessor method of the constrained property.
     * 
     * @return the accessor method.
     */
    protected Method requestMethodNameAccessor() {

        try {
            return requestClass.getMethod("get" + requestMethodName, (Class[]) null);
        } catch (SecurityException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
