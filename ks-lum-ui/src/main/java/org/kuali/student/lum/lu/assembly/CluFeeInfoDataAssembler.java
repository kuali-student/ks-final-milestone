package org.kuali.student.lum.lu.assembly;



import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.dto.CurrencyAmountInfo;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluFeeInfoMetadata;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoFixedRateFeeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoFixedRateFeeHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoMultipleRateFeeConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoMultipleRateFeeHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoPerCreditFeeHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoVariableRateFeeHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeSelectorConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.FeeInfoHelper.Properties;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;

import com.google.gwt.i18n.client.NumberFormat;

public class CluFeeInfoDataAssembler implements Assembler<Data, CluFeeInfo>{
	
	// TODO - from the spec
	// (https://test.kuali.org/confluence/display/KULSTG/DS+-+Adding+Fees+and+Financial+Info+to+a+Credit+Course):
	// 		  if rateType changes, a new DOL object must be created
	//		  If feeType changes, at the service layer, the cluFeeRecord
	//		   must be deleted and new one created with the correct type.
	// 
	// See comment @ bottom re: layout of data dealt with by this Assembler
	@Override
	public Data assemble(CluFeeInfo input)
			throws AssemblyException {
		if (input == null) {
			// TODO 4/1 : how else do you populate the path in the Data so
			// setValue() doesn't fail?
			// input = new CluFeeInfo();
			// input.setCluFeeRecords(new ArrayList<CluFeeRecordInfo>());
			return new Data();		
			// return null;
		}
		FeeInfoHelper result = FeeInfoHelper.wrap(new Data());
		
		result.setId(input.getId());
		
		String justification = input.getAttributes().get(Properties.JUSTIFICATION.getKey());
		
		result.setJustification(null != justification ? justification : "");
		
		if (null != input.getCluFeeRecords()) {
			for (CluFeeRecordInfo feeRecord : input.getCluFeeRecords()) {
				String rateType = feeRecord.getAttributes().get(FeeSelectorConstants.RATE_TYPE);
				if (FeeInfoConstants.FIXED_RATE_FEE.equals(rateType)) {
					FeeInfoFixedRateFeeHelper feeHelper = FeeInfoFixedRateFeeHelper.wrap(new Data());
					// Note: not using feeRecord.getFeeAmount(); see spec
					feeHelper.setAmount(feeRecord.getAttributes().get(FeeInfoFixedRateFeeConstants.AMOUNT));
					feeHelper.setFeeType(feeRecord.getFeeType());
					feeHelper.setId(feeRecord.getId());
					feeHelper.setRateType(FeeInfoConstants.FIXED_RATE_FEE);
					
					if (null == result.getFixedRateFee()) {
						result.setFixedRateFee(new Data());
					}
					result.getFixedRateFee().add(feeHelper.getData());
				} else if (FeeInfoConstants.MULTIPLE_RATE_FEE.equals(rateType)) {
					FeeInfoMultipleRateFeeHelper feeHelper = FeeInfoMultipleRateFeeHelper.wrap(new Data());
					// get comma-delimited amounts
					Data amounts = new Data();
					StringTokenizer amountTokens = new StringTokenizer(feeRecord.getAttributes().get(FeeInfoMultipleRateFeeConstants.AMOUNT), ",");
					while (amountTokens.hasMoreTokens()) {
						amounts.add(amountTokens.nextToken());
					}
					feeHelper.setAmount(amounts);
					feeHelper.setFeeType(feeRecord.getFeeType());
					feeHelper.setId(feeRecord.getId());
					feeHelper.setRateType(FeeInfoConstants.MULTIPLE_RATE_FEE);
					
					if (null == result.getMultipleRateFee()) {
						result.setMultipleRateFee(new Data());
					}
					result.getMultipleRateFee().add(feeHelper.getData());
				} else if (FeeInfoConstants.PER_CREDIT_FEE.equals(rateType)) {
					FeeInfoPerCreditFeeHelper feeHelper = FeeInfoPerCreditFeeHelper.wrap(new Data());
					// Note: not using feeRecord.getFeeAmount(); see spec
					feeHelper.setAmount(feeRecord.getAttributes().get(FeeInfoFixedRateFeeConstants.AMOUNT));
					feeHelper.setFeeType(feeRecord.getFeeType());
					feeHelper.setId(feeRecord.getId());
					feeHelper.setRateType(FeeInfoConstants.PER_CREDIT_FEE);
					
					if (null == result.getPerCreditFee()) {
						result.setPerCreditFee(new Data());
					}
					result.getPerCreditFee().add(feeHelper.getData());
				} else if (FeeInfoConstants.VARIABLE_RATE_FEE.equals(rateType)) {
					FeeInfoVariableRateFeeHelper feeHelper = FeeInfoVariableRateFeeHelper.wrap(new Data());
					// get comma-delimited amounts
					StringTokenizer amountTokens = new StringTokenizer(feeRecord.getAttributes().get(FeeInfoMultipleRateFeeConstants.AMOUNT), ",");
					if (amountTokens.countTokens() != 2) {
						throw new AssemblyException(amountTokens.countTokens() + " fee amounts found for variable rate course fee; only 2 (min & max) amounts allowed");
					}
					feeHelper.setMinAmount(amountTokens.nextToken());
					feeHelper.setMaxAmount(amountTokens.nextToken());
					feeHelper.setFeeType(feeRecord.getFeeType());
					feeHelper.setId(feeRecord.getId());
					feeHelper.setRateType(FeeInfoConstants.VARIABLE_RATE_FEE);
					
					if (null == result.getVariableRateFee()) {
						result.setVariableRateFee(new Data());
					}
					result.getVariableRateFee().add(feeHelper.getData());
				}
			}
		}
		
		addVersionIndicator(result.getData(), CluFeeInfo.class.getName(), input.getId(), input.getMetaInfo().getVersionInd());

		// There are multiple FeeInfo's; sigh
		// TODO - revisit after 1.0
		/*
		Data returnList = new Data();
		returnList.add(result.getData());
		return returnList;
		*/
		return result.getData();
	}

