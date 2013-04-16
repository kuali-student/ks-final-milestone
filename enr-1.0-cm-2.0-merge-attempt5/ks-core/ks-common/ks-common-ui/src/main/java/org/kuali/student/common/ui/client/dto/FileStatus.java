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

package org.kuali.student.common.ui.client.dto;

import java.io.Serializable;


@Deprecated
public class FileStatus implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static enum FileTransferStatus{IN_PROGRESS, UPLOAD_FINISHED, COMMIT_FINISHED, ERROR, FILE_ERROR}
	private FileTransferStatus status = FileTransferStatus.IN_PROGRESS;
	private Long progress = 0L;
	private String fileName;
	private String docId;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public FileTransferStatus getStatus() {
		return status;
	}
	public void setStatus(FileTransferStatus status) {
		this.status = status;
	}
	public Long getProgress() {
		return progress;
	}
	public void setProgress(Long progress) {
		this.progress = progress;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	
	
}
