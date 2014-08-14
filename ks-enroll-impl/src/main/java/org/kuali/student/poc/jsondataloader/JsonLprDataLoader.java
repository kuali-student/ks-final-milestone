/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 * Created by Charles on 8/12/2014
 */
package org.kuali.student.poc.jsondataloader;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.poc.jsonparser.json.SimpleJsonMap;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * Loads objects to a mock LPR service
 *
 * @author Kuali Student Team
 */
public class JsonLprDataLoader implements JsonServiceDataLoader {
    LprService lprService;

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    @Override
    public boolean acceptsType(String typeKey) {
        return typeKey.equals(LprServiceConstants.REGISTRANT_CO_LPR_TYPE_KEY) ||
                typeKey.equals(LprServiceConstants.REGISTRANT_RG_LPR_TYPE_KEY) ||
                typeKey.equals(LprServiceConstants.REGISTRANT_AO_LPR_TYPE_KEY) ||
                typeKey.equals(LprServiceConstants.WAITLIST_CO_LPR_TYPE_KEY) ||
                typeKey.equals(LprServiceConstants.WAITLIST_RG_LPR_TYPE_KEY) ||
                typeKey.equals(LprServiceConstants.WAITLIST_AO_LPR_TYPE_KEY);
    }

    @Override
    public void clearData() {
        if (lprService instanceof MockService) {
            MockService mockService = (MockService) lprService;
            mockService.clear();
        }
    }

    public void loadData(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        String typeKeyStr = jsonObject.getAsString("type");
        loadLpr(jsonObject, contextInfo);
    }

    private void loadLpr(SimpleJsonMap jsonObject, ContextInfo contextInfo) throws OperationFailedException {
        LprInfo lpr = new LprInfo();
        String typeKey = jsonObject.getAsString("type");
        String stateKey = jsonObject.getAsString("state");
        String termId = jsonObject.getAsString("termId");
        String id = jsonObject.getAsString("id");
        String luiId = jsonObject.getAsString("luiId");
        String personId = jsonObject.getAsString("personId");
        String masterLprId = jsonObject.getAsString("masterLprId");

        lpr.setId(id);
        lpr.setTypeKey(typeKey);
        lpr.setStateKey(stateKey);
        lpr.setAtpId(termId);
        lpr.setLuiId(luiId);
        lpr.setMasterLprId(masterLprId);
        lpr.setPersonId(personId);

        try {
            lprService.createLpr(personId, luiId, typeKey, lpr, contextInfo);
        } catch (Exception e) {
            throw new OperationFailedException();
        }
    }
}
