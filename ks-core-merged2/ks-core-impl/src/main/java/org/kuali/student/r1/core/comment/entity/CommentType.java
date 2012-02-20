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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.Type;;;
@Deprecated
@Entity
@Table(name = "KSCO_COMMENT_TYPE")
@NamedQueries(
	{@NamedQuery(name = "CommentType.getCommentTypesByReferenceTypeId", query="SELECT DISTINCT comment.type FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceType.id = :referenceTypeId")
})

public class CommentType extends Type<CommentTypeAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CommentTypeAttribute> attributes;

    @Override
    public List<CommentTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<CommentTypeAttribute> attributes) {
        this.attributes=attributes;
    }

}
