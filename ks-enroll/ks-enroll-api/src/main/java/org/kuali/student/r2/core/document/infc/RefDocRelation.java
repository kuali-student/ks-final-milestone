package org.kuali.student.r2.core.document.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Information about the object to document relation.
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public interface RefDocRelation extends Relationship {
    /**
     * Unique identifier for an object type. Used to identify the type of object being referred to,
     * so that the id can be resolved.
     *
     * @name Ref Object Type Key
     * @readOnly
     * @required
     *
     */
    String getRefObjectTypeKey();

    /**
     * Identifier for an object. This will likely require some additional context in order to be resolved,
     * such as the type of object. An objectId could be a cluId, a luiId, an orgId, a documentId, etc.
     *
     * @name Ref Object Id
     * @readOnly
     * @required
     *
     */
    String getRefObjectId();

    /**
     * Unique identifier for a document.
     *
     * @name Document Id
     * @readOnly
     * @required
     *
     */
    String getDocumentId();

    /**
     * The title of the document usage in the context of the relation to the object.
     *
     * @name Title
     *
     */
    String getTitle();

}
