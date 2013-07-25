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

import org.kuali.student.r2.lum.clu.dto.CluSetInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class ProgramCluSetInformation implements Serializable {

    private static final long serialVersionUID = 1123124L;
    private CluSetInfo cluSetInfo;
    private List<CluInformation> clus;
    private List<CluSetInfo> cluSets;
    private Map<String, ProgramCluSetInformation> subCluSetInformations;
    private ProgramCluSetInformation parent;

    private List<CluSetRangeInformation> cluSetRange;

    public CluSetInfo getCluSetInfo() {
        return cluSetInfo;
    }

    public void setCluSetInfo(CluSetInfo cluSetInfo) {
        this.cluSetInfo = cluSetInfo;
    }

    public List<CluInformation> getClus() {
        if (clus == null) {
            this.clus = new ArrayList<CluInformation>();
        }
        return this.clus;
    }

    public void setClus(List<CluInformation> clus) {
        this.clus = clus;
    }

    public List<CluSetInfo> getCluSets() {
        if (this.cluSets == null) {
            this.cluSets = new ArrayList<CluSetInfo>();
        }
        return this.cluSets;
    }

    public void setCluSets(List<CluSetInfo> cluSets) {
        this.cluSets = cluSets;
    }


    public List<CluSetRangeInformation> getCluSetRange() {
        if (this.cluSetRange == null) {
            this.cluSetRange = new ArrayList<CluSetRangeInformation>();
        }
        return cluSetRange;
    }

    public void setCluSetRange(List<CluSetRangeInformation> cluSetRange) {
        this.cluSetRange.set(0, cluSetRange.get(cluSetRange.size() - 1));
    }

    public Map<String, ProgramCluSetInformation> getSubCluSetInformations() {
        if (subCluSetInformations == null) {
            subCluSetInformations = new HashMap<String, ProgramCluSetInformation>();
        }
        return subCluSetInformations;
    }

    public void setSubCluSetInformations(Map<String, ProgramCluSetInformation> subCluSetInformations) {
        this.subCluSetInformations = subCluSetInformations;
    }

    public ProgramCluSetInformation getParent() {
        return parent;
    }

    public void setParent(ProgramCluSetInformation parent) {
        this.parent = parent;
    }

    public int getProgCluListSize(){
        if (this.getClus() != null) {
            return this.getClus().size();
        } else {
            return 0;
        }
    }

    public int getProgCluSetListSize(){
        if (this.getCluSets() != null) {
            return this.getCluSets().size();
        } else {
            return 0;
        }
    }

    public String getCluDelimitedString() {

        StringBuilder sb = new StringBuilder();
        for (CluInformation clu : this.getClus()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(clu.getVerIndependentId());
        }

        return sb.toString();
    }

    public String getCluSetDelimitedString() {

        StringBuilder sb = new StringBuilder();
        for (CluSetInfo cluSet : this.getCluSets()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(cluSet.getId());
        }

        return sb.toString();
    }

    public boolean hasClus() {
        if ((this.getClus() != null) && (!this.getClus().isEmpty())) {
            return true;
        }
        return false;
    }


    public List<String> getCluIds(){
        List<String> cluIds = new ArrayList<String>();
        for(CluInformation clu : this.getClus()){
            cluIds.add(clu.getVerIndependentId());
        }
        return cluIds;
    }

}
