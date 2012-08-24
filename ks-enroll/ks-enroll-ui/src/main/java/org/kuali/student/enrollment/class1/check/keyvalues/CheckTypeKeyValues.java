package org.kuali.student.enrollment.class1.check.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 21310823
 * Date: 7/19/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class CheckTypeKeyValues extends KeyValuesBase implements Serializable  {

    private static final long serialVersionUID = 1L;

    private transient TypeService typeService;

    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("");
        topKeyValue.setValue("Select One");
        keyValues.add(topKeyValue);

        ConcreteKeyValue keyValue = new ConcreteKeyValue();
        keyValue.setKey("hold");
        keyValue.setValue("Hold");
        keyValues.add(keyValue);

        ConcreteKeyValue keyValue1 = new ConcreteKeyValue();
        keyValue1.setKey("milestone");
        keyValue1.setValue("Milestone");
        keyValues.add(keyValue1);

        ConcreteKeyValue keyValue2 = new ConcreteKeyValue();
        keyValue2.setKey("process");
        keyValue2.setValue("Process");
        keyValues.add(keyValue2);


        return keyValues;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
