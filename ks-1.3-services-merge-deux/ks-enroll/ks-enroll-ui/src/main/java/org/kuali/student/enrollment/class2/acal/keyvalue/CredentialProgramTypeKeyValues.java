package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.lum.lu.service.LuServiceConstants;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

public class CredentialProgramTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
	private static final long serialVersionUID = 1L;	
	
    private transient LuService luService;

    private static List<LuTypeInfo> luTypes;

    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
        	//pull out data from KSLU_LUTYPE
           	List<LuTypeInfo> luTypeInfoList = getLuTypes();
        	
            for(LuTypeInfo luTypeInfo : luTypeInfoList) {
                String luTypeInfoKey = luTypeInfo.getId();
                if (luTypeInfoKey.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
                    String name = luTypeInfo.getName();
                    keyValues.add(new ConcreteKeyValue(luTypeInfoKey,name));
                }
            }
        }catch (OperationFailedException e) {
            throw new RuntimeException(e);
        }
       
        return keyValues;
    }
    
    //Note: here I am using r1 LuService implementation!!!
    protected LuService getLuService() {
        if(luService == null) {
        	luService = (LuService)GlobalResourceLoader.getService(new QName(LuServiceConstants.LU_NAMESPACE,"LuService"));
        }
        return this.luService;
    }

    public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
        if(luTypes == null) {
            luTypes = Collections.unmodifiableList(getLuService().getLuTypes());
        }

        return luTypes;
    }
}
