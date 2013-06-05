package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.enrollment.class1.krms.util.CluSetRangeHelper;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CluSetInformation implements Serializable {

    private static final long serialVersionUID = 1123124L;
    private CluSetInfo cluSetInfo;
    private List<CluInformation> clus;
    private List<CluSetInfo> cluSets;
    private MembershipQueryInfo membershipQueryInfo;
    private List<CluInformation> clusInRange;
    private Map<String, CluSetInformation> subCluSetInformations;

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

    public MembershipQueryInfo getMembershipQueryInfo() {
        return membershipQueryInfo;
    }

    public void setMembershipQueryInfo(MembershipQueryInfo membershipQueryInfo) {
        this.membershipQueryInfo = membershipQueryInfo;
    }

    public List<CluInformation> getClusInRange() {
        return clusInRange;
    }

    public void setClusInRange(List<CluInformation> clusInRange) {
        this.clusInRange = clusInRange;
    }

    public Map<String, CluSetInformation> getSubCluSetInformations() {
        if (subCluSetInformations == null) {
            subCluSetInformations = new HashMap<String, CluSetInformation>();
        }
        return subCluSetInformations;
    }

    public void setSubCluSetInformations(Map<String, CluSetInformation> subCluSetInformations) {
        this.subCluSetInformations = subCluSetInformations;
    }

    public String getCluDelimitedString() {

        List<String> cluIds = this.getCluIds();
        if (this.getClusInRange() != null) {
            for (CluInformation clu : this.getClusInRange()) {
                cluIds.add(clu.getVerIndependentId());
            }
        }

        Collections.sort(cluIds);
        return StringUtils.collectionToCommaDelimitedString(cluIds);
    }

    public String getCluSetDelimitedString() {

        List<String> cluSetIds = new ArrayList<String>();
        for (CluSetInfo cluSet : this.getCluSets()) {
            cluSetIds.add(cluSet.getId());
        }

        Collections.sort(cluSetIds);
        return StringUtils.collectionToCommaDelimitedString(cluSetIds);
    }

    public boolean hasClus() {
        if ((this.getClus() != null) && (!this.getClus().isEmpty())) {
            return true;
        }
        return false;
    }

    public boolean hasMembershipQuery() {
        MembershipQueryInfo mqInfo = this.getMembershipQueryInfo();
        if (mqInfo != null && mqInfo.getSearchTypeKey() != null && !mqInfo.getSearchTypeKey().isEmpty()) {
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
