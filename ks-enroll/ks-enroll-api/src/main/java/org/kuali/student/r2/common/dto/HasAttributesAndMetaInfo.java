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

import org.kuali.student.common.infc.HasAttributesAndMeta;
import org.kuali.student.common.infc.Meta;

@SuppressWarnings("serial")
@XmlTransient
public abstract class HasAttributesAndMetaInfo extends HasAttributesInfo implements HasAttributesAndMeta, Serializable {

    @XmlElement
    private final MetaInfo metaInfo;

    protected HasAttributesAndMetaInfo() {
        metaInfo = null;
    }

    protected HasAttributesAndMetaInfo(HasAttributesAndMeta builder) {
        super(builder);
        this.metaInfo = null != builder.getMetaInfo() ? new MetaInfo.Builder(builder.getMetaInfo()).build() : null;
    }

    @Override
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public static class Builder extends HasAttributesInfo.Builder implements HasAttributesAndMeta {

        private Meta metaInfo;

        public Builder() {}

        public Builder(HasAttributesAndMeta hasAMInfo) {
            super(hasAMInfo);
            
            if (null != hasAMInfo.getMetaInfo()) {
	            MetaInfo.Builder builder = new MetaInfo.Builder();
	            builder.setCreateId(hasAMInfo.getMetaInfo().getCreateId());
	            builder.setCreateTime(hasAMInfo.getMetaInfo().getCreateTime());
	            builder.setUpdateId(hasAMInfo.getMetaInfo().getUpdateId());
	            builder.setUpdateTime(hasAMInfo.getMetaInfo().getUpdateTime());
	            builder.setVersionInd(hasAMInfo.getMetaInfo().getVersionInd());
	            this.metaInfo = builder.build();
            }
        }

        @Override
        public Meta getMetaInfo() {
            return metaInfo;
        }

        public void setMetaInfo(Meta metaInfo) {
            this.metaInfo = metaInfo;
        }
    }
}
