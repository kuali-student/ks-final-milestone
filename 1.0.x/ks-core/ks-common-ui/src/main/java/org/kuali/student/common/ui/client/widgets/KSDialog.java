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

package org.kuali.student.common.ui.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * @deprecated
 * */
public class KSDialog{
    public final static int Yes = 1;
    public final static int No = 0;
    
    public final static int OK = 2;
    public final static int Cancel = 3;
    
    public final static String YesText = "Yes";
    public final static String NoText = "No";
    public final static String OkText = "Ok";
    public final static String CancelText = "Cancel";

    private KSLightBox lightBox = new KSLightBox();
    private VerticalPanel dialogLayout = new VerticalPanel();
    private Label titleLabel = new Label();
    private HorizontalPanel buttonPanel = new HorizontalPanel();
    
    public KSDialog(){
        dialogLayout.add(titleLabel);
        dialogLayout.add(buttonPanel);
        lightBox.setWidget(dialogLayout);
    }
    
    public KSDialog(String title){
        titleLabel.setText(title);
        dialogLayout.add(titleLabel);
        dialogLayout.add(buttonPanel);
        
        lightBox.setWidget(dialogLayout);
    }
    public void setTitle(String title){
        titleLabel.setText(title);
    }
    public void addButton(String name, ClickHandler buttonAction){
        Button bn = new Button(name);
        bn.addClickHandler(buttonAction);
        buttonPanel.add(bn);
    }
    public void setContent(Widget widget){
        dialogLayout.clear();
        dialogLayout.add(titleLabel);
        dialogLayout.add(widget);
        dialogLayout.add(buttonPanel);
        
    }
    public void setPixelSize(int width, int height){
        dialogLayout.setPixelSize(width, height);
    }
    public void show(){
        
        lightBox.show();
    }
    public void hide(){
        lightBox.hide();
    }
}
