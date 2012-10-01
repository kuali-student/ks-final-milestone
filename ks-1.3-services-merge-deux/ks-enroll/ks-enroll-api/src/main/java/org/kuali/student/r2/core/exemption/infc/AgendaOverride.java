/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.exemption.infc;

/**
 * Information about an Agenda override. An agenda override describes data
 * related to an override of a rule, such as a pre-req, optionally supplying a
 * new rule to be evaluated in it's place.
 *
 * @author tom
 * @since Tue Jun 14 14:22:34 EDT 2011
 */
public interface AgendaOverride {

    /**
     * The Id for the existing agenda id that is being overridden
     *
     * For example the agenda id for a pre-requisite rule.
     *
     * @name Agenda Id
     */
    public String getExistingAgendaId();

    /**
     * The Id for the new agenda id to use that overrides the existing 
     * agenda
     *
     * @name Agenda Id
     */
    public String getNewAgendaId();

    /**
     * The ref object Uri of the anchor object who's associated rule is being
     * overridden
     *
     * For example this should be the ref uri for a CluInfo to override a
     * statement related to a course or a program.
     *
     * @name Anchor Ref Object Uri
     */
    public String getAnchorRefObjectUri();

    /**
     * The Id of anchor object who's statement is being overridden
     *
     * @name Anchor Ref Object Id
     */
    public String getAnchorRefObjectId();
}
