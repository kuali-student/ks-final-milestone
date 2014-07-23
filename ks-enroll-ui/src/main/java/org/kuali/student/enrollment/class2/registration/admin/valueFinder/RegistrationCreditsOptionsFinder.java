package org.kuali.student.enrollment.class2.registration.admin.valueFinder;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 23 July 2014
 * <p/>
 * Implementation of the RegistrationCreditsOptionsFinder .... that support the AdminRegistrationView.xml.
 */
public class RegistrationCreditsOptionsFinder extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model, InputField field) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        keyValues.add(new ConcreteKeyValue("1", "1.0"));
        keyValues.add(new ConcreteKeyValue("2", "2.0"));
        keyValues.add(new ConcreteKeyValue("3", "3.0"));

        return keyValues;
    }

}
