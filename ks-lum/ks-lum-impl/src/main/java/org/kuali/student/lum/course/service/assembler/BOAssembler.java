/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.course.service.assembler;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode.NodeOperation;


/**
 * An assembler that provides assembly and disassembly of business DTO from
 * their base DTO.
 * 
 * During disassembly of business DTO, it is required that all implementations
 * generate the id (if newly created base DTO) from UUID and use that in mapping
 * relations.
 * 
 * @author Kuali Student Team
 * 
 */
public interface BOAssembler<E, T> {

	/**
	 * 
	 * This method assembles the business DTO from its base DTO.
	 * 
	 * @param baseDTO
	 *            Base DTO that corresponds to the business DTO
	 * @return Assembled business DTO
	 * @throws AssemblyException 
	 */
	public E assemble(T baseDTO) throws AssemblyException;

	/**
	 * 
	 * This method returns a collection of base DTOs and the operations that
	 * need to be performed on them in a given order
	 * 
	 * 
	 * @param businessDTO
	 *            Business DTO to be disassembled
	 * @param isCreate
	 *            Is the disassembly done for create
	 * 
	 * @return A sorted map of BaseDTOAssemblyNodes to be processed in the given
	 *         order. The key (Integer) is the sequence in which the nodes have
	 *         to be processed
	 * @throws AssemblyException 
	 */
	public BaseDTOAssemblyNode<T> disassemble(
			E businessDTO, NodeOperation operation) throws AssemblyException;
}
