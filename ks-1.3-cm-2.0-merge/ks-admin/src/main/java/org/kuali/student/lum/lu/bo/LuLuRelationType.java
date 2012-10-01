package org.kuali.student.lum.lu.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.kuali.student.core.bo.KsMetaInactivatableFromToBase;
import org.kuali.student.core.bo.KsTypeBusinessObject;

public class LuLuRelationType extends KsMetaInactivatableFromToBase implements KsTypeBusinessObject {

    private static final long serialVersionUID = 3365217787985069099L;
    
    @ManyToMany
    @JoinTable(name = "KSLU_LULU_RELTN_TYPE_JN_LU_TYP", joinColumns = @JoinColumn(name = "LULU_RELTN_TYPE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "LU_TYPE_ID", referencedColumnName = "TYPE_KEY"))
    private List<LuType> luTypes;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR")
    private String description;

    @Column(name = "REV_NAME")
    private String reverseName;

    @Column(name = "REV_DESC")
    private String reverseDescription;

    
    public List<LuType> getLuTypes() {
        return luTypes;
    }

    public void setLuTypes(List<LuType> luTypes) {
        this.luTypes = luTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReverseName() {
        return reverseName;
    }

    public void setReverseName(String reverseName) {
        this.reverseName = reverseName;
    }

    public String getReverseDescription() {
        return reverseDescription;
    }

    public void setReverseDescription(String reverseDescription) {
        this.reverseDescription = reverseDescription;
    }


}
