package org.kuali.student.ap.coursesearch.util;

import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.utils.CourseLinkBuilder;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class that is used to look up course requisites for a course or course offering
 */
public class CourseDetailsUtil {

    // Text Keys matching the Template values of the Natural Language Templates used in the requisite rules
    public static final String PREREQUISITE_KEY = "Student Eligibility & Prerequisite";
    public static final String COREQUISITE_KEY = "Corequisite";
    public static final String ANTIREQUISITE_KEY = "Antirequisite";


    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     *
     * @param course - Course that is being displayed
     * @return Formatted list of requisites
     */
    public static List<String> getCourseRequisites(Course course){
        List<String> courseRequisites = new ArrayList<String>();
        try {
            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(
                    course.getTypeKey(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            courseRequisites = getRequisites(course.getId(), typeInfo.getRefObjectUri());
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        }
        return courseRequisites;
    }

    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     * @param courseOffering - course offering that is being displayed
     * @return Formatted list of requisites
     */
    public static List<String> getCourseOfferingRequisites(CourseOffering courseOffering){
        String coType = CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING;
        return getRequisites(courseOffering.getId(), coType);

    }

    /**
     * Retrieve and format the list of AO requisites to be displayed on the page
     * @param activityOffering - activity offering that is being displayed
     * @return Formatted list of requisites
     */
    public static List<String> getActivityOfferingRequisites(ActivityOffering activityOffering){
        String aoType = CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING;
        return getRequisites(activityOffering.getId(), aoType);

    }

    /**
     * Retrieve and format the list of course requisites to be displayed on the page
     * @param id - object used in the lookup
     * @param type - object used in the lookup
     * @return Formatted list of requisites
     */
    private static List<String> getRequisites(String id, String type){
        List<String> courseRequisites = new ArrayList<String>();
        RuleManagementService rms = KsapFrameworkServiceLocator.getRuleManagementService();
        // Gather components for natural language translation
        List<ReferenceObjectBinding> referenceObjectBindings = rms.findReferenceObjectBindingsByReferenceObject(
                type, id);
        String language = KsapFrameworkServiceLocator.getContext().getContextInfo().getLocale().getLocaleLanguage();

        // Get requisites as natural language descriptions
        for(ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings){
            NaturalLanguageUsage nlu = rms.getNaturalLanguageUsageByNameAndNamespace(
                    KSKRMSServiceConstants.KRMS_NL_RULE_EDIT, referenceObjectBinding.getNamespace());
            String description = rms.translateNaturalLanguageForObject(
                    nlu.getId(),referenceObjectBinding.getKrmsDiscriminatorType().toLowerCase(),
                    referenceObjectBinding.getKrmsObjectId(),language);
            String formattedDescription = CourseLinkBuilder.makeLinks(description, KsapFrameworkServiceLocator.getContext().getContextInfo());
            courseRequisites.add(formattedDescription);

        }

        return courseRequisites;
    }

    /**
     * Retrieve and format the map of course requisites to be displayed on the page
     * @param course - course that is being displayed
     * @return Formatted map of requisites
     */
    public static Map<String,List<String>> getCourseRequisitesMap(Course course){
        Map<String,List<String>> courseRequisitesMap = new HashMap<String, List<String>>();
        try {
            TypeInfo typeInfo = KsapFrameworkServiceLocator.getTypeService().getType(
                course.getTypeKey(), KsapFrameworkServiceLocator.getContext().getContextInfo());
            courseRequisitesMap = getRequisitesMap(course.getId(), typeInfo.getRefObjectUri());
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Type lookup error", e);
        }

        return courseRequisitesMap;
    }

    /**
     * Retrieve and format the map of course requisites to be displayed on the page
     * @param courseOffering - course offering that is being displayed
     * @return Formatted map of requisites
     */
    public static Map<String,List<String>> getCourseOfferingRequisitesMap(CourseOffering courseOffering){
        String coType = CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING;
        return getRequisitesMap(courseOffering.getId(), coType);

    }

    /**
     * Retrieve and format the map of AO requisites to be displayed on the page
     * @param activityOffering - activity offering that is being displayed
     * @return Formatted Map of requisites
     */
    public static Map<String,List<String>> getActivityOfferingRequisitesMap (ActivityOffering activityOffering){
        String aoType = CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING;
        return getRequisitesMap(activityOffering.getId(), aoType);

    }

    /**
     * Creates a map grouping the requisites based on the their type.
     * Since requisites are stored as rules in the KRMS the type is defined as the natural language template associated
     * with the rule and language usuage of the rule and agenda item.
     *
     * @param id - Id of the object requisites are being searched for.
     * @param type - The type of object being searched on
     * @return A map containing natural language translations
     */
    private static Map<String,List<String>> getRequisitesMap(String id, String type){
        Map<String,List<String>> requisitiesMap = new HashMap<String,List<String>>();
        RuleManagementService rms = KsapFrameworkServiceLocator.getRuleManagementService();
        // Gather components for natural language translation
        List<ReferenceObjectBinding> referenceObjectBindings = rms.findReferenceObjectBindingsByReferenceObject(
                type, id);
        String language = KsapFrameworkServiceLocator.getContext().getContextInfo().getLocale().getLocaleLanguage();

        // Get requisites as natural language descriptions grouped in a map
        for(ReferenceObjectBinding referenceObjectBinding : referenceObjectBindings){
            NaturalLanguageUsage nlu = rms.getNaturalLanguageUsageByNameAndNamespace(
                    KSKRMSServiceConstants.KRMS_NL_RULE_EDIT, referenceObjectBinding.getNamespace());
            // Get a list of rule ids associated with the object
            AgendaTreeDefinition agendaTree = rms.getAgendaTree(referenceObjectBinding.getKrmsObjectId());
            List<String> ruleIds = deconstructAgendaEntryToRuleIds(agendaTree);
            List<RuleDefinition> rules = rms.getRules(ruleIds);

            // Create a map containing the natural language description for each rule, grouped by the NL template used
            for(RuleDefinition rule : rules){
                List<NaturalLanguageTemplate> templates = rms.findNaturalLanguageTemplatesByType(rule.getTypeId());
                for(NaturalLanguageTemplate template : templates){
                    if(template.getNaturalLanguageUsageId().equals(nlu.getId())){
                        String description = rms.translateNaturalLanguageForProposition(nlu.getId(),
                                rule.getProposition(),language);
                        String formattedDescription = CourseLinkBuilder.makeLinks(description, KsapFrameworkServiceLocator.getContext().getContextInfo());
                        if(requisitiesMap.containsKey(template.getTemplate())) requisitiesMap
                                .get(template.getTemplate()).add(formattedDescription);
                        else{
                            List<String> ruleDescriptions = new ArrayList<String>();
                            ruleDescriptions.add(formattedDescription);
                            requisitiesMap.put(template.getTemplate(),ruleDescriptions);
                        }
                        break;
                    }
                }
            }
        }

        return requisitiesMap;
    }

    /**
     * Recursive method that transverses a AgendaTreeDefinition and returns a full list of rule ids found within it
     *
     * @param agendaTreeDefinition - Agenda Tree to transverse
     * @return List of rule ids found in the tree segement
     */
    private static List<String> deconstructAgendaEntryToRuleIds(AgendaTreeDefinition agendaTreeDefinition){
        List<String> rules = new ArrayList<String>();

        // if definition is null return empty list
        if(agendaTreeDefinition == null){
            return rules;
        }

        // For each entry get its rule id and any ids in its branches
        for(AgendaTreeEntryDefinitionContract entry : agendaTreeDefinition.getEntries()){
            AgendaTreeRuleEntry ruleEntry = (AgendaTreeRuleEntry)entry;
            rules.add(ruleEntry.getRuleId());
            rules.addAll(deconstructAgendaEntryToRuleIds(ruleEntry.getIfTrue()));
            rules.addAll(deconstructAgendaEntryToRuleIds(ruleEntry.getIfFalse()));
        }

        return rules;
    }
}
