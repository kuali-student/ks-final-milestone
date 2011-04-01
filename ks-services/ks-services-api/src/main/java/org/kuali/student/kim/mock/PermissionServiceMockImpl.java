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
package org.kuali.student.kim.mock;

import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.role.dto.KimPermissionTemplateInfo;
import org.kuali.rice.kim.bo.role.dto.PermissionAssigneeInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;

import java.util.*;
import org.kuali.rice.kim.service.PermissionService;

/**
 * @author nwright
 */
public class PermissionServiceMockImpl implements PermissionService {

    @Override
    public List<KimPermissionTemplateInfo> getAllTemplates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> getAuthorizedPermissions(String string, String string1, String string2, AttributeSet as, AttributeSet as1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> getAuthorizedPermissionsByTemplateName(String string, String string1, String string2, AttributeSet as, AttributeSet as1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPermissionInfo getPermission(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssignees(String string, String string1, AttributeSet as, AttributeSet as1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PermissionAssigneeInfo> getPermissionAssigneesForTemplateName(String string, String string1, AttributeSet as, AttributeSet as1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getPermissionDetailLabel(String string, String string1, String string2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPermissionTemplateInfo getPermissionTemplate(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KimPermissionTemplateInfo getPermissionTemplateByName(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByName(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByNameIncludingInactive(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> getPermissionsByTemplateName(String string, String string1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermission(String string, String string1, AttributeSet as) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissionId(String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getRoleIdsForPermissions(List<KimPermissionInfo> list) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermission(String string, String string1, String string2, AttributeSet as) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean hasPermissionByTemplateName(String string, String string1, String string2, AttributeSet as) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isAuthorized(String string, String string1, String string2, AttributeSet as, AttributeSet as1) {
        return true;
    }

    @Override
    public boolean isAuthorizedByTemplateName(String string, String string1, String string2, AttributeSet as, AttributeSet as1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPermissionDefined(String string, String string1, AttributeSet as) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isPermissionDefinedForTemplateName(String string, String string1, AttributeSet as) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<KimPermissionInfo> lookupPermissions(Map<String, String> map, boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

