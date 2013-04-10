package org.kuali.rice.krms.dto;

import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/05
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class TermParameterEditor implements TermParameterDefinitionContract, Serializable {

    private String id;
    private String termId;
    private String name;
    private String value;
    private Long versionNumber;

    public TermParameterEditor(){
        super();
    }

    public TermParameterEditor(TermParameterDefinitionContract contract){
        this.id = contract.getId();
        this.termId = contract.getTermId();
        this.name = contract.getName();
        this.value = contract.getValue();
        this.versionNumber = contract.getVersionNumber();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    public String getTermId() {
        return this.termId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

}
