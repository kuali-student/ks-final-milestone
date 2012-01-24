package org.kuali.student.common.assembler;

import org.kuali.student.common.dto.ContextInfo;

public interface DTOAssembler<E, T> {
    public E assemble(T baseDTO, ContextInfo context) throws AssemblyException;
    public T disassemble(E businessDTO, ContextInfo context) throws AssemblyException;
}
