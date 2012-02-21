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

package org.kuali.student.r1.core.document.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dao.impl.AbstractSearchableCrudDaoImpl;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r1.core.document.dao.DocumentDao;
import org.kuali.student.r1.core.document.entity.Document;
import org.kuali.student.r1.core.document.entity.DocumentCategory;
import org.kuali.student.r1.core.document.entity.RefDocRelation;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Deprecated
public class DocumentDaoImpl extends AbstractSearchableCrudDaoImpl implements DocumentDao {
    
    final Logger logger = Logger.getLogger(DocumentDaoImpl.class);
    
    @PersistenceContext(unitName = "Document")
    @Override
    public void setEm(EntityManager em) {
        super.setEm(em);
    }

    @Override
    public Boolean addDocumentCategoryToDocument(String documentId, String documentCategoryKey) throws DoesNotExistException {

        DocumentCategory category = fetch(DocumentCategory.class, documentCategoryKey);
        Document doc = fetch(Document.class, documentId);
        List<DocumentCategory> categoryList = doc.getCategoryList();
        categoryList.add(category);
        doc.setCategoryList(categoryList);
        update(doc);
        return true;
    }

    @Override
    public List<DocumentCategory> getCategoriesByDocument(String documentId) {

        Document doc = null;
        try {
            doc = fetch(Document.class, documentId);
        } catch (DoesNotExistException e) {
            logger.error("Exception occured: ", e);
        }
        List<DocumentCategory> categories = doc.getCategoryList();

        return categories;
    }

    @Override
    public List<Document> getDocumentsByIdList(List<String> documentIdList) throws DoesNotExistException {
        List<Document> documents = new ArrayList<Document>();
        for (String documentId : documentIdList) {
            Document document;
            document = fetch(Document.class, documentId);
            documents.add(document);
        }
        return documents;
    }

    @Override
    public Boolean removeDocumentCategoryFromDocument(String documentId, String documentCategoryKey) throws DoesNotExistException {
        Document document = fetch(Document.class, documentId);
        List<DocumentCategory> categories = document.getCategoryList();
        ListIterator<DocumentCategory> listIterator = categories.listIterator();
        //Because there must be atleast one category associated with a document. 
        //https://test.kuali.org/confluence/display/KULSTU/Document+Service+Description+and+Assumptions
        if(categories.size()<=1){
            return false;
        }
        for (int intIndex = 0; intIndex < categories.size(); intIndex++) {

            DocumentCategory category = listIterator.next();
            if (category.getId().equals(documentCategoryKey)) {
              listIterator.remove();
            }
        }
        document.setCategoryList(categories);
        update(document);
        return true;
    }

	@Override
	public List<RefDocRelation> getRefDocRelationsByRef(
			String refObjectTypeKey, String refObjectId) {
		Query query = em.createNamedQuery("RefDocRelation.getRefDocRelationsByRef");
		query.setParameter("refObjectTypeKey", refObjectTypeKey);
		query.setParameter("refObjectId", refObjectId);
		@SuppressWarnings("unchecked")
		List<RefDocRelation> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<RefDocRelation> getRefDocRelationsByDoc(String documentId) {
		Query query = em.createNamedQuery("RefDocRelation.getRefDocRelationsByDoc");
		query.setParameter("documentId", documentId);
		@SuppressWarnings("unchecked")
		List<RefDocRelation> resultList = query.getResultList();
		return resultList;
	}

}
