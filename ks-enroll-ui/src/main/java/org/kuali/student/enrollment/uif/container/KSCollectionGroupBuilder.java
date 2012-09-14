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

    @Override
    protected List<Action> initializeLineActions(List<Action> lineActions, View view, Object model,
            CollectionGroup collectionGroup, Object collectionLine, int lineIndex, String actionScript) {
        String lineSuffix = UifConstants.IdSuffixes.LINE + Integer.toString(lineIndex);
        if (StringUtils.isNotBlank(collectionGroup.getSubCollectionSuffix())) {
            lineSuffix = collectionGroup.getSubCollectionSuffix() + lineSuffix;
        }
        List<Action> actions = ComponentUtils.copyComponentList(lineActions, lineSuffix);

        for (Action action : actions) {
            action.addActionParameter(UifParameters.SELLECTED_COLLECTION_PATH,
                    collectionGroup.getBindingInfo().getBindingPath());
            action.addActionParameter(UifParameters.SELECTED_LINE_INDEX, Integer.toString(lineIndex));
            action.setJumpToIdAfterSubmit(collectionGroup.getId());
            String script;
            if (StringUtils.isNotBlank(action.getActionScript())) {
                script = action.getActionScript();
            }else{
                script = actionScript;
            }
            action.setActionScript(script);
        }

        ComponentUtils.updateContextsForLine(actions, collectionLine, lineIndex);

        return actions;
    }
}
