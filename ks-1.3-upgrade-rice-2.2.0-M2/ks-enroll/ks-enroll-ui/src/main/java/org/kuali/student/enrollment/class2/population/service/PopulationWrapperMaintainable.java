package org.kuali.student.enrollment.class2.population.service;

import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.kuali.student.r2.core.population.dto.PopulationInfo;

import java.util.List;

public interface PopulationWrapperMaintainable {
    public PopulationWrapper getPopulation(String populationId);
    public PopulationWrapper createPopulation(PopulationWrapper wrapper) throws Exception;
    public void updatePopulation(PopulationWrapper wrapper) throws Exception;
    public List<PopulationInfo> getChildPopulations(List<String> childPopulationIds) throws Exception;
}
