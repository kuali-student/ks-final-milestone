package org.kuali.student.core.statement.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.service.impl.BaseAssembler;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;


/**
 * CRUD operations for StatementTreeViewInfo
 * <p/>
 * NOTE: the Natural Language field is NOT filled in. This has be done further up the calling stack.
 *
 * @author glindholm
 *
 */
public class StatementTreeViewAssembler extends BaseAssembler implements BOAssembler<StatementTreeViewInfo, StatementInfo>{
    final static Logger LOG = Logger.getLogger(StatementTreeViewAssembler.class);

    private StatementService statementService;

	@Override
	public StatementTreeViewInfo assemble(StatementInfo statementInfo, StatementTreeViewInfo treeViewInfo,
			boolean shallowBuild)
			throws AssemblyException {

		StatementTreeViewInfo treeInfo = (treeViewInfo == null ? new StatementTreeViewInfo() : treeViewInfo);

		try {
			getStatementTreeViewHelper(statementInfo, treeInfo, shallowBuild);
		} catch (DoesNotExistException e) {
			throw new AssemblyException(e);
		} catch (InvalidParameterException e) {
			throw new AssemblyException(e);
		} catch (MissingParameterException e) {
			throw new AssemblyException(e);
		} catch (OperationFailedException e) {
			throw new AssemblyException(e);
		}

		return treeInfo;
	}

	@Override
	public BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo> disassemble(
			StatementTreeViewInfo newTree, NodeOperation operation)
			throws AssemblyException {

		if (newTree == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("StatementTreeView to disassemble is null!");
			throw new AssemblyException("StatementTreeView can not be null");
		}


		StatementTreeViewInfo origTree = null;
		StatementInfo statement = null;
		if (NodeOperation.UPDATE == operation) {
			try {
				statement =  statementService.getStatement(newTree.getId());
	        } catch (Exception e) {
				throw new AssemblyException("Error getting existing Statement unit during statementTreeView update", e);
	        }
			try {
				origTree = new StatementTreeViewInfo();
				assemble(statement, origTree, false);
			} catch (AssemblyException e) {
				origTree = null; // Treat is a create
				operation = NodeOperation.CREATE;
			}
		}


        try {
	        // insert statements and reqComponents if they do not already exists in database
	        updateSTVHelperCreateStatements(newTree);
	        // check the two lists of relationships for ones that need to be deleted/created
	        if (origTree != null) {
	            List<String> toBeDeleted = notIn(origTree, newTree);
	            for (String delStatementId : toBeDeleted) {
	            	statementService.deleteStatement(delStatementId);
	            }
	        }
	        updateStatementTreeViewHelper(newTree);

	        BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo> result = new BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo>(this);
	        result.setBusinessDTORef(newTree);
	        result.setNodeData(statement);
	        result.setOperation(operation);
			return result;

		} catch (Exception e) {
			throw new AssemblyException("Error updating statement tree view:" + e.getMessage(), e);
        }
	}

    /**
     * Goes through the list of statementIds in statementInfo and retrieves all
     * information regarding to the current statementInfo and all the
     * sub-statements referenced by statementIds.  Data will be populated into
     * statementTreeViewInfo
     * @param statementInfo
     * @return void
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    private void getStatementTreeViewHelper(final StatementInfo statementInfo, final StatementTreeViewInfo statementTreeViewInfo,
    		final boolean shallowBuild)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        if (statementInfo == null) return;

        copyValues(statementTreeViewInfo, statementInfo);
        statementTreeViewInfo.setReqComponents(getReqComponentInfos(statementInfo));
        // get statements recursively and convert them into statementTreeViewInfo
        for (String statementId : statementInfo.getStatementIds()) {
        	StatementInfo subStatement = statementService.getStatement(statementId);

        	List<StatementTreeViewInfo> statements =
        		(statementTreeViewInfo.getStatements() == null) ? new ArrayList<StatementTreeViewInfo>() : statementTreeViewInfo.getStatements();
        		StatementTreeViewInfo subStatementTreeViewInfo = new StatementTreeViewInfo();

        		if (!shallowBuild) {
        			// recursive call to get subStatementTreeViewInfo
        			getStatementTreeViewHelper(subStatement, subStatementTreeViewInfo, shallowBuild);
        		}
        		statements.add(subStatementTreeViewInfo);
        		statementTreeViewInfo.setStatements(statements);
        }

    }


    /**
    *
    * @return a list of relationships in the first list but not in the second
    */
   private List<String> notIn(
           StatementTreeViewInfo oldTree,
           StatementTreeViewInfo newTree) {
       List<String> results = new ArrayList<String>(17);
       List<String> oldStatementIds = new ArrayList<String>(17);
       List<String> newStatementIds = new ArrayList<String>(17);
       getStatementIds(oldTree, oldStatementIds);
       getStatementIds(newTree, newStatementIds);
       if (oldStatementIds != null) {
           for (String oldStatementId : oldStatementIds) {
               boolean inNewStatementIds = false;
               if (newStatementIds != null) {
                   for (String newStatementId : newStatementIds) {
                       if (oldStatementId.equals(newStatementId)) {
                           inNewStatementIds = true;
                       }
                   }
               }
               if (!inNewStatementIds) {
                   results.add(oldStatementId);
               }
           }
       }
       return results;
   }

