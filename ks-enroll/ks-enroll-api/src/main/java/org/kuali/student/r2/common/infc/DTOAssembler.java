package org.kuali.student.r2.common.infc;

import org.kuali.student.r2.common.dto.ContextInfo;

public interface DTOAssembler<E, T> {
    public E assemble(T baseDTO, ContextInfo context);
    public T disassemble(E businessDTO, ContextInfo context);
}
