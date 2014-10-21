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

package org.kuali.student.r1.core.document.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.BaseEntity;

@Deprecated
@Entity
@Table(name = "KSDO_DOCUMENT_CATEGORY")
@NamedQueries( {
        @NamedQuery(name = "DocumentCategory.getCategories", query = "SELECT  category FROM DocumentCategory category JOIN category.documents doc WHERE doc.id=:documentId")})

@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="CATEGORY_ID"))})        
public class DocumentCategory extends BaseEntity implements AttributeOwner<DocumentCategoryAttribute> {

	@Column(name = "NAME")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private DocumentRichText descr;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<DocumentCategoryAttribute> attributes;

    @ManyToMany(mappedBy="categoryList",fetch=FetchType.EAGER)
    private List<Document> documents;
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * @return the commentText
     */
    public DocumentRichText getDescr() {
        return descr;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setDescr(DocumentRichText descr) {
        this.descr = descr;
    }
    
    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    
    
    
    @Override
    public List<DocumentCategoryAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<DocumentCategoryAttribute> attributes) {
        this.attributes=attributes;
    }
    
    
    public List<Document> getDocuments(){
        return documents;
    }
    
    public void setDocuements(List<Document> documents){
        this.documents=documents;
    }

}
