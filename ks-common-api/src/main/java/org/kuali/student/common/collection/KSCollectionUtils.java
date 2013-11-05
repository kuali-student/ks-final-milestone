/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.collection;

import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;

/**
 * @author Kuali Student Team
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

    	return getRequiredZeroElement(list, true, true);
    }
    
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
     * @param throwOnNullList true if an OperationFailedException should be thrown if the list is null.  If false it will allow null to be returned in the list is null case.
     * @param throwOnEmptyList true if an OperationFailedException should be thrown if the list is empty. If false it will allow null to be returned in the list is empty case.
     * @param <T>    Type of element to return
     * @return       the get(0) element in the list.
     * @throws OperationFailedException
     */
    public static <T> T getRequiredZeroElement(List<T> list, boolean throwOnNullList, boolean throwOnEmptyList) throws OperationFailedException{
        
    	if (!checkListContainsElements(list, throwOnNullList, throwOnEmptyList))
    		return null;
    	
        T t;
        if (list.size() == 1) {
            t = list.get(0);
        } else {
            throw new OperationFailedException("list size exceeds limit of 1");
        }

        return t;
    }

    /*
     * Helper code for validating that the list is not null and not empty and also whether to throw an exception or indicate null should be returned.
     * 
     * a return value of false indicates that null should be returned; a value of true means the list has contents.
     */
    private static <T> boolean checkListContainsElements (List<T>list, boolean throwOnNullList, boolean throwOnEmptyList) throws OperationFailedException {
    	if(list == null){
        	
        	if (throwOnNullList)
        		throw new OperationFailedException("list cannot be null");
        	else
        		return false;
        }

    	if (list.isEmpty()) {
    		if (throwOnEmptyList)
    			throw new OperationFailedException("list cannot be empty");
    		else
    			return false;
    	}
    	
    	// default
    	return true;
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
    	return getOptionalZeroElement(list, true, true);
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
     * @param list   Typed list passed into method. 
     * @param throwOnNullList true if an OperationFailedException should be thrown if the list is null.  If false it will allow null to be returned in the list is null case.
     * @param throwOnEmptyList true if an OperationFailedException should be thrown if the list is empty. If false it will allow null to be returned in the list is empty case.
     * @param <T>    Type of element to return
     * @return       the get(0) element in the list. If there are no elements, return null.
     * @throws OperationFailedException
     */ 
    public static <T> T getOptionalZeroElement(List<T> list, boolean throwOnNullList, boolean throwOnEmptyList) throws OperationFailedException{
    	
    	if (!checkListContainsElements(list, throwOnNullList, throwOnEmptyList))
    		return null;

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
