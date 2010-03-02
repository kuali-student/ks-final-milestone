/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KSLU_LRC_GRADE")
@NamedQueries( {
    @NamedQuery(name = "Grade.getGradesByIdList", query = "SELECT c FROM Grade c WHERE c.id IN (:gradeIdList)"),
    @NamedQuery(name = "Grade.getGradeIdsByGradeType", query = "SELECT c.id FROM Grade c JOIN c.type type WHERE type.id = :gradeTypeId"),
    @NamedQuery(name = "Grade.getGradesByScale", query = "SELECT c FROM Grade c WHERE c.scale.id = :scale")
})
@DiscriminatorValue("Grade")
public class Grade extends ResultValue implements AttributeOwner<GradeAttribute> {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SCALE_KEY")
    private Scale scale;

    @Column(name = "RANK")
    private String rank;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<GradeAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private GradeType type;
    /**
     * @return the scale
     */
    public Scale getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(Scale scale) {
        this.scale = scale;
    }

    /**
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * @param rank
     *            the rank to set
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * @return the type
     */
    public GradeType getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(GradeType type) {
        this.type = type;
    }

    @Override
    public List<GradeAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<GradeAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<GradeAttribute> attributes) {
        this.attributes = attributes;
    }

}
