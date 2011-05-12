package org.kuali.student.r2.common.infc;

public interface AtpAssembler<E, T> {
    public E assemble(T baseDTO, E businessDTO);
    public T disassemble(T baseDTO, E businessDTO);
}
