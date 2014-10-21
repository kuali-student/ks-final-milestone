/**
 * Copyright 2012 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * Created by Daniel on 4/26/12
 */
package org.kuali.student.r2.common.class1.search;

import org.kuali.student.r2.common.dao.GenericEntityDao;

/**
 * Abstract class for hard wired impls
 *
 * @author Kuali Student Team
 */
public abstract class SearchServiceAbstractHardwiredImpl
        extends SearchServiceAbstractHardwiredImplBase {

    private GenericEntityDao genericEntityDao;

    public GenericEntityDao getGenericEntityDao() {
        return genericEntityDao;
    }

    public void setGenericEntityDao(GenericEntityDao genericEntityDao) {
        this.genericEntityDao = genericEntityDao;
    }

}
