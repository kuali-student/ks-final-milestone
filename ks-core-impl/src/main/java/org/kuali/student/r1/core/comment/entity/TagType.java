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

package org.kuali.student.r1.core.comment.entity;

import org.kuali.student.r1.common.entity.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@Deprecated
@Entity
@Table(name = "KSCO_TAG_TYPE")
public class TagType extends Type<TagTypeAttribute> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TagTypeAttribute> attributes;
    
    @OneToMany(cascade=CascadeType.ALL, mappedBy="type")
    private List<Tag> tags;

    /**
     * 
     * @return
     */
    @Override
    public List<TagTypeAttribute> getAttributes() {
        
        return attributes;
    }

    /**
     * 
     * @param attributes
     */
    @Override
    public void setAttributes(List<TagTypeAttribute> attributes) {
        this.attributes=attributes;
        
    }
    
    /**
     * 
     * @return
     */
    public List<Tag> getTag(){
        return tags;
    }
    
    
    /**
     * 
     * @param tags
     */
    public void setTag(List<Tag> tags){
        this.tags=tags;
    }
    
    
}
