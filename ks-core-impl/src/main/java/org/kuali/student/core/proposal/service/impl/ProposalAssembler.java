/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.core.proposal.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.dto.ProposalTypeInfo;
import org.kuali.student.core.proposal.entity.Proposal;
import org.kuali.student.core.proposal.entity.ProposalType;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.springframework.beans.BeanUtils;

/**
 * Convert between DTO and enties
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ProposalAssembler extends BaseAssembler {

    public static ProposalInfo toProposalInfo(Proposal entity) {
        ProposalInfo dto = new ProposalInfo();


        BeanUtils.copyProperties(entity, dto,
                new String[] { "desc", "attributes", "type" });


        return dto;
    }

    public static List<ProposalTypeInfo> toProposalTypeInfos(List<ProposalType> entities) {
        List<ProposalTypeInfo> dtos = new ArrayList<ProposalTypeInfo>(entities.size());
        for (ProposalType entity : entities) {
            dtos.add(toProposalTypeInfo(entity));
        }
        return dtos;
    }

    private static ProposalTypeInfo toProposalTypeInfo(ProposalType entity) {
        ProposalTypeInfo dto = new ProposalTypeInfo();

        BeanUtils.copyProperties(entity, dto,
                new String[] { "attributes" });

        dto.setAttributes(toAttributeMap(entity.getAttributes()));
        return dto;
    }

}
