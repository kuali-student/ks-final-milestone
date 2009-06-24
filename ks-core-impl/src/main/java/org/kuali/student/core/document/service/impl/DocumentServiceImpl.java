/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.document.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.document.dto.DocumentCategoryInfo;
import org.kuali.student.core.document.dto.DocumentCriteriaInfo;
import org.kuali.student.core.document.dto.DocumentInfo;
import org.kuali.student.core.document.dto.DocumentTypeInfo;
import org.kuali.student.core.document.service.DocumentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.comment.service.CommentService", serviceName = "CommentService", portName = "CommentService", targetNamespace = "http://student.kuali.org/commentService")
@Transactional(rollbackFor={Throwable.class})
public class DocumentServiceImpl implements DocumentService {
    private CommentDao commentDao;
    private DictionaryService dictionaryServiceDelegate;// = new DictionaryServiceImpl(); //TODO this should probably be done differently, but I don't want to copy/paste the code in while it might still change
    private SearchManager searchManager;
    @Override
    public StatusInfo addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public DocumentInfo createDocument(String documentTypeKey, String documentCategoryKey, DocumentInfo documentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StatusInfo deleteDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<DocumentCategoryInfo> getCategoriesByDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public DocumentInfo getDocument(String documentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<DocumentCategoryInfo> getDocumentCategories() throws OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public DocumentCategoryInfo getDocumentCategory(String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public DocumentTypeInfo getDocumentType(String documentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<DocumentTypeInfo> getDocumentTypes() throws OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<DocumentInfo> getDocumentsByIdList(List<String> documentIdList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public StatusInfo removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<String> searchForDocuments(DocumentCriteriaInfo documentCriteria) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public DocumentInfo updateDocument(String documentId, DocumentInfo documentInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    @Override
    public List<ValidationResult> validateDocument(String validationType, DocumentInfo documentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

   

}
