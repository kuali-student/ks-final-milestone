package org.kuali.student.common.assembler;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.EntityInfo;

public class EntityDTOAssembler<E extends EntityInfo, F extends EntityInfo> {

    public F assemble(E fromDTO, F toDTO, ContextInfo context) {

        toDTO.setAttributes(fromDTO.getAttributes());
        toDTO.setMeta(fromDTO.getMeta());
        toDTO.setStateKey(fromDTO.getStateKey());
        toDTO.setTypeKey(fromDTO.getTypeKey());
        toDTO.setDescr(fromDTO.getDescr());

        return toDTO;

    }
}
