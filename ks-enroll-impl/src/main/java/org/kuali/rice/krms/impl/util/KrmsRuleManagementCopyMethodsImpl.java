package org.kuali.rice.krms.impl.util;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the copy logic for copying object references from one object to another
 * @author christoff
 */
public class KrmsRuleManagementCopyMethodsImpl implements KrmsRuleManagementCopyMethods {

    private RuleManagementService ruleManagementService;
    private KrmsTypeRepositoryService krmsTypeRepositoryService;

    @Override
    public List<ReferenceObjectBinding> deepCopyReferenceObjectBindingsFromTo(String fromReferenceDiscriminatorType,
            String fromReferenceObjectId,
            String toReferenceDiscriminatorType,
            String toReferenceObjectId,
            List<String> optionKeys)
            throws RiceIllegalArgumentException,
            RiceIllegalStateException {
        _checkEmptyParam(fromReferenceDiscriminatorType, "fromReferenceDiscriminatorType");
        _checkEmptyParam(fromReferenceObjectId, "fromReferenceObjectId");
        _checkEmptyParam(toReferenceDiscriminatorType, "toReferenceDiscriminatorType");
        _checkEmptyParam(toReferenceObjectId, "toReferenceObjectId");
        List<ReferenceObjectBinding> copiedRefList = new ArrayList<ReferenceObjectBinding>();
        List<ReferenceObjectBinding> refsToCopy = this.ruleManagementService.findReferenceObjectBindingsByReferenceObject(fromReferenceDiscriminatorType, fromReferenceObjectId);
        for(ReferenceObjectBinding reference : refsToCopy){
            ReferenceObjectBinding.Builder refBldr = null;
            if(reference.getKrmsDiscriminatorType().equals("Agenda")){//TODO data currently does not have the proper REF_OBJECT_URI in this field
                AgendaTreeDefinition agendaTree = ruleManagementService.getAgendaTree(reference.getKrmsObjectId());

                AgendaDefinition copiedAgenda = deepCopyAgenda(agendaTree,toReferenceObjectId);
                refBldr = ReferenceObjectBinding.Builder.create(reference);
                refBldr.setId(null);
                refBldr.setReferenceObjectId(toReferenceObjectId);
                refBldr.setReferenceDiscriminatorType(toReferenceDiscriminatorType);
                refBldr.setKrmsObjectId(copiedAgenda.getId());
            }else{
                //TODO no support for copying any other krms types yet
                continue;
            }
            ReferenceObjectBinding refBinding = ruleManagementService.createReferenceObjectBinding(refBldr.build());
            copiedRefList.add(refBinding);
        }
        return copiedRefList;
    }

    private AgendaDefinition deepCopyAgenda(AgendaTreeDefinition agendaTree, String refObjectId){
        //clone the Agenda
        AgendaDefinition oldAgenda = ruleManagementService.getAgenda(agendaTree.getAgendaId());
        String agendaTypeKey = krmsTypeRepositoryService.getTypeById(oldAgenda.getTypeId()).getName();
        AgendaDefinition copiedAgenda = AgendaDefinition.Builder.create(null, refObjectId + ":" + agendaTypeKey+":1", oldAgenda.getTypeId(), oldAgenda.getContextId()).build();
        copiedAgenda = ruleManagementService.createAgenda(copiedAgenda);

        AgendaItemDefinition.Builder firstAgendaItemBldr = null;
        AgendaItemDefinition.Builder previousAgendaItemBldr = null;
        boolean firstItem = true;
        for (AgendaTreeEntryDefinitionContract entry : agendaTree.getEntries()) {
            AgendaItemDefinition currentAgendaItem = ruleManagementService.getAgendaItem(entry.getAgendaItemId());
            AgendaItemDefinition.Builder copiedAgendaItemBldr = AgendaItemDefinition.Builder.create(currentAgendaItem);
            copiedAgendaItemBldr.setId((firstItem ? copiedAgenda.getFirstItemId() : null));
            copiedAgendaItemBldr.setAgendaId(copiedAgenda.getId());
            copiedAgendaItemBldr.setRuleId(null);
            RuleDefinition.Builder copiedRuleBldr = copiedAgendaItemBldr.getRule();
            copiedRuleBldr.setId(null);
            copiedRuleBldr.setPropId(null);
            String ruleTypeKey = krmsTypeRepositoryService.getTypeById(copiedRuleBldr.getTypeId()).getName();
            copiedRuleBldr.setName(refObjectId + ":" + ruleTypeKey+":1");
            deepUpdateForProposition(copiedRuleBldr.getProposition());

            if(firstItem){
                firstAgendaItemBldr = copiedAgendaItemBldr;
                previousAgendaItemBldr = firstAgendaItemBldr;
            }else{
                previousAgendaItemBldr.setWhenTrue(copiedAgendaItemBldr);
                previousAgendaItemBldr = copiedAgendaItemBldr;
            }
            firstItem = false;
        }
        ruleManagementService.updateAgendaItem(firstAgendaItemBldr.build());
        return null;
    }

    private void deepUpdateForProposition(PropositionDefinition.Builder propBldr){
        propBldr.setId(null);
        propBldr.setRuleId(null);
        for(PropositionParameter.Builder propParmBldr : propBldr.getParameters()){
            propParmBldr.setId(null);
            propParmBldr.setPropId(null);
            if(PropositionParameterType.TERM.getCode().equals(propParmBldr.getParameterType())){
                propParmBldr.setValue(null);
                TermDefinition.Builder termBldr = TermDefinition.Builder.create(propParmBldr.getTermValue());
                termBldr.setId(null);
                for(TermParameterDefinition.Builder termParmBldr : termBldr.getParameters()){
                    termParmBldr.setId(null);
                    termParmBldr.setTermId(null);
                }
            }
        }
        if(PropositionType.COMPOUND.getCode().equals(propBldr.getPropositionTypeCode())){
            for(PropositionDefinition.Builder subPropBldr : propBldr.getCompoundComponents()){
                deepUpdateForProposition(subPropBldr);
            }
        }
    }

    private void _checkEmptyParam(String param, String message)
            throws RiceIllegalArgumentException {
        if (param == null) {
            throw new RiceIllegalArgumentException(message);
        }
        if (param.trim().isEmpty()) {
            throw new RiceIllegalArgumentException(message);
        }
    }

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return krmsTypeRepositoryService;
    }

    public void setKrmsTypeRepositoryService(KrmsTypeRepositoryService krmsTypeRepositoryService) {
        this.krmsTypeRepositoryService = krmsTypeRepositoryService;
    }
}
