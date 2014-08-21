package org.kuali.student.lum.lu.ui.course.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class returns the possible Action types available for a particular workflow document status in an ordered list.
 *
 * @author Kuali Student Team
 */
public class AuthorCollaboratorActionValuesFinder  extends UifKeyValuesFinderBase {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorCollaboratorActionValuesFinder.class);

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> permissionsKeyValues = new ArrayList<KeyValue>();
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;

        WorkflowDocument workflowDocument = maintenanceForm.getDocument().getDocumentHeader().getWorkflowDocument();

        if (DocumentStatus.SAVED.getCode().equals(workflowDocument.getStatus().getCode())) {
            permissionsKeyValues.add(new ConcreteKeyValue(ActionType.FYI.getCode(), ActionType.FYI.getLabel()));
        }

        if (DocumentStatus.ENROUTE.getCode().equals(workflowDocument.getStatus().getCode())) {
            permissionsKeyValues.add(new ConcreteKeyValue(ActionType.APPROVE.getCode(),
                    StudentWorkflowConstants.ActionRequestType.APPROVE.getActionRequestLabel()));
            permissionsKeyValues.add(new ConcreteKeyValue(ActionType.ACKNOWLEDGE.getCode(),
                    StudentWorkflowConstants.ActionRequestType.ACKNOWLEDGE.getActionRequestLabel()));
            permissionsKeyValues.add(new ConcreteKeyValue(ActionType.FYI.getCode(), ActionType.FYI.getLabel()));
        }
        return permissionsKeyValues;
    }

}
