package org.kuali.student.enrollment.class2.scheduleofclasses.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.CollectionGroup;
import org.kuali.rice.krad.uif.field.Field;
import org.kuali.rice.krad.uif.field.FieldGroup;
import org.kuali.rice.krad.uif.layout.TableLayoutManager;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;

import java.util.List;

/**
 * This class overrides the default table layout manager to hide the details
 * action.
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesTableLayoutManager extends TableLayoutManager {

    private Field requisitesField;
    private Field commonRequisiteField;
    private String name = StringUtils.EMPTY;

    @Override
    public void buildLine(Object model, CollectionGroup collectionGroup, List<Field> lineFields, List<FieldGroup> subCollectionFields,
                          String bindingPath, List<? extends Component> actions, String idSuffix, Object currentLine, int lineIndex) {
        super.buildLine(model, collectionGroup, lineFields, subCollectionFields, bindingPath, actions, idSuffix,
                currentLine, lineIndex);

        if (currentLine instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper) currentLine;

            if ((aoWrapper.getRequisite()!=null)&&(!aoWrapper.getRequisite().isEmpty())){
                Field requisitesPrototype = this.getRequisitesField();

                Field requisites = ComponentUtils.copy(requisitesPrototype,
                        idSuffix + UifConstants.IdSuffixes.SUB + lineIndex);

                for (Field field : lineFields) {
                    if (field instanceof FieldGroup) {
                        field.setRowSpan(2);
                    }
                }

                requisites.setColSpan(this.getNumberOfDataColumns() - 1);
                this.getAllRowFields().add(requisites);
            }
        } else {
            RegistrationGroupWrapper rgWrapper = (RegistrationGroupWrapper) currentLine;

            //Set RegistrationGroupWrapper name field to encompass all rows related
            if (name.isEmpty() || !rgWrapper.getRgInfo().getName().equals(name)){

                name = rgWrapper.getRgInfo().getName();

                for (Field field : lineFields) {
                    if (field.getId().contains("regGroupName")) {
                        field.setRowSpan(getSpanSize(rgWrapper, false));
                    }
                    if (field.getId().contains("regGroupSeats")) {
                        field.setRowSpan(getSpanSize(rgWrapper, true));
                    }
                }
            } else {
                //Set related RegistrationGroupWrapper name field to hidden and remove
                // border so that it looks like one field
                for (Field field : lineFields) {
                    if (field.getId().contains("regGroupName")) {
                        field.setHidden(true);
                        field.setWrapperStyle("border-top:none;");
                        field.setRowSpan(getSpanSize(rgWrapper, false));
                    }
                    if (field.getId().contains("regGroupSeats")) {
                        field.setHidden(true);
                        field.setWrapperStyle("border-top:none");
                        field.setRowSpan(getSpanSize(rgWrapper, true));
                    }
                }
            }

            //If wrapper has common requisite, create field and add to collection
            if((rgWrapper.getCommonRequisite()!=null)&&(!rgWrapper.getCommonRequisite().isEmpty())){
                Field commonRequisitesPrototype = this.getCommonRequisiteField();

                Field commonRequisites = ComponentUtils.copy(commonRequisitesPrototype,
                        idSuffix + UifConstants.IdSuffixes.SUB + lineIndex);

                commonRequisites.setColSpan(this.getNumberOfDataColumns() - 1);

                //Add to start of row
                int index = (this.getAllRowFields().size() - lineFields.size()) + 1;
                this.getAllRowFields().add(index, commonRequisites);
            }

            //If wrapper has requisite, create field and add to collection
            if((rgWrapper.getRequisite()!=null)&&(!rgWrapper.getRequisite().isEmpty())){
                Field requisitesPrototype = this.getRequisitesField();

                Field requisites = ComponentUtils.copy(requisitesPrototype,
                        idSuffix + UifConstants.IdSuffixes.SUB + lineIndex);

                requisites.setColSpan(this.getNumberOfDataColumns() - 3);

                for(Field field : lineFields) {
                    if(field.getId().contains("activityOfferingCode")) {
                        field.setRowSpan(2);
                    }
                }

                this.getAllRowFields().add(requisites);
            }
        }
    }

    /**
     * Determine span size of row according to requisites populated
     *
     * @param registrationGroupWrapper
     * @return int of row span size
     */
    private int getSpanSize(RegistrationGroupWrapper registrationGroupWrapper, boolean seats) {
        int spanSize = 1;
        if (registrationGroupWrapper.getRequisite() != null) {
            spanSize++;
        }
        if (!seats) {
            if (registrationGroupWrapper.getCommonRequisite() != null) {
                spanSize++;
            }
        }
        return spanSize;
    }

    @Override
    public List<Component> getComponentPrototypes() {
        List<Component> components = super.getComponentPrototypes();
        components.add(this.getRequisitesField());
        components.add(this.getCommonRequisiteField());
        return components;
    }

    public Field getRequisitesField() {
        return requisitesField;
    }

    public void setRequisitesField(Field requisitesField) {
        this.requisitesField = requisitesField;
    }

    public Field getCommonRequisiteField() {
        return commonRequisiteField;
    }

    public void setCommonRequisiteField(Field commonRequisiteField) {
        this.commonRequisiteField = commonRequisiteField;
    }

    @Override
    protected <T> void copyProperties(T layoutManager) {
        super.copyProperties(layoutManager);

        ScheduleOfClassesTableLayoutManager tableLayoutManagerCopy = (ScheduleOfClassesTableLayoutManager) layoutManager;
        if (this.getRequisitesField() != null) {
            tableLayoutManagerCopy.setRequisitesField((Field) this.getRequisitesField().copy());
        }
        if (this.getCommonRequisiteField() != null) {
            tableLayoutManagerCopy.setCommonRequisiteField((Field) this.getCommonRequisiteField().copy());
        }
    }
}
