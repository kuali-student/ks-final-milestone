package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;

import org.kuali.student.r2.lum.lu.service.LuService;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;

public class TermInfoTypeKeyValues extends KeyValuesBase implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private transient LuService luService;
	
    public List getKeyValues() {
        List keyValues = new ArrayList();

        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Fall", "Fall" ));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Winter", "Winter"));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Spring", "Spring"));
        keyValues.add(new ConcreteKeyValue("kuali.atp.type.Summer", "Summer"));
        return keyValues;
    }
    
    protected LuService getLuService() {
        if(luService == null) {
            luService = (LuService)GlobalResourceLoader.getService(new QName(LuServiceConstants.LU_NAMESPACE,"LuService"));

        }
        return this.luService;
    }
}
