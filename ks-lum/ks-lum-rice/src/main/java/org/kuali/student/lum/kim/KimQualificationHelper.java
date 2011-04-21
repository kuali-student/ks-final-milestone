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

/**
 * 
 */
package org.kuali.student.lum.kim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.xml.dto.AttributeSet;
import org.kuali.rice.kew.dto.DocumentDetailDTO;
import org.kuali.rice.kew.dto.DocumentTypeDTO;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kim.service.support.impl.KimTypeAttributeValidationException;
import org.kuali.rice.kim.util.KimConstants;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;

/**
 * Class to allow convenience methods to help with qualification verification and translation
 *
 */
public class KimQualificationHelper {
    protected static final Logger LOG = Logger.getLogger(KimQualificationHelper.class);

	private static UniqueMap translationMap = new UniqueMap();

	{
	    // below is example of how this could work
	    // translationMap.put("referenceType.clu.proposal", "kuali.proposal.type.course.create");
	}

	protected static WorkflowUtility getWorkflowUtility() {
		return KEWServiceLocator.getWorkflowUtilityService();
	}

    public static void validateRequiredAttributesAgainstReceived(Set<List<String>> requiredAttributes, AttributeSet receivedAttributes, boolean checkRequiredAttributes, String commaSeparatorString) {
		// abort if type does not want the qualifiers to be checked
		if ( !checkRequiredAttributes ) {
			return;
		}
		// abort if the list is empty, no attributes need to be checked
		if ( requiredAttributes == null || requiredAttributes.isEmpty() ) {
			return;
		}
		// if attributes are null or empty, they're all missing
		if ( receivedAttributes == null || receivedAttributes.isEmpty() ) {
			return;		
		}
		
		Set<List<String>> totalMissingAttributes = new HashSet<List<String>>();
		for (List<String> currentReqAttributes : requiredAttributes) {
			List<String> missingAttributes = new ArrayList<String>();
			for( String requiredAttribute : currentReqAttributes ) {
				if( !receivedAttributes.containsKey(requiredAttribute) ) {
					missingAttributes.add(requiredAttribute);
				}
			}
			if (missingAttributes.isEmpty()) {
				// if no missing attributes from this list then we have required attributes needed
				return;
			}
			totalMissingAttributes.add(missingAttributes);
        }

		int i = 1;
    	StringBuffer errorMessage = new StringBuffer("Missing Required Attributes from lists - ");
    	for (List<String> missingAttributes : totalMissingAttributes) {
            if(missingAttributes.size()>0) {
            	errorMessage.append("List " + i + ": (");
            	i++;
            	Iterator<String> attribIter = missingAttributes.iterator();
            	while ( attribIter.hasNext() ) {
            		errorMessage.append( attribIter.next() );
            		if( attribIter.hasNext() ) {
            			errorMessage.append( commaSeparatorString );
            		}
            	}
            	errorMessage.append(")");
            }
        }
		LOG.info("Found missing attributes: " + errorMessage.toString());
        throw new KimTypeAttributeValidationException(errorMessage.toString());
    }

    protected static String getProposalId(AttributeSet qualification) {
        for (String proposalReferenceType : StudentIdentityConstants.QUALIFICATION_PROPOSAL_ID_REF_TYPES) {
            if (qualification.containsKey(proposalReferenceType)) {
                return qualification.get(proposalReferenceType);
            }
        }
        return null;
    }

