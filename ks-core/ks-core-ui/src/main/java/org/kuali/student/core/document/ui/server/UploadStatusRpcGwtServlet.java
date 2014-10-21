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

package org.kuali.student.core.document.ui.server;

import java.util.UUID;

import org.kuali.student.common.ui.client.dto.UploadStatus;
import org.kuali.student.core.document.ui.client.service.UploadStatusRpcService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UploadStatusRpcGwtServlet extends RemoteServiceServlet implements UploadStatusRpcService {
    private static final long serialVersionUID = 1L;

    @Override
    public UploadStatus getUploadStatus(String uploadId) {
        try
        {
            UploadStatus status = (UploadStatus) (getThreadLocalRequest().getSession().getAttribute(uploadId));
            return status;
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getUploadId() {
        return UUID.randomUUID().toString();
    }
}
