/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.datadictionary.infc;

import java.util.List;

/**
 * This is an info ojbect that contains the dictionary information
 * about an object known to the system.
 *
 * @author nwright
 */

public interface DictionaryEntry  {

    /**
     * The class of the object to which this dictionary entry refers.
     *
     * @name Object Class
     */
    public String getObjectClass();

    /**
     * The name of this entry.
     *
     * @name Name
     */
    public String getName();

    /**
     * The object label provides a short name of the business object
     * for use on help screens.
     *
     * @name Object Label
     */
    public String getObjectLabel();

    /**
     * The object description provides a brief description of the
     * business object for use on help screens.
     *
     * @name Object Description
     */
    public String getObjectDescription();

    /**
     * The titleAttribute element is the name of the attribute that
     * will be used as an inquiry field when the lookup search results
     * fields are displayed.
     *
     * For some business objects, there is no obvious field to serve
     * as the inquiry field. in that case a special field may be
     * required for inquiry purposes.
     *
     * @name Title Attribute
     */
    public String getTitleAttribute();

    /**
     * The primary key(s) associatd with the object.
     *
     * TODO: determine how/if this works with the than the "unique" flag on attribute definition
     * TODO: determine how/if this works with the id
     *
     * @name Primary Keys
     */
    public List<String> getPrimaryKeys();

    /**
     * Get the definitions of the attributes (fields) on the object
     *
     * @name Attribute Defiitions
     */
    public List<? extends AttributeDefinitionInfc> getAttributes();
}
