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
public class BaseDTOAssemblyNode<T> {

	public enum NodeOpr {
		CREATE, UPDATE, DELETE;
	}

	protected NodeOpr operation;
	
	protected T nodeData;

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
	public NodeOpr getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(NodeOpr operation) {
		this.operation = operation;
	}		
}
