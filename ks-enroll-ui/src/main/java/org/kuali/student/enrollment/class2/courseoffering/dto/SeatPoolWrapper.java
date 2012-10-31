/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 8/1/12
 */
package org.kuali.student.enrollment.class2.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import java.io.Serializable;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class SeatPoolWrapper implements Serializable, Comparable<SeatPoolWrapper> {
    private String id;
    private SeatPoolDefinitionInfo seatPool;
    private PopulationInfo seatPoolPopulation;

    public SeatPoolDefinitionInfo getSeatPool() {
        return seatPool;
    }

    public void setSeatPool(SeatPoolDefinitionInfo seatPool) {
        this.seatPool = seatPool;
    }

    public PopulationInfo getSeatPoolPopulation() {
        return seatPoolPopulation;
    }

    public void setSeatPoolPopulation(PopulationInfo seatPoolPopulation) {
        this.seatPoolPopulation = seatPoolPopulation;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    //FindBugs - it is fine as is
    public int compareTo(SeatPoolWrapper seatPoolToCompare) {
        return this.getSeatPool().getProcessingPriority()-seatPoolToCompare.getSeatPool().getProcessingPriority();
    }
}
