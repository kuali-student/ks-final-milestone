package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.Serializable;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;

import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.lum.lu.service.LuServiceConstants;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuTypeInfo;

public class CredentialProgramTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
	private static final long serialVersionUID = 1L;	
	
    private transient LuService luService;
    
    public List getKeyValues() {
        List<ConcreteKeyValue> keyValues = new ArrayList<ConcreteKeyValue>();

//        keyValues.add(new ConcreteKeyValue("kuali.lu.type.credential.Baccalaureate", "Baccalaureate" ));
//        keyValues.add(new ConcreteKeyValue("kuali.lu.type.credential.Masters", "Masters"));

 
        try {
        	//pull out data from KSLU_LUTYPE
           	List<LuTypeInfo> luTypeInfoList = getLuService().getLuTypes();
        	
        	if (luTypeInfoList == null){
        		System.out.println(">>Didn't get luTypeInfoList, luTypeInfoList is null.");
        	}
        	else if (luTypeInfoList.size()== 0){
        		System.out.println(">>Didn't get luTypeInfoList, luTypeInfoList is zero.");
        	}
        	else if (luTypeInfoList.size()>= 0){
        		Iterator luTypeInfoIterator = luTypeInfoList.iterator();
        		while (luTypeInfoIterator.hasNext()){
        			LuTypeInfo luTypeInfo = (LuTypeInfo)luTypeInfoIterator.next();
        			String luTypeInfoKey = luTypeInfo.getId();
        			if (luTypeInfoKey.startsWith(CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX)){
        				String name = luTypeInfo.getName();
        				System.out.println(">>>luTypeInfoKey="+luTypeInfoKey+", name="+name);
        				keyValues.add(new ConcreteKeyValue(luTypeInfoKey,name));
        			}        			
        		}
        	}
        }catch (OperationFailedException ofe){
        	return keyValues;
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
}
