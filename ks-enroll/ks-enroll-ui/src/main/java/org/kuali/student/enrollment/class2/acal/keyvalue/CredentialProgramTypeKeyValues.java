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
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.type.service.TypeService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LuServiceConstants;
import org.kuali.student.r2.common.util.constants.TypeServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class CredentialProgramTypeKeyValues extends KeyValuesBase implements Serializable{
	public static final String CREDENTIAL_PROGRAM_TYPE_KEY_PREFIX = "kuali.lu.type.credential.";
	private static final long serialVersionUID = 1L;



    private transient TypeService typeService;

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

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }
    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }

    public List<TypeInfo> getLuTypes() throws OperationFailedException {
        if(luTypes == null) {
            try {
                luTypes = Collections.unmodifiableList(getTypeService().getTypesByRefObjectUri(CluServiceConstants.REF_OBJECT_URI_CLU, ContextUtils.getContextInfo()));
            } catch (DoesNotExistException e) {
                throw new RuntimeException(e);
            } catch (MissingParameterException e) {
                throw new RuntimeException(e);
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            } catch (PermissionDeniedException e) {
                throw new RuntimeException(e);
            }
        }

        return luTypes;
    }
}
