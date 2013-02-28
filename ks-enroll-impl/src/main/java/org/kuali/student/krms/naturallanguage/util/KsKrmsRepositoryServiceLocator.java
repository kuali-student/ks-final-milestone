package org.kuali.student.krms.naturallanguage.util;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.impl.repository.NaturalLanguageTemplateBoService;
import org.kuali.rice.krms.impl.repository.NaturalLanguageUsageBoService;
import org.kuali.rice.krms.impl.repository.ReferenceObjectBindingBoService;
import org.kuali.student.krms.service.TemplateResolverService;

import javax.xml.namespace.QName;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/10
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public final class KsKrmsRepositoryServiceLocator {

    private KsKrmsRepositoryServiceLocator() {
        // private constructor since this is class is all static utility methods
    }

    private static final Logger LOG = Logger.getLogger(KsKrmsRepositoryServiceLocator.class);

    public static final String KRMS_NL_TEMPLATE_SERVICE = "naturalLanguageTemplateBoService";
    public static final String KRMS_NL_USAGE_SERVICE = "naturalLanguageUsageBoService";
    public static final String KRMS_REF_OBJECT_SERVICE = "referenceObjectBindingBoService";
    public static final String KRMS_TEMPLATE_RESOLVER_SERVICE = "templateResolverMockService";

    private static NaturalLanguageTemplateBoService naturalLanguageTemplateBoService;
    private static NaturalLanguageUsageBoService naturalLanguageUsageBoService;
    private static ReferenceObjectBindingBoService referenceObjectBindingBoService;
    private static TemplateResolverService templateResolverService;

    public static <T extends Object> T getService(String serviceName) {
        return KsKrmsRepositoryServiceLocator.<T>getBean(serviceName);
    }

    public static <T extends Object> T getBean(String serviceName) {
        if ( LOG.isDebugEnabled() ) {
            LOG.debug("Fetching service " + serviceName);
        }
        return GlobalResourceLoader.<T>getService(QName.valueOf(serviceName));
    }

    public static NaturalLanguageTemplateBoService getNaturalLanguageTemplateBoService() {
        if ( naturalLanguageTemplateBoService == null ) {
            naturalLanguageTemplateBoService = getService(KRMS_NL_TEMPLATE_SERVICE);
        }
        return naturalLanguageTemplateBoService;
    }

    public static NaturalLanguageUsageBoService getNaturalLanguageUsageBoService() {
        if (naturalLanguageUsageBoService == null) {
            naturalLanguageUsageBoService = getService(KRMS_NL_USAGE_SERVICE);
        }
        return naturalLanguageUsageBoService;
    }

    public static ReferenceObjectBindingBoService getReferenceObjectBindingBoService() {
        if (referenceObjectBindingBoService == null) {
            referenceObjectBindingBoService = getService(KRMS_REF_OBJECT_SERVICE);
        }
        return referenceObjectBindingBoService;
    }

    public static TemplateResolverService getTemplateResolverService() {
        if(templateResolverService == null){
            templateResolverService = getService(KRMS_TEMPLATE_RESOLVER_SERVICE);
        }
        return templateResolverService;
    }

}
