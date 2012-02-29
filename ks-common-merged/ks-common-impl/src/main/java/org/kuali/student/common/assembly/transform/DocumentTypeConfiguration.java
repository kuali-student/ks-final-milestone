package org.kuali.student.common.assembly.transform;

import java.util.Map;

public class DocumentTypeConfiguration {
	private String documentType;
	private Map<String, String> docContentFieldMap;
	private String defaultDocumentTitle;
	
	public Map<String, String> getDocContentFieldMap() {
		return docContentFieldMap;
	}
	
	public void setDocContentFieldMap(Map<String, String> docContentFieldMap) {
		this.docContentFieldMap = docContentFieldMap;
	}
	
	public String getDefaultDocumentTitle() {
		return defaultDocumentTitle;
	}
	
	/**
	 * Used to set default document title. The documentTitle can use property path replacement
	 * to set title based on fields found in data object.
	 * 
	 * @param defaultDocumentTitle (eg. "Proposal Title: ${proposal/name})
	 */
	public void setDefaultDocumentTitle(String defaultDocumentTitle) {
		this.defaultDocumentTitle = defaultDocumentTitle;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}	
	
}
