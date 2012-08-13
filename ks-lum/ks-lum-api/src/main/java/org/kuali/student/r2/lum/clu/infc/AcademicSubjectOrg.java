/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.r2.lum.clu.infc;

/**
 * Information about an organization that represents the Academic Subject for a
 * learning unit. This often would be the same as the primaryAdminOrg.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface AcademicSubjectOrg {
    /**
     * Unique identifier for an organization
     *
     * @name Org Id
     */
    public String getOrgId();
}
