/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Mezba Mahtab on 6/7/12
 */
package org.kuali.student.r2.core.process.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This class represents table KSEN_PROCESS_CATEGORY_RELTN which is a relation between a process and a process category
 *
 * @author Mezba Mahtab
 */

@Entity
@Table (name = "KSEN_PROCESS_CATEGORY_RELTN")
public class ProcessCategoryProcessRelationEntity {

    ////////////////////
    // DATA FIELDS
    ////////////////////

    @Column(name = "PROCESS_ID", nullable = false)
    private String processId;

    @Column(name = "PROCESS_CATEGORY_ID", nullable = false)
    private String processCategoryId;

    //////////////////////////
    // CONSTRUCTORS ETC.
    //////////////////////////

    public ProcessCategoryProcessRelationEntity () {}

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessCategoryId() {
        return processCategoryId;
    }

    public void setProcessCategoryId(String processCategoryId) {
        this.processCategoryId = processCategoryId;
    }
}
