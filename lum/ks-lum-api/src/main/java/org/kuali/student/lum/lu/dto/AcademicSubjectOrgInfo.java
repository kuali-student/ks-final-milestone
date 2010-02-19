/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Information about an organization that represents the Academic Subject for a learning unit. This often would be the same as the primaryAdminOrg.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Mon Jan 11 15:20:14 PST 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/academicSubjectOrgInfo+Structure+v1.0-rc2">AcademicSubjectOrgInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AcademicSubjectOrgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String orgId;

    /**
     * Unique identifier for an organization.
     */
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}