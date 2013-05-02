package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
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

    public String getDelimitedString() {

        StringBuilder sb = new StringBuilder();
        for (CluInformation clu : this.getClus()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(clu.getVerIndependentId());
        }

        for (CluSetInfo cluSet : this.getCluSets()) {
            for (String id : cluSet.getCluIds()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(id);
            }
        }

        if (this.getClusInRange() != null) {
            for (CluInformation clu : this.getClusInRange()) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(clu.getVerIndependentId());
            }
        }
        return sb.toString();
    }

}
