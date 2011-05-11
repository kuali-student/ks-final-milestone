package org.kuali.student.lum.common.server;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;

/**
 * 
 * This class contains common utility methods used with statements.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class StatementUtil {
	
	/**
	 * This method will recursively set the state of all statements in the tree.
	 * <p>
	 * WARNING: you must call the statement service in order to update statements.
	 * <p>
	 * 
	 * @param state is the state we should set all statements in the tree to
	 * @param statementTreeViewInfo the tree of statements
	 * @throws Exception
	 */
	public static void updateStatementTreeViewInfoState(String state, StatementTreeViewInfo statementTreeViewInfo) {
		// Set the state on the statement tree itself
	    statementTreeViewInfo.setState(state);
	     
	    // Get all the requirements components for this statement
        List<ReqComponentInfo> reqComponents = statementTreeViewInfo.getReqComponents();
        
        // Loop over requirements and set the state for each requirement
        for(Iterator<ReqComponentInfo> it = reqComponents.iterator(); it.hasNext();)
        	it.next().setState(state);
        
        // Loop over each statement and set the state for each statement (recursively calling this method)
        for(Iterator<StatementTreeViewInfo> itr = statementTreeViewInfo.getStatements().iterator(); itr.hasNext();)
        	updateStatementTreeViewInfoState(state, (StatementTreeViewInfo)itr.next());
	}
}
