package org.kuali.student.lum.lu.ui.course.keyvalues;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kew.api.WorkflowDocument;
import org.kuali.rice.kew.api.action.ActionRequestStatus;
import org.kuali.rice.kew.api.action.ActionType;
import org.kuali.rice.kew.api.document.DocumentStatus;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.cm.course.form.wrapper.CourseInfoWrapper;
import org.kuali.student.cm.proposal.form.wrapper.ProposalElementsWrapper;
import org.kuali.student.r1.common.rice.authorization.ProposalPermissionTypes;
import org.kuali.student.r1.core.workflow.dto.CollaboratorWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class returns the permission types available for a particular workflow document status in an ordered list.
 *
 * @author Kuali Student Team
 */
public class AuthorCollaboratorPermissionValuesFinder extends UifKeyValuesFinderBase {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorCollaboratorPermissionValuesFinder.class);

    @Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field) {
        List<KeyValue> permissionsKeyValues = new ArrayList<>();
        MaintenanceDocumentForm maintenanceForm = (MaintenanceDocumentForm) model;

        WorkflowDocument workflowDocument = maintenanceForm.getDocument().getDocumentHeader().getWorkflowDocument();

        if (DocumentStatus.SAVED.getCode().equals(workflowDocument.getStatus().getCode()) ||
                DocumentStatus.ENROUTE.getCode().equals(workflowDocument.getStatus().getCode())) {

            permissionsKeyValues.add(new ConcreteKeyValue(ProposalPermissionTypes.OPEN.getCode(),
                    ProposalPermissionTypes.OPEN.getLabel()));
            permissionsKeyValues.add(new ConcreteKeyValue(ProposalPermissionTypes.ADD_COMMENT.getCode(),
                    ProposalPermissionTypes.ADD_COMMENT.getLabel() + ", " + ProposalPermissionTypes.OPEN.getLabel()));

            String editDisplayLabel = new StringBuilder(ProposalPermissionTypes.EDIT.getLabel()).append(", ")
                                            .append(ProposalPermissionTypes.ADD_COMMENT.getLabel()).append(", ")
                                            .append(ProposalPermissionTypes.OPEN.getLabel()).toString();

            if (DocumentStatus.ENROUTE.getCode().equals(workflowDocument.getStatus().getCode())) {

                ProposalElementsWrapper Wrapper = (ProposalElementsWrapper) maintenanceForm.getDocument().getNewMaintainableObject().getDataObject();
                List<CollaboratorWrapper> collaboratorWrapperList = Wrapper.getCollaboratorWrappers();
                String index = field.getContext().get(UifConstants.ContextVariableNames.INDEX).toString();
                CollaboratorWrapper collaboratorWrapper = collaboratorWrapperList.get(Integer.valueOf(index));

                // Edit permission is available for action type Approve and for the already ACTIVATED action request
                if (collaboratorWrapper != null && collaboratorWrapper.getAction() != null) {
                    if ((collaboratorWrapper.getActionRequestStatus() != null &&
                        ActionRequestStatus.ACTIVATED.getLabel().equals(collaboratorWrapper.getActionRequestStatus())) ||
                        collaboratorWrapper.getAction().equals(ActionType.APPROVE.getCode())) {
                            permissionsKeyValues.add(new ConcreteKeyValue(ProposalPermissionTypes.EDIT.getCode(), editDisplayLabel));
                    }
                }
            } else {
                permissionsKeyValues.add(new ConcreteKeyValue(ProposalPermissionTypes.EDIT.getCode(), editDisplayLabel));
            }

        }

        return permissionsKeyValues;
    }

}
