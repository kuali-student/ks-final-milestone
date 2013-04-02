package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/04
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class CORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private transient CluService cluService;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        Object dataObject = null;

        String ruleId = dataObjectKeys.get("id");
        RuleDefinition rule = this.getRuleManagementService().getRule(ruleId);

        // Since the dataObject is a wrapper class we need to build it and populate with the agenda bo.
        EnrolRuleEditor ruleEditor = new EnrolRuleEditor(rule);

        //Initialize the PropositionEditors
        if ((ruleEditor != null) && (ruleEditor.getProposition() != null)) {
            this.initPropositionEditor((PropositionEditor) ruleEditor.getProposition());
        }

        String cluId = dataObjectKeys.get("cluId");

        ruleEditor.setCluId(cluId);

        //Retrieve the Clu information
        CluInfo cluInfo = null;
        if (cluId != null) {
            try {
                cluInfo = getCluService().getClu(cluId, getContextInfo());
            } catch (Exception e) {
                //TODO: Add Exception handling.
            }
        }

        //Populate Clu Identification Information
        if (cluInfo != null) {
            CluIdentifierInfo cluIdentInfo = cluInfo.getOfficialIdentifier();
            StringBuilder courseNameBuilder = new StringBuilder();
            courseNameBuilder.append(cluIdentInfo.getDivision());
            courseNameBuilder.append(" ");
            courseNameBuilder.append(cluIdentInfo.getSuffixCode());
            courseNameBuilder.append(" - ");
            courseNameBuilder.append(cluIdentInfo.getLongName());
            ruleEditor.setCourseName(courseNameBuilder.toString());
        }

        //Initialize the compare tree
        ruleEditor.setCompareTree(this.initCompareTree());

        return ruleEditor;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }


}
