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

import org.joda.time.DateTime;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.engine.Engine;
import org.kuali.rice.krms.api.engine.EngineResults;
import org.kuali.rice.krms.api.engine.Facts;
import org.kuali.rice.krms.api.engine.ResultEvent;
import org.kuali.rice.krms.api.engine.SelectionCriteria;
import org.kuali.rice.krms.api.engine.Term;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.AgendaTreeEntry;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.engine.BasicAgendaTree;
import org.kuali.rice.krms.framework.engine.BasicAgendaTreeEntry;
import org.kuali.rice.krms.framework.type.TermResolverTypeService;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.core.process.evaluator.KRMSEvaluator;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Utility class to build up a Proposition tree for to evaluate checks from a Process
 *
 * @author alubbers
 */
public class RequisitesEvaluator extends KRMSEvaluator {

    private RuleManagementService ruleManagementService;
    private KrmsTypeRepositoryService krmsTypeRepositoryService;
    private TermRepositoryService termRepositoryService;

    private static final String ruleManagementServiceConst = "ruleManagementService";
    private static final String krmsTypeRepositoryServiceConst = "krmsTypeRepositoryService";
    private static final String termRepositoryServiceConst = "termRepositoryService";

    public EngineResults evaluateRules(Map<Term, Object> facts, List<RuleDefinition> rules) {

        List<AgendaTreeEntry> treeEntries = new ArrayList<AgendaTreeEntry>();
        for(RuleDefinition rule : rules){
            treeEntries.add(new BasicAgendaTreeEntry(getRepositoryToEngineTranslator().translateRuleDefinition(rule)));
        }

        Map<String, String> qualifiers = Collections.emptyMap();
        Agenda agenda = new BasicAgenda(qualifiers, new BasicAgendaTree(treeEntries));

        Facts factsByTerm = Facts.Builder.create().addFactsByTerm(facts).build();
        Map<String, String> agendaQualifiers = Collections.emptyMap();

        return this.evaluateAgenda(agenda, factsByTerm, agendaQualifiers);
    }

    public EngineResults evaluateAgenda(Agenda agenda, Facts facts, Map<String, String> agendaQualifiers) {

        Engine engine = constructEngine(agenda, this.getTermResolvers());

        SelectionCriteria selectionCriteria = SelectionCriteria.
                createCriteria(new DateTime(), this.getContextQualifiers(), agendaQualifiers);

        return engine.execute(selectionCriteria, facts, this.getExecutionOptions());
    }

    @Override
    public List<TermResolver<?>> getTermResolvers() {
        List<TermResolver<?>> termResolvers = super.getTermResolvers();
        if (termResolvers == null) {

            KrmsTypeDefinition typeDefinition = this.getKrmsTypeRepositoryService().getTypeByName(
                    PermissionServiceConstants.KS_SYS_NAMESPACE, KSKRMSServiceConstants.TERM_RESOLVER_TYPE_COURSE_REQUISITES);

            TermResolverTypeService termResolverTypeService = GlobalResourceLoader.getService(new QName(typeDefinition.getServiceName()));

            termResolvers = new ArrayList<TermResolver<?>>();
            List<TermResolverDefinition> definitions = this.getTermRepositoryService().findTermResolversByNamespace(PermissionServiceConstants.KS_SYS_NAMESPACE);
            for (TermResolverDefinition definition : definitions) {
                if (definition.getTypeId().equals(typeDefinition.getId())) {
                    TermResolver<?> termResolver = termResolverTypeService.loadTermResolver(definition);
                    if(termResolver!=null){
                        termResolvers.add(termResolver);
                    }
                }
            }
            this.setTermResolvers(termResolvers);
        }
        return termResolvers;
    }

    public RuleDefinition getRuleForRefObjectIdAndType(String discriminator, String refObjectId,
                                                       String agendaTypeId, String ruleTypeId) {

        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(
                discriminator, refObjectId);

        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            AgendaDefinition agenda = this.getRuleManagementService().getAgenda(referenceObjectBinding.getKrmsObjectId());
            if(agenda.getTypeId().equals(agendaTypeId)){
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

    public TermRepositoryService getTermRepositoryService() {
        if (termRepositoryService == null) {
            termRepositoryService = GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, termRepositoryServiceConst));
        }
        return termRepositoryService;
    }

    public RepositoryToEngineTranslator getRepositoryToEngineTranslator(){
        return KrmsRepositoryServiceLocator.getKrmsRepositoryToEngineTranslator();
    }

}
