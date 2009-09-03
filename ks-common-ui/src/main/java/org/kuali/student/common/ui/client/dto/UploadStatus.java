package org.kuali.student.common.ui.client.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static enum UploadTransferStatus{IN_PROGRESS, UPLOAD_FINISHED, COMMIT_FINISHED, ERROR, FILE_ERROR}

	private Long totalSize;
	private Long progress;
	private UploadTransferStatus status = UploadTransferStatus.IN_PROGRESS;
	private int itemsRead = 0;
	private List<String> createdDocIds = new ArrayList<String>();
	private Map<String, FileStatus> fileStatusMap = new HashMap<String, FileStatus>();
	
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public Long getProgress() {
		return progress;
	}
	public void setProgress(Long progress) {
		this.progress = progress;
	}
	public UploadTransferStatus getStatus() {
		return status;
	}
	public void setStatus(UploadTransferStatus status) {
		this.status = status;
	}
	public int getItemsRead() {
		return itemsRead;
	}
	public void setItemsRead(int itemsRead) {
		this.itemsRead = itemsRead;
	}
	public List<String> getCreatedDocIds() {
		return createdDocIds;
	}
	public void setCreatedDocIds(List<String> createdDocIds) {
		this.createdDocIds = createdDocIds;
	}
	public Map<String, FileStatus> getFileStatusMap() {
		return fileStatusMap;
	}
	public void setFileStatusMap(Map<String, FileStatus> fileStatusMap) {
		this.fileStatusMap = fileStatusMap;
	}
	
	
	
}
