/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.uif.view;

import static org.kuali.student.logging.FormattedLogger.debug;
import static org.kuali.student.logging.FormattedLogger.error;

import org.kuali.rice.krad.uif.UifConstants;

import org.kuali.rice.krad.uif.element.ViewHeader;
import org.kuali.rice.krad.datadictionary.parse.BeanTag;
import org.kuali.rice.krad.datadictionary.parse.BeanTagAttribute;
import org.kuali.rice.krad.datadictionary.parse.BeanTags;
import org.kuali.rice.krad.datadictionary.state.StateMapping;

/**
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@BeanTag(name = "maintenanceView-bean", parent="Uif-MaintenanceView")
public class KsUifMaintenanceDocumentView extends org.kuali.rice.krad.uif.view.MaintenanceDocumentView {

    // sticky flags
    private boolean stickyTopGroup;
    private boolean stickyBreadcrumbs;
    private boolean stickyHeader;
    private boolean stickyApplicationHeader;
    private boolean stickyFooter;
    private boolean stickyApplicationFooter;

    /**
     * If true, the top group will be sticky (fixed to top of window)
     *
     * @return true if the top group is sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyTopGroup")
    public boolean isStickyTopGroup() {
        return stickyTopGroup;
    }

    /**
     * Set to true to make the top group sticky (fixed to top of window)
     *
     * @param stickyTopGroup
     */
    public void setStickyTopGroup(boolean stickyTopGroup) {
        this.stickyTopGroup = stickyTopGroup;
    }

    /**
     * If true, the breadcrumb widget will be sticky (fixed to top of window)
     *
     * @return true if breadcrumbs are sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyBreadcrumbs")
    public boolean isStickyBreadcrumbs() {
        return stickyBreadcrumbs;
    }

    /**
     * Set to true to make the breadcrumbs sticky
     *
     * @param stickyBreadcrumbs
     */
    public void setStickyBreadcrumbs(boolean stickyBreadcrumbs) {
        this.stickyBreadcrumbs = stickyBreadcrumbs;
    }

    /**
     * If true, the ViewHeader for this view will be sticky (fixed to top of window)
     *
     * @return true if the header is sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyHeader")
    public boolean isStickyHeader() {
        if (this.getHeader() != null && this.getHeader() instanceof ViewHeader) {
            return ((ViewHeader) this.getHeader()).isSticky();
        } else {
            return false;
        }
    }

    /**
     * Set to true to make the ViewHeader sticky
     *
     * @param stickyHeader
     */
    public void setStickyHeader(boolean stickyHeader) {
        this.stickyHeader = stickyHeader;
        if (this.getHeader() != null && this.getHeader() instanceof ViewHeader) {
            ((ViewHeader) this.getHeader()).setSticky(stickyHeader);
        }
    }

    /**
     * Set to true to make the applicationHeader sticky (fixed to top of window)
     *
     * @return true if applicationHeader is sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyApplicationHeader")
    public boolean isStickyApplicationHeader() {
        return stickyApplicationHeader;
    }

    /**
     * Set to true to make the applicationHeader sticky
     *
     * @param stickyApplicationHeader
     */
    public void setStickyApplicationHeader(boolean stickyApplicationHeader) {
        this.stickyApplicationHeader = stickyApplicationHeader;
    }

    /**
     * If true, the view footer will become sticky (fixed to bottom of window)
     *
     * @return ture if the view footer is sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyFooter")
    public boolean isStickyFooter() {
        return stickyFooter;
    }

    /**
     * Set to true to make the view footer sticky
     *
     * @param stickyFooter
     */
    public void setStickyFooter(boolean stickyFooter) {
        this.stickyFooter = stickyFooter;
        if (this.getFooter() != null) {
            this.getFooter().addDataAttribute(UifConstants.DataAttributes.STICKY_FOOTER, Boolean.toString(
                    stickyFooter));
        }
    }

    /**
     * If true, the applicationFooter will become sticky (fixed to bottom of window)
     *
     * @return true if the application footer is sticky, false otherwise
     */
    @BeanTagAttribute(name = "stickyApplicationFooter")
    public boolean isStickyApplicationFooter() {
        return stickyApplicationFooter;
    }

    /**
     * Set to true to make the application footer sticky
     *
     * @param stickyApplicationFooter
     */
    public void setStickyApplicationFooter(boolean stickyApplicationFooter) {
        this.stickyApplicationFooter = stickyApplicationFooter;
    }
}

