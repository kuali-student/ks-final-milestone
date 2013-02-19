package org.kuali.student.enrollment.class1.krms.service;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.krms.service.TemplateResolverService;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface RuleViewHelperService extends TemplateResolverService {

    public String getTermSpecIdForType(String type);

    public String getTranslatedNaturalLanguage(String typeId);

    public boolean validateProposition(PropositionEditor proposition, String namespace);

}
