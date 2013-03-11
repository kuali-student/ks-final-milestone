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
 * Created by mahtabme on 6/12/12
 */
package org.kuali.student.r2.core.process.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.r2.core.process.dao.CheckDao;
import org.kuali.student.r2.core.process.model.CheckEntity;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ProcessServiceModelTestDataLoader {

    ///////////////////
    // Constants
    ///////////////////

    Map<String,String> ATTR_SET_KEY_VALS = new Hashtable<String,String>() {
        {
            put("key1", "value1");
            put("key2", "value2");
            put("key3", "value3");
            put("key2", "value2");
        }
    };

    Map<String,String> ATTR_SET_TERM_INFO = new Hashtable<String,String>() {
        {
            put("term", "fall01");
            put("startDate", "20120901");
            put("endDate", "20130428");
        }
    };

    ///////////////////
    // DATA FIELDS
    ///////////////////

    private CheckDao checkDao;
    private  String principalId = ProcessServiceModelTestDataLoader.class.getSimpleName();
    private boolean debugMode = true;
    private static Logger logger = null;

    ////////////////////
    // CONSTRUCTORS
    ////////////////////

    public ProcessServiceModelTestDataLoader (CheckDao checkDao, Logger logger){
        this.checkDao = checkDao;
        this.logger = logger;
    }

    //////////////////////////
    // GETTERS AND SETTERS
    //////////////////////////

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    ////////////////////
    // FUNCTIONALS
    ////////////////////

    public void loadData ()  {
        if (debugMode) { logger.warn("loadData called"); }
        loadCheckEntity("kuali.check.is.alive", "kuali.process.check.type.rule.direct", "kuali.process.check.state.active", "is alive",
                "Checks if student is actually alive", null, null, null, "kuali.agenda.is.alive", null, null, null, ATTR_SET_TERM_INFO);
        loadCheckEntity("kuali.check.has.been.admitted", "kuali.process.check.type.rule.direct", "kuali.process.check.state.active", "has been admitted",
                "Checks if student has been admitted", null, null, null, "kuali.check.has.been.admitted", null, null, null, ATTR_SET_KEY_VALS);
    }

    private  void loadCheckEntity(String checkId, String checkType, String checkState, String checkName,
                                  String checkDescPlain, String checkDescFormatted, String checkIssueId,
                                  String milestoneType, String checkAgendaId, String checkRightAgendaId,
                                  String checkLeftAgendaId, String checkChildProcessId, Map<String, String> attributes) {
        CheckEntity checkEntity  = new CheckEntity();
        checkEntity.setId(checkId);
        checkEntity.setCheckType(checkType);
        checkEntity.setCheckState(checkState);
        checkEntity.setName(checkName);
        checkEntity.setDescrPlain(checkDescPlain);
        checkEntity.setDescrFormatted(checkDescFormatted);
        checkEntity.setIssueId(checkIssueId);
        checkEntity.setMilestoneType(milestoneType);
        checkEntity.setAgendaId(checkAgendaId);
        checkEntity.setRightAgendaId(checkRightAgendaId);
        checkEntity.setLeftAgendaId(checkLeftAgendaId);
        checkEntity.setChildProcessId(checkChildProcessId);

        // super class fields
        checkEntity.setCreateId(principalId);
        Date time;
        checkEntity.setCreateTime(time = new Date());
        checkEntity.setUpdateId(principalId);
        checkEntity.setUpdateTime(time);

        // attributes
        for (String key : attributes.keySet()) {
            checkEntity.addAttribute(key, attributes.get(key));
        }

        if (debugMode) { logger.warn("Create " + checkEntity); }
        if (debugMode) { logger.warn("Persisting ... ") ; }
        // persist
        checkDao.persist(checkEntity);
        if (debugMode) { logger.warn("done!"); }
    }

}
