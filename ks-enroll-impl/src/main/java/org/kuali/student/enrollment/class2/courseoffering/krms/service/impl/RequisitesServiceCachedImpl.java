package org.kuali.student.enrollment.class2.courseoffering.krms.service.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.engine.Rule;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.student.enrollment.class2.courseoffering.krms.service.RequisitesService;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * Created by SW Genis on 2014-09-18.
 */
public class RequisitesServiceCachedImpl implements RequisitesService {

    private RuleManagementService ruleManagementService;
    private KrmsTypeRepositoryService krmsTypeRepositoryService;

    private static final String ruleManagementServiceConst = "ruleManagementService";
    private static final String krmsTypeRepositoryServiceConst = "krmsTypeRepositoryService";

    private static String agendaCacheName = "agendaCache";
    private static String agendaItemCacheName = "agendaItemCache";

    private CacheManager cacheManager;

    @Override
    public Rule getRuleForCourseOfferingIdAndType(String courseOfferingId, String agendaType, String ruleType) {
        return this.getRuleForRefObjectIdAndType(CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
                courseOfferingId, agendaType, ruleType);
    }

    @Override
    public Rule getRuleForActivityOfferingIdAndType(String courseOfferingId, String agendaType, String ruleType) {
        return this.getRuleForRefObjectIdAndType(CourseOfferingServiceConstants.REF_OBJECT_URI_ACTIVITY_OFFERING,
                courseOfferingId, agendaType, ruleType);
    }

    private Rule getRuleForRefObjectIdAndType(String discriminator, String refObjectId, String agendaType, String ruleType) {

        KrmsTypeDefinition agendaTypeInfo = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, agendaType);
        KrmsTypeDefinition ruleTypeInfo = this.getKrmsTypeRepositoryService().getTypeByName(StudentIdentityConstants.KS_NAMESPACE_CD, ruleType);

        List<ReferenceObjectBinding> refObjectsBindings = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject(
                discriminator, refObjectId);

        for (ReferenceObjectBinding referenceObjectBinding : refObjectsBindings) {
            AgendaDefinition agenda = getAgendaDefinition(referenceObjectBinding.getKrmsObjectId());
            if (agenda.getTypeId().equals(agendaTypeInfo.getId())) {
                AgendaItemDefinition firstItem = getAgendaItemDefinition(agenda.getFirstItemId());
                return getRuleForType(firstItem, ruleTypeInfo.getId());
            }
        }

        return null;
    }

    private AgendaDefinition getAgendaDefinition(String agendaId) {
        Cache cache = getCacheManager().getCache(agendaCacheName);
        Element cachedResult = cache.get(agendaId);
        Object result;
        if (cachedResult == null) {
            result = this.getRuleManagementService().getAgenda(agendaId);
            cache.put(new Element(agendaId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (AgendaDefinition) result;
    }

    private AgendaItemDefinition getAgendaItemDefinition(String agendaItemId) {
        Cache cache = getCacheManager().getCache(agendaItemCacheName);
        Element cachedResult = cache.get(agendaItemId);
        Object result;
        if (cachedResult == null) {
            result = this.getRuleManagementService().getAgendaItem(agendaItemId);
            cache.put(new Element(agendaItemId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (AgendaItemDefinition) result;
    }

    private Rule getRuleForType(AgendaItemDefinition agendaItem, String ruleTypeId) {

        if (agendaItem.getRule() != null && agendaItem.getRule().getTypeId().equals(ruleTypeId)) {
            return this.getRepositoryToEngineTranslator().translateRuleDefinition(agendaItem.getRule());
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

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
