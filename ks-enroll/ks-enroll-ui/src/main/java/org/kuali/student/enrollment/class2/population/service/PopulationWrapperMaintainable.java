package org.kuali.student.enrollment.class2.population.service;

import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;

public interface PopulationWrapperMaintainable {
    public PopulationWrapper getPopulation(String populationId);
    public PopulationWrapper createPopulation(PopulationWrapper wrapper) throws Exception;
    public void updatePopulation(PopulationWrapper wrapper) throws Exception;
}
