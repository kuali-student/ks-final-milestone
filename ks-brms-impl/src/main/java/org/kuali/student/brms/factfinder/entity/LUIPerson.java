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

package org.kuali.student.brms.factfinder.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

/**
 * Temporary place holder for LUI Person data that can be retrieved by 
 * calling fetchFact. Unique key constraint on cluId and studentId so a 
 * student cannot have more than CLU.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@Entity
@Table(name = "LUIPerson_T",
	uniqueConstraints=@UniqueConstraint(columnNames={"cluId", "studentId"}))
@NamedQueries({@NamedQuery(name = "LUIPerson.findByStudentId", query = "SELECT c FROM LUIPerson c WHERE c.studentId = :studentID")})
public class LUIPerson {

    @Id
    private String id;
    
    private String studentId;
    
    private String cluId;
    
    private String description;

    private Double credits;

    
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
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
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the cluId
     */
    public String getCluId() {
        return cluId;
    }

    /**
     * @param cluId the cluId to set
     */
    public void setCluId(String cluId) {
        this.cluId = cluId;
    }

    /**
     * @return the credits
     */
    public Double getCredits() {
        return credits;
    }

    /**
     * @param credits the credits to set
     */
    public void setCredits(Double cretids) {
        this.credits = cretids;
    }

    /**
     * @return Clu description
     */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description Clu description
	 */
	public void setDescription(String description) {
		this.description = description;
	}    
}
