package org.kuali.student.r2.common.assembler;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.EntityInfo;
import org.kuali.student.r2.common.dto.RelationshipInfo;

public  class RelationshipDTOAssembler< E extends RelationshipInfo, F extends RelationshipInfo> {

       
    public F assemble(E fromDTO, F toDTO, ContextInfo context) {

        toDTO.setAttributes(fromDTO.getAttributes());
        toDTO.setEffectiveDate(fromDTO.getEffectiveDate());
        toDTO.setExpirationDate(fromDTO.getExpirationDate());
        toDTO.setId(fromDTO.getId());
        toDTO.setMeta(fromDTO.getMeta());
        toDTO.setStateKey(fromDTO.getStateKey());
        toDTO.setTypeKey(fromDTO.getTypeKey());
        toDTO.setId(fromDTO.getId());
        return toDTO;

    }
    
}
