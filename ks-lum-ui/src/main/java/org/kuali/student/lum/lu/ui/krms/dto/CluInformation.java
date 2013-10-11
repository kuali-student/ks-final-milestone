/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.dto;

import java.io.Serializable;

/**
 * This is a lightweight wrapper for Clu Information used in the KRMS UI.
 *
 * @author Kuali Student Team
 */
public class CluInformation extends CluCore implements Serializable {

    private static final long serialVersionUID = 1123124L;

    private String cluId;
    private String verIndependentId;
    private String title;
    private String description;
    private String type;
    private String state;
    private String parentCluId;

    public CluInformation() {
        super();
    }

    public CluInformation(String code, String shortName, String credits) {
        super(code, shortName, credits);
    }

    public String getCluId() {
        return cluId;
    }

    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVerIndependentId(String verIndependentId) {
        this.verIndependentId = verIndependentId;
    }

    public String getVerIndependentId() {
        return verIndependentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentCluId() {
        return parentCluId;
    }

    public void setParentCluId(String parentCluId) {
        this.parentCluId = parentCluId;
    }

    public void clear() {
        super.clear();
        this.cluId = null;
        this.verIndependentId = null;
        this.title = null;
        this.description = null;
        this.type = null;
        this.state = null;
        this.parentCluId = null;
    }

    @Override
    public String toString() {
        return this.getVerIndependentId();
    }
}
