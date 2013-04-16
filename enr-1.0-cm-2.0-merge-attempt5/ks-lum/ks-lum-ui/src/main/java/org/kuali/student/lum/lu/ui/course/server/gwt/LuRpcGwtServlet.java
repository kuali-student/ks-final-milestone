/**
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

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.lum.lu.ui.course.client.service.LuRpcService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

/**
 * This is a description of what this class does - Will Gomes don't forget to
 * fill this in.
 * 
 * @author Kuali Student Team
 * 
 */
public class LuRpcGwtServlet extends BaseRpcGwtServletAbstract<CluService>
		implements LuRpcService {

	private static final long serialVersionUID = 1L;
	final static Logger LOG = Logger.getLogger(LuRpcGwtServlet.class);
	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.service.LuRemoteService#createClu(java.lang.String,
	 *      org.kuali.student.lum.lu.dto.CluInfo)
	 */
	@Override
	public CluInfo createClu(String luTypeKey, CluInfo cluInfo) {
		try {
			return service.createClu(luTypeKey, cluInfo, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	/**
	 * @see org.kuali.student.lum.lu.ui.course.client.service.LuRemoteService#updateClu(java.lang.String,
	 *      org.kuali.student.lum.lu.dto.CluInfo)
	 */
	@Override
	public CluInfo updateClu(String cluId, CluInfo cluInfo) {
		try {
			return service.updateClu(cluId, cluInfo, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}
	
	@Override
	public List<CluLoRelationInfo> getCluLoRelationsByClu(String cluId) {
		try {
			return service.getCluLoRelationsByClu(cluId, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	@Override
	public CluInfo getClu(String cluId) {
		try {
			return service.getClu(cluId, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}

	
    @Override
    public VersionDisplayInfo getCurrentVersion(String refObjectTypeURI, String refObjectId) {
		try {
			return service.getCurrentVersion(refObjectTypeURI, refObjectId, ContextUtils.getContextInfo());
			
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
    }
}
