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

package org.kuali.student.common.ui.client.widgets.breadcrumb.impl;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSStyles;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbAbstract;
import org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbItem;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class KSBreadcrumbImpl extends KSBreadcrumbAbstract {

    HorizontalPanel main = new HorizontalPanel();

    public KSBreadcrumbImpl() {
        super.initWidget(main);       
        main.addStyleName(KSStyles.KS_BREADCRUMB);
    } 
    
    /**
     */
    @Override
    public void setLinkedBreadcrumbList(List<KSBreadcrumbItem> items) {

        int counter = 1;

        for (KSBreadcrumbItem item : items) {
            if (counter < items.size() ||
                    items.size() == 1){
                addLinkedItem(item.getText(), item.getUri(), item.getTitle(), item.getTarget());                
            }
            else {
                addCurrent(item.getText(), item.getTitle());
            }
            counter++;
        }
    }

    /**
     */
    @Override
    public void setUnLinkedBreadcrumbList(List<KSBreadcrumbItem> items) {

        int counter = 1;

        for (KSBreadcrumbItem item : items) {

            if (counter < items.size() ||
                    items.size() == 1){
                addUnlinkedItem(item.getText(), item.getTitle());                
            }
            else {
                addCurrent(item.getText(), item.getTitle());
            }
            counter++;
        }
    }

    /*
     *  Currently using Anchor for the item link.  The breadcrumb items would probably need to be an RPC call
     *  so Anchor may not be the best choice. 
     */
    private void addLinkedItem(String text, String URI, String title, String target) {

        final Anchor item = new Anchor(text, URI, target); 
        item.setTitle(title);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM_HISTORY);

        item.addFocusHandler(new FocusHandler() {

            @Override
            public void onFocus(FocusEvent event) {
                item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM_FOCUS);

            }});
        item.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM_FOCUS);               
            }});
        
        item.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                item.removeStyleName(KSStyles.KS_BREADCRUMB_ITEM_FOCUS);               
                
            }});
        main.add(item);       
    }

    private void addUnlinkedItem(String text, String title) {

        final KSLabel item = new KSLabel(text); 
        item.setTitle(title);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM_HISTORY);

        main.add(item);       
    }

    private void addCurrent(String text, String title) {
        KSLabel item = new KSLabel(text); 
        item.setTitle(title);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM);
        item.addStyleName(KSStyles.KS_BREADCRUMB_ITEM_CURRENT);
        main.add(item);       

    }
}
