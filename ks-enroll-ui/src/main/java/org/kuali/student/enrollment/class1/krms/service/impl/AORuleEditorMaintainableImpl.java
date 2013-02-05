package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.impl.repository.AgendaBo;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/04
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class AORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        Object dataObject = null;

        // Since the dataObject is a wrapper class we need to build it and populate with the agenda bo.
        RuleEditor ruleEditor = new RuleEditor();

        String cluId = dataObjectKeys.get("cluId");
        ruleEditor.setCluId(cluId);

        //Populate the agenda. Should be retrieved based on agenda type passed as parameter.
        List<ReferenceObjectBinding> refObjects = getReferenceObjectBindingBoService().findReferenceObjectBindingsByReferenceObject(cluId);
        for (ReferenceObjectBinding refObject : refObjects) {
            if ("Agenda".equals(refObject.getKrmsDiscriminatorType())) {

                AgendaBo agenda = KRADServiceLocator.getBusinessObjectService().findBySinglePrimaryKey(AgendaBo.class, refObject.getKrmsObjectId());
                if (agenda != null) {
                    ruleEditor.setAgenda(agenda);
                    break;
                }

            }
        }

        if (ruleEditor.getAgenda() == null) {
            ruleEditor.setAgenda(new AgendaBo());
        }

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

        dataObject = ruleEditor;

        return dataObject;
    }
}
