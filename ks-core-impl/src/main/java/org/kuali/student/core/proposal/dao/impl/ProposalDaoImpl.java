/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.core.proposal.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.student.core.dao.impl.AbstractCrudDaoImpl;
import org.kuali.student.core.proposal.dao.ProposalDao;

/**
 * Database access for the Proposal Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class ProposalDaoImpl extends AbstractCrudDaoImpl implements ProposalDao {
    @PersistenceContext(unitName = "Proposal")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }

}
