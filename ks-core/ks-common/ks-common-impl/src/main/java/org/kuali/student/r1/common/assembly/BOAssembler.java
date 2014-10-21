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
package org.kuali.student.r1.common.assembly;

import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;


/**
 * An assembler that provides assembly and disassembly of business DTO from/to
 * their base DTO.
 * 
 * During disassembly of business DTO, it is required that all implementations
 * generate the id (if newly created base DTO) from UUID and use that in mapping
 * relations.
 * 
 * Generic Mapping:
 * E -> Business DTO e.g. CourseInfo
 * T -> Base DTO e.g. CluInfo
 * 
 * It is a good practice to propagate state from the parent objects through to the children
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
	 * @param businessDTO
	 * 			  Reference to Business DTO
	 * @param shallowBuild
	 * 			  boolean flag to indicate if the assembly should be shallow or deep 
	 * @return Assembled business DTO
	 * @throws AssemblyException 
	 */
	public E assemble(T baseDTO, E businessDTO, boolean shallowBuild, ContextInfo contextInfo) throws AssemblyException, PermissionDeniedException;

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
	 * @throws PermissionDeniedException 
	 */
	public BaseDTOAssemblyNode<E,	T> disassemble(
			E businessDTO, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException, PermissionDeniedException, InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException;
}
