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
package org.kuali.student.ui.kitchensink.client.kscommons.pagetable;
import org.kuali.student.core.dto.Idable;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class Staffer implements IsSerializable, Idable{
    
    private String id;
    private String institution;
    private String name;
    private String ftePercent;
    private String comment;
    private String primaryProjectRole;
    private String primaryTeam;
    private String subTeamName;
    
    public Staffer(String data) {
        String[] splitData = data.split(",");
        int i = 0;
        setInstitution( splitData[0]);
        setName( splitData[1]);
        setFtePercent( splitData[2]);
        setComment( splitData[3]);
        setPrimaryProjectRole( splitData[4]);
        setPrimaryTeam( splitData[5]);
        setSubTeamName( splitData[6]);
    }

    /**
     * @return the institution
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * @param institution the institution to set
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ftePercent
     */
    public String getFtePercent() {
        return ftePercent;
    }

    /**
     * @param ftePercent the ftePercent to set
     */
    public void setFtePercent(String ftePercent) {
        this.ftePercent = ftePercent;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the primaryProjectRole
     */
    public String getPrimaryProjectRole() {
        return primaryProjectRole;
    }

    /**
     * @param primaryProjectRole the primaryProjectRole to set
     */
    public void setPrimaryProjectRole(String primaryProjectRole) {
        this.primaryProjectRole = primaryProjectRole;
    }

    /**
     * @return the primaryTeam
     */
    public String getPrimaryTeam() {
        return primaryTeam;
    }

    /**
     * @param primaryTeam the primaryTeam to set
     */
    public void setPrimaryTeam(String primaryTeam) {
        this.primaryTeam = primaryTeam;
    }

    /**
     * @return the subTeamName
     */
    public String getSubTeamName() {
        return subTeamName;
    }

    /**
     * @param subTeamName the subTeamName to set
     */
    public void setSubTeamName(String subTeamName) {
        this.subTeamName = subTeamName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        
    }
}
