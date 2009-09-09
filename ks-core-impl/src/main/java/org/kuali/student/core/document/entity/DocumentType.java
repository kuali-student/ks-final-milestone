package org.kuali.student.core.document.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;
@Entity
@Table(name = "KSDO_DOCUMENT_TYPE")

public class DocumentType extends Type<DocumentTypeAttribute> {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<DocumentTypeAttribute> attributes;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "type",targetEntity=Document.class)
    private List<Document> documents;
    

    
    @Override
    public List<DocumentTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<DocumentTypeAttribute> attributes) {
        this.attributes=attributes;
    }
    
    public List<Document> getDocument(){
        return documents;
    }
    
    public void setDocument(List<Document> documents){
        this.documents=documents;
    }
    


}
