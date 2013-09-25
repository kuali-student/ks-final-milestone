/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 9/10/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jacorb.trading.client.typemgr.TypeManager;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.KeyNameInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.lrc.service.LRCService;

/**
 * Transforms CourseOfferingInfo objects into CourseOfferingDisplayInfo objects.
 *
 * @author Kuali Student Team
 */
public class CourseOfferingDisplayTransformer {
    
    /**
     * 
     * A way to provide the Atp, Type and State information used in the transform.
     * 
     * This allows the same transform code to be used while supporting both a single or multiple courses being transformed at the same time. 
     * 
     * @author Kuali Student Team 
     *
     */
    public static interface RelatedData {

        /**
         * Get the TermInfo for the termId specified
         * @param termId
         * @return the Term
         * @throws OperationFailedException  unable to complete request
         */
        TermInfo getTerm(String termId) throws OperationFailedException;
        
        /**
         * Get the TypeInfo for the typeKey specified
         * @param typeKey
         * @return The Type
         * @throws OperationFailedException  unable to complete request
         */
        TypeInfo getType (String typeKey) throws OperationFailedException;
        
        /**
         * Get the StateInfo for the stateKey specified.
         * 
         * @param stateKey
         * @return
         * @throws OperationFailedException  unable to complete request
         */
        StateInfo getState (String stateKey) throws OperationFailedException;
        
    }
    
    /**
     * Transform a list of CourseOffering objects into a list of CourseOfferingDisplay objects.
     * 
     * @param courseOfferings The list of CourseOffering objects to transform
     * @param acalService the AcademicCalendarService
     * @param stateService the StateService
     * @param typeService the TypeService
     * @param contextInfo           information containing the principalId and
     *                              locale information about the caller of
     *                              service operation
     * @return the list of CourseOfferingDisplay objects.
     * @throws OperationFailedException  unable to complete request
     */
    public static List<CourseOfferingDisplayInfo>cos2coDisplays(List<CourseOfferingInfo>courseOfferings, final AcademicCalendarService acalService,
            final StateService stateService,
            final TypeService typeService,
            final ContextInfo contextInfo) throws OperationFailedException {
        
        try {
            List<CourseOfferingDisplayInfo>courseOfferingDisplays = new ArrayList<CourseOfferingDisplayInfo>();
            
            Set<String>termIds = new HashSet<String>();
            
            Set<String>typeKeys = new HashSet<String>();
            
            Set<String>stateKeys = new HashSet<String>();
            
            final Map<String, TermInfo>termMap = new HashMap<String, TermInfo>();
            
            final Map<String, TypeInfo>typeMap = new  HashMap<String, TypeInfo>();
            
            final Map<String, StateInfo>stateMap = new  HashMap<String, StateInfo>();
            
            for (CourseOfferingInfo courseOfferingInfo : courseOfferings) {
                
                termIds.add(courseOfferingInfo.getTermId());
                typeKeys.add(courseOfferingInfo.getTypeKey());
                stateKeys.add(courseOfferingInfo.getStateKey());
            }
            
            List<TermInfo> terms = acalService.getTermsByIds(new ArrayList<String>(termIds), contextInfo);
            
            
            
            for (TermInfo termInfo : terms) {
                
                termMap.put(termInfo.getId(), termInfo);
                
            }
            
            List<TypeInfo> types = typeService.getTypesByKeys(new ArrayList<String>(typeKeys), contextInfo);
            
            for (TypeInfo typeInfo : types) {
                
                typeMap.put(typeInfo.getKey(), typeInfo);
                
            }
            
            List<StateInfo> states = stateService.getStatesByKeys(new ArrayList<String>(stateKeys), contextInfo);
            
            for (StateInfo stateInfo : states) {
                stateMap.put(stateInfo.getKey(), stateInfo);
            }
            
            final RelatedData data = new RelatedData() {
                
                @Override
                public TypeInfo getType(String typeKey) throws OperationFailedException {
                    return typeMap.get(typeKey);
                }
                
                @Override
                public StateInfo getState(String stateKey) throws OperationFailedException {
                    return stateMap.get(stateKey);
                }
                
                @Override
                public TermInfo getTerm(String termId) throws OperationFailedException {
                    return termMap.get(termId);
                }
            };
            
            for (CourseOfferingInfo coInfo : courseOfferings) {
                
                CourseOfferingDisplayInfo display = transformData(coInfo, data);
                
                courseOfferingDisplays.add(display);
                
            }
            
            return courseOfferingDisplays;
            
        } catch (Exception e) {
            throw new OperationFailedException("cos2coDisplays failed: ", e);
        }
        
    }
    public static CourseOfferingDisplayInfo co2coDisplay(CourseOfferingInfo coInfo,
                                                         final AcademicCalendarService acalService,
                                                         final StateService stateService,
                                                         final TypeService typeService,
                                                         LRCService lrcService,
                                                         final ContextInfo context)
            throws OperationFailedException {
        
        return transformData(coInfo, new RelatedData() {

            @Override
            public TermInfo getTerm(String termId) throws OperationFailedException {
                try {
                    return acalService.getTerm(termId, context);
                } catch (Exception e) {
                    throw new OperationFailedException("co2coDisplay: failed to get atp for id =" + termId, e);
                }
            }

            @Override
            public TypeInfo getType(String typeKey)
                    throws OperationFailedException {
                try {
                    return typeService.getType(typeKey, context);
                    
                } catch (Exception e) {
                    throw new OperationFailedException("co2coDisplay: failed to get type for key =" + typeKey, e);
                }
            }

            @Override
            public StateInfo getState(String stateKey)
                    throws OperationFailedException {
                try {
                    return stateService.getState(stateKey, context);
                    
                } catch (Exception e) {
                    throw new OperationFailedException("co2coDisplay: failed to get state for key =" + stateKey, e);
                }
            }
            
            
           
            
        });
    }
    
