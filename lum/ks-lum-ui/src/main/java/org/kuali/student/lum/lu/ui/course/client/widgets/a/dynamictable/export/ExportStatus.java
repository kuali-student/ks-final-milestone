/*
 * Copyright 2009 Johnson Consulting Services
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.lum.lu.ui.course.client.widgets.a.dynamictable.export;

import java.io.Serializable;

/**
 * Defines the current status of an asynchonous export transaction
 * @author wilj
 *
 */
public class ExportStatus implements Serializable {
	/**
	 * Enumeration of export status codes
	 *
	 */
	public enum Status {
		PENDING, COMPLETE, FAILED
	}

	private static final long serialVersionUID = 1L;
	private String exportId;
	private Status status;
	private String fileName;
	private String mimeType;
	private boolean attachment = false;
	private Throwable error;

	/**
	 * Creates a new ExportStatus.  This constructor should only be used by RPC serialization 
	 */
	protected ExportStatus() {
		super();
	}

	/**
	 * Creates a new ExportStatus
	 * @param exportId the export transaction identifier
	 * @param status the current export status code
	 * @param fileName the name of the file to which the export results are written
	 * @param error any error that has occurred, or null if the export has not encountered an error
	 */
	public ExportStatus(final String exportId, final Status status, final String fileName, final String mimeType,
			final boolean isAttachment,
			final Throwable error) {
		super();
		this.exportId = exportId;
		this.status = status;
		this.fileName = fileName;
		this.mimeType = mimeType;
		this.attachment = isAttachment;
		this.error = error;
	}

	/**
	 * Returns the error, if any, that was encountered
	 * @return the error, if any, that was encountered
	 */
	public Throwable getError() {
		return error;
	}

	/**
	 * Returns the export transaction identifier
	 * @return the export transaction identifier
	 */
	public String getExportId() {
		return exportId;
	}

	/**
	 * Returns the name of the exported file
	 * @return the name of the exported file
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Returns the MIME type of the export result
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Returns the error that has occurred, or null if the export has not encountered an error
	 * @return the error that has occurred, or null if the export has not encountered an error
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Returns true if the export should be retrieved as an attachment
	 * @return true if the export should be retrieved as an attachment
	 */
	public boolean isAttachment() {
		return attachment;
	}

}
