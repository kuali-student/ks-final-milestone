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

import java.util.SortedMap;

import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;


/**
 * This is a description of what this class does - Kamal don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class CourseAssembler implements BOAssembler<CourseInfo, CluInfo> {

	protected LuService luService;
		
	public LuService getLuService() {
		return luService;
	}


	public void setLuService(LuService luService) {
		this.luService = luService;
	}


	@Override
	public CourseInfo assemble(CluInfo clu) {
		CourseInfo course = new CourseInfo();
		
		return course;
	}


	@Override
	public SortedMap<Integer, BaseDTOAssemblyNode<?>> disassemble(
			CourseInfo course, Boolean isCreate) {
			
		
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}	
}
