/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.brms.rulemanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

/**
 * Contains the structureKey values that uniquely identifies agenda info
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name="KSBRMS_AGENDA_DETER")
public class AgendaDetermination {

    @Id
    @Column(name="ID")
    private String id;
    
    @Column(name="STRUCT_KEY")
    private String structureKey; 
    
    @Column(name="VALUE")
    private String value;
    
    @ManyToOne
    @JoinColumn(name = "AGENDA")    
    private Agenda agenda;
        
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the structureKey
     */
    public String getStructureKey() {
        return structureKey;
    }

    /**
     * @param structureKey the structureKey to set
     */
    public void setStructureKey(String key) {
        this.structureKey = key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the agendaInfo
     */
    public Agenda getAgenda() {
        return agenda;
    }

    /**
     * @param agendaInfo the agendaInfo to set
     */
    public void setAgenda(Agenda agendaInfo) {
        this.agenda = agendaInfo;
    }
}
