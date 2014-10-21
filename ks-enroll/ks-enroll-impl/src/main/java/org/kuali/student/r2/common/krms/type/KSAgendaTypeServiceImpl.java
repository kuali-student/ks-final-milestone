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
package org.kuali.student.r2.common.krms.type;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.framework.engine.Agenda;
import org.kuali.rice.krms.framework.engine.BasicAgenda;
import org.kuali.rice.krms.framework.type.AgendaTypeService;
import org.kuali.rice.krms.impl.provider.repository.LazyAgendaTree;
import org.kuali.rice.krms.impl.provider.repository.RepositoryToEngineTranslator;
import org.kuali.rice.krms.impl.type.AgendaTypeServiceBase;
import org.kuali.rice.krms.impl.util.KrmsServiceLocatorInternal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuali Rice Team (rice.collab@kuali.org).
 */
public class KSAgendaTypeServiceImpl extends AgendaTypeServiceBase {

    public static final AgendaTypeService defaultAgendaTypeService = new AgendaTypeServiceBase();
    private static final String NAME_ATTRIBUTE = "name";
    private static final String ID_ATTRIBUTE = "id";
    private static final String TYPEID_ATTRIBUTE = "typeId";

    @Override
    public Agenda loadAgenda(AgendaDefinition agendaDefinition) {

        if (agendaDefinition == null) { throw new RiceIllegalArgumentException("agendaDefinition must not be null"); }
        if (getRepositoryToEngineTranslator() == null) {
            return null;
        }

        // pass the name as a built-in attribute so that it can be used during selection
        Map<String, String> existingAttributes = new HashMap<String, String>(agendaDefinition.getAttributes());
        existingAttributes.put(NAME_ATTRIBUTE, agendaDefinition.getName());
        existingAttributes.put(ID_ATTRIBUTE, agendaDefinition.getId());
        existingAttributes.put(TYPEID_ATTRIBUTE, agendaDefinition.getTypeId());

        return new BasicAgenda(existingAttributes, new LazyAgendaTree(agendaDefinition, getRepositoryToEngineTranslator()));
    }

    private RepositoryToEngineTranslator getRepositoryToEngineTranslator() {
        return RepositoryToEngineTranslatorHolder.instance;
    }

    // Lazy initialization holder class, see Effective Java item #71
    private static class RepositoryToEngineTranslatorHolder {
        static final RepositoryToEngineTranslator instance = KrmsServiceLocatorInternal.getRepositoryToEngineTranslator();
    }
}