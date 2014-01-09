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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.kuali.student.r2.common.exceptions.OperationFailedException;

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
        
    	if (isListNull(list, throwOnNullList))
    		return null;
    	
    	if (isListEmpty(list, throwOnEmptyList))
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
     * Helper code for validating that the list is not null whether to throw an exception or indicate null should be returned.
     * 
     * a return value of true indicates that null should be returned; a value of false means the list has contents.
     */
    
    private static <T> boolean isListNull (List<T>list, boolean throwOnNullList) throws OperationFailedException {
    	if(list == null){
        	
        	if (throwOnNullList)
        		throw new OperationFailedException("list cannot be null");
        	else
        		return true;
        }
    	else
    		return false;
    }
    /*
     * Helper code for validating that the list is not empty and also whether to throw an exception or indicate null should be returned.
     * 
     * a return value of true indicates that null should be returned; a value of false means the list has contents.
     */
    private static <T> boolean isListEmpty (List<T>list, boolean throwOnEmptyList) throws OperationFailedException {
    	
    	if (list.isEmpty()) {
    		if (throwOnEmptyList)
    			throw new OperationFailedException("list cannot be empty");
    		else
    			return true;
    	}
    	else
    		return false;
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
    	return getOptionalZeroElement(list, true);
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
    public static <T> T getOptionalZeroElement(List<T> list, boolean throwOnNullList) throws OperationFailedException {
    	
    	if (isListNull(list, throwOnNullList))
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
    
    /*
     * Compute a mapping of items and the number of counts in the source collection.
     */
    private static <T> Map<T, AtomicInteger>computeFrequencyMap(Collection<T>source) {
    
    	Map<T, AtomicInteger>frequencyMap = new HashMap<T, AtomicInteger>();
    	
    	Iterator<T> it = source.iterator();
    	
    	while (it.hasNext()) {

    		T element = it.next();
		
    		AtomicInteger counter = frequencyMap.get(element);
    		
    		if (counter == null) {
    			counter = new AtomicInteger(0);
    			frequencyMap.put(element, counter);
    		}
    		
    		counter.addAndGet(1);
    	}
    	
    	return frequencyMap;
    	
    }
    
    /**
	 * A Null-safe test of the contents of each list that returns true if they are identical using bag semantics.  
	 * <p/>
	 * With the same collection size and the frequency of values are equal between the collections.
	 * <p/>
	 * The type T needs to implement Object.hashCode and Object.equals since we need to compute the frequency of each 
	 * occurrence in the collection using a java.util.Map. 
	 * 
	 * @param source the first collection in the comparison
	 * @param target the second collection in the comparison
	 * @return true if the source and target list are of the same size and each element within them are equal.
	 */
	public static <T> boolean areCollectionContentsEqual (Collection<T>source, Collection<T>target) {
		if (source == null && target == null)
			return true;
		else if (source == null || target == null)
			return false;
		else {
			// both are not null
			
			if (source.size() != target.size())
				return false;
			else {
				// collections are both the same size
				
				Map<T, AtomicInteger>sourceFreqMap = computeFrequencyMap(source);
				
				Map<T, AtomicInteger>targetFreqMap = computeFrequencyMap(target);
				
				if (sourceFreqMap.size() != targetFreqMap.size())
					return false;
				
				for (Map.Entry<T, AtomicInteger> sourceItemEntry : sourceFreqMap.entrySet()) {
					
					T sourceItem = sourceItemEntry.getKey();
					
					AtomicInteger sourceItemFrequency = sourceItemEntry.getValue();
					
					AtomicInteger targetItemFrequency = targetFreqMap.get(sourceItem);
					
					// AtomicInteger.equals is not implemented so we need to convert to ints and test those instead.
					if (targetItemFrequency == null || sourceItemFrequency.intValue() != targetItemFrequency.intValue())
						return false;
				}
				
			}
		}
		
		// at this point we've tested the contents of both lists and they are equal.
		return true;
	}

}
