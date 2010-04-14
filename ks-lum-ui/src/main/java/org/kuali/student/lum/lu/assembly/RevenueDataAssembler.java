/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.assembly;



import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluFeeInfoMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.AffiliatedOrgInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;

/*
 * This Assembler has to do Data <-> List<CluFeeRecordInfo> because it has to
 * sift thru the CluFeeRecordInfo's found in a course's CluFeeInfo to find
 * the "Revenue" one; perhaps in 1.1 we could have it outside the fees?
 * (that would probably require a service cahnge, thought)
 */
public class RevenueDataAssembler implements Assembler<Data, List<CluFeeRecordInfo>> {

	@Override
	public Data assemble(List<CluFeeRecordInfo> input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CreditCourseRevenueInfoHelper revenueHelper = CreditCourseRevenueInfoHelper.wrap(new Data());
		
		revenueHelper.setFeeType(FeeInfoConstants.REVENUE);
		revenueHelper.setTotalPercentage("100.00");
		
		Data affiliatedOrgData = new Data();
		// gotta find the one "Revenue" fee
		for (CluFeeRecordInfo feeRecord : input) {
			if (FeeInfoConstants.REVENUE.equals(feeRecord.getFeeType())) {
				
				for (AffiliatedOrgInfo orgInfo : feeRecord.getAffiliatedOrgInfoList()) {
					AffiliatedOrgInfoHelper orgInfoHelper = AffiliatedOrgInfoHelper.wrap(new Data());
					// TODO - what shold this really be set to, especially if not already set
					// is there some ripple-down in our architecture that would handle this?
					if (null != orgInfo.getEffectiveDate()) {
						orgInfoHelper.setEffectiveDate(Long.toString(orgInfo.getEffectiveDate().getTime()));
					}
					orgInfoHelper.setOrgId(orgInfo.getOrgId());
					orgInfoHelper.setPercentage(orgInfo.getPercentage());
					affiliatedOrgData.add(orgInfoHelper.getData());
				}
				break; // or should we keep looping to detect other REVENUE CluFeeRecordInfo's?
			}
			// TODO - is it an error if there's more than one CluFeeRecordInfo of rateType "Revenue"?
		}
		if (affiliatedOrgData.size() > 0) {
			revenueHelper.setRevenueOrg(affiliatedOrgData);
		}
		/*
		// More than one RevenueInfo in orch. dictionary (or not)
		// TODO - revisit that after R1.0
		// TODO - put comment of current DOL structure this deals with @ bottom of this in comment
		Data returnData = new Data();
		returnData.add(revenueHelper.getData());
		return returnData;
		*/
		return revenueHelper.getData();
	}

	@Override
	public List<CluFeeRecordInfo> disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		
		List<CluFeeRecordInfo> feeRecords = new ArrayList<CluFeeRecordInfo>();
		List<AffiliatedOrgInfo> orgInfos = new ArrayList<AffiliatedOrgInfo>();
		
		/*
		// RevenueInfo is actually a List in R1.0 orch. dictionary, so go thru them (prolly only one)
		// TODO - revisit post-1.0
		Iterator<Property> revenueIter = input.iterator();
		// so, iterate thru all 1 of them
		while (revenueIter.hasNext()) {
			CreditCourseRevenueInfoHelper revenueHelper = CreditCourseRevenueInfoHelper.wrap((Data) revenueIter.next().getValue());
		*/
		// actually, orch dictionary was changed to have only one
			CreditCourseRevenueInfoHelper revenueHelper = CreditCourseRevenueInfoHelper.wrap(input);
			
			CluFeeRecordInfo feeRecordInfo = new CluFeeRecordInfo();
			feeRecordInfo.setId(revenueHelper.getId());
			feeRecordInfo.setFeeType(FeeInfoConstants.REVENUE);
			// Note: revenueHelper.getTotalPercentage() is not persisted
			
			// get the AffiliatedOrg's
			if (revenueHelper.getRevenueOrg() != null){
				Iterator<Property> revenueOrgIter = revenueHelper.getRevenueOrg().realPropertyIterator();
				// orgInfos.clear(); // only needed if there's more than one course/revenue elements; dictionary's been changed
				while (revenueOrgIter.hasNext()) {
					AffiliatedOrgInfoHelper orgHelper = AffiliatedOrgInfoHelper.wrap((Data) revenueOrgIter.next().getValue());
					if ( ! AssemblerUtils.isDeleted(orgHelper.getData()) ) {
						AffiliatedOrgInfo orgInfo = new AffiliatedOrgInfo();
						
						orgInfo.setOrgId(orgHelper.getOrgId());
						orgInfo.setPercentage(orgHelper.getPercentage());
						if (null != orgHelper.getEffectiveDate()) {
							// TODO - when/how should this be set?
							try {
								orgInfo.setEffectiveDate(DateFormat.getInstance().parse(orgHelper.getEffectiveDate()));
							} catch (ParseException pe) {
								throw new AssemblyException(pe);
							}
						}
						orgInfos.add(orgInfo);
					}
				}
			}
			if (orgInfos.size() > 0) {
				if (null == feeRecordInfo.getAffiliatedOrgInfoList()) {
					feeRecordInfo.setAffiliatedOrgInfoList(new ArrayList<AffiliatedOrgInfo>());
				}
				feeRecordInfo.getAffiliatedOrgInfoList().addAll(orgInfos);
			}
			feeRecords.add(feeRecordInfo);
		/*
		}
		*/
		return feeRecords;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(RevenueDataAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		return new CluFeeInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(RevenueDataAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return null;
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}

