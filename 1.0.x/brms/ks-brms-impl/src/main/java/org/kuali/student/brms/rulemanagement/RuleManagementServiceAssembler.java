package org.kuali.student.brms.rulemanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.internal.common.entity.BusinessRuleStatus;
import org.kuali.student.brms.internal.common.entity.ComparisonOperator;
import org.kuali.student.brms.internal.common.entity.RuleElementType;
import org.kuali.student.brms.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.brms.rulemanagement.dao.RuleManagementDAO;
import org.kuali.student.brms.rulemanagement.dto.AgendaDeterminationInfo;
import org.kuali.student.brms.rulemanagement.dto.AgendaInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleTypeInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;
import org.kuali.student.brms.rulemanagement.entity.Agenda;
import org.kuali.student.brms.rulemanagement.entity.BusinessRule;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleAttribute;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleType;
import org.kuali.student.brms.rulemanagement.entity.BusinessRuleTypeFactTypeKey;
import org.kuali.student.brms.rulemanagement.entity.FactStructure;
import org.kuali.student.brms.rulemanagement.entity.FactStructureTranslationKey;
import org.kuali.student.brms.rulemanagement.entity.FactStructureVariable;
import org.kuali.student.brms.rulemanagement.entity.LeftHandSide;
import org.kuali.student.brms.rulemanagement.entity.RightHandSide;
import org.kuali.student.brms.rulemanagement.entity.RuleElement;
import org.kuali.student.brms.rulemanagement.entity.RuleProposition;
import org.kuali.student.brms.rulemanagement.entity.YieldValueFunction;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.service.impl.BaseAssembler;
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
public class RuleManagementServiceAssembler extends BaseAssembler {
    
    public static AgendaInfo toAgendaInfo(Agenda entity) {
        
        if(null == entity) {
            return null;
        }
        
        AgendaInfo agendaInfoDTO = new AgendaInfo();
        
        agendaInfoDTO.setAgendaTypeKey(entity.getType());
        agendaInfoDTO.setAgendaOrchestration(entity.getAgendaOrchestration());

        AgendaDeterminationInfo agendaDeterminationInfoDTO = new AgendaDeterminationInfo();        
        agendaDeterminationInfoDTO.setAgendaInfoDeterminationKeyList(entity.getAgendaDeterminationInfoMap());        
        agendaInfoDTO.setAgendaDeterminationInfo(agendaDeterminationInfoDTO);
    
        List<BusinessRuleTypeInfo> brTypeInfoDTOList = new ArrayList<BusinessRuleTypeInfo>();
        for(BusinessRuleType brTypeInfo : entity.getBusinessRuleTypeInfoList()) {
            brTypeInfoDTOList.add( toBusinessRuleTypeInfo( brTypeInfo ));
        }
        
        agendaInfoDTO.setBusinessRuleTypeInfoList(brTypeInfoDTOList);
        
        return agendaInfoDTO;
    }
        
    
    public static BusinessRuleTypeInfo toBusinessRuleTypeInfo(BusinessRuleType ruleType) {
        BusinessRuleTypeInfo ruleTypeDTO = new BusinessRuleTypeInfo();
        
        ruleTypeDTO.setAnchorTypeKey(ruleType.getAnchorTypeKey().toString());
        ruleTypeDTO.setBussinessRuleTypeKey(ruleType.getBusinessRuleTypeKey().toString());
        
        List<String> factTypeKeyList = new ArrayList<String>();
        
        for(BusinessRuleTypeFactTypeKey ftk : ruleType.getFactTypeKeyList()) {
            factTypeKeyList.add(ftk.getFactTypeKey());
        }
        
        ruleTypeDTO.setFactTypeKeyList(factTypeKeyList);
                
        return ruleTypeDTO;
    }
    
    /**
     * 
     * This method maps the BusinessRule entity to BusinessRuleInfo
     * 
     * @param rule
     * @return
     */
    public static BusinessRuleInfo toBusinessRuleInfo(BusinessRule rule) {

        if(null == rule) {
            return null;
        }
        
        BusinessRuleInfo ruleDTO = new BusinessRuleInfo();
       
        BeanUtils.copyProperties(rule, ruleDTO,
                new String[] { "type", "anchorTypeKey", "state","businessRUleElementList",
                        "attributes", "metaInfo" });
        
        ruleDTO.setType( rule.getBusinessRuleType().getBusinessRuleTypeKey().toString() );
        ruleDTO.setAnchorTypeKey( rule.getBusinessRuleType().getAnchorTypeKey().toString() );
        ruleDTO.setMetaInfo(toMetaInfo(rule.getMeta(), rule.getVersionInd()));
        ruleDTO.setBusinessRuleElementList(toRuleElementInfos(rule.getRuleElements()));
        ruleDTO.setAttributes(toAttributeMap(rule.getAttributes()));
        ruleDTO.setState( rule.getState().toString() );
            
        return ruleDTO;                
    }

