package org.kuali.student.rules.rulemanagement.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.entity.RuleElementType;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.AgendaDeterminationInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleTypeInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.springframework.beans.BeanUtils;

/**
 * 
 * This class maps BusinessRule entity to BusinessRuleDTO and vice versa  
 * NOTE: Any time the signature of any of the entity or dto classes change, 
 * this class should be updated.
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 *
 */
public class BusinessRuleAdapter {
    
    public static AgendaInfoDTO getAgendaInfoDTO(AgendaInfo agendaInfo) {
        AgendaInfoDTO agendaInfoDTO = new AgendaInfoDTO();
        
        agendaInfoDTO.setAgendaTypeKey(agendaInfo.getType());
        agendaInfoDTO.setAgendaOrchestration(agendaInfo.getAgendaOrchestration());

        AgendaDeterminationInfoDTO agendaDeterminationInfoDTO = new AgendaDeterminationInfoDTO();        
        agendaDeterminationInfoDTO.setAgendaInfoDeterminationKeyList(agendaInfo.getAgendaDeterminationInfoMap());        
        agendaInfoDTO.setAgendaDeterminationInfo(agendaDeterminationInfoDTO);
    
        List<BusinessRuleTypeInfoDTO> brTypeInfoDTOList = new ArrayList<BusinessRuleTypeInfoDTO>();
        for(BusinessRuleType brTypeInfo : agendaInfo.getBusinessRuleTypeInfoList()) {
            brTypeInfoDTOList.add( getBusinessRuleTypeDTO( brTypeInfo ));
        }
        
        agendaInfoDTO.setBusinessRuleTypeInfoList(brTypeInfoDTOList);
        
        return agendaInfoDTO;
    }
        
    
    public static BusinessRuleTypeInfoDTO getBusinessRuleTypeDTO(BusinessRuleType ruleType) {
        BusinessRuleTypeInfoDTO ruleTypeDTO = new BusinessRuleTypeInfoDTO();
        
        ruleTypeDTO.setAnchorTypeKey(ruleType.getAnchorTypeKey().toString());
        ruleTypeDTO.setBussinessRuleTypeKey(ruleType.getBusinessRuleTypeKey().toString());
        ruleTypeDTO.setFactTypeKeyList(ruleType.getFactTypeKeyList());
                
        return ruleTypeDTO;
    }
    
    /**
     * 
     * This method maps the BusinessRule entity to BusinessRuleInfoDTO
     * 
     * @param rule
     * @return
     */
    public static BusinessRuleInfoDTO getBusinessRuleInfoDTO(BusinessRule rule) {

        if(null == rule) {
            return null;
        }
        
        BusinessRuleInfoDTO ruleDTO = new BusinessRuleInfoDTO();
       
        ruleDTO.setId( rule.getId() );
        ruleDTO.setAnchorValue( rule.getAnchor() );
        ruleDTO.setType( rule.getBusinessRuleType().getBusinessRuleTypeKey().toString() );
        ruleDTO.setAnchorTypeKey( rule.getBusinessRuleType().getAnchorTypeKey().toString() );
        ruleDTO.setCompiledId( rule.getCompiledId() );
        ruleDTO.setDesc( rule.getDescription() );
        ruleDTO.setEffectiveDate( rule.getMetaData().getEffectiveDate() );
        ruleDTO.setExpirationDate( rule.getMetaData().getExpirationDate() );
        ruleDTO.setFailureMessage( rule.getFailureMessage() );
        ruleDTO.setName( rule.getName() );
        ruleDTO.setOriginalRuleId(rule.getOriginalRuleId());
        ruleDTO.setRepositorySnapshotName(rule.getRepositorySnapshotName());
        
        // Extract the Meta Info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateID( rule.getMetaData().getCreatedBy() );
        metaInfo.setUpdateID( rule.getMetaData().getUpdateBy() );
        metaInfo.setCreateTime( rule.getMetaData().getCreateDate() );
        metaInfo.setUpdateTime( rule.getMetaData().getUpdateDate() );
        
        ruleDTO.setMetaInfo( metaInfo );
        
        // Extract the Rule Elements
        List<RuleElementDTO> elementDTOList = new ArrayList<RuleElementDTO>();
        for(RuleElement element : rule.getRuleElements() ) {
            elementDTOList.add( getRuleElementDTO( element ) );
        }
        
        ruleDTO.setBusinessRuleElementList(elementDTOList);
        ruleDTO.setState( rule.getState().toString() );
        ruleDTO.setSuccessMessage( rule.getSuccessMessage() );
            
        return ruleDTO;                
    }
    
 
    /**
     * 
     * This method maps RuleElement entity to RuleElementDTO
     * 
     * @param element
     * @return
     */
    public static RuleElementDTO getRuleElementDTO(RuleElement element) {
        
        RuleElementDTO elementDTO = new RuleElementDTO();
        
        elementDTO.setId(element.getId());
        elementDTO.setDescription( element.getDescription() );
        elementDTO.setName( element.getName() );
        elementDTO.setBusinessRuleElemnetTypeKey( element.getBusinessRuleElemnetTypeKey().getName() );
        elementDTO.setOrdinalPosition( element.getOrdinalPosition() );
        
        // If we have a proposition
        if(null != element.getRuleProposition()) {
            elementDTO.setBusinessRuleProposition( getRulePropositionDTO( element.getRuleProposition() ) );
        }
        
        return elementDTO;
    }
    