	@Override
	public CluFeeInfo disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluFeeInfo returnFeeInfo = new CluFeeInfo();
		
		// There are multiple Fee's (CluFeeInfo's, in other words)
		// in the DOL dictionary. Revisit after R1.0; trying to keep
		// dictionary changes to a minimum, since they have to go 
		// back to the spreadsheet
		// Iterator<Property> feeInfoIter = input.iterator();
		// FeeInfoHelper feeHelper = FeeInfoHelper.wrap((Data) feeInfoIter.next().getValue());
		FeeInfoHelper feeHelper = FeeInfoHelper.wrap(input);
		
		returnFeeInfo.setId(feeHelper.getId());
		
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put(Properties.JUSTIFICATION.getKey(), feeHelper.getJustification());
		returnFeeInfo.setAttributes(attributes);
		
		// container for all CluFeeRecord's
		List<CluFeeRecordInfo> feeRecords = new ArrayList<CluFeeRecordInfo>();
		// work storage for CluFeeRecord attributes
		Map<String, String> feeRecordAttributes = new HashMap<String, String>();
		
		if (null != feeHelper.getFixedRateFee()) {
			// get the FixedRateFee's and add them to feeRecords
			Iterator<Property> feeIter = feeHelper.getFixedRateFee().iterator();
			while (feeIter.hasNext()) {
				FeeInfoFixedRateFeeHelper fixedHelper = FeeInfoFixedRateFeeHelper.wrap((Data) feeIter.next().getValue());
				CluFeeRecordInfo feeRecordInfo = new CluFeeRecordInfo();
				
				feeRecordAttributes.clear();
				feeRecordAttributes.put(FeeSelectorConstants.RATE_TYPE, fixedHelper.getRateType());
				// Note: not using feeRecordInfo.setFeeAmount(); see spec
				feeRecordAttributes.put(FeeInfoFixedRateFeeConstants.AMOUNT, fixedHelper.getAmount());
				feeRecordInfo.setAttributes(feeRecordAttributes);
				
				feeRecordInfo.setFeeType(fixedHelper.getFeeType());
				feeRecords.add(feeRecordInfo);
			}
		}
		
		if (null != feeHelper.getMultipleRateFee()) {
			// get the MultipleRateFee's and add them to feeRecords
			Iterator<Property> feeIter = feeHelper.getMultipleRateFee().iterator();
			while (feeIter.hasNext()) {
				FeeInfoMultipleRateFeeHelper multipleHelper = FeeInfoMultipleRateFeeHelper.wrap((Data) feeIter.next().getValue());
				CluFeeRecordInfo feeRecordInfo = new CluFeeRecordInfo();
				
				feeRecordAttributes.clear();
				feeRecordAttributes.put(FeeSelectorConstants.RATE_TYPE, FeeInfoConstants.MULTIPLE_RATE_FEE);
				// Note: not using feeRecordInfo.setFeeAmount(); see spec
				Data amountsData = multipleHelper.getAmount();
				Iterator<Property> amountIter = amountsData.iterator();
				StringBuilder amountStrBuilder = new StringBuilder ();
				while (amountIter.hasNext()) {
					amountStrBuilder.append(amountIter.next().getValue());
					amountStrBuilder.append(",");
				}
				feeRecordAttributes.put(FeeInfoMultipleRateFeeConstants.AMOUNT, amountStrBuilder.substring(0, amountStrBuilder.lastIndexOf(",")));
				feeRecordInfo.setAttributes(feeRecordAttributes);
				
				feeRecordInfo.setFeeType(multipleHelper.getFeeType());
				feeRecords.add(feeRecordInfo);
			}
		}
		
