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
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a lightweight wrapper for Clu Set Information used in the KRMS UI.
 *
 * @author Kuali Student Team
 */
public class CluSetWrapper implements Serializable {

    private static final long serialVersionUID = 1123124L;
    private String id;
    private String name;
    private String descr;
    private String typeKey;

    private List<CluInformation> clus;
    private List<CluSetWrapper> cluSets;
    private List<CluSetRangeInformation> cluSetRanges;

    public CluSetWrapper() {
        super();
    }

    public CluSetWrapper(CluSetInfo cluSetInfo) {
        super();
        this.id = cluSetInfo.getId();
        this.name = cluSetInfo.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
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

    public List<CluSetWrapper> getCluSets() {
        if (this.cluSets == null) {
            this.cluSets = new ArrayList<CluSetWrapper>();
        }
        return this.cluSets;
    }

    public void setCluSets(List<CluSetWrapper> cluSets) {
        this.cluSets = cluSets;
    }

    public List<CluSetRangeInformation> getCluSetRanges() {
        if (cluSetRanges == null) {
            cluSetRanges = new ArrayList<CluSetRangeInformation>();
        }
        return cluSetRanges;
    }

    public void setCluSetRanges(List<CluSetRangeInformation> cluSetRanges) {
        this.cluSetRanges = cluSetRanges;
    }

    public void clear(){
        this.id = null;
        this.name = null;
        this.cluSets = null;
        this.cluSetRanges = null;
    }

    public int getCluListSize(){
        return this.getClus().size();
    }

    public int getCluSetListSize(){
        return this.getCluSets().size();
    }

    public int getCluRangeListSize(){
        return this.getCluSetRanges().size();
    }

    /**
     * Returns a list of all the clus and the clusInRange.
     *
     * @return
     */
    public List<CluInformation> getClusAndClusInRange() {
        List<CluInformation> clus = new ArrayList<CluInformation>();
        clus.addAll(this.getClus());
        for (CluSetRangeInformation cluSetRange : this.getCluSetRanges()) {
            clus.addAll(cluSetRange.getClusInRange());
        }
        return clus;
    }

    /**
     * Returns a comma delimited list of clu ids including the
     * clusInRange to be passed to the service for the natural
     * lantuage translation.
     *
     * @return
     */
    public String getCluDelimitedString() {

        List<String> cluIds = this.getCluIds();
        for (CluSetRangeInformation cluSetRange : this.getCluSetRanges()) {
            if (cluSetRange.getClusInRange() != null) {
                for (CluInformation clu : cluSetRange.getClusInRange()) {
                    cluIds.add(clu.getVerIndependentId());
                }
            }
        }

        Collections.sort(cluIds);
        return StringUtils.collectionToCommaDelimitedString(cluIds);
    }

    /**
     * Returns a comma delimited list of cluset ids to be passed to
     * the service for the natural lantuage translation.
     *
     * @return
     */
    public String getCluSetDelimitedString() {

        List<String> cluSetIds = new ArrayList<String>();
        for (CluSetWrapper cluSet : this.getCluSets()) {
            cluSetIds.add(cluSet.getId());
        }

        Collections.sort(cluSetIds);
        return StringUtils.collectionToCommaDelimitedString(cluSetIds);
    }

    /**
     * Check if there are any simple clus linked to this cluset wrapper.
     * Does not include clusInRAnge(membershipQuery) or other cluset.
     *
     * @return
     */
    public boolean hasClus() {
        if ((this.getClus() != null) && (!this.getClus().isEmpty())) {
            return true;
        }
        return false;
    }

    /**
     * Check if this Cluset contains any membershipqueries.
     *
     * @return
     */
    public boolean hasMembershipQuery() {
        if ((this.getCluSetRanges() == null) || (this.getCluSetRanges().size() == 0)) {
            return false;
        }

        for (CluSetRangeInformation cluSetRange : this.getCluSetRanges()) {
            MembershipQueryInfo mqInfo = cluSetRange.getMembershipQueryInfo();
            if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public List<String> getCluIds() {
        List<String> cluIds = new ArrayList<String>();
        for (CluInformation clu : this.getClus()) {
            cluIds.add(clu.getVerIndependentId());
        }
        return cluIds;
    }

    public List<CluGroup> getCluGroups() {
        List<CluGroup> cluGroups = new ArrayList<CluGroup>();
        //Individual Clus
        if (this.getClus().size() > 0) {
            CluGroup indCourses = new CluGroup("INDIVIDUAL COURSE(S)");
            indCourses.setClus(this.getClus());

            //Only display the Indivdual Courses heading when clusets or queries exist.
            if ((this.getCluSets().size() == 0) && (!this.hasMembershipQuery())) {
                indCourses.setShowTitle(false);
            }
            cluGroups.add(indCourses);
        }

        //Course sets.
        for (CluSetWrapper cluSet : this.getCluSets()) {
            CluGroup cluGroup = new CluGroup(cluSet.getName());
            cluGroup.setClus(cluSet.getClus());
            cluGroups.add(cluGroup);
        }

        //CourseRange
        for (CluSetRangeInformation cluSetRange : this.getCluSetRanges()) {
            if (cluSetRange.getClusInRange().size() > 0) {
                CluGroup cluRangeCourses = new CluGroup(cluSetRange.getCluSetRangeLabel());
                cluRangeCourses.setClus(cluSetRange.getClusInRange());
                cluGroups.add(cluRangeCourses);
            }
        }

        return cluGroups;
    }

}
