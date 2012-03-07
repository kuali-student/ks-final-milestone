/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.program.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;


import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.program.infc.CommonWithCoreProgram;

@SuppressWarnings("serial")
@XmlTransient
public class CommonWithCoreProgramInfo extends CommonWithCredentialProgramInfo
        implements CommonWithCoreProgram,
        Serializable {

    @XmlElement
    private String referenceURL;
    @XmlElement
    private RichTextInfo catalogDescr;
    @XmlElement
    private List<String> catalogPublicationTargets;
   
    public CommonWithCoreProgramInfo() {
    }

    public CommonWithCoreProgramInfo(CommonWithCoreProgram input) {
        super(input);
        if (input != null) {
            this.referenceURL = input.getReferenceURL();
            if (input.getCatalogDescr() != null) {
                this.catalogDescr = new RichTextInfo(input.getCatalogDescr());
            }
            this.catalogPublicationTargets = input.getCatalogPublicationTargets() != null
                    ? new ArrayList<String>(input.getCatalogPublicationTargets())
                    : new ArrayList<String>();
        }
    }

    @Override
    public String getReferenceURL() {
        return referenceURL;
    }

    public void setReferenceURL(String referenceURL) {
        this.referenceURL = referenceURL;
    }

    @Override
    public RichTextInfo getCatalogDescr() {
        return catalogDescr;
    }

    public void setCatalogDescr(RichTextInfo catalogDescr) {
        this.catalogDescr = catalogDescr;
    }

    @Override
    public List<String> getCatalogPublicationTargets() {
        return catalogPublicationTargets;
    }

    public void setCatalogPublicationTargets(List<String> catalogPublicationTargets) {
        this.catalogPublicationTargets = catalogPublicationTargets;
    }
}