		if (null != feeHelper.getPerCreditFee()) {
			// get the CreditRateFee's and add them to feeRecords
			Iterator<Property> feeIter = feeHelper.getPerCreditFee().iterator();
			while (feeIter.hasNext()) {
				FeeInfoPerCreditFeeHelper perCreditHelper = FeeInfoPerCreditFeeHelper.wrap((Data) feeIter.next().getValue());
				CluFeeRecordInfo feeRecordInfo = new CluFeeRecordInfo();
				
				feeRecordAttributes.clear();
				feeRecordAttributes.put(FeeSelectorConstants.RATE_TYPE, FeeInfoConstants.MULTIPLE_RATE_FEE);
				// Note: not using feeRecordInfo.setFeeAmount(); see spec
				feeRecordAttributes.put(FeeInfoMultipleRateFeeConstants.AMOUNT, perCreditHelper.getAmount());
				feeRecordInfo.setAttributes(feeRecordAttributes);
				
				feeRecordInfo.setFeeType(perCreditHelper.getFeeType());
				feeRecords.add(feeRecordInfo);
			}
		}
		
		if (null != feeHelper.getVariableRateFee()) {
			// get the VariableRateFee's and add them to feeRecords
			Iterator<Property> feeIter = feeHelper.getVariableRateFee().iterator();
			while (feeIter.hasNext()) {
				FeeInfoVariableRateFeeHelper variableHelper = FeeInfoVariableRateFeeHelper.wrap((Data) feeIter.next().getValue());
				CluFeeRecordInfo feeRecordInfo = new CluFeeRecordInfo();
				
				feeRecordAttributes.clear();
				feeRecordAttributes.put(FeeSelectorConstants.RATE_TYPE, FeeInfoConstants.MULTIPLE_RATE_FEE);
				// Note: not using feeRecordInfo.setFeeAmount(); see spec
				feeRecordAttributes.put(FeeInfoMultipleRateFeeConstants.AMOUNT, variableHelper.getMinAmount() + "," + variableHelper.getMaxAmount());
				feeRecordInfo.setAttributes(feeRecordAttributes);
				
				feeRecordInfo.setFeeType(variableHelper.getFeeType());
				feeRecords.add(feeRecordInfo);
			}
		}
		
		// put them all in the CluFeeInfo
		returnFeeInfo.setCluFeeRecords(feeRecords);
		
		MetaInfo meta = new MetaInfo();
		meta.setVersionInd(getVersionIndicator(feeHelper.getData()));
		returnFeeInfo.setMetaInfo(meta);
		
		return returnFeeInfo;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(CluFeeInfoDataAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		return new CluFeeInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(CluFeeInfoDataAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return null;
	}
	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}

/*
fees=Type: LIST, Default: null, Properties: {
	(
		* = Type: DATA, Default: null, Properties: {
			(
				justification = Type: LIST, Default: null, Properties: {
					(
						* = Type: STRING, Default: null, Properties: {}
					);
				}
			);
			(
				variableRateFee = Type: LIST, Default: null, Properties: {
					(
						* = Type: DATA, Default: null, Properties: {
							(
								id = Type: STRING, Default: null, Properties: {}
							);
							(
								rateType = Type: LIST, Default: null, Properties: {
									(
										* = Type: STRING, Default: null, Properties: {}
									);
								}
							);
							(
								minAmount = Type: STRING, Default: null, Properties: {}
							);
							(
								feeType = Type: STRING, Default: null, Properties: {}
							);
							(
								maxAmount = Type: STRING, Default: null, Properties: {}
							);
						}
					);
				}
			);
			(
				fixedRateFee = Type: LIST, Default: null, Properties: {
					(
						* = Type: DATA, Default: null, Properties: {
								(
									id = Type: STRING, Default: null, Properties: {}
								);
								(
									amount = Type: LIST, Default: null, Properties: {
										(
											* = Type: STRING, Default: null, Properties: {}
										);
									}
								);
								(
									rateType = Type: LIST, Default: null, Properties: {
										(
											* = Type: STRING, Default: null, Properties: {}
										);
									}
								);
								(
									feeType = Type: STRING, Default: null, Properties: {}
								);
						}
					);
				}
			);
			(
				multipleRateFee = Type: LIST, Default: null, Properties: {
					(
						* = Type: DATA, Default: null, Properties: {
							(
								id = Type: STRING, Default: null, Properties: {}
							);
							(
								amount = Type: LIST, Default: null, Properties: {
									(
										* = Type: STRING, Default: null, Properties: {}
									);
								}
							);
							(
								rateType = Type: LIST, Default: null, Properties: {
									(
										* = Type: STRING, Default: null, Properties: {}
									);
								}
							);
							(
								feeType = Type: STRING, Default: null, Properties: {});
						}
					);
				}
			);
			(
				perCreditFee = Type: LIST, Default: null, Properties: {
					(
						* = Type: DATA, Default: null, Properties: {
							(
								id = Type: STRING, Default: null, Properties: {}
							);
							(
								amount = Type: LIST, Default: null, Properties: {
									(
										* = Type: STRING, Default: null, Properties: {}
									);
								}
							);
							(
								rateType = Type: LIST, Default: null, Properties: {
									(
										* = Type: STRING, Default: null, Properties: {}
									);
								}
							);
							(
								feeType = Type: STRING, Default: null, Properties: {}
							);
						}
					);
				}
			);
		}
	);
}
*/