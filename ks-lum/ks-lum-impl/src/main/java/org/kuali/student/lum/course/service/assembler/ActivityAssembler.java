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
import java.util.TreeMap;

import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

/**
 * Assembles/Disassembles ActivityInfo DTO from/To CluInfo 
 * 
 * @author Kuali Student Team
 *
 */
public class ActivityAssembler implements BOAssembler<ActivityInfo, CluInfo> {

	protected LuService luService;
	
	
	/**
	 * @return the luService
	 */
	public LuService getLuService() {
		return luService;
	}

	/**
	 * @param luService the luService to set
	 */
	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	@Override
	public ActivityInfo assemble(CluInfo clu) {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public SortedMap<Integer, BaseDTOAssemblyNode<?>> disassemble(
			ActivityInfo activity, Boolean isCreate) {
		
		int index = 0;
		SortedMap<Integer, BaseDTOAssemblyNode<?>> result = new TreeMap<Integer, BaseDTOAssemblyNode<?>>();
		
		CluInfo clu = new CluInfo();
	
		clu.setId(activity.getId());
		clu.setType(activity.getActivityType());
		clu.setState(activity.getState());
		clu.setDefaultEnrollmentEstimate(activity.getDefaultEnrollmentEstimate());
		clu.setStdDuration(activity.getDuration());
		
		
		// TODO Add contact hours to activity
		//TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getIntensity()));
//		AmountInfo time = clu.getIntensity();
//		if (time != null) {
//			CreditCourseActivityContactHoursHelper hours = CreditCourseActivityContactHoursHelper.wrap(new Data());
//			if(time.getUnitQuantity() != null) {
//				hours.setHrs(Integer.valueOf(time.getUnitQuantity()));
//			}
//			hours.setPer(time.getUnitType());
//			activity.setContactHours(hours);
//		}


//		addVersionIndicator(activity.getData(), CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd());

		
		return result;
	}
}
