/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by mahtabme on 2/5/14
 */
package org.kuali.student.core.ges.service.proposition;

import org.kuali.rice.krms.api.engine.ExecutionEnvironment;
import org.kuali.rice.krms.framework.engine.PropositionResult;
import org.kuali.student.common.util.krms.proposition.AbstractLeafProposition;

import java.util.Calendar;

/**
 * This class represents a proposition that checks whether a Ges Service value is
 * applicable if a certain day is the specified day.
 *
 * @author Kuali Student Team
 */
public class IsGesValueApplicableOnDayProposition extends AbstractLeafProposition {

    ///////////////////////////
    // DATA VARIABLES
    ///////////////////////////

    /**
     * The day this Ges Value is supposed to be active.
     * @see Calendar
     */
    private int dayValueIsApplicable = Calendar.FRIDAY; // DEFAULT: FRIDAY

    /**
     * The day this proposition is being checked for.
     * @see Calendar
     */
    private int dayBeingChecked = Calendar.FRIDAY; // DEFAULT: FRIDAY

    ///////////////////////////
    // ACCESSORS AND MODIFIERS
    ///////////////////////////

    public int getDayValueIsApplicable() {
        return dayValueIsApplicable;
    }

    public void setDayValueIsApplicable(int dayValueIsApplicable) {
        this.dayValueIsApplicable = dayValueIsApplicable;
    }

    public int getDayBeingChecked() {
        return dayBeingChecked;
    }

    public void setDayBeingChecked(int dayBeingChecked) {
        this.dayBeingChecked = dayBeingChecked;
    }

    /////////////////////////////
    // CONSTRUCTORS
    /////////////////////////////

    /**
     * Constructs this proposition with given values
     * @param dayValueIsApplicable corresponds to a day of the week as per Calendar, the day this
     *                             Ges value becomes applicable.
     * @param dayBeingChecked corresponds to a day of the week as per Calendar, the day being checked.
     */
    public IsGesValueApplicableOnDayProposition(int dayValueIsApplicable, int dayBeingChecked) {
        this.dayValueIsApplicable = dayValueIsApplicable;
        this.dayBeingChecked = dayBeingChecked;
    }

    public IsGesValueApplicableOnDayProposition() {}

    /////////////////////////////
    // FUNCTIONALS
    /////////////////////////////

    @Override
    public PropositionResult evaluate(ExecutionEnvironment environment) {
        return this.recordResultWithNoDetails(environment, dayBeingChecked==dayValueIsApplicable);
    }
}
