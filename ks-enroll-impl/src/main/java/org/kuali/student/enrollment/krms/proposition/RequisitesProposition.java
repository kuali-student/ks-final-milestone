/**
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.krms.proposition;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.api.engine.ExecutionFlag;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicContext;
import org.kuali.rice.krms.framework.engine.ContextProvider;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.framework.engine.result.BasicResult;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroup;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.registration.client.service.impl.util.RegistrationValidationResultsUtil;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This proposition evaluates all the instructions associated with a process
 *
 * @author nwright
 */
public class RequisitesProposition extends AbstractLeafProposition {

    private RuleManagementService ruleManagementService;
    private KrmsTypeRepositoryService krmsTypeRepositoryService;

    private String agendaType;
    private List<String> ruleTypes;

    private static final String ruleManagementServiceConst = "ruleManagementService";
    private static final String krmsTypeRepositoryServiceConst = "krmsTypeRepositoryService";

    public RequisitesProposition() {
    }

    public RequisitesProposition(String agendaType, List<String> ruleTypes) {
        this.agendaType = agendaType;
        this.ruleTypes = ruleTypes;
    }

    public String getAgendaType() {
        return agendaType;
    }

    public void setAgendaType(String agendaType) {
        this.agendaType = agendaType;
    }

    public List<String> getRuleTypes() {
        return ruleTypes;
    }

    public void setRuleTypes(List<String> ruleTypes) {
        this.ruleTypes = ruleTypes;
    }

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        ContextInfo contextInfo = environment.resolveTerm(RulesExecutionConstants.CONTEXT_INFO_TERM, this);
        RegistrationRequestInfo request = environment.resolveTerm(RulesExecutionConstants.REGISTRATION_REQUEST_TERM, this);

        List<ValidationResult> validationResults = new ArrayList<ValidationResult>();
        // get all the needed services from the execution context
        // we do this so we can locally cache some services and the cache lives just for the length of krms execution
        CourseOfferingService courseOfferingService = environment.resolveTerm(RulesExecutionConstants.COURSE_OFFERING_SERVICE_TERM, this);
        for (RegistrationRequestItemInfo item : request.getRegistrationRequestItems()) {
            RegistrationGroup regGroup;
            try {
                regGroup = courseOfferingService.getRegistrationGroup(item.getRegistrationGroupId(), contextInfo);
            } catch (Exception ex) {
                return KRMSEvaluator.constructExceptionPropositionResult(environment, ex, this);
            }

            if (!executeRuleForRegGroupAndType(environment, regGroup)) {
                String msg = RegistrationValidationResultsUtil.marshallSimpleMessage(LprServiceConstants.LPRTRANS_ITEM_FAILED_ANTI_REQUISITES_MESSAGE_KEY);
                ValidationResultInfo vr = new ValidationResultInfo();
                vr.setLevel(ValidationResult.ErrorLevel.ERROR);
                String elt = "registrationRequestItems['" + item.getId() + "']";
                vr.setElement(elt);
                vr.setMessage(msg);
                validationResults.add(vr);
            }
        }

        Map<String, Object> executionDetails = new LinkedHashMap<String, Object>();
        executionDetails.put(RulesExecutionConstants.PROCESS_EVALUATION_RESULTS, validationResults);
        PropositionResult result = new PropositionResult(validationResults.size() > 0 ? false : true, executionDetails);
        BasicResult br = new BasicResult(ResultEvent.PROPOSITION_EVALUATED, this, environment, result.getResult());
        environment.getEngineResults().addResult(br);
        return result;
    }

    private boolean executeRuleForRegGroupAndType(ExecutionEnvironment environment, RegistrationGroup regGroup) {

        KrmsTypeDefinition agendaTypeInfo = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, this.getAgendaType());

        // Build the KRMS agenda and other startup objects to execute
        for (String ruleType : this.getRuleTypes()) {
            KrmsTypeDefinition ruleTypeInfo = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD,
                    ruleType);

            boolean isCoRuleEvaluated = false;
            for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
                // Get the rule for the ao.
                RuleDefinition definition = this.getRuleForRefObjectIdAndType(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                        activityOfferingId, agendaTypeInfo.getId(), ruleTypeInfo.getId());

                if ((definition == null) && (!isCoRuleEvaluated)) {
                    definition = this.getRuleForRefObjectIdAndType(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
                            regGroup.getCourseOfferingId(), agendaTypeInfo.getId(), ruleTypeInfo.getId());
                }

                if (definition != null) {
                    Rule rule = this.getRepositoryToEngineTranslator().translateRuleDefinition(definition);
                    if (!rule.evaluate(environment)) {
                        return false;
                    }
                }

            }
        }

        return true;

    }

    public RuleDefinition getRuleForRefObjectIdAndType(String discriminator, String refObjectId,
                                                       String agendaTypeId, String ruleTypeId) {

        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(
                discriminator, refObjectId);

        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            AgendaDefinition agenda = this.getRuleManagementService().getAgenda(referenceObjectBinding.getKrmsObjectId());
            if (agenda.getTypeId().equals(agendaTypeId)) {
                AgendaItemDefinition firstItem = this.getRuleManagementService().getAgendaItem(agenda.getFirstItemId());
                return getRuleForType(firstItem, ruleTypeId);
            }
        }

        return null;
    }

    public RuleDefinition getRuleForType(AgendaItemDefinition agendaItem, String ruleTypeId) {

        if (agendaItem.getRule() != null && agendaItem.getRule().getTypeId().equals(ruleTypeId)) {
            return agendaItem.getRule();
        }

        if (agendaItem.getWhenTrue() != null) {
            return getRuleForType(agendaItem.getWhenTrue(), ruleTypeId);
        }

        return null;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, ruleManagementServiceConst));
        }
        return ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, krmsTypeRepositoryServiceConst));
        }
        return krmsTypeRepositoryService;
    }

    public RepositoryToEngineTranslator getRepositoryToEngineTranslator() {
        return KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator();
    }

}
