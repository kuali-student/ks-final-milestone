package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.element.Label;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * This class overrides the default table layout manager to hide the details
 * action.
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesTableLayoutManager extends TableLayoutManager {

    private Field requisitesField;

    @Override
    public void buildLine(View view, Object model, CollectionGroup collectionGroup, List<Field> lineFields, List<FieldGroup> subCollectionFields,
                          String bindingPath, List<Action> actions, String idSuffix, Object currentLine, int lineIndex) {
        super.buildLine(view, model, collectionGroup, lineFields, subCollectionFields, bindingPath, actions, idSuffix,
                currentLine, lineIndex);

        ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper) currentLine;

        if((aoWrapper.getRequisite()!=null)&&(!aoWrapper.getRequisite().isEmpty())){
            Field requisitesPrototype = this.getRequisitesField();

            Field requisites = ComponentUtils.copy(requisitesPrototype,
                idSuffix + UifConstants.IdSuffixes.SUB + lineIndex);

            requisites.setColSpan(this.getNumberOfDataColumns());
            this.getAllRowFields().add(requisites);
        }
    }

    @Override
    public List<Component> getComponentPrototypes() {
        List<Component> components = super.getComponentPrototypes();
        components.add(this.getRequisitesField());
        return components;
    }

    public Field getRequisitesField() {
        return requisitesField;
    }

    public void setRequisitesField(Field requisitesField) {
        this.requisitesField = requisitesField;
    }

    @Override
    protected <T> void copyProperties(T layoutManager) {
        super.copyProperties(layoutManager);

        ScheduleOfClassesTableLayoutManager tableLayoutManagerCopy = (ScheduleOfClassesTableLayoutManager) layoutManager;
        if (this.getRequisitesField() != null) {
            tableLayoutManagerCopy.setRequisitesField((Field) this.getRequisitesField().copy());
        }
    }
}
