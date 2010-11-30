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
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.common.client.lo.rpc.LoCategoryRpcService;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;

import java.util.Date;
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
    public List<LoCategoryTypeInfo> getLoCategoryTypes() {
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
    public LoCategoryTypeInfo getLoCategoryType(String loCategoryTypeKey) {
        try {
            return loService.getLoCategoryType(loCategoryTypeKey);

        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public List<LoCategoryInfo> getLoCategories(String loRepositoryKey) {
        try {
            return loService.getLoCategories(loRepositoryKey);

        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public StatusInfo deleteLoCategory(String loCategoryId) {
        try {
            return loService.deleteLoCategory(loCategoryId);
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

}
