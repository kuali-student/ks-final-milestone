package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.student.enrollment.class1.krms.util.CluSetRangeHelper;
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

    private List<CluSetRangeHelper> cluSetRange;

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

    public List<CluSetRangeHelper> getCluSetRange() {
        if (this.cluSetRange == null) {
            this.cluSetRange = new ArrayList<CluSetRangeHelper>();
        }
        return cluSetRange;
    }

    public void setCluSetRange(List<CluSetRangeHelper> cluSetRange) {
        this.cluSetRange.set(0, cluSetRange.get(cluSetRange.size() - 1));
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

        StringBuilder sb = new StringBuilder();
        for (CluInformation clu : this.getClus()) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(clu.getVerIndependentId());
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