    /*
     * The actual transformer implementation.  The RelatedData interface 
     * implementation provided give the Atp, State and Type information 
     * required for the transformation.
     * 
     */
    private static CourseOfferingDisplayInfo transformData(CourseOfferingInfo coInfo, RelatedData data) throws OperationFailedException {
        
        CourseOfferingDisplayInfo displayInfo = new CourseOfferingDisplayInfo();
        displayInfo.setId(coInfo.getId());
        // Fields use in course offering display info
        // descr, courseId,
        displayInfo.setDescr(coInfo.getDescr());
        displayInfo.setCourseId(coInfo.getCourseId());
        //  termId, courseOfferingTitle,  courseOfferingCode,
        displayInfo.setTermId(coInfo.getTermId());
        displayInfo.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());
        displayInfo.setCourseOfferingCode(coInfo.getCourseOfferingCode());
        // subjectArea,
        displayInfo.setSubjectArea(coInfo.getSubjectArea());
        // termName, termCode
        TermInfo termInfo = data.getTerm(coInfo.getTermId());
        displayInfo.setTermName(termInfo.getName());
        displayInfo.setTermCode(termInfo.getCode());

        // gradingOptionName, creditOptionName,
        displayInfo.setGradingOption(new KeyNameInfo(coInfo.getGradingOptionId(), coInfo.getGradingOptionName()));
        displayInfo.setCreditOption(new KeyNameInfo(coInfo.getCreditOptionId(), coInfo.getCreditCnt()));

        // studentRegistrationGradingOptions: I use key for name as well, because we don't use name really otherwise
        // we have to call rvg = getLrcService().getResultValuesGroup(studentGradingOption, contextInfo); to get rvg.getName() as a name
        if (!coInfo.getStudentRegistrationGradingOptions().isEmpty()) {
            List<KeyNameInfo> studentGradingOptionList = new ArrayList<KeyNameInfo>();
            for (String studentGradingOption : coInfo.getStudentRegistrationGradingOptions()) {
                studentGradingOptionList.add(new KeyNameInfo(studentGradingOption, studentGradingOption));
            }
            displayInfo.setStudentRegistrationGradingOptions(studentGradingOptionList);
        }

        displayInfo.setHonorsOffering(coInfo.getIsHonorsOffering());

        // typeName, stateName
        TypeInfo typeInfo = data.getType(coInfo.getTypeKey());
        displayInfo.setTypeName(typeInfo.getName());
        StateInfo stateInfo = data.getState(coInfo.getStateKey());
        displayInfo.setStateName(stateInfo.getName());

        return displayInfo;
    }
}
