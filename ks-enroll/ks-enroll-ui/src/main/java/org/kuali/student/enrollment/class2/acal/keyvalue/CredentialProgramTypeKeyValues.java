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
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class CredentialProgramTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
	private static final long serialVersionUID = 1L;	
	
    private transient CluService luService;

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
    
    protected CluService getCluService() {
        if(luService == null) {
        	luService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE,"CluService"));
        }
        return this.luService;
    }

    public List<LuTypeInfo> getLuTypes() throws OperationFailedException {
        if(luTypes == null) {
            luTypes = Collections.unmodifiableList(getCluService().getLuTypes());
        }

        return luTypes;
    }
}
