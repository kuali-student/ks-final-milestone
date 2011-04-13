package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

public class CourseStateUtil {

	private CourseStateUtil() {	
	}
	
	/* Recursively set state for StatementTreeViewInfo */
	public static void updateStatementTreeViewInfoState(String courseState, StatementTreeViewInfo statementTreeViewInfo) throws Exception {
		statementTreeViewInfo.setState(courseState);
        List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
        	it.next().setState(courseState);
        for(Iterator<StatementTreeViewInfo> itr = statementTreeViewInfo.getStatements().iterator(); itr.hasNext();)
        	updateStatementTreeViewInfoState(courseState, (StatementTreeViewInfo)itr.next());
	}
}
