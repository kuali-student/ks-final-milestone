package org.kuali.student.lum.workflow;

import java.util.Iterator;
import java.util.List;

import org.kuali.student.r1.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
 
/**
 * 
 * This class contains common utility methods used with statements.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class StatementUtil {
    
    /**
     * WARNING: this constant is also declared in ProgramRequirementsSummaryView.  We cannot use
     * the one from ProgramRequirementsSummaryView because we cannot compile it into this
     * package.  
     * 
     * TODO: create a constants class that can be shared across view, server, etc.
     */
    public static final String NEW_STMT_TREE_ID = "NEWSTMTTREE";
    
    /**
     * WARNING: this constant is also declared in ProgramRequirementsSummaryView.  We cannot use
     * the one from ProgramRequirementsSummaryView because we cannot compile it into this
     * package.  
     * 
     * TODO: create a constants class that can be shared across view, server, etc.
     */
    public static final String NEW_REQ_COMP_ID = "NEWREQCOMP";
	
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
 	   /**
	    * This method strips the statement ids from the statement tree prior to saving
	    * the tree, which allows the web service to generate new ids from the database.
	    * <p>
	    * The UI needs to create temporary ids in order to work with the statements
	    * in memory prior to storing them in the database.  On save, we strip
	    * the temporary ids in this method, then write the statement tree to the database,
	    * allowing the database to generate proper ids.
	    * <p>
	    * 
	    * @param tree
	    */
 	   public static void stripStatementIds(StatementTreeViewInfo tree) {
	        List<StatementTreeViewInfo> statements = tree.getStatements();
	        List<ReqComponentInfo> reqComponentInfos = tree.getReqComponents();
	        
	        // If there are no statements in the tree, set its ID to null
	        if ((tree.getId() != null) && (tree.getId().indexOf(NEW_STMT_TREE_ID) >= 0)) {
	            tree.setId(null);
	        }
	        
	        // If there are statements in the tree
	        if ((statements != null) && (statements.size() > 0)) {
	            
	            // retrieve all statements, loop over them, and recursively strip their IDs
 	            for (StatementTreeViewInfo statement : statements) {
	                stripStatementIds(statement); // inside set the children of this statementTreeViewInfo
	            }
	        } else if ((reqComponentInfos != null) && (reqComponentInfos.size() > 0)) {
	            // If there are requirements, loop over them and set their IDs to null
	            for (ReqComponentInfo reqComponent : reqComponentInfos) {
	                if ((reqComponent.getId() != null) && (reqComponent.getId().indexOf(NEW_REQ_COMP_ID) >= 0)) {
	                    reqComponent.setId(null);
	                }
	                // Also set the field IDs to null
	                for (ReqCompFieldInfo field : reqComponent.getReqCompFields()) {
	                    field.setId(null);
	                }
	                 
	            }
	        }
	    }
}
