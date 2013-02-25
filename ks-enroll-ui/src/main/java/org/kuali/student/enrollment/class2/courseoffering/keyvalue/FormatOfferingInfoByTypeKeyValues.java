package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.util.FormatOfferingConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *  @Date                     @Author                            @Work Description
 *  December/06/2012          Kuali Student Team                 Initial  Issuance  - Hard-Coded Drop-Down value for "Format Offering Info TypeKey"
 */
//public class FormatOfferingInfoByTypeKeyValues extends  AbstractFormatOfferingTypeKeyValues implements Serializable {
public class FormatOfferingInfoByTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(FormatOfferingConstants.BLANK, FormatOfferingConstants.SELECT_FORMAT_OFFERING_TYPE_KEY ));
        keyValues.add(new ConcreteKeyValue(FormatOfferingConstants.FORMAT_OFFERING_TYPE_KEY_VALUE, FormatOfferingConstants.FORMAT_OFFERING_TYPE_KEY_VALUE));

        return keyValues;
    }
}