   private void getStatementIds(StatementTreeViewInfo statementTreeViewInfo, List<String> statementIds) {
       if (statementTreeViewInfo.getStatements() != null) {
           for (StatementTreeViewInfo subTree : statementTreeViewInfo.getStatements()) {
               getStatementIds(subTree, statementIds);
           }
       }
       statementIds.add(statementTreeViewInfo.getId());
   }

   private void updateStatementTreeViewHelper(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
       if (statementTreeViewInfo.getStatements() != null) {
           for (StatementTreeViewInfo subStatement : statementTreeViewInfo.getStatements()) {
               subStatement.setParentId(statementTreeViewInfo.getId());
               updateStatementTreeViewHelper(subStatement);
           }
       }
       if (statementTreeViewInfo.getReqComponents() != null) {
           List<ReqComponentInfo> updatedReqComponentInfos = new ArrayList<ReqComponentInfo>(7);
           for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
               ReqComponentInfo updatedReqComponentInfo = statementService.updateReqComponent(reqComponentInfo.getId(), reqComponentInfo);
               updatedReqComponentInfos.add(updatedReqComponentInfo);
           }
           statementTreeViewInfo.setReqComponents(updatedReqComponentInfos);
       }
       StatementInfo updatedStatementInfo = statementService.updateStatement(statementTreeViewInfo.getId(), toStatementInfo(
               statementTreeViewInfo));
       copyValues(statementTreeViewInfo, updatedStatementInfo);
   }

   private StatementInfo toStatementInfo(final StatementTreeViewInfo statementTreeViewInfo) {
       StatementInfo statementInfo = null;
       if (statementTreeViewInfo == null) return null;
       statementInfo = new StatementInfo();
       statementInfo.setAttributes(statementTreeViewInfo.getAttributes());
       statementInfo.setDesc(statementTreeViewInfo.getDesc());
       statementInfo.setId(statementTreeViewInfo.getId());
       statementInfo.setMetaInfo(statementTreeViewInfo.getMetaInfo());
       statementInfo.setName(statementTreeViewInfo.getName());
       statementInfo.setOperator(statementTreeViewInfo.getOperator());
       statementInfo.setParentId(statementTreeViewInfo.getParentId());
       // goes through the list of reqComponents in statementTreeViewInfo and extract the reqComponent ids
       if (statementTreeViewInfo.getReqComponents() != null) {
           List<String> reqCompIds = new ArrayList<String>(7);
           for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
               reqCompIds.add(reqComponentInfo.getId());
           }
           statementInfo.setReqComponentIds(reqCompIds);
       }
       statementInfo.setState(statementTreeViewInfo.getState());
       // TODO goes through the list of statements in statementTreeViewInfo and extract the statement ids
       if (statementTreeViewInfo.getStatements() != null) {
           List<String> statementIds = new ArrayList<String>(7);
           for (StatementTreeViewInfo subStatementTreeViewInfo : statementTreeViewInfo.getStatements()) {
               statementIds.add(subStatementTreeViewInfo.getId());
           }
           statementInfo.setStatementIds(statementIds);
       }
       statementInfo.setType(statementTreeViewInfo.getType());
       return statementInfo;
   }


   private void updateSTVHelperCreateStatements(StatementTreeViewInfo statementTreeViewInfo) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
       String statementId = null;
       StatementInfo origStatementInfo = null;
       StatementInfo newStatementInfo = null;
       if (statementTreeViewInfo.getStatements() != null) {
           for (StatementTreeViewInfo subTreeInfo : statementTreeViewInfo.getStatements()) {
               subTreeInfo.setParentId(statementTreeViewInfo.getId());
               updateSTVHelperCreateStatements(subTreeInfo);
           }
       }
       if (statementTreeViewInfo.getReqComponents() != null) {
           List<ReqComponentInfo> rcsAfterInserts = new ArrayList<ReqComponentInfo>(7);
           for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
               String reqComponentId = reqComponentInfo.getId();
               ReqComponentInfo origReqComponentInfo = null;
               ReqComponentInfo rcAfterInsert = null;
               // determine the original reqComponentInfo
               if (reqComponentId != null) {
                   try {
                       origReqComponentInfo = statementService.getReqComponent(reqComponentId);
                   } catch (DoesNotExistException dnee) {
                       origReqComponentInfo = null;
                   }
               }
               if (origReqComponentInfo == null) {
                   // The reqComponentInfo is a new one so create it
                   // the id here even if it is not null it is the temporary ids assigned by client
                   // so resets the id to null to allow a new id to be generated.
                   reqComponentInfo.setId(null);
                   try {
                       rcAfterInsert = statementService.createReqComponent(reqComponentInfo.getType(), reqComponentInfo);
                   } catch (AlreadyExistsException e) {
                       // shouldn't happen because of all the check that has been done up to this point
                       // if this exception is thrown it should be an error!
                       throw new OperationFailedException("Tried to create a reqComponent that already exists");
                   }
               } else {
                   rcAfterInsert = reqComponentInfo;
               }
               rcsAfterInserts.add(rcAfterInsert);
           }
           statementTreeViewInfo.setReqComponents(rcsAfterInserts);
       }
       // check if statementTreeViewInfo already exist if not create it.
       statementId = statementTreeViewInfo.getId();
       if (statementId != null) {
           try {
               origStatementInfo = statementService.getStatement(statementId);
           } catch(DoesNotExistException dnee) {
               origStatementInfo = null;
           }
       }
       if (origStatementInfo == null) {
           // the id here even if it is not null it is the temporary ids assigned by client
           // so resets the id to null to allow a new id to be generated.
           statementTreeViewInfo.setId(null);
           newStatementInfo = toStatementInfo(statementTreeViewInfo);
           try {
               newStatementInfo = statementService.createStatement(newStatementInfo.getType(), newStatementInfo);
           } catch (AlreadyExistsException e) {
               // shouldn't happen because of all the check that has been done up to this point
               // if this exception is thrown it should be an error!
               throw new OperationFailedException("Tried to create a statement that already exists");
           }
          copyValues(statementTreeViewInfo, newStatementInfo);
       }
   }


    /**
     * copies the values in statementInfo into statementTreeViewInfo.  Only the values of the root statement will
     * be affected.
     * @param statementTreeViewInfo
     * @param statementInfo
     */
    public static void copyValues(final StatementTreeViewInfo statementTreeViewInfo, StatementInfo statementInfo) {
        statementTreeViewInfo.setAttributes(statementInfo.getAttributes());
        statementTreeViewInfo.setDesc(statementInfo.getDesc());
        statementTreeViewInfo.setId(statementInfo.getId());
        statementTreeViewInfo.setMetaInfo(statementInfo.getMetaInfo());
        statementTreeViewInfo.setName(statementInfo.getName());
        statementTreeViewInfo.setOperator(statementInfo.getOperator());
        statementTreeViewInfo.setState(statementInfo.getState());
        statementTreeViewInfo.setType(statementInfo.getType());
    }

    /**
     * Goes through the list of reqComponentIds in statementInfo and retrieves the reqComponentInfos being referenced
     * @param statementInfo
     * @return list of reqComponentInfo referenced by the list of reqComponentIds in statementInfo
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     */
    private List<ReqComponentInfo> getReqComponentInfos(final StatementInfo statementInfo)
    	throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<ReqComponentInfo> reqComponentInfos = new ArrayList<ReqComponentInfo>();
        if (statementInfo == null) {
        	return null;
        }

        if (statementInfo.getReqComponentIds() != null) {
            for (String reqComponentId : statementInfo.getReqComponentIds()) {
                //ReqComponentInfo reqCompInfo = getReqComponent(reqComponentId);
            	ReqComponentInfo reqCompInfo = statementService.getReqComponent(reqComponentId);
            	reqComponentInfos.add(reqCompInfo);
            }
        }
        return reqComponentInfos;
    }

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}


}
