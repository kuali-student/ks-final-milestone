package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;


//import org.kuali.student.r2.common.dto.ContextInfo;
//import org.kuali.student.r2.core.type.dto.TypeInfo;
//import org.kuali.student.test.utilities.TestHelper;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;



import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegWindowTypeKeyValues extends KeyValuesBase implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient AppointmentService appointmentService;

    private String[][] mockData = new String[][]{{"kuali.appointment.window.type.one.slot","One Slot"},
            {"kuali.appointment.window.type.slotted.uniform","Uniform"},
            {"kuali.appointment.window.type.slotted.max","Max Number"}};

    @Override
    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        for(String[] data:mockData){
            ConcreteKeyValue keyValue = new ConcreteKeyValue();
            keyValue.setKey(data[0]);
            keyValue.setValue(data[1]);
            keyValues.add(keyValue);
        }

        return keyValues;
    }

    public AppointmentService getAppointmentService() {
        if(appointmentService == null) {
            appointmentService = (AppointmentService) GlobalResourceLoader.getService(new QName(AppointmentServiceConstants.NAMESPACE, AppointmentServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.appointmentService;
    }
}
