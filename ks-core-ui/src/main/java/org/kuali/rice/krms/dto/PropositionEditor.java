/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.dto;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.impl.ui.TermParameter;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Kuali Student Team
 */
public class PropositionEditor implements PropositionDefinitionContract, Serializable {

    private static final long serialVersionUID = 1L;

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PropositionEditor.class);

    private String key;

    private String id;
    private String description;
    private String ruleId;
    private String compoundOpCode;
    private Integer compoundSequenceNumber;
    private String typeId;
    private String propositionTypeCode;
    private Long versionNumber;

    /**Natural Language**/
    private Map<String, String> naturalLanguage = new HashMap<String, String>();

    private List<PropositionParameterEditor> parameters;
    private List<PropositionEditor> compoundEditors;

    private TermEditor term;
    private String termParameter;
    private List<TermParameter> termParameterList = new ArrayList<TermParameter>();
    private String type;
    private boolean editMode = false;
    private boolean newProp = false;

    private String bindingPath;
    private String newTermDescription = "new term " + UUID.randomUUID().toString();

    public PropositionEditor() {
        super();
    }

    /**
     * Converts a immutable object to it's mutable bo counterpart
     *
     * @param definition immutable object
     * @return the mutable bo
     */
    public PropositionEditor(PropositionDefinitionContract definition) {
        this.id = definition.getId();
        this.description = definition.getDescription();
        this.ruleId = definition.getRuleId();

        this.typeId = definition.getTypeId();
        this.propositionTypeCode = definition.getPropositionTypeCode();
        this.parameters = new ArrayList<PropositionParameterEditor>();
        for (PropositionParameterContract parm : definition.getParameters()) {
            this.parameters.add(new PropositionParameterEditor(parm));
        }
        this.compoundOpCode = definition.getCompoundOpCode();
        this.compoundSequenceNumber = definition.getCompoundSequenceNumber();
        this.compoundEditors = new ArrayList<PropositionEditor>();
        for (PropositionDefinitionContract prop : definition.getCompoundComponents()) {
            this.compoundEditors.add(createPropositionEditor(prop));
        }
        this.versionNumber = definition.getVersionNumber();
    }

    public void clear(){
        this.description = null;
        this.term = null;
        this.termParameter = null;
        for (PropositionParameterEditor parm : this.getParameters()) {
            parm.clear();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCompoundOpCode() {
        return compoundOpCode;
    }
    
    @Override
    public Integer getCompoundSequenceNumber() {
        return compoundSequenceNumber;
    }

    public void setId(String id) {
        if (!StringUtils.isBlank(id) || id == null) {
            this.id = id;
        }
    }

    public void setDescription(String description) {
        //Description can only handle 100 characters.
        if ((description != null) && (description.length()>100)) {
            description = description.substring(0,97) + "...";
        }
        this.description = description;
        LOG.info(this.description);
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void setCompoundOpCode(String compoundOpCode) {
        this.compoundOpCode = compoundOpCode;
    }

    public void setParameters(List<PropositionParameterEditor> parameters) {
        this.parameters = parameters;
    }

    public void setCompoundEditors(List<PropositionEditor> compoundEditors) {
        this.compoundEditors = compoundEditors;
    }

    public List<PropositionEditor> getCompoundEditors() {
        return compoundEditors;
    }

    @Override
    public List<? extends PropositionDefinitionContract> getCompoundComponents() {
        return compoundEditors;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

    public List<PropositionParameterEditor> getParameters() {
        return parameters;
    }

    public String getTypeId() {
        return typeId;
    }

    @Override
    public String getRuleId() {
        return this.ruleId;
    }

    public void setPropositionTypeCode(String propositionTypeCode) {
        this.propositionTypeCode = propositionTypeCode;
    }

    @Override
    public String getPropositionTypeCode() {
        return propositionTypeCode;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public TermEditor getTerm() {
        return term;
    }

    public void setTerm(TermEditor term) {
        this.term = term;
    }

    public String getTermParameter() {
        return termParameter;
    }

    public void setTermParameter(String termParameter) {
        this.termParameter = termParameter;

        if(this.termParameter!=null){
            LOG.info(this.termParameter);

            //This is just temp code to prove what the actual problem is.
            this.termParameter.replaceAll("\\u00a0","");
        }
        LOG.info(termParameter);
    }

    public List<TermParameter> getTermParameterList() {
        return termParameterList;
    }

    public void setTermParameterList(List<TermParameter> termParameterList) {
        this.termParameterList = termParameterList;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    @Override
    public Long getVersionNumber() {
        return this.versionNumber;
    }

    protected PropositionEditor createPropositionEditor(PropositionDefinitionContract definition) {
        return new PropositionEditor(definition);
    }

    public String getNewTermDescription() {
        return newTermDescription;
    }

    public boolean isNewProp() {
        return newProp;
    }

    public void setNewProp(boolean newProp) {
        this.newProp = newProp;
    }

    public Map<String, String> getNaturalLanguage() {
        return naturalLanguage;
    }

    public void setNaturalLanguage(Map<String, String> naturalLanguage) {
        this.naturalLanguage = naturalLanguage;
    }

    /**
     * Return the natural language description for the given usage key from the natural language map.
     *
     * @param usage
     * @return
     */
    public String getNaturalLanguageForUsage(String usage){
        String description = this.getNaturalLanguage().get(usage);

        if (description == null){
            return StringUtils.EMPTY;
        }

        return description;
    }

    /**
     * Set the natuaral language string on the map with the usage as key. If the usage is the default
     * usage also set the description of the proposition.
     *
     * @param usage
     * @param nl
     */
    public void setNaturalLanguageForUsage(String usage, String nl){
        this.getNaturalLanguage().put(usage, nl);

        if (usage.equals(this.getDefaultNlKey())){
            this.setDescription(nl);
        }
    }

    /**
     * Override this method to return a method of custom parameters to be used in the natural
     * language context implementation.
     *
     * @return
     */
    public Map<String, String> getNlParameters() {
        return new HashMap<String, String>();
    }

    public String getBindingPath() {
        return bindingPath;
    }

    public void setBindingPath(String bindingPath) {
        this.bindingPath = bindingPath;
    }

    protected String getDefaultNlKey(){
        return null;
    }
}


