/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.kim.permission.mock;

import org.kuali.rice.kim.api.type.KimType;
import org.kuali.rice.kim.api.type.KimTypeInfoService;
import org.kuali.rice.kim.framework.role.RoleTypeService;
import org.kuali.rice.kns.kim.role.PrincipalDerivedRoleTypeServiceImpl;
import org.kuali.student.common.mock.MockService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock of the service to get kim type infos
 * 
 * @author nwright
 */
public class KimTypeInfoServiceMockImpl implements KimTypeInfoService, MockService {

    private Map<String, KimType> kimTypeInfoCache = new HashMap<String, KimType>();
    private Map<String, RoleTypeService> kimRoleTypeServiceCache = new HashMap <String, RoleTypeService> ();
    
    private void init() {
    	// TODO: this code should be moved into a DataLoader
        KimType.Builder info = KimType.Builder.create();
        info.setId("2");
        info.setServiceName(PrincipalDerivedRoleTypeServiceImpl.class.getName());
        info.setName(PrincipalDerivedRoleTypeServiceImpl.class.getSimpleName());
        info.setNamespaceCode(KimPermissionConstants.KR_IDM_NAMESPACE);
        KimType data = info.build();
        this.kimTypeInfoCache.put(info.getId(), data);
        this.kimRoleTypeServiceCache.put (data.getServiceName(), new PrincipalDerivedRoleTypeServiceImpl ());
    }
    
	public KimTypeInfoServiceMockImpl() {
		super();
		
		init();
	}



	@Override
	public void clear() {
    	
    	this.kimRoleTypeServiceCache.clear();
    	this.kimTypeInfoCache.clear();
    	
    	init();
		
	}

	@Override
    public Collection<KimType> findAllKimTypes() {
        return this.kimTypeInfoCache.values();
    }

    @Override
    public KimType getKimType(String kimTypeId) {
        return this.kimTypeInfoCache.get(kimTypeId);
    }

    @Override
    public KimType findKimTypeByNameAndNamespace(String namespaceCode, String typeName) {
        for (KimType info : this.kimTypeInfoCache.values()) {
            if (info.getNamespaceCode().equals(namespaceCode)) {
                if (info.getName().equals(typeName)) {
                    return info;
                }
            }
        }
        return null;
    }

    // no usages no longer remotable from rice
    public RoleTypeService getRoleTypeService(KimType typeInfo) {
        return null;

//        String serviceName = typeInfo.getServiceName();
//        if (serviceName == null) {
//            return null;
//        }
//
//        try {
//            KimTypeService service = this.kimRoleTypeServiceCache.get(serviceName);
//            if (service != null && service instanceof RoleTypeService) {
//                return (RoleTypeService) service;
//            } else {
////                return (KimRoleTypeService) KIMServiceLocatorWeb.getService("kimNoMembersRoleTypeService");
////                return new KimDerivedRoleTypeServiceBase ();
//                return null; // remove once above is fixed
//            }
//        } catch (Exception ex) {
////            LOG.error("Unable to find role type service with name: " + serviceName);
////            LOG.error(ex.getClass().getName() + " : " + ex.getMessage());
////            return (KimRoleTypeService) KIMServiceLocatorWeb.getService("kimNoMembersRoleTypeService");
////            return new KimDerivedRoleTypeServiceBase ();
//            return null; // remove once above is fixed
//        }
    }
}
