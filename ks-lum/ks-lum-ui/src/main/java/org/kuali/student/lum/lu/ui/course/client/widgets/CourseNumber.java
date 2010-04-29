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
package org.kuali.student.lum.lu.ui.course.client.widgets;

import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasName;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * FIXME: This widget needs to be rewritten.
 * 
 * @author Kuali Student Team
 *
 */
public class CourseNumber extends Composite implements HasValue<CluIdentifierInfo>, HasName, HasText{
    private CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
    private HorizontalPanel numberPanel = new HorizontalPanel();
    private String name;
    
    private KSTextBox subjectBox = new KSTextBox();

    //TODO: On keyup auto-suggest list of available/not available numbers 
    private KSTextBox numberBox = new KSTextBox();
    
    public CourseNumber(){
        init();
    }
   
    private void init(){
        super.initWidget(numberPanel);
        subjectBox.addValueChangeHandler(new ValueChangeHandler<String>(){
            public void onValueChange(ValueChangeEvent<String> event) {
                cluIdentifier.setDivision(event.getValue());
                fireValueChange();
            }            
        });
        numberBox.addValueChangeHandler(new ValueChangeHandler<String>(){
            public void onValueChange(ValueChangeEvent<String> event) {
                cluIdentifier.setCode(event.getValue());
                fireValueChange();
            }            
        });
        
        subjectBox.addStyleName("KS-Course-Number");
        numberBox.addStyleName("KS-Course-Number");
        
        numberPanel.add(subjectBox);
        numberPanel.add(numberBox);
    }
    
    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    @Override
    public CluIdentifierInfo getValue() {
        return cluIdentifier;
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    @Override
    public void setValue(CluIdentifierInfo value) {
    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    @Override
    public void setValue(CluIdentifierInfo value, boolean fireEvents) {
        setValue(value);        
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<CluIdentifierInfo> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     * @see com.google.gwt.dev.jjs.ast.HasName#getName()
     */
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * @see com.google.gwt.user.client.ui.HasName#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    private void fireValueChange(){
        ValueChangeEvent.fire(this, cluIdentifier);
    }

    /**
     * Returns the division + identifier as code to display
     * 
     * @see com.google.gwt.user.client.ui.HasText#getText()
     */
    @Override
    public String getText() {
        return subjectBox.getText() + numberBox.getText();
    }

    /**
     * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        //TODO: Parse text to set individual components?
        throw new UnsupportedOperationException();
    }

    

}
