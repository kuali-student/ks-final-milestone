package org.kuali.student.r2.common.assembler;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.EntityInfo;

public class EntityDTOAssembler<E extends EntityInfo, F extends EntityInfo> {

    public F assemble(E fromDTO, F toDTO, ContextInfo context) {

        toDTO.setAttributes(fromDTO.getAttributes());
        toDTO.setMeta(fromDTO.getMeta());
        toDTO.setStateKey(fromDTO.getStateKey());
        toDTO.setTypeKey(fromDTO.getTypeKey());
        toDTO.setName(fromDTO.getName());
        toDTO.setDescr(fromDTO.getDescr());

        return toDTO;

    }
}
