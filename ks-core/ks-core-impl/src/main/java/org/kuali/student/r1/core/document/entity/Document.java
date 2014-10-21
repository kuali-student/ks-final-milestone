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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r1.common.entity.AttributeOwner;
import org.kuali.student.r1.common.entity.MetaEntity;
/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
@Entity
@Table(name = "KSDO_DOCUMENT")
@AttributeOverrides({
    @AttributeOverride(name="id", column=@Column(name="DOC_ID"))})
public class Document extends MetaEntity implements AttributeOwner<DocumentAttribute> {
    private static final long serialVersionUID = 1L;

    @Column(name = "NAME")
    private String name;
    
    @Column(name = "FILE_NAME")
    private String fileName;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private DocumentRichText descr;

    @Lob
    @Column(name = "DOCUMENT", length=10000000)
    private String document;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<DocumentAttribute> attributes;
    
    @ManyToOne(optional=true)
    @JoinColumn(name = "TYPE")
    private DocumentType type;
    
    
    @Column(name = "STATE")
    private String state;
    
    
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="KSDO_DOCUMENT_JN_DOC_CATEGORY",
            joinColumns=
            @JoinColumn(name="DOC_ID", referencedColumnName="DOC_ID"),
      inverseJoinColumns=
            @JoinColumn(name="CATEGORY_ID", referencedColumnName="CATEGORY_ID")
    )
    private List<DocumentCategory> categoryList; 
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public void setFileName(String fileName){
        this.fileName=fileName;
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
    
    
    public String getDocument(){
        return document;
    }
    
    public void setDocument(String document){
        this.document=document;
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
    public List<DocumentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<DocumentAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the type
     */
    public DocumentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(DocumentType type) {
        this.type = type;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    
    public List<DocumentCategory> getCategoryList(){
        return categoryList;
    }
    
    public void setCategoryList(List<DocumentCategory> categoryList){
        this.categoryList = categoryList;
    }

}
