package org.kuali.student.cm.course.service.impl;

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.cm.course.form.LoCategoryInfoWrapper;
import org.kuali.student.cm.course.service.util.LoCategorySearchUtil;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

/**
 * Lookupable service class for {@link LoCategoryInfoWrapper} "Browse for categories" link
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class LoCategoryInfoLookupableImpl extends LookupableImpl {

    private static final long serialVersionUID = 4742457104713770383L;
    
    private LearningObjectiveService learningObjectiveService;
    
    @Override
    public List<?> performSearch(LookupForm form, Map<String, String> searchCriteria, boolean bounded) {
        return LoCategorySearchUtil.searchForLoCategories(null, getLearningObjectiveService());
    }   
    
    protected LearningObjectiveService getLearningObjectiveService() {
        if (learningObjectiveService == null) {
            learningObjectiveService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
        }
        return learningObjectiveService;
    }
    
}
