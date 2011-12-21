/*
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

package org.kuali.student.lum.common.server;

import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;

import org.kuali.student.lum.program.dto.MajorDisciplineInfo;

import java.util.List;
import java.util.Map;

public class LoCategoryDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private LearningObjectiveService loService;

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id) throws Exception {

        //TODO Check that only LO categories are coming through this way. LOs are persisted only in the context of a CLU?
        Object returnDTO ;

        try {
            returnDTO = loService.getLoCategory(id);
        }
        catch (DoesNotExistException e) {
            throw new InvalidParameterException("Only LoCategoryInfo supported by this DataService implementation.");
        }
        return  returnDTO;
    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof LoCategoryInfo) {
            LoCategoryInfo loCatInfo = (LoCategoryInfo) dto;
            if (loCatInfo.getId() == null ) {
            	loCatInfo = loService.createLoCategory(loCatInfo.getLoRepository(), loCatInfo.getType(), loCatInfo);
            } else {
                loCatInfo = loService.updateLoCategory(loCatInfo.getId(), loCatInfo);
            }
            return loCatInfo;
        } else  {
            throw new InvalidParameterException("Only LoCategoryInfo supported by this DataService implementation.");
        }
    }

    @Override
	protected List<ValidationResultInfo> validate(Object dto) throws Exception {
		return loService.validateLoCategory("OBJECT", (LoCategoryInfo)dto);
	}

	@Override
    protected Class<?> getDtoClass() {
        return LoCategoryInfo.class;
    }

    public void setLearningObjectiveService(LearningObjectiveService loService) {
        this.loService = loService;
    }


}
