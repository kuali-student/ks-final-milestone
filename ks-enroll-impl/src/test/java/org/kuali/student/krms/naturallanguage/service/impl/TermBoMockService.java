/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by christoff on 2013/02/26
 */
package org.kuali.student.krms.naturallanguage.service.impl;

import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.impl.repository.TermBoService;
import org.kuali.rice.krms.impl.repository.TermBoServiceImpl;
import org.kuali.student.krms.naturallanguage.KRMSDataGenerator;

import java.util.List;

/**
 * This class is a Mock implementation of the TermBoService
 *
 * @author Kuali Student Team
 */
public class TermBoMockService implements TermBoService {
    public TermDefinitionContract termDefinitionContract = KRMSDataGenerator.createTermDefinition(null,null,null,null,0L);

    @Override
    public TermSpecificationDefinition getTermSpecificationById(String id) {
        return null;  //TODO implement auto generated
    }

    @Override
    public TermSpecificationDefinition createTermSpecification(TermSpecificationDefinition termSpec) {
        return null;  //TODO implement auto generated
    }

    @Override
    public TermDefinition getTerm(String id) {
        return (TermDefinition) termDefinitionContract;
    }

    @Override
    public TermDefinition createTerm(TermDefinition termDef) {
        return null;  //TODO implement auto generated
    }

    @Override
    public TermResolverDefinition getTermResolverById(String id) {
        return null;  //TODO implement auto generated
    }

    /**
     * Get the {@link org.kuali.rice.krms.api.repository.term.TermResolverDefinition}s for any term resolvers in the specified namespace that have the given
     * term specification as their output.
     *
     * @param id        the id for the term specification
     * @param namespace the namespace to search
     * @return the List of term resolvers found.  If none are found, an empty list will be returned.
     */
    @Override
    public List<TermResolverDefinition> findTermResolversByOutputId(String id, String namespace) {
        return null;  //TODO implement auto generated
    }

    @Override
    public List<TermResolverDefinition> findTermResolversByNamespace(String namespace) {
        return null;  //TODO implement auto generated
    }

    @Override
    public TermResolverDefinition createTermResolver(TermResolverDefinition termResolver) {
        return null;  //TODO implement auto generated
    }
}