    /**
     * 
     * This method maps RuleProposition entity to RulePropositionDTO
     * 
     * @param proposition
     * @return
     */
    public static RulePropositionDTO getRulePropositionDTO(RuleProposition proposition) {
        RulePropositionDTO propositionDTO = new RulePropositionDTO();
        
        propositionDTO.setComparisonDataTypeKey( proposition.getComparisonDataTypeKey() );
        propositionDTO.setComparisonOperatorTypeKey( proposition.getComparisonOperatorTypeKey().name() );
        propositionDTO.setDescription( proposition.getDescription() );
        propositionDTO.setSuccessMessage( proposition.getSuccessMessage() );
        propositionDTO.setFailureMessage( proposition.getFailureMessage() );
        propositionDTO.setLeftHandSide( getLeftHandSideDTO( proposition.getLeftHandSide() ) );
        propositionDTO.setName( proposition.getName() );
        propositionDTO.setRightHandSide( getRightHandSideDTO( proposition.getRightHandSide() ) );
     
        return propositionDTO;
    }
    
    /**
     * 
     * This method maps LeftHandSide entity to LeftHandSideDTO
     * 
     * @param lhs
     * @return
     */
    public static LeftHandSideDTO getLeftHandSideDTO(LeftHandSide lhs) {
        LeftHandSideDTO lhsDTO = new LeftHandSideDTO();
        
        lhsDTO.setYieldValueFunction( getYieldValueFunctionDTO( lhs.getYieldValueFunction() ) );
        return lhsDTO;
    }
    
    /**
     * 
     * This method maps RightHandSide entity to RightHandSideDTO
     * 
     * @param rhs
     * @return
     */
    public static RightHandSideDTO getRightHandSideDTO(RightHandSide rhs) {
        RightHandSideDTO rhsDTO = new RightHandSideDTO();
        rhsDTO.setExpectedValue( rhs.getExpectedValue() );
        return rhsDTO;
    }
    
    /**
     * 
     * This method maps YiledValueFunction entity to YieldValueFunctionDTO
     * 
     * @param yvf
     * @return
     */
    public static YieldValueFunctionDTO getYieldValueFunctionDTO(YieldValueFunction yvf) {
     
        YieldValueFunctionDTO yvfDTO = new YieldValueFunctionDTO();
        
        yvfDTO.setYieldValueFunctionType( yvf.getYieldValueFunctionType().name() );
        
        List<FactStructureDTO> factStructureDTOList = new ArrayList<FactStructureDTO>();
        for(FactStructure fs : yvf.getFacts() ) {
            factStructureDTOList.add( getFactStructureDTO( fs ) );
        }

        yvfDTO.setFactStructureList( factStructureDTOList );
        
        return yvfDTO;        
    }

