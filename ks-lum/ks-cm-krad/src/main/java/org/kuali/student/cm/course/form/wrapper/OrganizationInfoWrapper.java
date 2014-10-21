/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Adi Rajesh on 6/7/12
 */
package org.kuali.student.cm.course.form.wrapper;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.common.util.DTOWrapper;
import org.kuali.student.common.util.DisplayWrapper;
import org.kuali.student.r2.core.organization.dto.OrgInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper around the {@link CluInstructorInfo} instance for use with KRAD UI components like the StackedCollection
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class OrganizationInfoWrapper extends OrgInfo implements Serializable, DisplayWrapper, DTOWrapper {
    private static final long serialVersionUID = -277262408836106453L;
    private String abbreviation;
    private String organizationName;
    private String id;

    protected Map<String,Object> extensionData;

    public OrganizationInfoWrapper() {
    }

    public OrganizationInfoWrapper(final OrgInfo orgInfo) {
        this.id = orgInfo.getId();
        this.organizationName = orgInfo.getLongName();
    }


    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(final String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isUserEntered() {
        if (StringUtils.isNotBlank(organizationName)) {
            return true;
        }
        return false;
    }

    public boolean isNewDto() {
        return false;
    }

    @Override
    public Map<String, Object> getExtensionData() {
        return extensionData;
    }

    /**
     * Provides a way to add additional data to the wrapper object.
     *
     * @param key
     * @param value
     */
    @Override
    public void putExtensionData(String key,Object value) {
        if (extensionData == null){
            extensionData = new HashMap<>();
        }
        extensionData.put(key, value);
    }
}