    /**
     * 
     * @param entities
     * @return
     */
    public static List<RuleElementInfo> toRuleElementInfos(List<RuleElement> entities) {
       List<RuleElementInfo> dtos = new ArrayList<RuleElementInfo>();
       for(RuleElement element : entities ) {
           dtos.add( toRuleElementInfo( element ) );
       }
       
       return dtos;
    }
    
 
    /**
     * 
     * This method maps RuleElement entity to RuleElementInfo
     * 
     * @param element
     * @return
     */
    public static RuleElementInfo toRuleElementInfo(RuleElement element) {
        
        if(null == element) {
            return null;
        }
        
        RuleElementInfo elementDTO = new RuleElementInfo();
 
        BeanUtils.copyProperties(element, elementDTO,
                new String[] { "businessRuleElemnetTypeKey", "businessRuleProposition"});
         
        elementDTO.setBusinessRuleElemnetTypeKey( element.getBusinessRuleElemnetTypeKey().getName() );
        elementDTO.setBusinessRuleProposition(toRulePropositionInfo( element.getRuleProposition() ));
        
        return elementDTO;
    }
    
    /**
     * 
     * This method maps RuleProposition entity to RulePropositionInfo
     * 
     * @param proposition
     * @return
     */
    public static RulePropositionInfo toRulePropositionInfo(RuleProposition proposition) {
        
        if(null == proposition) {
            return null;
        }
        
        RulePropositionInfo propositionDTO = new RulePropositionInfo();
        
        BeanUtils.copyProperties(proposition, propositionDTO,
                new String[] { "comparisonOperatorTypeKey","leftHandSide","rightHandSide"});

        propositionDTO.setComparisonOperatorTypeKey( proposition.getComparisonOperatorTypeKey().name() );
        propositionDTO.setLeftHandSide( toLeftHandSideInfo( proposition.getLeftHandSide() ) );
        propositionDTO.setRightHandSide( toRightHandSideInfo( proposition.getRightHandSide() ) );
     
        return propositionDTO;
    }
    
    /**
     * 
     * This method maps LeftHandSide entity to LeftHandSideInfo
     * 
     * @param lhs
     * @return
     */
    public static LeftHandSideInfo toLeftHandSideInfo(LeftHandSide lhs) {
        if(null == lhs) {
            return null;
        }
        
        LeftHandSideInfo lhsDTO = new LeftHandSideInfo();        
        lhsDTO.setYieldValueFunction( toYieldValueFunctionInfo( lhs.getYieldValueFunction() ) );
        
        return lhsDTO;
    }
    
    /**
     * 
     * This method maps RightHandSide entity to RightHandSideInfo
     * 
     * @param rhs
     * @return
     */
    public static RightHandSideInfo toRightHandSideInfo(RightHandSide rhs) {
        if(null == rhs) {
            return null;
        }
        
        RightHandSideInfo rhsDTO = new RightHandSideInfo();
        rhsDTO.setExpectedValue( rhs.getExpectedValue() );
        
        return rhsDTO;
    }
    
    /**
     * 
     * This method maps YiledValueFunction entity to YieldValueFunctionInfo
     * 
     * @param yvf
     * @return
     */
    public static YieldValueFunctionInfo toYieldValueFunctionInfo(YieldValueFunction yvf) {
        if(null == yvf) {
            return null;
        }
        
        YieldValueFunctionInfo yvfDTO = new YieldValueFunctionInfo();
        
        yvfDTO.setYieldValueFunctionType( yvf.getYieldValueFunctionType().name() );
        
        List<FactStructureInfo> factStructureDTOList = new ArrayList<FactStructureInfo>();
        for(FactStructure fs : yvf.getFacts() ) {
            factStructureDTOList.add( toFactStructureInfo( fs ) );
        }

        yvfDTO.setFactStructureList( factStructureDTOList );
        
        return yvfDTO;        
    }

    /**
     * 
     * This method maps the FactStructure entity to FactStructureInfo
     * 
     * @param fs
     * @return
     */
    public static FactStructureInfo toFactStructureInfo(FactStructure fs) {
        if(null == fs) {
            return null;
        }
        
        FactStructureInfo fsDTO = new FactStructureInfo();

        BeanUtils.copyProperties(fs, fsDTO,
                new String[] { "paramValueMap","resultColumnKeyTranslations","criteriaTypeInfo"});
                
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
        
        return fsDTO;
    }

    
    /* MAP DTO -> ENTITY */            
    public static BusinessRule toBusinessRuleRelation(BusinessRuleInfo ruleInfoDTO, RuleManagementDAO dao) throws InvalidParameterException {
        
        if(null == ruleInfoDTO) {
            return null;
        }
        
        BusinessRule rule = new BusinessRule();
                
        BeanUtils.copyProperties(ruleInfoDTO, rule,
                new String[] { "businessRuleType", "state","businessRUleElementList",
                        "attributes", "metaInfo" });

        rule.setState( BusinessRuleStatus.valueOf( ruleInfoDTO.getState() ));
        rule.setAttributes(toGenericAttributes(BusinessRuleAttribute.class, ruleInfoDTO.getAttributes(), rule, dao));
                        
        List<RuleElement> elementList = new ArrayList<RuleElement>();
        for(RuleElementInfo elementDTO : ruleInfoDTO.getBusinessRuleElementList()) {
            RuleElement element = toRuleElementRelation(elementDTO);
            element.setBusinessRule( rule );
            elementList.add( element );
        }
        
        rule.setRuleElements( elementList );                
        
        return rule;
    }

