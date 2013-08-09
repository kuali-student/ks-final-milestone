/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.ui.course.server.gwt;


import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

import java.util.List;


public class LoCategoryRpcGwtServlet extends DataGwtServlet implements LoCategoryRpcService {
    private LearningObjectiveService loService;

    final static Logger LOG = Logger.getLogger(LoCategoryRpcGwtServlet.class);

	private static final long serialVersionUID = 1L;

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService#getLoCategoryTypes()
     */
    @Override
    public List<TypeInfo> getLoCategoryTypes() {
        try {
            return loService.getLoCategoryTypes();
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService#getLoCategoryType(java.lang.String)
     */
    @Override
    public TypeInfo getLoCategoryType(String loCategoryTypeKey) {
        try {
            return loService.getLoCategoryType(loCategoryTypeKey, ContextUtils.getContextInfo());

        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public List<LoCategoryInfo> getLoCategories(String loRepositoryKey) {
        try {
            return loService.getLoCategoriesByLoRepository(loRepositoryKey, ContextUtils.getContextInfo());

        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public StatusInfo deleteLoCategory(String loCategoryId) {
        try {
            return loService.deleteLoCategory(loCategoryId, ContextUtils.getContextInfo());
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

}
