/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.lum.lo.dto.LoCategoryTypeInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentTypeInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.lu.ui.course.client.service.LrcRpcService;

/**
 * 
 * @author Kuali Student Team
 *
 */
public class LrcRpcGwtServlet extends BaseRpcGwtServletAbstract<LrcService> implements LrcRpcService{

    private static final long serialVersionUID = 1L;

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.lum.lu.ui.course.client.service.LrcRpcService#getResultComponent(java.lang.String)
     */
	@Override
	public ResultComponentInfo getResultComponent(String resultComponentId) {
        try {
            return service.getResultComponent(resultComponentId);

        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
		return null;
	}

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.lum.lu.ui.course.client.service.LrcRpcService#getResultComponentIdsByResultComponentType(java.lang.String)
     */
	@Override
	public List<String> getResultComponentIdsByResultComponentType(
			String resultComponentTypeKey) {
		try {
            return service.getResultComponentIdsByResultComponentType(resultComponentTypeKey);

        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
		return null;
	}

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.lum.lu.ui.course.client.service.LrcRpcService#getResultComponentType(java.lang.String)
     */
	@Override
	public ResultComponentTypeInfo getResultComponentType(
			String resultComponentTypeKey) {
		try {
            return service.getResultComponentType(resultComponentTypeKey);

        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
		return null;
	}

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.lum.lu.ui.course.client.service.LrcRpcService#getResultComponentTypes()
     */
	@Override
	public List<ResultComponentTypeInfo> getResultComponentTypes() {
		try {
            return service.getResultComponentTypes();

        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
		return null;
	}

    
    
    
}


