package org.kuali.student.enrollment.uif.container;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.CollectionGroupBuilder;
import org.kuali.rice.krad.uif.field.ActionField;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;

import java.util.List;

public class KSCollectionGroupBuilder extends CollectionGroupBuilder{

    protected List<ActionField> getLineActions(View view, Object model, CollectionGroup collectionGroup,
			Object collectionLine, int lineIndex) {

        String lineSuffix = UifConstants.IdSuffixes.LINE + Integer.toString(lineIndex);
        if (StringUtils.isNotBlank(collectionGroup.getSubCollectionSuffix())) {
            lineSuffix = collectionGroup.getSubCollectionSuffix() + lineSuffix;
        }
        List<ActionField> lineActions = ComponentUtils.copyFieldList(collectionGroup.getActionFields(), lineSuffix);

		for (ActionField actionField : lineActions) {
			actionField.addActionParameter(UifParameters.SELLECTED_COLLECTION_PATH, collectionGroup.getBindingInfo()
					.getBindingPath());
			actionField.addActionParameter(UifParameters.SELECTED_LINE_INDEX, Integer.toString(lineIndex));
			actionField.setJumpToIdAfterSubmit(collectionGroup.getId() + "_div");

            if (StringUtils.isBlank(actionField.getClientSideJs())){
                actionField.setClientSideJs("performCollectionAction('"+collectionGroup.getId()+"');");
            }
		}

		ComponentUtils.updateContextsForLine(lineActions, collectionLine, lineIndex);

		return lineActions;
	}
}
