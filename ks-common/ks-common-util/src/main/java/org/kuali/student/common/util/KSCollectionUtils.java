package org.kuali.student.common.util;

import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/23/13
 * Time: 9:24 AM
 *
 * This is a common utility class for the KS project.
 *
 */
public class KSCollectionUtils {

    /**
     * Cardinality of one
     *
     * This is a generic method so you will not need to worry about casting when using this method
     *
     * Using get(0) in a non-test class will result in a critical sonar issue being reported which will result in a JIRA issue.
     * Correcting get(0) depends on the cardinality constraints on the list.
     * If a list constraint is changed, get(0) might not be the right answer anymore and we would prefer to expose that problem
     * at the source rather than requiring implementers to track down the cardinality constraint enforcement in java.
     * This is handled via OperationFailedException being thrown in the examples belowCardinality of one and only one element:
     *
     * @param list   Typed list passed into method. Cannot be null.
     * @param <T>    Type of element to return
     * @return       the get(0) element in the list.
     * @throws OperationFailedException
     */
    public static <T> T getRequiredZeroElement(List<T> list) throws OperationFailedException{

        if(list == null){
            throw new OperationFailedException("list cannot be null");
        }

        T t;
        if (list.size() == 1) {
            t = list.get(0);
        } else {
            throw new OperationFailedException("list size exceeds limit of 1");
        }

        return t;
    }

    /**
     * Cardinality of zero or one. Will return the value or null depending on what's in the list.
     *
     * List has 1 element, return element.
     * List has 0 elements, return null.
     * else throw exception.
     *
     * This is a generic method so you will not need to worry about casting when using this method
     *
     * Using get(0) in a non-test class will result in a critical sonar issue being reported which will result in a JIRA issue.
     * Correcting get(0) depends on the cardinality constraints on the list.
     * If a list constraint is changed, get(0) might not be the right answer anymore and we would prefer to expose that problem
     * at the source rather than requiring implementers to track down the cardinality constraint enforcement in java.
     * This is handled via OperationFailedException being thrown in the examples belowCardinality of one and only one element:
     *
     *
     * @param list   Typed list passed into method. Cannot be null.
     * @param <T>    Type of element to return
     * @return       the get(0) element in the list. If there are no elements, return null.
     * @throws OperationFailedException
     */
    public static <T> T getOptionalZeroElement(List<T> list) throws OperationFailedException{
        if(list == null){
            throw new OperationFailedException("list cannot be null");
        }

        T t;
        if (list.size() == 1) {
            t = list.get(0);
        } else if (list.size() == 0) {
            t = null;
        } else {
            throw new OperationFailedException("list size exceeds limit of 1");
        }

        return t;
    }
}