    /**
     * 
     * This method maps the FactStructure entity to FactStructureDTO
     * 
     * @param fs
     * @return
     */
    public static FactStructureDTO getFactStructureDTO(FactStructure fs) {
        FactStructureDTO fsDTO = new FactStructureDTO();
        
        fsDTO.setAnchorFlag( fs.getAnchorFlag() );
        
        // Extract the parameter values
        if (fs.getParamValueSet() != null) {
	        Map<String, String> paramValueMap = new HashMap<String, String>();
	        for(FactStructureVariable fsVar : fs.getParamValueSet()) {
	            paramValueMap.put( fsVar.getStructureKey(), fsVar.getValue() );
	        }
	        fsDTO.setParamValueMap(paramValueMap);
        }        

        
        // Extract the translation keys
        if (fs.getTranslationKeySet() != null) {
            Map<String, String> translationKeyMap = new HashMap<String, String>();
            for(FactStructureTranslationKey trKey : fs.getTranslationKeySet()) {
                translationKeyMap.put( trKey.getTranslationKey(), trKey.getValue() );
            }
            fsDTO.setResultColumnKeyTranslations(translationKeyMap);
        }        
                
        fsDTO.setCriteriaTypeInfo(null);        
        fsDTO.setFactStructureId(fs.getFactStructureId());        
        fsDTO.setFactTypeKey(fs.getFactTypeKey());        
        fsDTO.setStaticFact(fs.getStaticFact());        
        fsDTO.setStaticValue(fs.getStaticValue());
        fsDTO.setStaticValueDataType(fs.getStaticValueDataType());
        
        return fsDTO;
    }

    
    /* MAP DTO -> ENTITY */            
    public static BusinessRule getBusinessRuleEntity(BusinessRuleInfoDTO ruleInfoDTO) {
        
        BusinessRule rule = new BusinessRule();
                
        rule.setAnchor( ruleInfoDTO.getAnchorValue() );
        rule.setCompiledId(ruleInfoDTO.getCompiledId());
        rule.setDescription(ruleInfoDTO.getDesc());
        rule.setFailureMessage(ruleInfoDTO.getFailureMessage());
        rule.setName( ruleInfoDTO.getName() ); 
        rule.setId( ruleInfoDTO.getId() );
        rule.setSuccessMessage( ruleInfoDTO.getSuccessMessage() );                        
        rule.setRepositorySnapshotName(ruleInfoDTO.getRepositorySnapshotName());
        rule.setOriginalRuleId(ruleInfoDTO.getOriginalRuleId());
        rule.setState( BusinessRuleStatus.valueOf( ruleInfoDTO.getState() ));
        
        RuleMetaData metaData = new RuleMetaData();
        metaData.setCreateDate( ruleInfoDTO.getMetaInfo().getCreateTime() );
        metaData.setCreatedBy( ruleInfoDTO.getMetaInfo().getCreateID() );
        metaData.setEffectiveDate(ruleInfoDTO.getEffectiveDate());
        metaData.setExpirationDate(ruleInfoDTO.getExpirationDate());
        
        metaData.setUpdateBy( ruleInfoDTO.getMetaInfo().getUpdateID());
        metaData.setUpdateDate(ruleInfoDTO.getMetaInfo().getUpdateTime());
        
        rule.setMetaData(metaData);
        
        List<RuleElement> elementList = new ArrayList<RuleElement>();
        for(RuleElementDTO elementDTO : ruleInfoDTO.getBusinessRuleElementList()) {
            RuleElement element = getRuleElementEntity(elementDTO);
            element.setBusinessRule( rule );
            elementList.add( element );
        }
        
        rule.setRuleElements( elementList );                
        
        return rule;
    }

