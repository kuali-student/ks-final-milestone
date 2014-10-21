/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.statement.infc;

import org.kuali.student.r2.common.infc.Relationship;

/**
 * Ref Statement Relation
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
public interface RefStatementRelation extends Relationship {


    /**
     * Unique identifier for an object type. Used to identify the type of
     * object being referred to, so that the id can be resolved.
     *
     * @name Ref Object Type Key
     * @readOnly
     * @required
     *
     */
    String getRefObjectTypeKey();

    /**
     * Sets the identifier for an object. This will likely require some
     * additional context in order to be resolved, such as the type of object.
     * An objectId could be a cluId, a luiId, an orgId, a documentId, etc.
     *
     * @name Ref Object Id
     * @readOnly
     * @required
     */
    String getRefObjectId();

    /**
     * Gets the unique identifier for a single statement record.
     *
     * @name Statement Id
     * @readOnly
     * @required
     */
    String getStatementId();

}
