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
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoHelper;
import org.kuali.student.lum.lu.dto.AffiliatedOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;

/*
 * This Assembler has to do Data <-> List<CluFeeRecordInfo> because it has to
 * sift thru the CluFeeRecordInfo's found in a course's CluFeeInfo to find
 * the "Revenue" one; perhaps in 1.1 we could have it outside the fees?
 * (that would probably require a service cahnge, thought)
 */
public class ExpenditureDataAssembler implements Assembler<Data, CluAccountingInfo> {

	@Override
	public Data assemble(CluAccountingInfo input) throws AssemblyException {
		
		if (input == null) {
			// TODO 4/1 : how else do you populate the path in the Data so
			// setValue() doesn't fail?
			// input = new CluAccountingInfo();
			// input.setAffiliatedOrgs(new ArrayList<AffiliatedOrgInfo>());
			return new Data();
					
			// return null;
		}
		CreditCourseExpenditureInfoHelper expenditureHelper = CreditCourseExpenditureInfoHelper.wrap(new Data());
		
		expenditureHelper.setTotalPercentage("100.00");
		
		Data affiliatedOrgData = new Data();
		for (AffiliatedOrgInfo orgInfo : input.getAffiliatedOrgs()) {
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
		if (affiliatedOrgData.size() > 0) {
			expenditureHelper.setExpenditureOrg(affiliatedOrgData);
		}
		return expenditureHelper.getData();
	}

	@Override
	public CluAccountingInfo disassemble(Data input) throws AssemblyException {
		
		if (input == null) {
			return null;
		}
		
		CluAccountingInfo returnAccountingInfo = new CluAccountingInfo();
		List<AffiliatedOrgInfo> orgInfos = new ArrayList<AffiliatedOrgInfo>();
		
		CreditCourseExpenditureInfoHelper expenditureHelper = CreditCourseExpenditureInfoHelper.wrap(input);
		
		returnAccountingInfo.setId(expenditureHelper.getId());
		// Note: expenditureHelper.getTotalPercentage() is not persisted
		
		// get the AffiliatedOrg's
		if (null != expenditureHelper.getExpenditureOrg()) {
			Iterator<Property> revenueOrgIter = expenditureHelper.getExpenditureOrg().realPropertyIterator();
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
			if (orgInfos.size() > 0) { // no orgs, no accounting info
				returnAccountingInfo.setAffiliatedOrgs(orgInfos);
				return returnAccountingInfo;
			}
		}
		return null;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(ExpenditureDataAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		return new CluFeeInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(ExpenditureDataAssembler.class.getName() + " does not support persistence");
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

