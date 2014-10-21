/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.lum.program.dto.assembly;

import org.kuali.student.r2.common.dto.RichTextInfo;

import java.util.List;

public interface ProgramPublicationAssembly extends ProgramCommonAssembly {

    /**
     * Narrative description of the Major that will show up in Catalog
     */
    public RichTextInfo getCatalogDescr();
    public void setCatalogDescr(RichTextInfo catalogDescr);

    /**
     * List of catalog targets where major information will be published.
     */
    public List<String> getCatalogPublicationTargets();
    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets);


    /**
     * An URL for additional information about the Major.
     */
    public String getReferenceURL() ;
    public void setReferenceURL(String referenceURL);

}
