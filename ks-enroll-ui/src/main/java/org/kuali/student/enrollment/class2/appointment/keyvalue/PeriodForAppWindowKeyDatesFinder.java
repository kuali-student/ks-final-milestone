package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodForAppWindowKeyDatesFinder extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<>();
        RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm) model;

        if ("all".equals(form.getPeriodId())) {
            for (KeyDateInfo keyDate : form.getPeriodMilestones()) {
                keyValues.add(new ConcreteKeyValue(keyDate.getId(), keyDate.getName()));
            }
        } else {
            for (KeyDateInfo keyDate : form.getPeriodMilestones()) {
                if (form.getPeriodId().equals(keyDate.getId())) {
                    keyValues.add(new ConcreteKeyValue(keyDate.getId(), keyDate.getName()));
                }
            }
        }

        return keyValues;
    }
}
