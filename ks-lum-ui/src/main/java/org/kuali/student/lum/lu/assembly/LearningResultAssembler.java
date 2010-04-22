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

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.LearningResultOutcomeHelper;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.service.LuService;

public class LearningResultAssembler implements Assembler<Data, Void> {

    private LuService luService;
    
    private final static String GRADE_RESULT_TYPE = "kuali.resultType.gradeCourseResult";
    private final static String CREDIT_RESULT_TYPE = "kuali.resultType.creditCourseResult";
    private final static String STATE = "active";

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    @Override
    public Data assemble(Void input) throws AssemblyException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void disassemble(Data input) throws AssemblyException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Data get(String cluId) throws AssemblyException {
        throw new UnsupportedOperationException("LearningResultAssembler#get(String) is not implemented");
    }
    
    public Data getGradingOptions(String cluId) throws AssemblyException {
        Data outcomeData = new Data();

        try {
            List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
            if(cluResultList != null && !cluResultList.isEmpty()) {
                for(CluResultInfo cluResult : cluResultList) {
                    if(cluResult.getType().equals(GRADE_RESULT_TYPE)) {
                        for(ResultOptionInfo option : cluResult.getResultOptions()) {
                            String typeKey = option.getResultComponentId();
                            outcomeData.add(typeKey);
                        }
                    }
                }
            }
        } catch(Exception e) {
            throw new AssemblyException("Unable to load learning results for course", e);
        }
        
        return outcomeData;
    }

    public Data getOutcomeOptions(String cluId) throws AssemblyException {                 
        Data outcomeData = new Data();
        
        try {
            List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
            
            if (cluResultList != null) {
                for (CluResultInfo cluResult : cluResultList) {
                    if (cluResult.getType().equals(CREDIT_RESULT_TYPE)) {
                        for(ResultOptionInfo option : cluResult.getResultOptions()) {
                            LearningResultOutcomeHelper outcome = LearningResultOutcomeHelper.wrap(new Data());
                            outcome.setOutcomeId(option.getId());
                            outcome.setOutcomeType(option.getResultComponentId());
                            outcomeData.add(outcome.getData());                        
                        }                    
                    }
                }
            }
        } catch(Exception e) {
            throw new AssemblyException("Unable to load learning results for course", e);
        }       
        
        return outcomeData;
    }

    @Override
    public Metadata getDefaultMetadata() throws AssemblyException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
        throw new UnsupportedOperationException("LearningResultAssembler#save(Data) is not implemented");
    }
   
    public SaveResult<Data> saveGradingOptions(Data input, String cluId) throws AssemblyException {
        try {
            SaveResult<Data> result = new SaveResult<Data>();
            result.setValue(input);
            
            CreditCourseHelper creditCourseHelper = CreditCourseHelper.wrap(input);
            //String cluId = creditCourseHelper.getId();
            
            CluResultInfo cluResultInfo = new CluResultInfo();
            cluResultInfo.setCluId(cluId);
            cluResultInfo.setState(STATE);
    
            // Grading options
            List<ResultOptionInfo> gradeResultOptions = new ArrayList<ResultOptionInfo>();
            if(creditCourseHelper.getGradingOptions() != null) {
                for (Data.Property p : creditCourseHelper.getGradingOptions()) {
                    if(!CreditCourseHelper.Properties._RUNTIME_DATA.getKey().equals(p.getKey())){
                        String resultType = p.getValue();
                        ResultOptionInfo roi = new ResultOptionInfo();
                        roi.setResultComponentId(resultType);
                        roi.setState(STATE);
                        gradeResultOptions.add(roi);
                    }
                }
                
                List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
                if(cluResultList == null || cluResultList.isEmpty()) {
	                // Create or update clu result
                    cluResultInfo.setResultOptions(gradeResultOptions);
                    luService.createCluResult(cluId, GRADE_RESULT_TYPE, cluResultInfo);
                } else {
                	// Should only be one CluResultInfo
                	for(CluResultInfo cri : cluResultList) {
	                    cri = luService.getCluResult(cri.getId());
	                    cri.setResultOptions(gradeResultOptions);
	                    if(cri.getType().equals(GRADE_RESULT_TYPE)) {
	                        luService.updateCluResult(cri.getId(), cri);
	                    }
                	}
                }
            }
            
            return result;
        } catch(Exception e) {
            throw new AssemblyException("Unable to save learning result grades for course", e);
        }
    }
    
    public SaveResult<Data> saveOutcomeOptions(Data input, String cluId) throws AssemblyException {
        // Expect to get the following from the client:
        // Example: ResultComponent.type='kuali.resultComponentType.credit.degree.fixed',
        //          ResultComponent.id='kuali.creditType.credit.degree.10'

        try {
            List<ResultOptionInfo> outcomeTypeResultOptions = new ArrayList<ResultOptionInfo>();
            SaveResult<Data> result = new SaveResult<Data>();
            result.setValue(input);
            
            CreditCourseHelper creditCourseHelper = CreditCourseHelper.wrap(input);

            // Outcome options
            if(creditCourseHelper.getOutcomeOptions() != null) {
                for (Data.Property p : creditCourseHelper.getOutcomeOptions()) {                    
                    if(!CreditCourseHelper.Properties._RUNTIME_DATA.equals(p.getKey())){
                        if (AssemblerUtils.isCreated((Data)p.getValue())) {
                            String resultType = ((Data)p.getValue()).get("outcomeType");
                            ResultOptionInfo roi = new ResultOptionInfo();
                            roi.setResultComponentId(resultType);
                            roi.setState(STATE);
                            outcomeTypeResultOptions.add(roi);
                        } else if (AssemblerUtils.isDeleted((Data)p.getValue())) {
                            continue;
                        } else if (AssemblerUtils.isUpdated((Data)p.getValue())) {
                            String id = ((Data)p.getValue()).get("outcomeId");
                            String resultType = ((Data)p.getValue()).get("outcomeType");
                            ResultOptionInfo roi = new ResultOptionInfo();
                            roi.setId(id);
                            roi.setResultComponentId(resultType);
                            roi.setState(STATE);
                            outcomeTypeResultOptions.add(roi);
                        }                         
                    }                                                         
                }
                
                boolean update = false;
                List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
                for (CluResultInfo cluResult : cluResultList) {
                    if(cluResult.getType().equals(CREDIT_RESULT_TYPE)) {
                        RichTextInfo desc = new RichTextInfo();
                        desc.setPlain("Temporary description");
                        cluResult.setDesc(desc);                  
                        cluResult.setResultOptions(outcomeTypeResultOptions);                        
                        luService.updateCluResult(cluResult.getId(), cluResult);
                        update = true;
                    }
                }                
                
                if(update == false) {
                    CluResultInfo cluResultInfo = new CluResultInfo();
                    cluResultInfo.setCluId(cluId);       
                    cluResultInfo.setState(STATE);
                    RichTextInfo desc = new RichTextInfo();
                    desc.setPlain("Temporary description");
                    cluResultInfo.setDesc(desc);
                    cluResultInfo.setResultOptions(outcomeTypeResultOptions);
                    luService.createCluResult(cluId, CREDIT_RESULT_TYPE, cluResultInfo);
                }             
            }

            return result;
        } catch(Exception e) {
            throw new AssemblyException("Unable to save learning result outcomes for course", e);
        }
    }

    @Override
    public List<ValidationResultInfo> validate(Data input)
            throws AssemblyException {
        // TODO Auto-generated method stub
        return null;
    }

}
