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
package org.kuali.student.common.ui.client.widgets.breadcrumb;

import java.util.List;

import org.kuali.student.common.ui.client.widgets.breadcrumb.impl.KSBreadcrumbImpl;

import com.google.gwt.core.client.GWT;

/**
 * KSBreadcrumb is used as a navigation aid in KS interfaces giving the user a way to track their location
 * within a series of tasks or options.
 * 
 * This implementation takes a list of KSBreadcrumbItems. The list is built by the calling process and is
 * used to build a breadcrumb trail which can either be links or not as required.  The final item in 
 * the list refers to the current page and is not generated as a link in either case.
 * 
 * This implementation is based on the tutorial at :-
 *     http://veerle.duoh.com/blog/comments/simple_scalable_css_based_breadcrumbs/
 *  
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class KSBreadcrumb extends KSBreadcrumbAbstract {

    KSBreadcrumbAbstract breadcrumb = GWT.create(KSBreadcrumbImpl.class);

/*
 * This constructs a KSBreadcrumb instance which can generate either a linked or unlinked list
 * of items
 * 
 */    public KSBreadcrumb() {
        initWidget(breadcrumb);
    }

    /**
     * This method will build a breadcrumb trail of clickable links allowing the user to navigate through
     * selected previous pages. The last (current page) item will not be a link.
     * 
     * @see org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbAbstract#setLinkedBreadcrumbList()
     */
    @Override
    public void setLinkedBreadcrumbList(List<KSBreadcrumbItem> items) {
        breadcrumb.setLinkedBreadcrumbList(items);        
    }

    /**
     * This method will build a breadcrumb trail of unclickable names allowing the user to view 
     * their path to reach the current page.
     * 
     * @see org.kuali.student.common.ui.client.widgets.breadcrumb.KSBreadcrumbAbstract#setUnLinkedBreadcrumbList()
     */
    @Override
    public void setUnLinkedBreadcrumbList(List<KSBreadcrumbItem> items) {

        breadcrumb.setUnLinkedBreadcrumbList(items);    
    }


}
