package org.kuali.student.rules.rulemanagement.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.BusinessRuleTypeKey;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.MetaInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

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

    private static final BusinessRuleAdapter _instance = new BusinessRuleAdapter();
    
    /**
     * 
     * This constructs a private constructor for the singleton
     *
     */
    private BusinessRuleAdapter() {
        
    }
    
    public static BusinessRuleAdapter getInstance() {
        return _instance;
    }
    
    
    /**
     * 
     * This method maps the BusinessRule entity to BusinessRuleInfoDTO
     * 
     * @param rule
     * @return
     */
    public BusinessRuleInfoDTO getBusinessRuleInfoDTO(BusinessRule rule) {

        if(null == rule) {
            return null;
        }
        
        BusinessRuleInfoDTO ruleDTO = new BusinessRuleInfoDTO();
       
        ruleDTO.setBusinessRuleId( rule.getRuleId() );
        ruleDTO.setAnchorValue( rule.getAnchor() );
        ruleDTO.setBusinessRuleTypeKey( rule.getBusinessRuleTypeKey() );
        ruleDTO.setCompiledId( rule.getCompiledId() );
        ruleDTO.setDescription( rule.getDescription() );
        ruleDTO.setEffectiveEndTime( rule.getMetaData().getEffectiveDateEnd() );
        ruleDTO.setEffectiveStartTime( rule.getMetaData().getEffectiveDateStart() );
        ruleDTO.setFailureMessage( rule.getFailureMessage() );
        ruleDTO.setName( rule.getName() );
        
        // Extract the Meta Info
        MetaInfoDTO metaInfo = new MetaInfoDTO();
        metaInfo.setCreateID( rule.getMetaData().getCreatedBy() );
        metaInfo.setUpdateID( rule.getMetaData().getUpdateBy() );
        metaInfo.setCreateTime( rule.getMetaData().getCreateDate() );
        metaInfo.setUpdateTime( rule.getMetaData().getUpdateDate() );
        
        ruleDTO.setMetaInfo( metaInfo );
        
        // Extract the Rule Elments
        List<RuleElementDTO> elementDTOList = new ArrayList<RuleElementDTO>();
        for(RuleElement element : rule.getRuleElements() ) {
            elementDTOList.add( getRuleElementDTO( element ) );
        }
        
        ruleDTO.setRuleElementList(elementDTOList);
        ruleDTO.setStatus( rule.getMetaData().getStatus() );
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
    public RuleElementDTO getRuleElementDTO(RuleElement element) {
        
        RuleElementDTO elementDTO = new RuleElementDTO();
        
        elementDTO.setDescription( element.getDescription() );
        elementDTO.setName( element.getName() );
        elementDTO.setOperation( element.getOperation().getName() );
        elementDTO.setOrdinalPosition( element.getOrdinalPosition() );
        elementDTO.setRuleProposition( getRulePropositionDTO( element.getRuleProposition() ) );
        
        return elementDTO;
    }
    
    /**
     * 
     * This method maps RuleProposition entity to RulePropositionDTO
     * 
     * @param proposition
     * @return
     */
    public RulePropositionDTO getRulePropositionDTO(RuleProposition proposition) {
        
        RulePropositionDTO propositionDTO = new RulePropositionDTO();
        
        propositionDTO.setComparisonDataType( proposition.getComparisonDataType() );
        propositionDTO.setComparisonOperatorType( proposition.getOperator().name() );
        propositionDTO.setDescription( proposition.getDescription() );
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
    public LeftHandSideDTO getLeftHandSideDTO(LeftHandSide lhs) {
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
    public RightHandSideDTO getRightHandSideDTO(RightHandSide rhs) {
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
    public YieldValueFunctionDTO getYieldValueFunctionDTO(YieldValueFunction yvf) {
     
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
    public FactStructureDTO getFactStructureDTO(FactStructure fs) {
        FactStructureDTO fsDTO = new FactStructureDTO();
        
        fsDTO.setAnchorFlag( fs.getAnchorFlag() );
        fsDTO.setDataType( fs.getDataType() );
        
        // Extract the definition variables
        Map<String, String> definitionVariables = new HashMap<String, String>();
        for(FactStructureVariable fsVar : fs.getDefinitionVariableList()) {
            definitionVariables.put( fsVar.getStructureKey(),  fsVar.getValue() );
        }
        fsDTO.setDefinitionVariableList(definitionVariables);
        
        // Extract the execution variables
        Map<String, String> executionVariables = new HashMap<String, String>();
        for(FactStructureVariable fsVar : fs.getExecutionVariableList() ) {
            executionVariables.put( fsVar.getStructureKey(),  fsVar.getValue() );
        }
        fsDTO.setExecutionVariableList(executionVariables);
        
        return fsDTO;
    }

    
    /* MAP DTO -> ENTITY */    
    public BusinessRule getBusinessRuleEntity(BusinessRuleInfoDTO ruleInfoDTO) {
        BusinessRule rule = new BusinessRule();
        
        rule.setAnchor( ruleInfoDTO.getAnchorValue() );
        rule.setBusinessRuleTypeKey( ruleInfoDTO.getBusinessRuleTypeKey() );
        
        return rule;
    }    
}
