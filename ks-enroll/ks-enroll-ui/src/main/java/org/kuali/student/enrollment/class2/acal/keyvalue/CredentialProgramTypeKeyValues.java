package org.kuali.student.enrollment.class2.acal.keyvalue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

public class CredentialProgramTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;

    private static List<TypeInfo> luTypes;

    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        try {
        	//pull out data from KSLU_LUTYPE
           	List<TypeInfo> luTypeInfoList = getLuTypes();
        	
            for(TypeInfo luTypeInfo : luTypeInfoList) {
                String luTypeInfoKey = luTypeInfo.getKey();
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
    
    //Note: here I am using r1 CluService implementation!!!
    protected CluService getCluService() {
        if(cluService == null) {
        	cluService = (CluService)GlobalResourceLoader.getService(new QName(CluServiceConstants.NAMESPACE,"CluService"));
        }
        return this.cluService;
    }

    public List<TypeInfo> getLuTypes() throws OperationFailedException {
        if(luTypes == null) {
            try {
                luTypes = Collections.unmodifiableList(getCluService().getLuTypes(new ContextInfo()));
            } catch (MissingParameterException mpe) {
                throw new OperationFailedException("Missing Parameter getting List of LU Types from CLU Service", mpe);
            } catch (InvalidParameterException ipe) {
                throw new OperationFailedException("Invalid Parameter getting List of LU Types from CLU Service", ipe);
            } catch (PermissionDeniedException pde) {
                throw new OperationFailedException("Permission Denied getting List of LU Types from CLU Service", pde);
            }
        }

        return luTypes;
    }
}
