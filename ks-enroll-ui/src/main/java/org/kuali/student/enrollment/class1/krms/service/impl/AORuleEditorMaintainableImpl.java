package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.service.impl.RuleEditorMaintainableImpl;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
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
public class AORuleEditorMaintainableImpl extends RuleEditorMaintainableImpl {

    private transient CluService cluService;

    @Override
    public Object retrieveObjectForEditOrCopy(MaintenanceDocument document, Map<String, String> dataObjectKeys) {
        RuleEditor ruleEditor = (RuleEditor) super.retrieveObjectForEditOrCopy(document, dataObjectKeys);

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

        return ruleEditor;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }
}
