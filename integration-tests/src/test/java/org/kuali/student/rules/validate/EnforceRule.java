/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.validate;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.kuali.student.rules.brms.agenda.AgendaDiscovery;
import org.kuali.student.rules.internal.common.agenda.AgendaRequest;
import org.kuali.student.rules.internal.common.agenda.entity.Agenda;
import org.kuali.student.rules.internal.common.agenda.entity.Anchor;
import org.kuali.student.rules.internal.common.agenda.entity.AnchorType;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.internal.common.facts.CourseEnrollmentRequest;
import org.kuali.student.rules.internal.common.statement.PropositionContainer;
import org.kuali.student.rules.repository.RuleEngineRepository;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutor;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutorInternal;
import org.kuali.student.rules.rulesetexecution.drools.RuleSetExecutorDroolsImpl;
import org.kuali.student.rules.rulesetexecution.runtime.ast.GenerateRuleReport;
import org.kuali.student.rules.util.FactContainer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsibile for extracting rule from the BRMS, Generating the required constraints and excecuting the rules
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
public class EnforceRule {

    @Autowired
    AgendaDiscovery agendaDiscovery;
        
    @Autowired
    private RuleEngineRepository droolsRepository;

    /*public ValidationResult validateLuiPersonRelation(String personID, String luiID, String luiPersonRelationType,
            String relationState) {
        
        RuleSetExecutorInternal executor = new RuleSetExecutorDroolsImpl();
        GenerateRuleReport ruleReportBuilder = new GenerateRuleReport(executor);
        ValidationResult result = new ValidationResult();

        //1. Discover Agenda
        AgendaRequest agendaRequest = new AgendaRequest( luiPersonRelationType, "course", "offered", relationState );
        AnchorType type = new AnchorType( "course", "clu.type.course" );
        //Anchor anchor = new Anchor( luiID.replaceAll("\\s", "_"), luiID, type );
        Anchor anchor = new Anchor( luiID, luiID, type );
        
        Agenda agenda = agendaDiscovery.getAgenda( agendaRequest, anchor );
        
        Iterator<BusinessRuleSet> itr = agenda.getBusinessRules().iterator();
        
        while(itr.hasNext()) {
            BusinessRuleSet rule = itr.next();
            String ruleID = rule.getId();
            
            // 2. Extract compiled rule from drools repository
            //org.drools.rule.Package binPkg = (org.drools.rule.Package) droolsRepository.loadCompiledRuleSet(ruleID);
            String ruleSetSource = droolsRepository.loadRuleSet(ruleID).getContent();

            // 3. Inject facts
            PropositionContainer props =  new PropositionContainer();
            //props.setFunctionalRuleString(rule.getFunctionalRuleString());
            CourseEnrollmentRequest request = new CourseEnrollmentRequest(rule.getBusinessRuleName());
            request.setLuiIds(parseList("CPR 106,CPR 110,CPR 201,Math 105,Art 55"));

            //FactContainer factContainer = new FactContainer(rule.getBusinessRuleName(), request, props);
            FactContainer factContainer = new FactContainer(anchor.getId(), request, props);

            // 4. Execute the compiled rule
            /*try {
                RuleBase rb = RuleBaseFactory.newRuleBase();
                rb.addPackage(binPkg);
                StatelessSession sess = rb.newStatelessSession();
                //sess.execute(factList.toArray());
                sess.execute(factContainer);

            } catch (Exception e) {
                System.out.println("Exception while executing rule:" + rule.getId());
                e.printStackTrace(System.out);
                // Eating exception here. BAD BAD Code!
            }*/
            /*executor.addRuleSet("validateLuiPersonRelation", new StringReader(ruleSetSource));
            executor.execute("validateLuiPersonRelation", Arrays.asList(factContainer));

            ruleReportBuilder.execute(props);
            
            // 5. Process rule outcome
            if (props.getRuleResult()) {
                result.setSuccess(true);
                result.setRuleMessage(props.getRuleReport().getSuccessMessage());
                System.out.println("Rule Report: "+props.getRuleReport().getSuccessMessage());
            } else {
                result.setRuleMessage(props.getRuleReport().getFailureMessage());
                System.out.println("Rule Report: "+props.getRuleReport().getFailureMessage());
            }
            
            // In POC we only process one rule
            break;
        }

        return result;
    }*/

    public ValidationResult validateLuiPersonRelation(String personID,
            String luiID, String luiPersonRelationType, String relationState) {
        RuleSetExecutor executor = new RuleSetExecutorDroolsImpl(this.droolsRepository);
        ValidationResult result = new ValidationResult();

        //1. Discover Agenda
        AgendaRequest agendaRequest = new AgendaRequest( luiPersonRelationType, "course", "offered", relationState );
        AnchorType type = new AnchorType( "course", "clu.type.course" );
        Anchor anchor = new Anchor( luiID, luiID, type );
        Agenda agenda = agendaDiscovery.getAgenda( agendaRequest, anchor );
        
        //2. Setup facts
        CourseEnrollmentRequest request = new CourseEnrollmentRequest(anchor.getId());
        request.setLuiIds(parseList("CPR 106,CPR 110,CPR 201,Math 105,Art 55"));
        FactContainer factContainer = new FactContainer(anchor.getId(), request);

        // 3. Execute Agenda with facts
        executor.execute(agenda, Arrays.asList(factContainer));
        
        PropositionContainer props = factContainer.getPropositionContainer();

        // 4. Process rule outcome
        if (props.getRuleResult()) {
            result.setSuccess(true);
            result.setRuleMessage(props.getRuleReport().getSuccessMessage());
            System.out.println("Rule Report: "+props.getRuleReport().getSuccessMessage());
        } else {
            result.setRuleMessage(props.getRuleReport().getFailureMessage());
            System.out.println("Rule Report: "+props.getRuleReport().getFailureMessage());
        }

        return result;
    }

    /**
     * This method generates a Set of String from a comma separated list of string elements
     * 
     * @param list
     * @return
     */
    private Set<String> parseList(String list) {
        return new HashSet<String>(Arrays.asList(list.split(",")));
    }

    /**
     * @return the droolsRepository
     */
    public RuleEngineRepository getDroolsRepository() {
        return droolsRepository;
    }

    /**
     * @param droolsRepository
     *            the droolsRepository to set
     */
    public void setDroolsRepository(RuleEngineRepository droolsRepository) {
        this.droolsRepository = droolsRepository;
    }

    /**
     * @return the agendaDiscovery
     */
    public AgendaDiscovery getAgendaDiscovery() {
        return agendaDiscovery;
    }

    /**
     * @param agendaDiscovery the agendaDiscovery to set
     */
    public void setAgendaDiscovery(AgendaDiscovery agendaDiscovery) {
        this.agendaDiscovery = agendaDiscovery;
    }

 }
