/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by mahtabme on 12/9/13
 */
package org.kuali.student.core.population.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;

import java.util.List;
import org.kuali.student.core.population.service.impl.PopulationTestStudentEnum;

/**
 * This class represents a Year of Study calculator where the year of study is calculated from the field "credits completed" in the student enum.
 *
 * @author Mezba Mahtab
 */
public class YearOfStudyCalculatorFromEnumCreditsImpl implements YearOfStudyCalculator {

    ///////////////////////////
    // DATA VARIABLES
    ///////////////////////////

    private String maximumCreditsNeededToComplete;
    private List<String> yearsOfStudyLabels;

    ///////////////////////////
    // GETTERS AND SETTERS
    ///////////////////////////

    public String getMaximumCreditsNeededToComplete() {
        return maximumCreditsNeededToComplete;
    }

    public void setMaximumCreditsNeededToComplete(String maximumCreditsNeededToComplete) {
        this.maximumCreditsNeededToComplete = maximumCreditsNeededToComplete;
    }

    public List<String> getYearsOfStudyLabels() {
        return yearsOfStudyLabels;
    }

    public void setYearsOfStudyLabels(List<String> yearsOfStudyLabels) {
        this.yearsOfStudyLabels = yearsOfStudyLabels;
    }

    ///////////////////////////
    // CONSTRUCTOR
    ///////////////////////////

    public YearOfStudyCalculatorFromEnumCreditsImpl(String maximumCreditsNeededToComplete, List<String> yearsOfStudyLabels) {
        this.maximumCreditsNeededToComplete = maximumCreditsNeededToComplete;
        this.yearsOfStudyLabels = yearsOfStudyLabels;
    }

    ///////////////////////////
    // FUNCTIONALS
    ///////////////////////////

    @Override
    public String getYearOfStudy(PopulationTestStudentEnum populationTestStudentEnum, ContextInfo contextInfo) throws OperationFailedException {
        Integer maximumCreditsNeeded_int = null;
        try {
            maximumCreditsNeeded_int = Integer.parseInt(maximumCreditsNeededToComplete);
        } catch (NumberFormatException e) {
            throw new OperationFailedException("could not parse maximumCreditsNeededToComplete " + maximumCreditsNeededToComplete + " as int.");
        }
        if (maximumCreditsNeeded_int <=0) {
            throw new OperationFailedException("maximumCreditsNeeded_int " + maximumCreditsNeeded_int + "is less or equal to 0.");
        }

        Integer creditsCompleted_int = null;
        try {
            creditsCompleted_int = Integer.parseInt(populationTestStudentEnum.getCreditsCompleted());
        } catch (NumberFormatException e) {
            throw new OperationFailedException("could not parse credits completed as int.");
        }

        Integer yearsOfStudyToComplete_int = null;
        try {
            yearsOfStudyToComplete_int =  yearsOfStudyLabels.size();
        } catch (Exception e) {
            throw new OperationFailedException("could not parse yearsOfStudyLabels as int.");
        }
        if (yearsOfStudyToComplete_int <=0) {
            throw new OperationFailedException("yearsOfStudyToComplete_int " + yearsOfStudyToComplete_int + "is less or equal to 0.");
        }

        // complete calculation based on average number of credits taken per year
        int numCreditsNeededPerYear = maximumCreditsNeeded_int / yearsOfStudyToComplete_int;
        for (int i=1; i<=yearsOfStudyToComplete_int; i++) {
            if (creditsCompleted_int<=(i*numCreditsNeededPerYear)) {
                return yearsOfStudyLabels.get(i-1);
            }
        }
        return yearsOfStudyLabels.get(yearsOfStudyLabels.size()-1); // return the final year
    }
}
