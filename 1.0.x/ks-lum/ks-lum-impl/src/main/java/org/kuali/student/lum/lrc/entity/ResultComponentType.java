/**
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

package org.kuali.student.lum.lrc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;

@Entity
@Table(name = "KSLU_LRC_RESCOMP_TYPE")
public class ResultComponentType extends Type<ResultComponentTypeAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ResultComponentTypeAttribute> attributes;

    @Column(name = "VALUE_TYPE")
    Class<? extends ResultValue> resultValueType;

    @Override
    public List<ResultComponentTypeAttribute> getAttributes() {
        if(attributes==null){
            attributes = new ArrayList<ResultComponentTypeAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<ResultComponentTypeAttribute> attributes) {
        this.attributes=attributes;
    }

    /**
     * @return the resultValueType
     */
    public Class<? extends ResultValue> getResultValueType() {
        return resultValueType;
    }

    /**
     * @param resultValueType the resultValueType to set
     */
    public void setResultValueType(Class<? extends ResultValue> resultValueType) {
        this.resultValueType = resultValueType;
    }


}
