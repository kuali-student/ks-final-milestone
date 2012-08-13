package org.kuali.student.enrollment.class1.hold.service.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.type.service.TypeService;

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
public class ProcessInfoTypeKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient TypeService typeService;

    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        ConcreteKeyValue topKeyValue = new ConcreteKeyValue();
        topKeyValue.setKey("select");
        topKeyValue.setValue("Select One");
        keyValues.add(topKeyValue);

        ConcreteKeyValue keyValue = new ConcreteKeyValue();
        keyValue.setKey("reg");
        keyValue.setValue("Registration");
        keyValues.add(keyValue);

        ConcreteKeyValue keyValue1 = new ConcreteKeyValue();
        keyValue1.setKey("academic");
        keyValue1.setValue("Academic Record");
        keyValues.add(keyValue1);

        return keyValues;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
