/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.client.event;

import java.util.Comparator;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This can be used to add an invalid range check handler on any two widgets
 * that implements HasValue. The range check will verify that widget A value
 * <= widget B value
 * 
 * When the range is invalid the onInvalidRange() method gets called to handle the invalid range.
 * 
 * This handler needs to be added to both widgets using the widgets addValueChangeHandler(); 
 * 
 * @author Kuali Student Team
 *
 */
public abstract class ValueChangeRangeHandler<I> implements ValueChangeHandler<I> {

    Widget minWidget;
    Widget maxWidget;
    I minAllowed;
    I maxAllowed;
    Comparator<I> comparator = null;

    public ValueChangeRangeHandler(Widget rangeMin, Widget rangeMax){
        this.minWidget = rangeMin;
        this.maxWidget = rangeMax;
    }
    
    /**
     * This method checks the range for the two widgets and calls onInvalidRange()
     * if the range is invalid. 
     * 
     * @see com.google.gwt.event.logical.shared.ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)
     */
    @Override
    //TODO: Better handling for bounded min and/or max.
    public void onValueChange(ValueChangeEvent<I> event) {
        I minValue = getRangeMinValue();
        I maxValue = getRangeMaxValue();
        
        boolean isValid = true;
        
        if (minAllowed != null){
            isValid = isValid && (comparator != null ?
                    (comparator.compare(minAllowed, minValue) <= 0) :
                        ((Comparable)minAllowed).compareTo(minValue) <= 0);
        } 
        
        if (maxAllowed != null){
            isValid = isValid && (comparator != null ?
                    (comparator.compare(maxAllowed, maxValue) >= 0) :
                        ((Comparable)maxAllowed).compareTo(maxValue) >= 0);
        } 
        
        if (minValue != null && maxValue != null){
            isValid = isValid && (comparator != null ?
                    (comparator.compare(minValue, maxValue) < 0) :
                        ((Comparable)minValue).compareTo(maxValue) < 0);
        }        
        
        if (!isValid){
            onInvalidRange();
        }
    }
    
    /**
     * This method gets fired when an invalid range entered in the widgets
     *
     */
    public abstract void onInvalidRange();
    
    @SuppressWarnings("unchecked")
    protected I getRangeMinValue(){
        return ((HasValue<I>)this.minWidget).getValue();       
    }
    
    @SuppressWarnings("unchecked")
    protected I getRangeMaxValue(){
        return ((HasValue<I>)this.maxWidget).getValue();
    }
       
    public I getRangeMax(){
        return maxAllowed;
    }

    public void setRangeMin(I min){
        this.minAllowed = min;
    }
    
    public void setRangeMax(I max){
        this.maxAllowed = max;
    }
    
    /**
     * Set to use a different comparator than the value object's compareTo method
     * 
     * @param comparator
     */
    public void setComparator(Comparator<I> comparator){
        this.comparator = comparator;
    }

    /**
     * This can be used to compare values for widgets (e.g. KSTextBox) whose underlying
     * String value is being used to display integers. Rather than doing a string
     * comparison, it will convert the string values to integers and do an integer comparison instead.   
     * 
     * @author Kuali Student Team
     *
     */
    public static class StringAsIntegerComparator implements Comparator<String>{

        /**
         * This overridden method compares two strings by first converting them
         * to integers.
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(String s1, String s2) {
            if (s1.length() ==0 && s2.length() == 0){
                return 0;
            } else if (s1.length() == 0){
                return 1;   //unbounded min?
            } else if (s2.length() == 0){
                return -1;  //unbounded max?
            } else {
                Integer i1 = Integer.valueOf(s1);
                Integer i2 = Integer.valueOf(s2);            
                return i1.compareTo(i2);
            }
        }
        
    }
}
