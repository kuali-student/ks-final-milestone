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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.kuali.rice.kim.bo.types.dto.KimTypeInfo;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.KimTypeInfoService;
import org.kuali.rice.kim.service.support.KimRoleTypeService;
import org.kuali.rice.kim.service.support.KimTypeService;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;
import org.kuali.rice.kim.service.support.impl.PrincipalDerivedRoleTypeServiceImpl;

/**
 * Mock of the service to get kim type infos
 * 
 * @author nwright
 */
public class KimTypeInfoServiceMockImpl implements KimTypeInfoService {

    private Map<String, KimTypeInfo> kimTypeInfoCache = new HashMap<String, KimTypeInfo>();
    private Map<String, KimRoleTypeService> kimRoleTypeServiceCache = new HashMap <String, KimRoleTypeService> ();
    {
        KimTypeInfo info = null;

        info = new KimTypeInfo ();
        info.setKimTypeId("2");
        info.setKimTypeServiceName(PrincipalDerivedRoleTypeServiceImpl.class.getName());
        info.setName(PrincipalDerivedRoleTypeServiceImpl.class.getSimpleName());
        info.setNamespaceCode(KimPermissionConstants.KR_IDM_NAMESPACE);
        this.kimTypeInfoCache.put(info.getKimTypeId(), info);
        this.kimRoleTypeServiceCache.put (info.getKimTypeServiceName(), new PrincipalDerivedRoleTypeServiceImpl ());
    }
    @Override
    public Collection<KimTypeInfo> getAllTypes() {
        return this.kimTypeInfoCache.values();
    }

    @Override
    public KimTypeInfo getKimType(String kimTypeId) {
        return this.kimTypeInfoCache.get(kimTypeId);
    }

    @Override
    public KimTypeInfo getKimTypeByName(String namespaceCode, String typeName) {
        for (KimTypeInfo info : this.kimTypeInfoCache.values()) {
            if (info.getNamespaceCode().equals(namespaceCode)) {
                if (info.getName().equals(typeName)) {
                    return info;
                }
            }
        }
        return null;
    }

    public KimRoleTypeService getRoleTypeService(KimTypeInfo typeInfo) {
        String serviceName = typeInfo.getKimTypeServiceName();
        if (serviceName == null) {
            return null;
        }

        try {
            KimTypeService service = this.kimRoleTypeServiceCache.get(serviceName);
            if (service != null && service instanceof KimRoleTypeService) {
                return (KimRoleTypeService) service;
            } else {
//                return (KimRoleTypeService) KIMServiceLocator.getService("kimNoMembersRoleTypeService");
                return new KimDerivedRoleTypeServiceBase ();
            }
        } catch (Exception ex) {
//            LOG.error("Unable to find role type service with name: " + serviceName);
//            LOG.error(ex.getClass().getName() + " : " + ex.getMessage());
//            return (KimRoleTypeService) KIMServiceLocator.getService("kimNoMembersRoleTypeService");
            return new KimDerivedRoleTypeServiceBase ();
        }
    }
}
