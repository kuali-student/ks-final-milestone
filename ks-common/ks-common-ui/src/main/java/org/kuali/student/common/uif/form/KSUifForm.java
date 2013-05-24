/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 1/16/13
 */
package org.kuali.student.common.uif.form;

import org.kuali.rice.krad.web.form.UifFormBase;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * KS form class that extends UifFormBase. It contains properties that are shared
 * among all the KS forms.
 *
 * @author Kuali Student Team
 */
public class KSUifForm extends UifFormBase {
    private String homeUrl;
    private Map<String, Map<String, String>> previousFormsMap;

    public KSUifForm() {
        super();
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public Map<String, Map<String, String>> getPreviousFormsMap() {
        return previousFormsMap;
    }

    public void setPreviousFormsMap(Map<String, Map<String, String>> previousFormsMap) {
        this.previousFormsMap = previousFormsMap;
    }
}
