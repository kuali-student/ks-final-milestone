package org.kuali.student.krms.test;

import org.kuali.rice.krms.api.Asset;
import org.kuali.rice.krms.api.AssetResolutionException;
import org.kuali.rice.krms.api.ExecutionEnvironment;
import org.kuali.rice.krms.api.Proposition;
import org.kuali.rice.krms.framework.engine.ComparableTermBasedProposition;
import org.kuali.rice.krms.framework.engine.ComparisonOperator;

public class CourseSetCreditsProposition implements Proposition {

    private String courseSetId;
    private ComparisonOperator operator;
    private final Asset creditsAsset = new Asset("completedCoursesInACourseListAsset", "Integer");
    private Integer compareCreditCount;

    public CourseSetCreditsProposition(String courseSetId, ComparisonOperator operator, Integer compareCreditCount) {
        this.courseSetId = courseSetId;
        this.operator = operator;
        this.compareCreditCount = compareCreditCount;
    }

    @Override
    public boolean evaluate(ExecutionEnvironment environment) {
        
        // add parameter to credits asset of course set before resolving asset
        // KRMS team will be defining this in the future
        // something like this:
        // asset.addProperty("courseSetId", courseSetId)
        
        Integer termValue;
        try {
            termValue = environment.resolveTerm(creditsAsset);
        } catch (AssetResolutionException e) {
            // TODO Something better than this
            throw new RuntimeException(e);
        }
        
        boolean result = Boolean.valueOf(operator.compare(termValue, compareCreditCount));
        
        return result;
    }

}