    /**
     * 
     * This method maps RuleElementDTO to RuleElement
     * 
     * @param elementDTO
     * @return
     */
    public static RuleElement getRuleElementEntity(RuleElementDTO elementDTO) {
        RuleElement element = new RuleElement();
        
        element.setDescription( elementDTO.getDescription() );
        
        element.setName( elementDTO.getName() );
            
        if(RuleElementType.LPAREN.getName().equals( elementDTO.getBusinessRuleElemnetTypeKey())) {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.LPAREN );
        } else if(RuleElementType.RPAREN.getName().equals( elementDTO.getBusinessRuleElemnetTypeKey())) {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.RPAREN );
        } else {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.valueOf( elementDTO.getBusinessRuleElemnetTypeKey() ) );
        }
                
        element.setOrdinalPosition( elementDTO.getOrdinalPosition() );
        element.setRuleProposition( getRulePropositionEntity( elementDTO.getBusinessRuleProposition() ));
        return element;        
    }

    /**
     * 
     * This method maps RulePropositionDTO to RuleProposition entity
     * 
     * @param rulePropositionDTO
     * @return
     */
    public static RuleProposition getRulePropositionEntity(RulePropositionDTO rulePropositionDTO) {
        // In case of AND|OR|(|)... type of rule elements
        if(null == rulePropositionDTO) {
            return null;
        }
        
        RuleProposition ruleProposition = new RuleProposition();
        
        ruleProposition.setComparisonDataTypeKey( rulePropositionDTO.getComparisonDataTypeKey() );
        ruleProposition.setDescription( rulePropositionDTO.getDescription() );
        ruleProposition.setSuccessMessage( rulePropositionDTO.getSuccessMessage() );
        ruleProposition.setFailureMessage( rulePropositionDTO.getFailureMessage() );
        ruleProposition.setLeftHandSide( getLeftHandSideEntity( rulePropositionDTO.getLeftHandSide() ));
        ruleProposition.setName( rulePropositionDTO.getName() );
        ruleProposition.setComparisonOperatorTypeKey( ComparisonOperator.valueOf( rulePropositionDTO.getComparisonOperatorTypeKey() ));
        ruleProposition.setRightHandSide( getRightHandSideEntity( rulePropositionDTO.getRightHandSide() ) );
        
        return ruleProposition;
    }    

    /**
     * 
     * This method LeftHandSideDTO to LeftHandSide entity
     * 
     * @param lhsDTO
     * @return
     */
    public static LeftHandSide getLeftHandSideEntity(LeftHandSideDTO lhsDTO) {
        LeftHandSide lhs = new LeftHandSide();        
        lhs.setYieldValueFunction( getYieldValueFunctionEntity( lhsDTO.getYieldValueFunction() ) );        
        return lhs;
    }

    /**
     * 
     * This method maps RightHandSideDTO to RightHandSide entity
     * 
     * @param rhsDTO
     * @return
     */
    public static RightHandSide getRightHandSideEntity(RightHandSideDTO rhsDTO) {
        RightHandSide rhs = new RightHandSide();        
        rhs.setExpectedValue( rhsDTO.getExpectedValue() );
        return rhs;
    }
 
    /**
     * 
     * This method maps YieldValueFuncitonDTO to YieldValueFunction entity
     * 
     * @param yvfDTO
     * @return
     */
    public static YieldValueFunction getYieldValueFunctionEntity(YieldValueFunctionDTO yvfDTO) {
        YieldValueFunction yvf = new YieldValueFunction();
       
        yvf.setYieldValueFunctionType( YieldValueFunctionType.valueOf( yvfDTO.getYieldValueFunctionType() ) );
        
        List<FactStructure> factList = new ArrayList<FactStructure>();
        for(FactStructureDTO factDTO: yvfDTO.getFactStructureList()) {
            FactStructure fact = getFactStructureEntity( factDTO );
            fact.setYieldValueFunction(yvf);
            factList.add( fact  ) ;
        }
        yvf.setFacts(factList);
        
        return yvf;
    }
    
    /**
     * 
     * This method maps FactStructureDTO to FactStructure entity
     * 
     * @param factDTO
     * @return
     */
    public static FactStructure getFactStructureEntity(FactStructureDTO factDTO) {
       FactStructure fs = new FactStructure();
       
       fs.setAnchorFlag( factDTO.getAnchorFlag() );
       fs.setFactStructureId( factDTO.getFactStructureId() );
       fs.setFactTypeKey(factDTO.getFactTypeKey());
       fs.setStaticFact(factDTO.isStaticFact());
       fs.setStaticValue(factDTO.getStaticValue());
       fs.setStaticValueDataType(factDTO.getStaticValueDataType());
       
       // Extract parameter variables
       Set<FactStructureVariable> fsParamVarList = new HashSet<FactStructureVariable>(); 
       Map<String,String> factParamVarMap = factDTO.getParamValueMap();
       if (factParamVarMap != null) {
	       for(String key: factParamVarMap.keySet()) {
	           FactStructureVariable fsVar = new FactStructureVariable();
	           fsVar.setFactStructure(fs);
	           fsVar.setStructureKey(key);
	           fsVar.setValue((String)factParamVarMap.get(key));
	           fsParamVarList.add(fsVar);
	       }
	       fs.setParamValueSet(fsParamVarList);
       }

       
       // Extract translation keys
       Set<FactStructureTranslationKey> fsTranslationKeySet = new HashSet<FactStructureTranslationKey>(); 
       Map<String,String> translationKeyMap = factDTO.getResultColumnKeyTranslations();
       if (translationKeyMap != null) {
           for(String key: translationKeyMap.keySet()) {
               FactStructureTranslationKey fsKey = new FactStructureTranslationKey();
               fsKey.setFactStructure(fs);
               fsKey.setTranslationKey(key);
               fsKey.setValue((String)translationKeyMap.get(key));
               fsTranslationKeySet.add(fsKey);
           }
           fs.setTranslationKeySet(fsTranslationKeySet);
       }       
              
       return fs;
    }
        
    /**
     * 
     * This method copies data from one rule to another keeping the id the same
     * 
     * @param fromRule
     * @param toRule
     * @return
     */
    public static BusinessRule copyBusinessRule(BusinessRule fromRule, BusinessRule toRule) {        

        // Copy From rule to To rule with a list of ignore attributes
        BeanUtils.copyProperties(fromRule, toRule, new String[]{"id","origName","compiledId","businessRuleType"});        
        
        // Now update the parent reference in rule element to change from fromRule to toRule
        for(RuleElement element : fromRule.getRuleElements()) {
            element.setBusinessRule(toRule);
        }

        toRule.setRuleElements(fromRule.getRuleElements());
                        
        return toRule;
    }
}
