/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.state.infc;

import org.kuali.student.r2.common.infc.HasAttributesAndMeta;
import org.kuali.student.r2.common.infc.HasKey;
import org.kuali.student.r2.common.infc.RichText;

/**
 * Detailed Information about a Lifecycle. A Lifecycle represents a
 * collection of States.
 *
 * @author Kuali Student Team (Kamal)
 */

public interface Lifecycle 
    extends HasKey, HasAttributesAndMeta {

    /**
     * Friendly name of the Lifecycle.
     *
     * @name Name
     * @required
     */
    public String getName();

    /**
     * Narrative description of the Lifecycle.
     *
     * @name Description
     */
    public RichText getDescr();

    /**
     * The reference to the Object URI to which the lifecycle is associated.
     *
     * E.g http://student.kuali.org/cluService/wsdl/CluInfo will be the
     *          //objectTypeURI for type 'kuali.lu.type.CreditCourse'
     *
     * The refObjectURI has three parts:
     * <ol>
     *    <li>http://student.kuali.org/wsdl -- which is fixed
     *    <li>cluService -- which should match the namespace of the service 
     *                     in which the object is defined
     *    <li>CluInfo -- which should match the java class's simple name
     * </ol>
     * @name Ref Object URI
     */
    public String getRefObjectUri();
}
