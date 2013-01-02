/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.statement.ui.client.widgets.table;

import org.kuali.student.core.statement.ui.client.widgets.rules.Token;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NodeWidget extends SimplePanel implements NativePreviewHandler {
    private Node node;
    HTML html = new HTML();
    CheckBox checkBox = new CheckBox();
    HandlerRegistration handlerRegistration;
    VerticalPanel verticalPanel = new VerticalPanel();
    public NodeWidget(Node<Token> n) {
        Event.addNativePreviewHandler(this);
        
        node = n;
        super.setWidth("100%");
        super.setHeight("100%");
        setNode(n);

//        checkBox.addBlurHandler(new BlurHandler() {
  //          @Override
    //        public void onBlur(BlurEvent event) {
      //          DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffffff");
        //    }
//        });
  //      checkBox.addFocusHandler(new FocusHandler() {
    //        @Override
      //      public void onFocus(FocusEvent event) {
        //        DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffeeff");
          //  }
//        });
       
  //      super.addBlurHandler(new BlurHandler() {
    //        @Override
      //      public void onBlur(BlurEvent event) {
        //        DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffffff");
          //  }
//        });
  //      super.addFocusHandler(new FocusHandler() {
    //        @Override
      //      public void onFocus(FocusEvent event) {
        //        DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffeeff");
          //  }
//        });
//        super.addClickHandler(new ClickHandler() {
  //          @Override
    //        public void onClick(ClickEvent event) {
      
//                boolean before = checkBox.getValue();
  //              checkBox.setValue(!before);
    //            ValueChangeEvent.fireIfNotEqual(checkBox, before,checkBox.getValue());
      //          checkBox.setFocus(true);
        //        setFocus(true);
         //   }
       // });
        checkBox.addValueChangeHandler(new ValueChangeHandler(){
            @Override
            public void onValueChange(ValueChangeEvent event) {
                if(checkBox.getValue() == true){
                    DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffeeff");
                }else{
                    DOM.setStyleAttribute(NodeWidget.this.getElement(), "background", "#ffffff");
                }
            }
            
        });
    }
    @Override
    public void onPreviewNativeEvent(NativePreviewEvent pevent) {
        NativeEvent event = pevent.getNativeEvent();
        EventTarget target = event.getEventTarget();
     //   System.out.println(this.getElement().is(Element.as(target)));
        if(checkBox.getElement().isOrHasChild(Element.as(target))){
            return;
        }else if(this.getElement().is(Element.as(target)) && 
                Event.as(event).getTypeInt() == Event.ONMOUSEDOWN ){
                    GWT.log("doing", null);
                  boolean before = checkBox.getValue();
                                  checkBox.setValue(!before);
                                  ValueChangeEvent.fireIfNotEqual(checkBox, before,checkBox.getValue());
                                  //checkBox.setFocus(true);
                          //        setFocus(true);
                    
        }

    }
    public Node getNode() {
        return node;
    }

    public void installCheckBox() {
        verticalPanel.clear();
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        verticalPanel.setWidth("100%");
        verticalPanel.setHeight("100%");
        verticalPanel.add(checkBox);
        super.setWidget(verticalPanel);
        //contentPanel.setWidget(checkBox);
    }

    public boolean isSelected() {
        return checkBox.getValue() == true;
    }

    public void addSelectionHandler(ValueChangeHandler<Boolean> ch) {
        if (handlerRegistration != null) {
            handlerRegistration.removeHandler();
        }
        handlerRegistration = checkBox.addValueChangeHandler(ch);
    }

    public void setNode(Node n) {
        node = n;
        html.setHTML(node.getUserObject().toString());
        checkBox.setHTML(node.getUserObject().toString());
    }
}
