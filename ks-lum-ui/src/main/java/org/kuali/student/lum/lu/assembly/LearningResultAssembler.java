package org.kuali.student.lum.lu.assembly;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
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
        Data resultData = new Data();

        try {
            List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
            if(cluResultList != null && !cluResultList.isEmpty()) {
                //for(CluResultInfo cluResult : cluResultList) {
                CluResultInfo cluResult = cluResultList.get(0);
                    if(cluResult.getType().equals(GRADE_RESULT_TYPE)) {
                        for(ResultOptionInfo option : cluResult.getResultOptions()) {
                            String typeKey = option.getResultComponentId();
                            resultData.add(typeKey);
                        }
                    }
                //}
            }
        } catch(Exception e) {
            throw new AssemblyException("Unable to load learning results for course", e);
        }
        
        return resultData;
    }

    public Data getOutcomeOptions(String cluId) throws AssemblyException {
        CreditCourseHelper result = CreditCourseHelper.wrap(new Data());
        result.setGradingOptions(new Data());

        try {
            List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
            if(cluResultList != null && !cluResultList.isEmpty()) {
                //for(CluResultInfo cluResult : cluResultList) {
                CluResultInfo cluResult = cluResultList.get(0);
                    if(cluResult.getType().equals(CREDIT_RESULT_TYPE)) {
                        for(ResultOptionInfo option : cluResult.getResultOptions()) {
                            String typeKey = option.getResultComponentId();
                            result.getOutcomeOptions().add(typeKey);
                        }
                    }
                //}
            }
        } catch(Exception e) {
            throw new AssemblyException("Unable to load learning results for course", e);
        }
        
        return result.getData();
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
        // Expect to get the following from the client:
        // Example: ResultComponent.type='kuali.resultComponentType.credit.degree.fixed',
        //          ResultComponent.id='kuali.creditType.credit.degree.10'

        try {
            SaveResult<Data> result = new SaveResult<Data>();
            result.setValue(input);
            
            CreditCourseHelper creditCourseHelper = CreditCourseHelper.wrap(input);
            //String cluId = creditCourseHelper.getId();
            
            CluResultInfo cluResultInfo = new CluResultInfo();
            cluResultInfo.setCluId(cluId);
            cluResultInfo.setState(STATE);
//            MetaInfo metaInfo = toMetaInfo(creditCourseHelper.getMetaInfo());
//            cluResultInfo.setMetaInfo(metaInfo);
    
            // Grading options
            List<ResultOptionInfo> gradeResultOptions = new ArrayList<ResultOptionInfo>();
            if(creditCourseHelper.getGradingOptions() != null) {
                for (Data.Property p : creditCourseHelper.getGradingOptions()) {
                    if(!CreditCourseHelper.Properties._RUNTIME_DATA.equals(p.getKey())){
//                      CreditCourseHelper helper = CreditCourseHelper.wrap((Data)p.getValue());
                        String resultType = p.getValue();
                        ResultOptionInfo roi = new ResultOptionInfo();
                        roi.setResultComponentId(resultType);
                        //roi.setResultUsageTypeKey("lrType.finalGrade");
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
                    CluResultInfo cri = cluResultList.get(0);
                    cri = luService.getCluResult(cri.getId());
                    cri.setResultOptions(gradeResultOptions);
                    if(cri.getType().equals(GRADE_RESULT_TYPE)) {
                        luService.updateCluResult(cri.getId(), cri);
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
            SaveResult<Data> result = new SaveResult<Data>();
            result.setValue(input);
            
            CreditCourseHelper creditCourseHelper = CreditCourseHelper.wrap(input);
            //String cluId = creditCourseHelper.getId();

            CluResultInfo cluResultInfo = new CluResultInfo();
            cluResultInfo.setCluId(cluId);
            //cluResultInfo.setEffectiveDate(effectiveDate);
            //cluResultInfo.setExpirationDate(expirationDate);
            //cluResultInfo.setMetaInfo(metaInfo);
            cluResultInfo.setState(STATE);

            // Outcome options
            if(creditCourseHelper.getOutcomeOptions() != null) {
                List<ResultOptionInfo> outcomeTypeResultOptions = new ArrayList<ResultOptionInfo>();
                for (Data.Property p : creditCourseHelper.getOutcomeOptions()) {
                    if(!CreditCourseHelper.Properties._RUNTIME_DATA.equals(p.getKey())){
                        String resultType = ((Data)p.getValue()).get("outcomeType");
                        ResultOptionInfo roi = new ResultOptionInfo();
                        roi.setResultComponentId(resultType);
                        //roi.setResultUsageTypeKey("lrType.finalGrade");
                        roi.setState(STATE);
                        outcomeTypeResultOptions.add(roi);
                    }
                }
                if(outcomeTypeResultOptions != null && !outcomeTypeResultOptions.isEmpty()) {
                    cluResultInfo.setResultOptions(outcomeTypeResultOptions);
                    // Create or update clu result
                    List<CluResultInfo> cluResultList = luService.getCluResultByClu(cluId);
                    if(cluResultList == null || cluResultList.isEmpty()) {
                        luService.createCluResult(cluId, CREDIT_RESULT_TYPE, cluResultInfo);
                    } else {
                        CluResultInfo cri = cluResultList.get(0);
                        if(cri.getType().equals(CREDIT_RESULT_TYPE)) {
                            luService.updateCluResult(cri.getId(), cri);
                        }
                    }
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

    /*private MetaInfoHelper toMetaInfoHelper(MetaInfo metaInfo) {
        MetaInfoHelper metaInfoHelper = null;
        Data metaData = new Data();
        if (metaInfo == null) return null;
        metaInfoHelper = MetaInfoHelper.wrap(metaData);
        metaInfoHelper.setCreateId(metaInfo.getCreateId());
        metaInfoHelper.setCreateTime(metaInfo.getCreateTime());
        metaInfoHelper.setUpdateId(metaInfo.getUpdateId());
        metaInfoHelper.setUpdateTime(metaInfo.getUpdateTime());
        metaInfoHelper.setVersionInd(metaInfo.getVersionInd());
        return metaInfoHelper;
    }
    
    public MetaInfo toMetaInfo(MetaInfoHelper metaInfoHelper) {
        MetaInfo metaInfo = null;
//        if (metaInfoHelper == null) return null;
        metaInfo = new MetaInfo();
//        metaInfo.setCreateId(metaInfoHelper.getCreateId());
//        metaInfo.setCreateTime(metaInfoHelper.getCreateTime());
//        metaInfo.setUpdateId(metaInfoHelper.getUpdateId());
//        metaInfo.setUpdateTime(metaInfoHelper.getUpdateTime());
//        metaInfo.setVersionInd(metaInfoHelper.getVersionInd());
        metaInfo.setCreateId("XXX");
        metaInfo.setCreateTime(new Date());
        metaInfo.setUpdateId("XXX");
        metaInfo.setUpdateTime(new Date());
        metaInfo.setVersionInd("0");
        return metaInfo;
    }*/
    
}
