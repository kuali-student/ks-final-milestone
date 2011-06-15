package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.service.KNSServiceLocator;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.lu.service.LuService;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

public class CredentialProgramTypeKeyValues extends KeyValuesBase{
    private transient LuService luService;
    
    public List getKeyValues() {
        List keyValues = new ArrayList();
/*
        Collection<TravelAccountType> bos = KNSServiceLocator.getBusinessObjectService().findAll( TravelAccountType.class );
        
        keyValues.add(new ConcreteKeyValue("", ""));
        for ( TravelAccountType typ : bos ) {
            keyValues.add(new ConcreteKeyValue(typ.getAccountTypeCode(), typ.getName()));
        }
        
        
*/      /*
        try{
            List<LuTypeInfo> list = getLuService().getLuTypes(ContextInfo.newInstance());
            
        }catch (OperationFailedException ofe){
            
        }
        */
        keyValues.add(new ConcreteKeyValue("kuali.lu.type.credential.Baccalaureate", "Baccalaureate" ));
        keyValues.add(new ConcreteKeyValue("kuali.lu.type.credential.Masters", "Masters"));
        return keyValues;
    }
    
    protected LuService getLuService() {
        if(luService == null) {
            luService = GlobalResourceLoader.getService("LuService");
        }
        return this.luService;
    }
}