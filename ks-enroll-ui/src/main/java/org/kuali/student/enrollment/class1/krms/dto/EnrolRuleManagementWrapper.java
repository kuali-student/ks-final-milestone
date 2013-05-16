package org.kuali.student.enrollment.class1.krms.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.student.enrollment.class1.krms.util.CluSetRangeHelper;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/04/04
 * Time: 1:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnrolRuleManagementWrapper extends RuleManagementWrapper {

    private String cluDescription;
    private List<CluInformation> clusInRange;

    public String getCluDescription() {
        return cluDescription;
    }

    public void setCluDescription(String cluDescription) {
        this.cluDescription = cluDescription;
    }

    public EnrolRuleEditor getEnrolRuleEditor(){
        return (EnrolRuleEditor) this.getRuleEditor();
    }

    public String getSearchByCourseRange() {
        if (this.getEnrolRuleEditor()==null){
            return "-1";
        }
        return this.getEnrolRuleEditor().getSearchByCourseRange();
    }

    public void setSearchByCourseRange(String searchByCourseRange) {
        this.getEnrolRuleEditor().setSearchByCourseRange(searchByCourseRange);
    }

    public CluSetRangeHelper getCluSetRange() {
        if(this.getEnrolRuleEditor()==null){
            return new CluSetRangeHelper();
        }
        return this.getEnrolRuleEditor().getCluSetRange();
    }

    public void setSubjectCode(CluSetRangeHelper cluSetRange) {
        this.getEnrolRuleEditor().setCluSetRange(cluSetRange);
    }

    public List<CluInformation> getClusInRange() {
        return clusInRange;
    }

    public void setClusInRange(List<CluInformation> clusInRange) {
        this.clusInRange = clusInRange;
    }
}
