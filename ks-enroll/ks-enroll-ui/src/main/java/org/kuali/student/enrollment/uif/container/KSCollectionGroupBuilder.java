package org.kuali.student.enrollment.uif.container;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.container.CollectionGroupBuilder;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;

import java.util.List;

public class KSCollectionGroupBuilder extends CollectionGroupBuilder{

    protected List<Action> getLineActions(View view, Object model, CollectionGroup collectionGroup,
			Object collectionLine, int lineIndex) {

        String lineSuffix = UifConstants.IdSuffixes.LINE + Integer.toString(lineIndex);
        if (StringUtils.isNotBlank(collectionGroup.getSubCollectionSuffix())) {
            lineSuffix = collectionGroup.getSubCollectionSuffix() + lineSuffix;
        }
        List<Action> lineActions = ComponentUtils.copyComponentList(collectionGroup.getLineActions(), lineSuffix);

		for (Action action : lineActions) {
			action.addActionParameter(UifParameters.SELLECTED_COLLECTION_PATH, collectionGroup.getBindingInfo()
					.getBindingPath());
			action.addActionParameter(UifParameters.SELECTED_LINE_INDEX, Integer.toString(lineIndex));
			action.setJumpToIdAfterSubmit(collectionGroup.getId() + "_div");

            if (StringUtils.isBlank(action.getActionScript())){
                action.setActionScript("performCollectionAction('"+collectionGroup.getId()+"');");
            }
		}

		ComponentUtils.updateContextsForLine(lineActions, collectionLine, lineIndex);

		return lineActions;
	}
}
