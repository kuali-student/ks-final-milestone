package ca.ubc.student.kuali.lum.workflow.qualifierresolver;
import java.util.List;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.lum.workflow.qualifierresolver.SenateCommitteeQualifierResolver;

/**
 * Senate Commitee Qualifer Resolver.
 * 
 * @author jhong
 */
public class SenateCommitteeQualifierResolverUBC extends SenateCommitteeQualifierResolver {
//  private TestCourseService courseService = null;
  
	@Override
	public List<AttributeSet> resolve(final RouteContext routeContext) {
		LOG.info("Hello World UBC");
		final List<AttributeSet> theList = super.resolve(routeContext);
		// TODO add code to call web service
		LOG.info("Calling Test Course Web Service:");
		//    courseService = (TestCourseService) new TestCourseServiceService().getTestCourseServicePort();
		//    String response = courseService.getSomething();
		//    LOG.info("Test Course Web Service Response:" + response);
		return theList;
	}
}
