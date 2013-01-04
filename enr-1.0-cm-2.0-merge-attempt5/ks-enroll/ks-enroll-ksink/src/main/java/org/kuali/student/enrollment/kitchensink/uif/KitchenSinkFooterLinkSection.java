/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by bobhurt on 9/20/12
 */
package org.kuali.student.enrollment.kitchensink.uif;

import org.kuali.rice.krad.uif.widget.WidgetBase;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for storing the various links for the KS Kitchen Sink examples
 * See ksKitchenSinkFooterLinks.ftl
 *
 * @author Kuali Student Team
 */
public class KitchenSinkFooterLinkSection extends WidgetBase {

    private String linkDelimiterEnd, linkDelimiterMiddle, linkDelimiterStart;
    private List<KitchenSinkFooterLinkGroup> footerLinkGroups;

    public KitchenSinkFooterLinkSection() {
        linkDelimiterStart = "[";
        linkDelimiterMiddle = "|";
        linkDelimiterEnd = "]";
        footerLinkGroups = new ArrayList<KitchenSinkFooterLinkGroup>();
    }

    public String getLinkDelimiterEnd() {
        return linkDelimiterEnd;
    }
    public void setLinkDelimiterEnd(String linkDelimiterEnd) {
        this.linkDelimiterEnd = linkDelimiterEnd;
    }

    public String getLinkDelimiterMiddle() {
        return linkDelimiterMiddle;
    }
    public void setLinkDelimiterMiddle(String linkDelimiterMiddle) {
        this.linkDelimiterMiddle = linkDelimiterMiddle;
    }

    public String getLinkDelimiterStart() {
        return linkDelimiterStart;
    }
    public void setLinkDelimiterStart(String linkDelimiterStart) {
        this.linkDelimiterStart = linkDelimiterStart;
    }

    public List<KitchenSinkFooterLinkGroup> getFooterLinkGroups() {
        return footerLinkGroups;
    }
    public void setFooterLinkGroups(List<KitchenSinkFooterLinkGroup> footerLinkGroups) {
        this.footerLinkGroups = footerLinkGroups;
    }
}