    /**
     * 
     * This method maps RuleElementInfo to RuleElement
     * 
     * @param elementDTO
     * @return
     */
    public static RuleElement toRuleElementRelation(RuleElementInfo elementDTO) {
        RuleElement element = new RuleElement();
        
        element.setDesc( elementDTO.getDesc() );        
        element.setName( elementDTO.getName() );
            
        if(RuleElementType.LPAREN.getName().equals( elementDTO.getBusinessRuleElemnetTypeKey())) {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.LPAREN );
        } else if(RuleElementType.RPAREN.getName().equals( elementDTO.getBusinessRuleElemnetTypeKey())) {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.RPAREN );
        } else {
            element.setBusinessRuleElemnetTypeKey( RuleElementType.valueOf( elementDTO.getBusinessRuleElemnetTypeKey() ) );
        }
                
        element.setOrdinalPosition( elementDTO.getOrdinalPosition() );
        element.setRuleProposition( toRulePropositionRelation( elementDTO.getBusinessRuleProposition() ));
        return element;        
    }

    /**
     * 
     * This method maps RulePropositionInfo to RuleProposition entity
     * 
     * @param rulePropositionDTO
     * @return
     */
    public static RuleProposition toRulePropositionRelation(RulePropositionInfo rulePropositionDTO) {
        // In case of AND|OR|(|)... type of rule elements
        if(null == rulePropositionDTO) {
            return null;
        }
        
        RuleProposition ruleProposition = new RuleProposition();
        
        ruleProposition.setComparisonDataTypeKey( rulePropositionDTO.getComparisonDataTypeKey() );
        ruleProposition.setDesc( rulePropositionDTO.getDesc() );
        ruleProposition.setSuccessMessage( rulePropositionDTO.getSuccessMessage() );
        ruleProposition.setFailureMessage( rulePropositionDTO.getFailureMessage() );
        ruleProposition.setLeftHandSide( toLeftHandSideRelation( rulePropositionDTO.getLeftHandSide() ));
        ruleProposition.setName( rulePropositionDTO.getName() );
        ruleProposition.setComparisonOperatorTypeKey( ComparisonOperator.valueOf( rulePropositionDTO.getComparisonOperatorTypeKey() ));
        ruleProposition.setRightHandSide( toRightHandSideRelation( rulePropositionDTO.getRightHandSide() ) );
        
        return ruleProposition;
    }    

    /**
     * 
     * This method LeftHandSideInfo to LeftHandSide entity
     * 
     * @param lhsDTO
     * @return
     */
    public static LeftHandSide toLeftHandSideRelation(LeftHandSideInfo lhsDTO) {
        LeftHandSide lhs = new LeftHandSide();        
        lhs.setYieldValueFunction( toYieldValueFunctionRelation( lhsDTO.getYieldValueFunction() ) );        
        return lhs;
    }

    /**
     * 
     * This method maps RightHandSideInfo to RightHandSide entity
     * 
     * @param rhsDTO
     * @return
     */
    public static RightHandSide toRightHandSideRelation(RightHandSideInfo rhsDTO) {
        
        if(null == rhsDTO) {
            return null;
        }
        
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
    public static YieldValueFunction toYieldValueFunctionRelation(YieldValueFunctionInfo yvfDTO) {
        YieldValueFunction yvf = new YieldValueFunction();
       
        yvf.setYieldValueFunctionType( YieldValueFunctionType.valueOf( yvfDTO.getYieldValueFunctionType() ) );
        
        List<FactStructure> factList = new ArrayList<FactStructure>();
        for(FactStructureInfo factDTO: yvfDTO.getFactStructureList()) {
            FactStructure fact = toFactStructureRelation( factDTO );
            fact.setYieldValueFunction(yvf);
            factList.add( fact  ) ;
        }
        yvf.setFacts(factList);
        
        return yvf;
    }
    
    /**
     * 
     * This method maps FactStructureInfo to FactStructure entity
     * 
     * @param factDTO
     * @return
     */
    public static FactStructure toFactStructureRelation(FactStructureInfo factDTO) {
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
