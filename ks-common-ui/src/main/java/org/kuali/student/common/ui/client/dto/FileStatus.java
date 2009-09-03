package org.kuali.student.common.ui.client.dto;

import java.io.Serializable;


public class FileStatus implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static enum FileTransferStatus{IN_PROGRESS, UPLOAD_FINISHED, COMMIT_FINISHED, ERROR, FILE_ERROR}
	private FileTransferStatus status = FileTransferStatus.IN_PROGRESS;
	private Long progress = 0L;
	private String fileName;
	
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
	
}