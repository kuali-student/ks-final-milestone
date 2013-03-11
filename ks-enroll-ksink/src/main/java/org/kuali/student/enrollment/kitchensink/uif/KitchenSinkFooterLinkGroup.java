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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is for storing the various links for the KS Kitchen Sink examples
 * See ksKitchenSinkFooterLinks.ftl
 *
 * @author Kuali Student Team
 */
public class KitchenSinkFooterLinkGroup implements Serializable {
    private String label;
    private String style;
    private Map<String, String> footerLinks;

    public KitchenSinkFooterLinkGroup() {
        footerLinks = new HashMap<String, String>();
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

    public String getStyle() {
        return style;
    }
    public void setStyle(String style) {
        this.style = style;
    }

    public Map<String, String> getFooterLinks() {
        return footerLinks;
    }
    public void setFooterLinks(Map<String, String> footerLinks) {
        this.footerLinks = footerLinks;
    }
}
