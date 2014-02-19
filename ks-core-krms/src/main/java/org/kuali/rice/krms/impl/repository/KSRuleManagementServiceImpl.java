/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.impl.repository;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.NaturalLanguage;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.TranslateBusinessMethods;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.rice.krad.service.SequenceAccessorService;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplaterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.impl.repository.language.SimpleNaturalLanguageTemplater;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;

/**
 * The implementation of {@link RuleManagementService} operations facilitate management of rules and
 * associated information.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class KSRuleManagementServiceImpl extends RuleManagementServiceImpl {

    private boolean isSame(AgendaDefinition agenda, AgendaDefinition existing) {
        if (!this.isSame(agenda.isActive(), existing.isActive())) {
            return false;
        }

        if (!this.isSame(agenda.getAttributes(), existing.getAttributes())) {
            return false;
        }

        if (!this.isSame(agenda.getContextId(), existing.getContextId())) {
            return false;
        }

        if (!this.isSame(agenda.getFirstItemId(), existing.getFirstItemId())) {
            return false;
        }

        if (!this.isSame(agenda.getName(), existing.getName())) {
            return false;
        }

        if (!this.isSame(agenda.getTypeId(), existing.getTypeId())) {
            return false;
        }

        return true;
    }

    private boolean isSame(boolean o1, boolean o2) {
        if (o1 && !o2) {
            return false;
        }

        if (!o1 && o2) {
            return false;
        }

        return true;
    }

    private boolean isSame(Map<String, String> o1, Map<String, String> o2) {
        if (o1 == null && o2 != null) {
            return false;
        }

        if (o1 != null && o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

    private boolean isSame(String o1, String o2) {
        if (o1 == null && o2 != null) {
            return false;
        }

        if (o1 != null && o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }

}

