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
import java.lang.String;
import java.util.List;

/**
 * This is a lightweight wrapper for Clu Information used in the KRMS UI.
 *
 * @author Kuali Student Team
 */
public class CluGroup implements Serializable {

    private String title;
    private List<CluInformation> clus;
    private List<CluGroup> cluGroups;

    private boolean showClus = true;
    private boolean showTitle = true;

    public CluGroup(){
        super();
    }

    public CluGroup(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CluInformation> getClus() {
        return clus;
    }

    public void setClus(List<CluInformation> clus) {
        this.clus = clus;
    }

    public List<CluGroup> getCluGroups() {
        return cluGroups;
    }

    public void setCluGroups(List<CluGroup> cluGroups) {
        this.cluGroups = cluGroups;
    }

    public boolean getShowClus() {
        return showClus;
    }

    public void setShowClus(boolean showClus) {
        this.showClus = showClus;
    }

    public boolean getShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }
}
