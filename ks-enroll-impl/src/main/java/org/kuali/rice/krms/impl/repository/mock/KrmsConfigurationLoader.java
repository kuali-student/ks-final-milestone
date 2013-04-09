/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository.mock;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.RelationshipType;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

/**
 *
 * @author nwright
 */
public class KrmsConfigurationLoader {

    private KrmsTypeRepositoryService krmsTypeRepositoryService = null;
    private RuleManagementService ruleManagementService = null;
    private TermRepositoryService termRepositoryService = null;

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (this.krmsTypeRepositoryService == null) {
            this.krmsTypeRepositoryService = new KrmsTypeRepositoryServiceMockImpl();
        }
        return krmsTypeRepositoryService;
    }

    public void setKrmsTypeRepositoryService(KrmsTypeRepositoryService krmsTypeRepositoryService) {
        this.krmsTypeRepositoryService = krmsTypeRepositoryService;
    }

    public RuleManagementService getRuleManagementService() {
        if (this.ruleManagementService == null) {
            this.ruleManagementService = new RuleManagementServiceMockImpl();
        }
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    public TermRepositoryService getTermRepositoryService() {
        if (this.termRepositoryService == null) {
            this.termRepositoryService = new TermRepositoryServiceMockImpl();
        }
        return termRepositoryService;
    }

    public void setTermRepositoryService(TermRepositoryService termRepositoryService) {
        this.termRepositoryService = termRepositoryService;
    }

    public void loadConfiguration() {
        this.loadTypes();
        this.loadTypeRelations();
        this.loadNlUsages();
        this.loadNaturalLanguateTemplates();
        this.loadTermSpecs();
        this.loadTermResolvers();
        this.loadContexts();
    }

    private void loadTypes() {// Contexts
        loadType("10000", "kuali.krms.context.type.course", "KS-SYS", "contextTypeService");
        loadType("10001", "kuali.krms.context.type.program", "KS-SYS", "contextTypeService");
        loadType("10078", "kuali.krms.context.type.course.offering", "KS-SYS", "contextTypeService");
// Agendas
        loadType("10002", "kuali.krms.agenda.type.course.enrollmentEligibility", "KS-SYS", "agendaTypeService");
        loadType("10003", "kuali.krms.agenda.type.course.creditConstraints", "KS-SYS", "agendaTypeService");
        loadType("10004", "kuali.krms.agenda.type.schedule.eligibility", "KS-SYS", "agendaTypeService");
// Rules
        loadType("10005", "kuali.krms.rule.type.course.academicReadiness.antireq", "KS-SYS", "ruleTypeService");
        loadType("10006", "kuali.krms.rule.type.course.academicReadiness.coreq", "KS-SYS", "ruleTypeService");
        loadType("10008", "kuali.krms.rule.type.course.recommendedPreparation", "KS-SYS", "ruleTypeService");
        loadType("10009", "kuali.krms.rule.type.course.academicReadiness.studentEligibility", "KS-SYS", "ruleTypeService");
        loadType("10010", "kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq", "KS-SYS", "ruleTypeService");
        loadType("10011", "kuali.krms.rule.type.course.credit.repeatable", "KS-SYS", "ruleTypeService");
        loadType("10012", "kuali.krms.rule.type.course.credit.restriction", "KS-SYS", "ruleTypeService");
        loadType("10013", "kuali.krms.rule.type.program.completion", "KS-SYS", "ruleTypeService");
        loadType("10014", "kuali.krms.rule.type.program.entrance", "KS-SYS", "ruleTypeService");
        loadType("10015", "kuali.krms.rule.type.program.satisfactoryProgress", "KS-SYS", "ruleTypeService");
        loadType("10016", "kuali.krms.rule.type.schedule.eligibility", "KS-SYS", "ruleTypeService");
// Propositions
        loadType("10017", "kuali.krms.proposition.type.success.compl.course", "KS-SYS", "simplePropositionTypeService");
        loadType("10018", "kuali.krms.proposition.type.success.course.courseset.completed.all", "KS-SYS", "simplePropositionTypeService");
        loadType("10019", "kuali.krms.proposition.type.success.course.courseset.completed.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10020", "kuali.krms.proposition.type.course.courseset.completed.none", "KS-SYS", "simplePropositionTypeService");
        loadType("10021", "kuali.krms.proposition.type.course.courseset.credits.completed.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10022", "kuali.krms.proposition.type.course.courseset.credits.completed.none", "KS-SYS", "simplePropositionTypeService");
        loadType("10023", "kuali.krms.proposition.type.course.courseset.enrolled.all", "KS-SYS", "simplePropositionTypeService");
        loadType("10024", "kuali.krms.proposition.type.course.courseset.enrolled.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10025", "kuali.krms.proposition.type.course.courseset.gpa.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10026", "kuali.krms.proposition.type.course.courseset.grade.max", "KS-SYS", "simplePropositionTypeService");
        loadType("10027", "kuali.krms.proposition.type.course.courseset.grade.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10028", "kuali.krms.proposition.type.course.courseset.nof.grade.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10029", "kuali.krms.proposition.type.course.credits.repeat.max", "KS-SYS", "simplePropositionTypeService");
        loadType("10030", "kuali.krms.proposition.type.course.enrolled", "KS-SYS", "simplePropositionTypeService");
        loadType("10031", "kuali.krms.proposition.type.freeform.text", "KS-SYS", "simplePropositionTypeService");
        loadType("10032", "kuali.krms.proposition.type.course.notcompleted", "KS-SYS", "simplePropositionTypeService");
        loadType("10033", "kuali.krms.proposition.type.admitted.to.program.campus", "KS-SYS", "simplePropositionTypeService");
        loadType("10034", "kuali.krms.proposition.type.permission.instructor.required", "KS-SYS", "simplePropositionTypeService");
        loadType("10035", "kuali.krms.proposition.type.permission.admin.org", "KS-SYS", "simplePropositionTypeService");
        loadType("10036", "kuali.krms.proposition.type.notadmitted.to.program", "KS-SYS", "simplePropositionTypeService");
        loadType("10037", "kuali.krms.proposition.type.course.test.score.max", "KS-SYS", "simplePropositionTypeService");
        loadType("10038", "kuali.krms.proposition.type.course.test.score.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10039", "kuali.krms.proposition.type.credits.earned.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10040", "kuali.krms.proposition.type.cumulative.gpa.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10041", "kuali.krms.proposition.type.duration.cumulative.gpa.min", "KS-SYS", "simplePropositionTypeService");
        loadType("10042", "kuali.krms.proposition.type.drop.min.credit.hours.due.to.attribute", "KS-SYS", "simplePropositionTypeService");
        loadType("10043", "kuali.krms.proposition.type.drop.min.credit.hours", "KS-SYS", "simplePropositionTypeService");
        loadType("10044", "kuali.krms.proposition.type.exceeds.minutes.overlap.allowed", "KS-SYS", "simplePropositionTypeService");
        loadType("10045", "kuali.krms.proposition.type.time.conflict.start.end", "KS-SYS", "simplePropositionTypeService");
        loadType("10046", "kuali.krms.proposition.type.max.limit.courses.for.program", "KS-SYS", "simplePropositionTypeService");
        loadType("10047", "kuali.krms.proposition.type.max.limit.credits.for.program", "KS-SYS", "simplePropositionTypeService");
        loadType("10050", "kuali.krms.proposition.type.max.limit.courses.for.campus.duration", "KS-SYS", "simplePropositionTypeService");
        loadType("10051", "kuali.krms.proposition.type.max.limit.credits.for.campus.duration", "KS-SYS", "simplePropositionTypeService");
        loadType("10052", "kuali.krms.proposition.type.admitted.to.program", "KS-SYS", "simplePropositionTypeService");
        loadType("10053", "kuali.krms.proposition.type.course.courseset.completed.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10054", "kuali.krms.proposition.type.success.credit.courseset.completed.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10055", "kuali.krms.proposition.type.success.credits.courseset.completed.nof.org", "KS-SYS", "simplePropositionTypeService");
        loadType("10056", "kuali.krms.proposition.type.cant.add.to.activity.offering.due.to.state", "KS-SYS", "simplePropositionTypeService");
        loadType("10057", "kuali.krms.proposition.type.no.repeat.course", "KS-SYS", "simplePropositionTypeService");
        loadType("10058", "kuali.krms.proposition.type.no.repeat.courses", "KS-SYS", "simplePropositionTypeService");
        loadType("10059", "kuali.krms.proposition.type.avail.seat", "KS-SYS", "simplePropositionTypeService");
        loadType("10060", "kuali.krms.proposition.type.success.compl.course.as.of.term", "KS-SYS", "simplePropositionTypeService");
        loadType("10061", "kuali.krms.proposition.type.success.compl.prior.to.term", "KS-SYS", "simplePropositionTypeService");
        loadType("10062", "kuali.krms.proposition.type.success.compl.course.between.terms", "KS-SYS", "simplePropositionTypeService");
        loadType("10064", "kuali.krms.proposition.type.notadmitted.to.program.in.class.standing", "KS-SYS", "simplePropositionTypeService");
        loadType("10065", "kuali.krms.proposition.type.admitted.to.program.org", "KS-SYS", "simplePropositionTypeService");
        loadType("10066", "kuali.krms.proposition.type.in.class.standing", "KS-SYS", "simplePropositionTypeService");
        loadType("10067", "kuali.krms.proposition.type.greater.than.class.standing", "KS-SYS", "simplePropositionTypeService");
        loadType("10068", "kuali.krms.proposition.type.less.than.class.standing", "KS-SYS", "simplePropositionTypeService");
        loadType("10069", "kuali.krms.proposition.type.notin.class.standing", "KS-SYS", "simplePropositionTypeService");
        loadType("10071", "kuali.krms.proposition.type.course.courseset.enrolled", "KS-SYS", "simplePropositionTypeService");
        loadType("10072", "kuali.krms.proposition.type.no.repeat.course.nof", "KS-SYS", "simplePropositionTypeService");
        loadType("10074", "kuali.krms.proposition.type.test.score.between.values", "KS-SYS", "simplePropositionTypeService");
        loadType("10075", "kuali.krms.proposition.type.test.score", "KS-SYS", "simplePropositionTypeService");
        loadType("10076", "kuali.krms.proposition.type.compound.and", "KS-SYS", "compoundPropositionTypeService");
        loadType("10077", "kuali.krms.proposition.type.compound.or", "KS-SYS", "compoundPropositionTypeService");
// Parameters
        loadType("10100", "kuali.krms.proposition.parameter.type.term.number.of.completed.courses", "KS-SYS", "termPropositionParameterTypeService");
        loadType("10101", "kuali.krms.proposition.parameter.type.operator.less.than.or.equal.to", "KS-SYS", "operatorPropositionParameterTypeService");
        loadType("10102", "kuali.krms.proposition.parameter.type.constant.value.n", "KS-SYS", "constantPropositionParameterTypeService");
        // term parameters
        loadType("10103", "kuali.term.parameter.type.course.clu.id", "KS-SYS", "termParameterTypeService");
        loadType("10104", "kuali.term.parameter.type.course.cluSet.id", "KS-SYS", "termParameterTypeService");
        loadType("10105", "kuali.term.parameter.type.free.text", "KS-SYS", "termParameterTypeService");
        loadType("10106", "kuali.term.parameter.type.grade.id", "KS-SYS", "termParameterTypeService");
        loadType("10107", "kuali.term.parameter.type.org.id", "KS-SYS", "termParameterTypeService");
        loadType("10108", "kuali.term.parameter.type.program.cluSet.id", "KS-SYS", "termParameterTypeService");
    }

    private void loadType(String id, String name, String nameSpace, String serviceName) {
        KrmsTypeDefinition.Builder bldr = KrmsTypeDefinition.Builder.create(name, nameSpace);
        bldr.setId(id);
        bldr.setActive(true);
        bldr.setServiceName(serviceName);
        this.getKrmsTypeRepositoryService().createKrmsType(bldr.build());
    }

    private void loadTypeRelations() {// agendas for contexts
        loadTypeRelation("10000", "10000", "10002", "A", 1);
        loadTypeRelation("10001", "10000", "10003", "A", 6);
        loadTypeRelation("10010", "10001", "10014", "A", 1);
        loadTypeRelation("10011", "10001", "10015", "A", 2);
        loadTypeRelation("10009", "10001", "10013", "A", 3);
// rules for agendas
        loadTypeRelation("10006", "10002", "10010", "A", 2);
        loadTypeRelation("10003", "10002", "10006", "A", 3);
        loadTypeRelation("10005", "10002", "10008", "A", 4);
        loadTypeRelation("10002", "10002", "10005", "A", 5);
        loadTypeRelation("10008", "10003", "10012", "A", 7);
        loadTypeRelation("10007", "10003", "10011", "A", 8);
        loadTypeRelation("10012", "10004", "10016", "A", 2);
// propositions for rules
        loadTypeRelation("10030", "10005", "10021", "A", 1);
        loadTypeRelation("10057", "10005", "10032", "A", 1);
        loadTypeRelation("10026", "10005", "10020", "A", 2);
        loadTypeRelation("10033", "10005", "10022", "A", 3);
        loadTypeRelation("10067", "10005", "10037", "A", 5);
        loadTypeRelation("10055", "10006", "10031", "A", 0);
        loadTypeRelation("10054", "10006", "10030", "A", 1);
        loadTypeRelation("10036", "10006", "10024", "A", 2);
        loadTypeRelation("10035", "10006", "10023", "A", 3);
        loadTypeRelation("10014", "10008", "10017", "A", 1);
        loadTypeRelation("10059", "10009", "10033", "A", 2);
        loadTypeRelation("10065", "10009", "10036", "A", 3);
        loadTypeRelation("10056", "10010", "10031", "A", 0);
        loadTypeRelation("10015", "10010", "10017", "A", 1);
        loadTypeRelation("10114", "10010", "10040", "A", 1);
        loadTypeRelation("10116", "10010", "10052", "A", 1);
        loadTypeRelation("10113", "10010", "10054", "A", 1);
        loadTypeRelation("10115", "10010", "10055", "A", 1);
        loadTypeRelation("10019", "10010", "10018", "A", 2);
        loadTypeRelation("10023", "10010", "10019", "A", 3);
        loadTypeRelation("10038", "10010", "10025", "A", 6);
        loadTypeRelation("10045", "10010", "10027", "A", 7);
        loadTypeRelation("10049", "10010", "10028", "A", 8);
        loadTypeRelation("10042", "10010", "10026", "A", 9);
        loadTypeRelation("10060", "10010", "10033", "A", 11);
        loadTypeRelation("10066", "10010", "10036", "A", 12);
        loadTypeRelation("10062", "10010", "10034", "A", 13);
        loadTypeRelation("10064", "10010", "10035", "A", 14);
        loadTypeRelation("10070", "10010", "10038", "A", 15);
        loadTypeRelation("10108", "10010", "10074", "A", 15);
        loadTypeRelation("10111", "10010", "10075", "A", 15);
        loadTypeRelation("10053", "10011", "10029", "A", 1);
        loadTypeRelation("10058", "10012", "10032", "A", 1);
        loadTypeRelation("10027", "10012", "10020", "A", 2);
        loadTypeRelation("10039", "10013", "10025", "A", 4);
        loadTypeRelation("10072", "10013", "10039", "A", 5);
        loadTypeRelation("10024", "10013", "10019", "A", 10);
        loadTypeRelation("10020", "10013", "10018", "A", 11);
        loadTypeRelation("10031", "10013", "10021", "A", 12);
        loadTypeRelation("10046", "10013", "10027", "A", 13);
        loadTypeRelation("10050", "10013", "10028", "A", 14);
        loadTypeRelation("10016", "10013", "10017", "A", 16);
        loadTypeRelation("10073", "10013", "10040", "A", 18);
        loadTypeRelation("10040", "10014", "10025", "A", 5);
        loadTypeRelation("10071", "10014", "10038", "A", 6);
        loadTypeRelation("10109", "10014", "10074", "A", 6);
        loadTypeRelation("10112", "10014", "10075", "A", 6);
        loadTypeRelation("10025", "10014", "10019", "A", 9);
        loadTypeRelation("10028", "10014", "10020", "A", 10);
        loadTypeRelation("10021", "10014", "10018", "A", 11);
        loadTypeRelation("10032", "10014", "10021", "A", 12);
        loadTypeRelation("10034", "10014", "10022", "A", 13);
        loadTypeRelation("10068", "10014", "10037", "A", 15);
        loadTypeRelation("10047", "10014", "10027", "A", 16);
        loadTypeRelation("10051", "10014", "10028", "A", 17);
        loadTypeRelation("10043", "10014", "10026", "A", 18);
        loadTypeRelation("10017", "10014", "10017", "A", 19);
        loadTypeRelation("10052", "10015", "10028", "A", 5);
        loadTypeRelation("10074", "10015", "10040", "A", 6);
        loadTypeRelation("10075", "10015", "10041", "A", 7);
        loadTypeRelation("10076", "10016", "10042", "A", 2);
        loadTypeRelation("10077", "10016", "10043", "A", 2);
        loadTypeRelation("10078", "10016", "10044", "A", 2);
        loadTypeRelation("10079", "10016", "10045", "A", 2);
        loadTypeRelation("10080", "10016", "10046", "A", 2);
        loadTypeRelation("10081", "10016", "10047", "A", 2);
        loadTypeRelation("10084", "10016", "10050", "A", 2);
        loadTypeRelation("10085", "10016", "10051", "A", 2);
        loadTypeRelation("10086", "10016", "10052", "A", 2);
        loadTypeRelation("10087", "10016", "10053", "A", 2);
        loadTypeRelation("10088", "10016", "10054", "A", 2);
        loadTypeRelation("10089", "10016", "10055", "A", 2);
        loadTypeRelation("10090", "10016", "10056", "A", 2);
        loadTypeRelation("10091", "10016", "10057", "A", 2);
        loadTypeRelation("10092", "10016", "10058", "A", 2);
        loadTypeRelation("10093", "10016", "10059", "A", 2);
        loadTypeRelation("10094", "10016", "10060", "A", 2);
        loadTypeRelation("10095", "10016", "10061", "A", 2);
        loadTypeRelation("10096", "10016", "10062", "A", 2);
        loadTypeRelation("10098", "10016", "10064", "A", 2);
        loadTypeRelation("10099", "10016", "10065", "A", 2);
        loadTypeRelation("10100", "10016", "10066", "A", 2);
        loadTypeRelation("10101", "10016", "10067", "A", 2);
        loadTypeRelation("10102", "10016", "10068", "A", 2);
        loadTypeRelation("10103", "10016", "10069", "A", 2);
        loadTypeRelation("10105", "10016", "10071", "A", 2);
        loadTypeRelation("10106", "10016", "10072", "A", 2);
// parameters for propositions
        loadTypeRelation("10019-A-10100", "10019", "10100", "A", 1);
        loadTypeRelation("10019-A-10101", "10019", "10101", "A", 2);
        loadTypeRelation("10019-A-10102", "10019", "10102", "A", 3);
        // TERM PARAMETER
        loadTypeRelation("10100-A-10104", "10100", "10104", "A", 1);
    }

    private void loadTypeRelation(String id, String fromTypeId, String toTypeId, String relType, Integer sequenceNumber) {
        RelationshipType relationshipType = RelationshipType.USAGE_ALLOWED;
        if (!relType.equals("A")) {
            relationshipType = RelationshipType.UNKNOWN;
        }
        TypeTypeRelation.Builder bldr = TypeTypeRelation.Builder.create(fromTypeId, relationshipType, sequenceNumber, toTypeId);
        bldr.setId(id);
        bldr.setActive(true);
        this.getKrmsTypeRepositoryService().createTypeTypeRelation(bldr.build());
    }

    private void loadNlUsages() {
        loadNlUsage("10000", "kuali.krms.edit", "KS-SYS", "Kuali Rule Edit");
        loadNlUsage("10001", "kuali.krms.composition", "KS-SYS", "Kuali Rule Composition");
        loadNlUsage("10002", "kuali.krms.example", "KS-SYS", "Kuali Rule Example");
        loadNlUsage("10003", "kuali.krms.preview", "KS-SYS", "Kuali Rule Preview");
        loadNlUsage("10004", "kuali.krms.type.description", "KS-SYS", "Kuali Rule Type Description");
        loadNlUsage("10005", "kuali.krms.catalog", "KS-SYS", "Kuali Rule Catalog");
        loadNlUsage("10006", "kuali.krms.type.instruction", "KS-SYS", "Kuali Rule Type Instructions");
    }

    private void loadNlUsage(String id, String name, String nameSpace, String description) {
        NaturalLanguageUsage.Builder bldr = NaturalLanguageUsage.Builder.create(name, nameSpace);
        bldr.setId(id);
        bldr.setActive(true);
        bldr.setDescription(description);
        this.getRuleManagementService().createNaturalLanguageUsage(bldr.build());
    }

    private void loadNaturalLanguateTemplates() {
        // descriptions
        loadNaturalLanguageTemplate("10000-10004-en", "en", "10004", "10000", "Course Requirements", "", "");
        loadNaturalLanguageTemplate("10001-10004-en", "en", "10004", "10001", "Program Requirements", "", "");
        loadNaturalLanguageTemplate("10078-10004-en", "en", "10004", "10078", "Course Offering Requirements", "", "");
        loadNaturalLanguageTemplate("10002-10004-en", "en", "10004", "10002", "Enrollment Eligibility", "", "");
        loadNaturalLanguageTemplate("10003-10004-en", "en", "10004", "10003", "Credit Constraints", "", "");
        loadNaturalLanguageTemplate("10004-10004-en", "en", "10004", "10004", "Schedule Eligibility", "", "");
        loadNaturalLanguageTemplate("10005-10004-en", "en", "10004", "10005", "Anti-requisite", "", "");
        loadNaturalLanguageTemplate("10006-10004-en", "en", "10004", "10006", "Co-requisite", "", "");
        loadNaturalLanguageTemplate("10008-10004-en", "en", "10004", "10008", "Recommended Preparation", "", "");
        loadNaturalLanguageTemplate("10009-10004-en", "en", "10004", "10009", "Student Eligiblity", "", "");
        loadNaturalLanguageTemplate("10010-10004-en", "en", "10004", "10010", "Student Eligibility and Prerequisites", "", "");
        loadNaturalLanguageTemplate("10011-10004-en", "en", "10004", "10011", "Repeatable for Credit", "", "");
        loadNaturalLanguageTemplate("10012-10004-en", "en", "10004", "10012", "Restrictions on Credit", "", "");
        loadNaturalLanguageTemplate("10013-10004-en", "en", "10004", "10013", "Completion Requirements", "", "");
        loadNaturalLanguageTemplate("10014-10004-en", "en", "10004", "10014", "Entrance Requirements", "", "");
        loadNaturalLanguageTemplate("10015-10004-en", "en", "10004", "10015", "Satisfactory Progress", "", "");
        loadNaturalLanguageTemplate("10016-10004-en", "en", "10004", "10016", "Schedule Eligibility", "", "");

        loadNaturalLanguageTemplate("10021", "en", "10002", "10019", "Must have successfully completed a minimum of 2 courses from (MATH140,MATH111,STAT100)", "", "");
        loadNaturalLanguageTemplate("10100-10002-en", "en", "10002", "10100", "NumberOfCompletedCourses", "", "");
        loadNaturalLanguageTemplate("10101-10002-en", "en", "10002", "10101", "less than or equal to", "", "");
        loadNaturalLanguageTemplate("10102-10002-en", "en", "10002", "10102", "1", "", "");
        loadNaturalLanguageTemplate("10104-10002-en", "en", "10002", "10104", "A 32 digit UUID or sequence number", "", "");

        loadNaturalLanguageTemplate("10090", "en", "10004", "10019", "Must have successfully completed a minimum of <n> courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10100-10004-en", "en", "10004", "10100", "A term that resolves into the number of courses that a student has completed from a specified set of courses", "", "");
        loadNaturalLanguageTemplate("10101-10004-en", "en", "10004", "10101", "Less than or equal to comparison operator", "", "");
        loadNaturalLanguageTemplate("10102-10004-en", "en", "10004", "10102", "A constant numeric value N", "", "");
        loadNaturalLanguageTemplate("10104-10004-en", "en", "10004", "10104", "The ID of a course set", "", "");

        loadNaturalLanguageTemplate("10375", "en", "10000", "10019", "#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from $courseCluSet.getCluSetAsCode()#end", "", "");
        loadNaturalLanguageTemplate("10100-10000-en", "en", "10000", "10100", "NumberOfCompletedCourses", "", "");
        loadNaturalLanguageTemplate("10101-10000-en", "en", "10000", "10101", "<=", "", "");
        loadNaturalLanguageTemplate("10102-10000-en", "en", "10000", "10102", "1", "KRMS-NumberOfCourses-ConstantValue", "");
        loadNaturalLanguageTemplate("10104-10000-en", "en", "10000", "10104", "IGNORE THIS", "KRMS-MultiCourse-Section", "org.kuali.student.enrollment.class1.krms.builder.MultiCourseComponentBuilder");

        loadNaturalLanguageTemplate("10336", "en", "10003", "10019", "#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from#end", "", "");








        loadNaturalLanguageTemplate("10000", "en", "10002", "10056", "Student cannot add Activity Offering with Draft of Cancelled", "", "");
        loadNaturalLanguageTemplate("10001", "en", "10002", "10057", "May not repeat MATH140", "", "");
        loadNaturalLanguageTemplate("10002", "en", "10002", "10058", "May not repeat any of (MATH140, MATH241)", "", "");
        loadNaturalLanguageTemplate("10003", "en", "10002", "10034", "Permission of instructor required", "", "");
        loadNaturalLanguageTemplate("10004", "en", "10002", "10031", "Free Form Text", "", "");
        loadNaturalLanguageTemplate("10006", "en", "10002", "10035", "Student cannot drop course without permission of Music Dept", "", "");
        loadNaturalLanguageTemplate("10007", "en", "10002", "10042", "If student is Athlete AND upon drop total credit hours would be less than 12 credit hours, prevent drop", "", "");
        loadNaturalLanguageTemplate("10008", "en", "10002", "10043", "If upon drop, total credit hours would be less than 12 credit hours, prevent course drop", "", "");
        loadNaturalLanguageTemplate("10009", "en", "10002", "10044", "Course has more than 10 minutes overlap with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10010", "en", "10002", "10045", "Course has less than 5 minutes between start time or end time with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10011", "en", "10002", "10046", "Student in Music Performance can enrol in a maximum of 3 courses for the term", "", "");
        loadNaturalLanguageTemplate("10012", "en", "10002", "10047", "Student in Music Performance can enrol in a maximum of 12 credits for the term", "", "");
        loadNaturalLanguageTemplate("10015", "en", "10002", "10059", "Student is in an existing seat pool for the course with an available seat", "", "");
        loadNaturalLanguageTemplate("10016", "en", "10002", "10017", "Must have successfully completed MATH140", "", "");
        loadNaturalLanguageTemplate("10017", "en", "10002", "10060", "Must have successfully completed MATH140 as of <term>", "", "");
        loadNaturalLanguageTemplate("10018", "en", "10002", "10061", "Must have successfully completed MATH140 prior to <term>", "", "");
        loadNaturalLanguageTemplate("10019", "en", "10002", "10062", "Must have successfully completed MATH140 between <term1> and <term2>", "", "");
        loadNaturalLanguageTemplate("10022", "en", "10002", "10018", "Must have successfully completed all courses from (MATH140,STAT100)", "", "");
        loadNaturalLanguageTemplate("10023", "en", "10002", "10054", "Must have successfully completed a minimum of 3 credits from MATH140", "", "");
        loadNaturalLanguageTemplate("10024", "en", "10002", "10055", "Must have successfully completed a minimum of 3 credits from courses in the Music Dept", "", "");
        loadNaturalLanguageTemplate("10025", "en", "10002", "10040", "Must have earned a minimum cumulative GPA of 3.12", "", "");
        loadNaturalLanguageTemplate("10026", "en", "10002", "10041", "Must have earned a minimum Cumulative GPA of 3.12 in Fall 2012", "", "");
        loadNaturalLanguageTemplate("10027", "en", "10002", "10025", "Must have earned a minimum GPA of 2.5  in (MATH140,MATH111)", "", "");
        loadNaturalLanguageTemplate("10028", "en", "10002", "10027", "Must have earned a minimum grade of Letter B in (MATH140,MATH111)", "", "");
        loadNaturalLanguageTemplate("10029", "en", "10002", "10028", "Must successfully complete a minimum of 2 courses  from (MATH140,MATH111,STAT100) with a minimum grade of Letter C", "", "");
        loadNaturalLanguageTemplate("10030", "en", "10002", "10026", "Must not have earned a grade of Letter D or higher in (MATH140,MATH111)", "", "");
        loadNaturalLanguageTemplate("10031", "en", "10002", "10038", "Must have achieved a minimum score of <score> on <tests>", "", "");
        loadNaturalLanguageTemplate("10032", "en", "10002", "10037", "Must have achieved a score no higher than <score> on <tests>", "", "");
        loadNaturalLanguageTemplate("10033", "en", "10002", "10032", "Must not have successfully completed MATH111", "", "");
        loadNaturalLanguageTemplate("10034", "en", "10002", "10020", "Must not have successfully completed any courses from MATH140", "", "");
        loadNaturalLanguageTemplate("10035", "en", "10002", "10022", "Must not have successfully completed any credits from (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10036", "en", "10002", "10021", "Must successfully complete no more than 4 credits from (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10037", "en", "10002", "10053", "Must have successfully completed no more than 2 courses from (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10038", "en", "10002", "10030", "Must be concurrently enrolled in MATH140", "", "");
        loadNaturalLanguageTemplate("10039", "en", "10002", "10024", "Must be concurrently enrolled in a minimum of 2 courses from (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10040", "en", "10002", "10023", "Must be concurrently enrolled in all courses from (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10041", "en", "10002", "10039", "Must have earned a minimum of 6 total credits", "", "");
        loadNaturalLanguageTemplate("10042", "en", "10002", "10029", "May be repeater for a maximum of 8 credits", "", "");
        loadNaturalLanguageTemplate("10043", "en", "10002", "10050", "Students admitted to South Campus may take no more than 3 courses at North Campus in 1 Year", "", "");
        loadNaturalLanguageTemplate("10044", "en", "10002", "10051", "Students admitted to North Campus may take no more than 8 credits at South Campus in 1 Year", "", "");
        loadNaturalLanguageTemplate("10045", "en", "10002", "10052", "Must have been admitted to the Sociology Program", "", "");
        loadNaturalLanguageTemplate("10046", "en", "10002", "10033", "Must be admitted to any Program offered at the course campus location", "", "");
        loadNaturalLanguageTemplate("10047", "en", "10002", "10036", "Must not have been admitted to the Sociology Program", "", "");
        loadNaturalLanguageTemplate("10048", "en", "10002", "10064", "Must not have been admitted to the Sociology Program with a class standing of <Class Standing>", "", "");
        loadNaturalLanguageTemplate("10049", "en", "10002", "10065", "Must have been admitted to a Program offered by Music Dept", "", "");
        loadNaturalLanguageTemplate("10050", "en", "10002", "10066", "Student must be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10051", "en", "10002", "10067", "Student must be in a class standing of <class standing> or greater", "", "");
        loadNaturalLanguageTemplate("10052", "en", "10002", "10068", "Student must be in a class standing of <class standing> or less", "", "");
        loadNaturalLanguageTemplate("10053", "en", "10002", "10069", "Must not be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10055", "en", "10002", "10071", "Must be concurrently enrolled in (MATH111,MATH140)", "", "");
        loadNaturalLanguageTemplate("10056", "en", "10002", "10072", "May not repeat MATH111 if repeated 2 times", "", "");
        loadNaturalLanguageTemplate("10057", "en", "10002", "10074", "Must have achieved a score between <score> and <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10058", "en", "10002", "10075", "Must have achieved a score of <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10059", "en", "10004", "10002", "Enrollment Eligibility", "", "");
        loadNaturalLanguageTemplate("10060", "en", "10004", "10003", "Credit Constraints", "", "");
        loadNaturalLanguageTemplate("10061", "en", "10004", "10005", "Antirequisite", "", "");
        loadNaturalLanguageTemplate("10062", "en", "10004", "10006", "Corequisite", "", "");
        loadNaturalLanguageTemplate("10064", "en", "10004", "10009", "Student Eligibility", "", "");
        loadNaturalLanguageTemplate("10065", "en", "10004", "10008", "Recommended Preparation", "", "");
        loadNaturalLanguageTemplate("10066", "en", "10004", "10010", "Student Eligibility & Prerequisite", "", "");
        loadNaturalLanguageTemplate("10067", "en", "10004", "10011", "Repeatable for Credit", "", "");
        loadNaturalLanguageTemplate("10068", "en", "10004", "10012", "Restricted for Credit", "", "");
        loadNaturalLanguageTemplate("10069", "en", "10004", "10056", "Student cannot add Activity Offering with <Activity Offering State>  of <state>", "", "");
        loadNaturalLanguageTemplate("10070", "en", "10004", "10057", "May not repeat <course>", "", "");
        loadNaturalLanguageTemplate("10071", "en", "10004", "10058", "May not repeat any of  <courses>", "", "");
        loadNaturalLanguageTemplate("10072", "en", "10004", "10034", "Permission of instructor required", "", "");
        loadNaturalLanguageTemplate("10073", "en", "10004", "10031", "Free Form Text", "", "");
        loadNaturalLanguageTemplate("10075", "en", "10004", "10035", "Student cannot drop course without permission of <administering org>", "", "");
        loadNaturalLanguageTemplate("10076", "en", "10004", "10042", "If student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop", "", "");
        loadNaturalLanguageTemplate("10077", "en", "10004", "10043", "If upon drop, total credit hours would be less than <min credit hours>, prevent course drop", "", "");
        loadNaturalLanguageTemplate("10078", "en", "10004", "10044", "Course has more than <n> minutes overlap with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10079", "en", "10004", "10045", "Course has less than <n> minutes between start time or end time with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10080", "en", "10004", "10046", "Student in <Program> can enrol in a maximum of <n> courses for the term", "", "");
        loadNaturalLanguageTemplate("10081", "en", "10004", "10047", "Student in <Program> can enrol in a maximum of <n> credits for the term", "", "");
        loadNaturalLanguageTemplate("10084", "en", "10004", "10059", "Student is in an existing seat pool for the course with an available seat", "", "");
        loadNaturalLanguageTemplate("10085", "en", "10004", "10017", "Must have successfully completed <course>", "", "");
        loadNaturalLanguageTemplate("10086", "en", "10004", "10060", "Must have successfully completed <course> as of <term>", "", "");
        loadNaturalLanguageTemplate("10087", "en", "10004", "10061", "Must have successfully completed <course> prior to <term>", "", "");
        loadNaturalLanguageTemplate("10088", "en", "10004", "10062", "Must have successfully completed <course> between <term1> and <term2>", "", "");
        loadNaturalLanguageTemplate("10091", "en", "10004", "10018", "Must have successfully completed all courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10092", "en", "10004", "10054", "Must have successfully completed a minimum of <n> credits from <course>", "", "");
        loadNaturalLanguageTemplate("10093", "en", "10004", "10055", "Must have successfully completed a minimum of <n> credits from courses in the <org>", "", "");
        loadNaturalLanguageTemplate("10094", "en", "10004", "10040", "Must have earned a minimum cumulative GPA of <GPA>", "", "");
        loadNaturalLanguageTemplate("10095", "en", "10004", "10041", "Must have earned a minimum Cumulative GPA of <GPA> in <duration>", "", "");
        loadNaturalLanguageTemplate("10096", "en", "10004", "10025", "Must have earned a minimum GPA of <GPA>  in <courses>", "", "");
        loadNaturalLanguageTemplate("10097", "en", "10004", "10027", "Must have earned a minimum grade of <gradeType> <grade> in  <courses>", "", "");
        loadNaturalLanguageTemplate("10098", "en", "10004", "10028", "Must successfully complete a minimum of <n> courses  from <courses> with a minimum grade of <gradeType> <grade>", "", "");
        loadNaturalLanguageTemplate("10099", "en", "10004", "10026", "Must not have earned a grade of <gradeType> <grade> or higher in <courses>", "", "");
        loadNaturalLanguageTemplate("10100", "en", "10004", "10038", "Must have achieved a minimum score of <score> on <tests>", "", "");
        loadNaturalLanguageTemplate("10101", "en", "10004", "10037", "Must have achieved a score no higher than <score> on <tests>", "", "");
        loadNaturalLanguageTemplate("10102", "en", "10004", "10032", "Must not have successfully completed <course>", "", "");
        loadNaturalLanguageTemplate("10103", "en", "10004", "10020", "Must not have successfully completed any courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10104", "en", "10004", "10022", "Must not have successfully completed any credits from <courses>", "", "");
        loadNaturalLanguageTemplate("10105", "en", "10004", "10021", "Must successfully complete no more than  <n> credits from <courses>", "", "");
        loadNaturalLanguageTemplate("10106", "en", "10004", "10053", "Must have successfully completed no more than <n> courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10107", "en", "10004", "10030", "Must be concurrently enrolled in <course>", "", "");
        loadNaturalLanguageTemplate("10108", "en", "10004", "10024", "Must be concurrently enrolled in a minimum of <n> courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10109", "en", "10004", "10023", "Must be concurrently enrolled in all courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10110", "en", "10004", "10039", "Must have earned a minimum of <n> total credits", "", "");
        loadNaturalLanguageTemplate("10111", "en", "10004", "10029", "May be repeater for a maximum of <n> credits", "", "");
        loadNaturalLanguageTemplate("10112", "en", "10004", "10050", "Students admitted to <campus> may take no more than <n> courses at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10113", "en", "10004", "10051", "Students admitted to <campus> may take no more than <n  credits> at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10114", "en", "10004", "10052", "Must have been admitted to the <Program> Program", "", "");
        loadNaturalLanguageTemplate("10115", "en", "10004", "10033", "Must be admitted to any Program offered at the course campus location", "", "");
        loadNaturalLanguageTemplate("10116", "en", "10004", "10036", "Must not have been admitted to the <Program> Program", "", "");
        loadNaturalLanguageTemplate("10117", "en", "10004", "10064", "Must not have been admitted to the <Program> Program with a class standing of <Class Standing>", "", "");
        loadNaturalLanguageTemplate("10118", "en", "10004", "10065", "Must have been admitted to a Program offered by <Org>", "", "");
        loadNaturalLanguageTemplate("10119", "en", "10004", "10066", "Student must be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10120", "en", "10004", "10067", "Student must be in a class standing of <class standing> or greater", "", "");
        loadNaturalLanguageTemplate("10121", "en", "10004", "10068", "Student must be in a class standing of <class standing> or less", "", "");
        loadNaturalLanguageTemplate("10122", "en", "10004", "10069", "Must not be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10124", "en", "10004", "10071", "Must be concurrently enrolled in <courses>", "", "");
        loadNaturalLanguageTemplate("10125", "en", "10004", "10072", "May not repeat <course> if repeated <n> times", "", "");
        loadNaturalLanguageTemplate("10126", "en", "10004", "10074", "Must have achieved a score between <score> and <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10127", "en", "10004", "10075", "Must have achieved a score of <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10128", "en", "10000", "10035", "Permission of $org.getLongName() required", "", "");
        loadNaturalLanguageTemplate("10129", "en", "10000", "10034", "Permission of instructor required", "", "");
        loadNaturalLanguageTemplate("10130", "en", "10000", "10038", "Must have achieved a minimum score of $fields.get('kuali.reqComponent.field.type.test.score') on $testCluSet.getCluSetAsLongName()", "", "");
        loadNaturalLanguageTemplate("10131", "en", "10000", "10037", "Must have achieved a score no higher than $fields.get('kuali.reqComponent.field.type.test.score') on $testCluSet.getCluSetAsLongName()", "", "");
        loadNaturalLanguageTemplate("10132", "en", "10000", "10028", "Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") with a minimum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade from $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10133", "en", "10000", "10018", "#if($courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed all courses from $courseCluSet.getCluSetAsCode()#end", "", "");
        loadNaturalLanguageTemplate("10169", "en", "10000", "10033", "Must be admitted to any program offered at the course campus location", "", "");
        loadNaturalLanguageTemplate("10180", "en", "10000", "10036", "Must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program", "", "");
        loadNaturalLanguageTemplate("10181", "en", "10000", "10029", "May be repeated for a maximum of $intValue credits", "", "");
        loadNaturalLanguageTemplate("10182", "en", "10000", "10055", "Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"credit\") from courses in the $org.getLongName()", "", "");
        loadNaturalLanguageTemplate("10183", "en", "10000", "10052", "Must have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program", "", "");
        loadNaturalLanguageTemplate("10184", "en", "10000", "10017", "Must have successfully completed $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10185", "en", "10000", "10030", "Must be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10186", "en", "10000", "10032", "Must not have successfully completed $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10187", "en", "10000", "10053", "#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from $courseCluSet.getCluSetAsCode()#end", "", "");
        loadNaturalLanguageTemplate("10193", "en", "10001", "10039", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits>", "", "");
        loadNaturalLanguageTemplate("10194", "en", "10001", "10047", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Maximum Number of Credits>", "", "");
        loadNaturalLanguageTemplate("10197", "en", "10001", "10020", "<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10203", "en", "10001", "10053", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10204", "en", "10001", "10040", "<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>", "", "");
        loadNaturalLanguageTemplate("10205", "en", "10001", "10041", "<reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type> <reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>", "", "");
        loadNaturalLanguageTemplate("10206", "en", "10001", "10018", "<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10208", "en", "10001", "10024", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10209", "en", "10001", "10021", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10210", "en", "10001", "10022", "<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10212", "en", "10001", "10025", "<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10213", "en", "10001", "10023", "<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10214", "en", "10001", "10027", "<reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10215", "en", "10001", "10026", "<reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>", "", "");
        loadNaturalLanguageTemplate("10216", "en", "10001", "10035", "<reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Organization>", "", "");
        loadNaturalLanguageTemplate("10217", "en", "10001", "10034", "<reqCompFieldType=kuali.reqComponent.field.type.person.id;reqCompFieldLabel=Instructor>", "", "");
        loadNaturalLanguageTemplate("10218", "en", "10001", "10038", "<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.test.cluSet.id;reqCompFieldLabel=Tests>", "", "");
        loadNaturalLanguageTemplate("10219", "en", "10001", "10037", "<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.test.cluSet.id;reqCompFieldLabel=Tests>", "", "");
        loadNaturalLanguageTemplate("10220", "en", "10001", "10028", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Number of Courses> from <reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses> with <reqCompFieldType=kuali.reqComponent.field.type.gradeType.id;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade.id;reqCompFieldLabel=Grade>", "", "");
        loadNaturalLanguageTemplate("10224", "en", "10001", "10036", "<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)>", "", "");
        loadNaturalLanguageTemplate("10225", "en", "10001", "10029", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits>", "", "");
        loadNaturalLanguageTemplate("10226", "en", "10001", "10055", "<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Credits> from courses in <reqCompFieldType=kuali.reqComponent.field.type.org.id;reqCompFieldLabel=Department>", "", "");
        loadNaturalLanguageTemplate("10227", "en", "10001", "10052", "<reqCompFieldType=kuali.reqComponent.field.type.program.cluSet.id;reqCompFieldLabel=Program(s)>", "", "");
        loadNaturalLanguageTemplate("10228", "en", "10001", "10017", "<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>", "", "");
        loadNaturalLanguageTemplate("10229", "en", "10000", "10020", "#if($courseCluSet.getCluList().size() == 1)Must not have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must not have successfully completed any courses from $courseCluSet.getCluSetAsCode()#end", "", "");
        loadNaturalLanguageTemplate("10235", "en", "10000", "10039", "Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, \"credit\")", "", "");
        loadNaturalLanguageTemplate("10236", "en", "10000", "10047", "Must not have earned more than $intValue $NLHelper.getProperGrammar($intValue, \"credit\")", "", "");
        loadNaturalLanguageTemplate("10239", "en", "10003", "10020", "#if($courseCluSet.getCluList().size() == 1)Must not have successfully completed#{else}Must not have successfully completed any courses from#end", "", "");
        loadNaturalLanguageTemplate("10246", "en", "10000", "10040", "Must have earned a minimum cumulative GPA of $gpa", "", "");
        loadNaturalLanguageTemplate("10247", "en", "10000", "10041", "Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa", "", "");
        loadNaturalLanguageTemplate("10248", "en", "10003", "10018", "#if($courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed all courses from#end", "", "");
        loadNaturalLanguageTemplate("10249", "en", "10003", "10053", "#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from#end", "", "");
        loadNaturalLanguageTemplate("10250", "en", "10003", "10024", "Must be concurrently enrolled in a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from", "", "");
        loadNaturalLanguageTemplate("10251", "en", "10003", "10021", "Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"credit\") from", "", "");
        loadNaturalLanguageTemplate("10252", "en", "10003", "10022", "Must not have successfully completed any credits from", "", "");
        loadNaturalLanguageTemplate("10254", "en", "10003", "10025", "Must have earned a minimum GPA of $gpa in", "", "");
        loadNaturalLanguageTemplate("10255", "en", "10003", "10023", "#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in#{else}Must be concurrently enrolled in all courses from#end", "", "");
        loadNaturalLanguageTemplate("10261", "en", "10003", "10039", "Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, \"credit\")", "", "");
        loadNaturalLanguageTemplate("10262", "en", "10003", "10047", "Must not have earned more than $intValue $NLHelper.getProperGrammar($intValue, \"credit\")", "", "");
        loadNaturalLanguageTemplate("10271", "en", "10003", "10040", "Must have earned a minimum cumulative GPA of $gpa", "", "");
        loadNaturalLanguageTemplate("10272", "en", "10003", "10041", "Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa", "", "");
        loadNaturalLanguageTemplate("10273", "en", "10003", "10027", "Must have earned a minimum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade in", "", "");
        loadNaturalLanguageTemplate("10274", "en", "10003", "10026", "Must not have earned a maximum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade or higher in", "", "");
        loadNaturalLanguageTemplate("10275", "en", "10003", "10035", "Permission of $org.getLongName() required", "", "");
        loadNaturalLanguageTemplate("10276", "en", "10003", "10034", "Permission of instructor required", "", "");
        loadNaturalLanguageTemplate("10277", "en", "10003", "10038", "Must have achieved a minimum score of $fields.get('kuali.reqComponent.field.type.test.score') on $testCluSet.getCluSetAsLongName()", "", "");
        loadNaturalLanguageTemplate("10278", "en", "10003", "10037", "Must have achieved a score no higher than $fields.get('kuali.reqComponent.field.type.test.score') on $testCluSet.getCluSetAsLongName()", "", "");
        loadNaturalLanguageTemplate("10279", "en", "10003", "10028", "Must successfully complete a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") with a minimum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade from", "", "");
        loadNaturalLanguageTemplate("10282", "en", "10003", "10033", "Must be admitted to any program offered at the course campus location", "", "");
        loadNaturalLanguageTemplate("10283", "en", "10003", "10036", "Must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program", "", "");
        loadNaturalLanguageTemplate("10284", "en", "10003", "10029", "May be repeated for a maximum of $intValue credits", "", "");
        loadNaturalLanguageTemplate("10285", "en", "10003", "10055", "Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"credit\") from courses in the $org.getLongName()", "", "");
        loadNaturalLanguageTemplate("10286", "en", "10003", "10052", "Must have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) program", "", "");
        loadNaturalLanguageTemplate("10287", "en", "10003", "10017", "Must have successfully completed $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10288", "en", "10003", "10030", "Must be concurrently enrolled in $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10289", "en", "10003", "10032", "Must not have successfully completed $courseClu.getOfficialIdentifier().getCode()", "", "");
        loadNaturalLanguageTemplate("10290", "en", "10000", "10027", "Must have earned a minimum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade in $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10291", "en", "10000", "10026", "Must not have earned a maximum grade of #if($gradeType.getId().equals(\"kuali.result.scale.grade.letter\") || $gradeType.getId().equals(\"kuali.result.scale.grade.percentage\"))$gradeType.getName().toLowerCase() #{end}$grade or higher in $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10292", "en", "10001", "10030", "<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>", "", "");
        loadNaturalLanguageTemplate("10293", "en", "10001", "10032", "<reqCompFieldType=kuali.reqComponent.field.type.course.clu.id;reqCompFieldLabel=Course>", "", "");
        loadNaturalLanguageTemplate("10294", "en", "10000", "10024", "Must be concurrently enrolled in a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"course\") from $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10304", "en", "10001", "10031", "<reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Text>", "", "");
        loadNaturalLanguageTemplate("10318", "en", "10003", "10031", "$freeText", "", "");
        loadNaturalLanguageTemplate("10325", "en", "10000", "10031", "$freeText", "", "");
        loadNaturalLanguageTemplate("10330", "en", "10000", "10021", "Must have successfully completed a minimum of $intValue $NLHelper.getProperGrammar($intValue, \"credit\") from $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10331", "en", "10000", "10022", "Must not have successfully completed any credits from $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10333", "en", "10000", "10025", "Must have earned a minimum GPA of $gpa in $courseCluSet.getCluSetAsCode()", "", "");
        loadNaturalLanguageTemplate("10334", "en", "10000", "10023", "#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in all courses from $courseCluSet.getCluSetAsCode()#end", "", "");
        loadNaturalLanguageTemplate("10335", "en", "10003", "10065", "Must have been admitted to a Program offered by <Org>", "", "");
        loadNaturalLanguageTemplate("10337", "en", "10003", "10074", "Must have achieved a score between <score> and <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10338", "en", "10003", "10064", "Must not have been admitted to the <Program> Program with a class standing of <Class Standing>", "", "");
        loadNaturalLanguageTemplate("10339", "en", "10003", "10057", "May not repeat <course> if repeated <n> times ", "", "");
        loadNaturalLanguageTemplate("10340", "en", "10003", "10075", "Must have achieved a score between <score> and <score> on <test> ", "", "");
        loadNaturalLanguageTemplate("10341", "en", "10003", "10067", "Student must be in a class standing of <class standing> or greater ", "", "");
        loadNaturalLanguageTemplate("10342", "en", "10003", "10046", "Student in <Program> can enrol in a maximum of <n> courses for the term", "", "");
        loadNaturalLanguageTemplate("10343", "en", "10003", "10062", "Must have successfully completed <course> between <term1> and <term2>", "", "");
        loadNaturalLanguageTemplate("10344", "en", "10003", "10056", "Student cannot add Activity Offering with <Activity Offering State>  of <state> ", "", "");
        loadNaturalLanguageTemplate("10345", "en", "10003", "10069", "Must not be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10347", "en", "10003", "10058", "May not repeat any of  <courses>", "", "");
        loadNaturalLanguageTemplate("10348", "en", "10003", "10044", "Course has more than <n> minutes overlap with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10349", "en", "10003", "10060", "Must have successfully completed <course> as of <term>", "", "");
        loadNaturalLanguageTemplate("10350", "en", "10003", "10054", "Must have successfully completed a minimum of <n> credits from <courses>", "", "");
        loadNaturalLanguageTemplate("10351", "en", "10003", "10071", "Must be concurrently enrolled in all courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10352", "en", "10003", "10051", "Students admitted to <campus> may take no more than <n  credits> at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10353", "en", "10003", "10050", "Students admitted to <campus> may take no more than <n> courses at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10354", "en", "10003", "10043", "If student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ", "", "");
        loadNaturalLanguageTemplate("10356", "en", "10003", "10072", "May not repeat <course> if repeated <n> times ", "", "");
        loadNaturalLanguageTemplate("10357", "en", "10003", "10061", "Must have successfully completed <course> prior to <term>", "", "");
        loadNaturalLanguageTemplate("10358", "en", "10003", "10068", "Student must be in a class standing of <class standing> or less ", "", "");
        loadNaturalLanguageTemplate("10359", "en", "10003", "10045", "Course has less than <n> minutes between start time or end time with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10360", "en", "10003", "10066", "Student must be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10362", "en", "10003", "10059", "Student is in an existing seat pool for the course with an available seat", "", "");
        loadNaturalLanguageTemplate("10363", "en", "10003", "10042", "If student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ", "", "");
        loadNaturalLanguageTemplate("10364", "en", "10000", "10077", "Must meet 1 of the following", "", "");
        loadNaturalLanguageTemplate("10365", "en", "10001", "10077", "Must meet 1 of the following", "", "");
        loadNaturalLanguageTemplate("10366", "en", "10002", "10077", "Must meet 1 of the following", "", "");
        loadNaturalLanguageTemplate("10367", "en", "10003", "10077", "Must meet 1 of the following", "", "");
        loadNaturalLanguageTemplate("10368", "en", "10005", "10077", "Must meet 1 of the following", "", "");
        loadNaturalLanguageTemplate("10369", "en", "10000", "10076", "Must meet all of the following", "", "");
        loadNaturalLanguageTemplate("10370", "en", "10001", "10076", "Must meet all of the following", "", "");
        loadNaturalLanguageTemplate("10371", "en", "10002", "10076", "Must meet all of the following", "", "");
        loadNaturalLanguageTemplate("10372", "en", "10003", "10076", "Must meet all of the following", "", "");
        loadNaturalLanguageTemplate("10373", "en", "10005", "10076", "Must meet all of the following", "", "");
        loadNaturalLanguageTemplate("10374", "en", "10000", "10065", "Must have been admitted to a Program offered by <Org>", "", "");
        loadNaturalLanguageTemplate("10376", "en", "10000", "10074", "Must have achieved a score between <score> and <score> on <test>", "", "");
        loadNaturalLanguageTemplate("10377", "en", "10000", "10064", "Must not have been admitted to the <Program> Program with a class standing of <Class Standing>", "", "");
        loadNaturalLanguageTemplate("10378", "en", "10000", "10057", "May not repeat <course> if repeated <n> times ", "", "");
        loadNaturalLanguageTemplate("10379", "en", "10000", "10075", "Must have achieved a score between <score> and <score> on <test> ", "", "");
        loadNaturalLanguageTemplate("10380", "en", "10000", "10067", "Student must be in a class standing of <class standing> or greater ", "", "");
        loadNaturalLanguageTemplate("10381", "en", "10000", "10046", "Student in <Program> can enrol in a maximum of <n> courses for the term", "", "");
        loadNaturalLanguageTemplate("10382", "en", "10000", "10062", "Must have successfully completed <course> between <term1> and <term2>", "", "");
        loadNaturalLanguageTemplate("10383", "en", "10000", "10056", "Student cannot add Activity Offering with <Activity Offering State>  of <state> ", "", "");
        loadNaturalLanguageTemplate("10384", "en", "10000", "10069", "Must not be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10386", "en", "10000", "10058", "May not repeat any of  <courses>", "", "");
        loadNaturalLanguageTemplate("10387", "en", "10000", "10044", "Course has more than <n> minutes overlap with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10388", "en", "10000", "10060", "Must have successfully completed <course> as of <term>", "", "");
        loadNaturalLanguageTemplate("10389", "en", "10000", "10054", "Must have successfully completed a minimum of <n> credits from <courses>", "", "");
        loadNaturalLanguageTemplate("10390", "en", "10000", "10071", "Must be concurrently enrolled in all courses from <courses>", "", "");
        loadNaturalLanguageTemplate("10391", "en", "10000", "10051", "Students admitted to <campus> may take no more than <n  credits> at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10392", "en", "10000", "10050", "Students admitted to <campus> may take no more than <n> courses at <campus> in <duration> <durationType>", "", "");
        loadNaturalLanguageTemplate("10393", "en", "10000", "10043", "If student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ", "", "");
        loadNaturalLanguageTemplate("10395", "en", "10000", "10072", "May not repeat <course> if repeated <n> times ", "", "");
        loadNaturalLanguageTemplate("10396", "en", "10000", "10061", "Must have successfully completed <course> prior to <term>", "", "");
        loadNaturalLanguageTemplate("10397", "en", "10000", "10068", "Student must be in a class standing of <class standing> or less ", "", "");
        loadNaturalLanguageTemplate("10398", "en", "10000", "10045", "Course has less than <n> minutes between start time or end time with already enrolled course", "", "");
        loadNaturalLanguageTemplate("10399", "en", "10000", "10066", "Student must be in a class standing of <class standing>", "", "");
        loadNaturalLanguageTemplate("10401", "en", "10000", "10059", "Student is in an existing seat pool for the course with an available seat", "", "");
        loadNaturalLanguageTemplate("10402", "en", "10000", "10042", "If student is <attribute> AND upon drop total credit hours would be less than <min credit hours>, prevent drop ", "", "");
        loadNaturalLanguageTemplate("10403", "en", "10006", "10010", "Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.", "", "");
        loadNaturalLanguageTemplate("10404", "en", "10006", "10006", "Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll. (TL: Why is this the same definition as above? It should at least specify that the restriction is based on concurrent enrolment, not completion.)", "", "");
        loadNaturalLanguageTemplate("10405", "en", "10006", "10008", "The courses and/or preparation added here will not prevent students from registering, but will be printed in the catalog.", "", "");
        loadNaturalLanguageTemplate("10406", "en", "10006", "10005", "Add courses that, if completed, would prevent a student from enrolling in this course.", "", "");
        loadNaturalLanguageTemplate("10407", "en", "10006", "10012", "Enrollment in or completion of another course that will restrict the credits to be awarded.", "", "");
        loadNaturalLanguageTemplate("10408", "en", "10006", "10011", "Course repeatable for credit.", "", "");
    }

    private void loadNaturalLanguageTemplate(String id,
            String languageCode,
            String naturalLanguageUsageId,
            String typeId,
            String template,
            String componentId,
            String componentBuilderClass) {
        NaturalLanguageTemplate.Builder bldr = NaturalLanguageTemplate.Builder.create(languageCode, naturalLanguageUsageId, template, typeId);
        bldr.setId(id);
        bldr.setActive(true);
        Map<String, String> attrs = new LinkedHashMap<String, String>();
        bldr.setAttributes(attrs);
        if (componentId != null && !componentId.isEmpty()) {
            attrs.put(KsKrmsConstants.ATTRIBUTE_COMPONENT_ID, componentId);
        }
        if (componentBuilderClass != null && !componentBuilderClass.isEmpty()) {
            attrs.put(KsKrmsConstants.ATTRIBUTE_COMPONENT_BUILDER_CLASS, componentBuilderClass);
        }
        if (!attrs.isEmpty()) {
            bldr.setAttributes(attrs);
        }
        this.getRuleManagementService().createNaturalLanguageTemplate(bldr.build());
    }

    private void loadTermSpecs() {
        loadTermSpec("10000", "CompletedCourse", "java.lang.Boolean", "Completed course", "KS-SYS");
        loadTermSpec("10001", "CompletedCourses", "java.lang.Boolean", "Completed courses", "KS-SYS");
        loadTermSpec("10002", "NumberOfCompletedCourses", "java.lang.Integer", " Number of completed courses", "KS-SYS");
        loadTermSpec("10003", "NumberOfCreditsFromCompletedCourses", "java.lang.Integer", "Number of credits from completed courses", "KS-SYS");
        loadTermSpec("10004", "EnrolledCourses", "java.lang.Integer", "Enrolled courses", "KS-SYS");
        loadTermSpec("10005", "GPAForCourses", "java.lang.Integer", "GPA for courses", "KS-SYS");
        loadTermSpec("10006", "GradeTypeForCourses", "java.lang.Integer", "Grade type for courses", "KS-SYS");
        loadTermSpec("10007", "NumberOfCredits", "java.lang.Integer", "Number of credits", "KS-SYS");
        loadTermSpec("10008", "NumberOfCreditsFromOrganization", "java.lang.Integer", "Number of credits from organization", "KS-SYS");
        loadTermSpec("10009", "AdminOrganizationPermissionRequired", "java.lang.Boolean", "Admin organization permission required", "KS-SYS");
        loadTermSpec("10010", "ScoreOnTest", "java.lang.Integer", "Score on test", "KS-SYS");
        loadTermSpec("10011", "AdmittedToProgram", "java.lang.Boolean", "Admitted to program", "KS-SYS");
        loadTermSpec("10012", "AdmittedToProgramLimitCoursesInOrgForDuration", "java.lang.Integer", "Admitted to program limit courses in organization for duration", "KS-SYS");
        loadTermSpec("10013", "FreeFormText", "java.lang.Boolean", "Free Form Text", "KS-SYS");
    }

    private void loadTermSpec(String id, String name, String type, String description, String namespace) {
        TermSpecificationDefinition.Builder bldr = TermSpecificationDefinition.Builder.create(id, name, namespace, type);
        bldr.setDescription(description);
        bldr.setId(id);
        bldr.setActive(true);
        this.getTermRepositoryService().createTermSpecification(bldr.build());
    }

    private void loadTermResolvers() {
        loadTermResolver("10000", "KS-SYS", "CompletedCourse", "10000", "10000", "", "", "");
        loadTermResolver("10001", "KS-SYS", "CompletedCourses", "10000", "10001", "", "", "");
        loadTermResolver("10002", "KS-SYS", "NumberOfCompletedCourses", "10000", "10002", "", "", "");
        loadTermResolver("10003", "KS-SYS", "NumberOfCreditsFromCompletedCourses", "10000", "10003", "", "", "");
        loadTermResolver("10004", "KS-SYS", "EnrolledCourses", "10000", "10004", "", "", "");
        loadTermResolver("10005", "KS-SYS", "GPAForCourses", "10000", "10005", "", "", "");
        loadTermResolver("10006", "KS-SYS", "GradeTypeForCourses", "10000", "10006", "", "", "");
        loadTermResolver("10007", "KS-SYS", "NumberOfCredits", "10000", "10007", "", "", "");
        loadTermResolver("10008", "KS-SYS", "NumberOfCreditsFromOrganization", "10000", "10008", "", "", "");
        loadTermResolver("10009", "KS-SYS", "AdminOrganizationPermissionRequired", "10000", "10009", "", "", "");
        loadTermResolver("10010", "KS-SYS", "ScoreOnTest", "10000", "10010", "", "", "");
        loadTermResolver("10011", "KS-SYS", "AdmittedToProgram", "10000", "10011", "", "", "");
        loadTermResolver("10012", "KS-SYS", "AdmittedToProgramLimitCoursesInOrgForDuration", "10000", "10012", "", "", "");
        loadTermResolver("10013", "KS-SYS", "FreeFormText", "10000", "10013", "", "", "");
    }

    private void loadTermResolver(String id,
            String namespace,
            String name,
            String typeId,
            String outputId,
            String prereqId1,
            String prereqId2,
            String prereqId3) {
        TermSpecificationDefinition.Builder output =
                TermSpecificationDefinition.Builder.create(this.getTermRepositoryService().getTermSpecificationById(outputId));
        Set<TermSpecificationDefinition.Builder> prerequisites =
                new LinkedHashSet<TermSpecificationDefinition.Builder>();
        if (prereqId1 != null && !prereqId1.isEmpty()) {
            prerequisites.add(TermSpecificationDefinition.Builder.create(this.getTermRepositoryService().getTermSpecificationById(prereqId1)));
        }
        if (prereqId2 != null && !prereqId2.isEmpty()) {
            prerequisites.add(TermSpecificationDefinition.Builder.create(this.getTermRepositoryService().getTermSpecificationById(prereqId2)));
        }
        if (prereqId3 != null && !prereqId3.isEmpty()) {
            prerequisites.add(TermSpecificationDefinition.Builder.create(this.getTermRepositoryService().getTermSpecificationById(prereqId3)));
        }
        Map<String, String> attributes = new HashMap<String, String>();
        // TODO: findout what parameter names are used for!
        Set<String> parameterNames = new LinkedHashSet<String>();
//        create(String id, 
//        String namespaceCode, 
//                String name, 
//                String typeId,
//                TermSpecificationDefinition.Builder output, 
//                Set<TermSpecificationDefinition.Builder> prerequisites, 
//                Map<String, String> attributes,
//                Set<String> parameterNames)
        TermResolverDefinition.Builder bldr = TermResolverDefinition.Builder.create(id,
                namespace,
                name,
                typeId,
                output,
                prerequisites,
                attributes,
                parameterNames);
        bldr.setActive(true);
        this.getTermRepositoryService().createTermResolver(bldr.build());
    }

    // TODO: decide if we should even load these instead of find/create them as needed
    private void loadContexts() {
        loadContext("10000", "KS-SYS", "Course Requirements", "T1004", "Course Requirements");
        loadContext("10001", "KS-SYS", "Program Requirements", "T1004", "Program Requirements");
    }

    private void loadContext(String id, String namespace, String name, String typeId, String description) {
//        CNTXT_ID	NMSPC_CD	NM	TYP_ID	???? What kind of type	ACTV	VER_NBR	DESC_TXT
        ContextDefinition.Builder bldr = ContextDefinition.Builder.create(namespace, name);
        bldr.setId(id);
        bldr.setActive(true);
        bldr.setTypeId(typeId);
        bldr.setDescription(description);
        this.getRuleManagementService().createContext(bldr.build());
    }
}
