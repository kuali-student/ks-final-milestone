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
package org.kuali.student.core.assembly;

import java.util.ArrayList;
import java.util.List;


/**
 * A node in the sorted map of disassembled base DTOs. The node provides the
 * data for the base DTO along with the operation information on the data.
 * 
 * The consumer of the map of these nodes is required to process the nodes in
 * sorted order to guarantee data integrity within the base DTOs.
 * 
 * @author Kuali Student Team
 * 
 */
public class BaseDTOAssemblyNode<E,T> {

	public enum NodeOperation {
		CREATE, UPDATE, DELETE;
	}

	protected NodeOperation operation;

	protected E businessDTORef;
	
	protected T nodeData;

	
	protected BOAssembler<E, T> assembler;
	
	protected List<BaseDTOAssemblyNode<?,?>> childNodes;

	public BaseDTOAssemblyNode(BOAssembler<E, T> assembler) {
		super();
		this.assembler = assembler;
	}

	/**
	 * @return the nodeData
	 */
	public T getNodeData() {
		return nodeData;
	}

	/**
	 * @param nodeData
	 *            the nodeData to set
	 */
	public void setNodeData(T nodeData) {
		this.nodeData = nodeData;
	}

	/**
	 * @return the operation
	 */
	public NodeOperation getOperation() {
		return operation;
	}

	/**
	 * @param operation
	 *            the operation to set
	 */
	public void setOperation(NodeOperation operation) {
		this.operation = operation;
	}

	public List<BaseDTOAssemblyNode<?,?>> getChildNodes() {
		if (childNodes == null) {
			childNodes = new ArrayList<BaseDTOAssemblyNode<?,?>>();
		}
		return childNodes;
	}

	public void setChildNodes(List<BaseDTOAssemblyNode<?,?>> childNodes) {
		this.childNodes = childNodes;
	}
	
	/**
	 * @return the assembler
	 */
	public BOAssembler<E, T> getAssembler() {
		return assembler;
	}

	/**
	 * @param assembler the assembler to set
	 */
	public void setAssembler(BOAssembler<E, T> assembler) {
		this.assembler = assembler;
	}

	/**
	 * @return the businessDTORef
	 */
	public E getBusinessDTORef() {
		return businessDTORef;
	}

	/**
	 * @param businessDTORef the businessDTORef to set
	 */
	public void setBusinessDTORef(E businessDTORef) {
		this.businessDTORef = businessDTORef;
	}
	
}
