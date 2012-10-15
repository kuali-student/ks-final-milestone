package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a dummy class to fix the Rice 2.2 M4 issue... when we use finalizeMethodToCall() with
 * Multi select controls, it results in NPE. To fix the NPE (KSENROLL-3423), created this dummy
 * values finder which would return a single element. This has been already fixed at the rice
 * trunk. So, the rice upgrade should fix this issue..
 *
 * @author venkat
 */
public class DummyKeyValue extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        ConcreteKeyValue dummy = new ConcreteKeyValue("", "");
        List<KeyValue> keyValueList = new ArrayList<KeyValue>();
        keyValueList.add(dummy);

        return keyValueList;
    }
}