    public static AttributeSet translateInputAttributeSet(AttributeSet qualification) {
		try {
			DocumentDetailDTO docDetail = null;
			// first get a valid DocumentDetailDTO object if possible
			String documentNumber = qualification.get(KimConstants.AttributeConstants.DOCUMENT_NUMBER);
			String proposalId = getProposalId(qualification);
			if (StringUtils.isBlank(documentNumber)) {
			    // if document number is not in qualification try to get it using proposal id qualification
	            if (StringUtils.isNotBlank(proposalId)) {
	                ProposalInfo propInfo = getProposalService().getProposal(proposalId);
	                documentNumber = propInfo.getWorkflowId();
	            }
			}
			if (StringUtils.isNotBlank(documentNumber)) {
				// document id exists so look up KEW document instance using it
				docDetail = getWorkflowUtility().getDocumentDetail(Long.valueOf(documentNumber));
			}
			else {
				// document id does not exist so attempt lookup by Document Type Name and Application ID
				String appId = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID );
				if (StringUtils.isNotBlank(appId)) {
					String documentTypeName = qualification.get( KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME );
					if (StringUtils.isBlank(documentTypeName)) {
						// could not find Document Type Name in qualification so check for KS Object Type
						String ksObjectType = qualification.get( StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE );
						if (StringUtils.isNotBlank(ksObjectType)) {
							documentTypeName = translationMap.get(ksObjectType);
						}
					}
					// check for a valid Document Type Name
					if (StringUtils.isNotBlank(documentTypeName)) {
						// found valid Application ID and Document Type Name so KEW Document instance can be retrieved
						docDetail = getWorkflowUtility().getDocumentDetailFromAppId(documentTypeName, appId);
					}
					else {
						// if neither Document Type Name nor KS object type is found then KEW document instance cannot be retrieved
						LOG.warn("Could not find valid document type name or KS object type using qualifications: " + qualification);
					}
				}
				else {
					// if application id is not found then KEW document instance cannot be retrieved
					LOG.warn("Could not find valid document id or application id using qualifications: " + qualification);
				}
			}
			translateQualifications(docDetail, proposalId, qualification);
		    return qualification;
	    }
		catch (Exception e) {
            LOG.error(e.getLocalizedMessage(), e);
            throw new RuntimeException(e);
		}
	}

	protected static void translateQualifications(DocumentDetailDTO docDetail, String proposalId, AttributeSet qualifications) {
		if (docDetail != null) {
			// add document id if necessary
			if (!qualifications.containsKey(KimConstants.AttributeConstants.DOCUMENT_NUMBER)) {
				qualifications.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, docDetail.getRouteHeaderId().toString());
			}
			// add KS proposal id if possible
			if (!qualifications.containsKey(StudentIdentityConstants.QUALIFICATION_KS_PROPOSAL_ID) && StringUtils.isNotBlank(proposalId)) {
			    qualifications.put(StudentIdentityConstants.QUALIFICATION_KS_PROPOSAL_ID, proposalId);
			}
			// add KS object id if necessary
			if (!qualifications.containsKey(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID)) {
				qualifications.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID, docDetail.getAppDocId());
			}
			DocumentTypeDTO docType = KEWServiceLocator.getDocumentTypeService().getDocumentTypeVO(docDetail.getDocTypeId());
			if (docType != null) {
				String documentTypeName = docType.getName();
				// add document type name if necessary
				if (!qualifications.containsKey(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME)) {
					qualifications.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentTypeName);
				}
				// add KS object type code if necessary
				if (!qualifications.containsKey(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE)) {
					qualifications.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE, translationMap.getKeyForValue(documentTypeName));
				}
			}
			else {
				String errorMsg = "Could not find valid KEW document type for document id " + docDetail.getRouteHeaderId(); 
				LOG.error(errorMsg);
				throw new RuntimeException(errorMsg);
			}
		}
		else {
			LOG.warn("Could not find KEW document instance for qualifications: " + qualifications);
			// add KS object type code if necessary
			if ((!qualifications.containsKey(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE)) && 
					qualifications.containsKey(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME)) {
				qualifications.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE, translationMap.getKeyForValue(qualifications.get(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME)));
			}
			else if ((!qualifications.containsKey(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME)) && 
					qualifications.containsKey(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE)) {
				qualifications.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, translationMap.get(qualifications.get(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE)));
			}
		}
	}

    protected static ProposalService getProposalService() {
        return (ProposalService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/proposal","ProposalService"));
    }

	private static class UniqueMap extends HashMap<String,String> {

        private static final long serialVersionUID = 1L;

		@Override
        public String put(String key, String value) {
			if (this.containsValue(value)) {
				throw new UnsupportedOperationException("Map already contains an entry with value: " + value);
			}
	        return super.put(key, value);
        }

		public String getKeyForValue(String value) {
			for (Map.Entry<String, String> mapEntry : this.entrySet()) {
	            if (StringUtils.equals(value, mapEntry.getValue())) {
	            	return mapEntry.getKey();
	            }
            }
			return null;
		}
	}

}
