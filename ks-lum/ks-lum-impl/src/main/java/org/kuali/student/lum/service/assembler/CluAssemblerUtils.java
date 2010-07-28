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
package org.kuali.student.lum.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.course.service.assembler.LoAssembler;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * This is a description of what this class does - jimt don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class CluAssemblerUtils {
    private LuService luService;
    private LearningObjectiveService loService;
    private LoAssembler loAssembler;

    public List<LoDisplayInfo> getLearningObjectives(String cluId, boolean shallowBuild) throws AssemblyException {
        List<LoDisplayInfo> loInfos = new ArrayList<LoDisplayInfo>();
        try {
            List<CluLoRelationInfo> cluLoRelations = luService.getCluLoRelationsByClu(cluId);
            for (CluLoRelationInfo cluLoRelation : cluLoRelations) {
                String loId = cluLoRelation.getLoId();
                LoInfo lo = loService.getLo(loId);
                loInfos.add(loAssembler.assemble(lo, null, shallowBuild));
            }
        } catch (Exception e) {
            throw new AssemblyException("Error getting learning objectives", e);
        }

        return loInfos;
    }

    // Spring setters
    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
    }

    public void setLoAssembler(LoAssembler loAssembler) {
        this.loAssembler = loAssembler;
    }
}
