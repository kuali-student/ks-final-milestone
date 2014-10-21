package org.kuali.student.enrollment.class1.process.keyvalues;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

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
public class ProcessInfoTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient TypeService typeService;

    public List<KeyValue> getKeyValues(ViewModel model) {
        /*
        //TODO:Build real context.
        ContextInfo context = TestHelper.getContext1();

        try {
            List<TypeInfo> types = getTypeService().getTypesByRefObjectUri(ProcessServiceConstants.REF_OBJECT_URI_PROCESS, context);
            for (TypeInfo type : types) {
                if(type.getKey().startsWith("kuali.process.process.type")) { //TODO remove check after data is fixed
                    ConcreteKeyValue keyValue = new ConcreteKeyValue();
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                    keyValues.add(keyValue);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }*/

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

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

    public KeyValue getTypeKeyValue(String typeKey) {
        ConcreteKeyValue keyValue = new ConcreteKeyValue();

        ContextInfo context = TestHelper.getContext1();

        try {
            List<TypeInfo> types = getTypeService().getTypesByRefObjectUri(ProcessServiceConstants.REF_OBJECT_URI_PROCESS, context);
            for (TypeInfo type : types) {
                if(type.getKey().equals(typeKey)) { //TODO remove check after data is fixed
                    keyValue.setKey(type.getKey());
                    keyValue.setValue(type.getName());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return keyValue;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
