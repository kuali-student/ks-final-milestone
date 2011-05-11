/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.common.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.Meta;

@SuppressWarnings("serial")
@XmlTransient
public abstract class HasAttributesAndMetaInfo extends HasAttributesInfo implements HasAttributesAndMeta, Serializable {

    @XmlElement
    private MetaInfo metaInfo;

    protected HasAttributesAndMetaInfo() {
        metaInfo = null;
    }

    protected HasAttributesAndMetaInfo(HasAttributesAndMeta hasAttsAndMeta) {
        super(hasAttsAndMeta);
        if (null != hasAttsAndMeta) {
	        this.metaInfo = null != hasAttsAndMeta.getMetaInfo() ? MetaInfo.getInstance(hasAttsAndMeta.getMetaInfo()) : null;
        }
    }

    protected HasAttributesAndMetaInfo(Meta meta) {
        this.metaInfo = null != meta ? MetaInfo.getInstance(meta) : null;
    }

    @Override
    public Meta getMetaInfo() {
        return metaInfo;
    }
    
    @Override
    public void setMetaInfo(Meta metaInfo) {
        this.metaInfo = MetaInfo.getInstance(metaInfo);
    }
}
