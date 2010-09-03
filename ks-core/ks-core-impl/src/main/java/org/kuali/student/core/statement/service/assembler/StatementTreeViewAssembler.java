package org.kuali.student.core.statement.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
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
import org.kuali.student.core.statement.dto.AbstractStatementInfo;
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

		BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo> result = new BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo>(null);
		try {
			disassembleStatementTreeHelper(newTree, operation, null, result);
		} catch (Exception e) {
			throw new AssemblyException("Problems dissasembling StatementTreeView", e);
		}
		return result;
	}

	private void disassembleStatementTreeHelper(StatementTreeViewInfo statementTreeViewInfo, NodeOperation operation, String parentId, BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo> result) throws CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
		if (statementTreeViewInfo == null) {
			return;
		}

		StatementInfo statement = new StatementInfo();
		copyValues(statement, statementTreeViewInfo);
		statement.setId(UUIDHelper.genStringUUID(statement.getId()));
		statement.setParentId(parentId);

		if (statementTreeViewInfo.getReqComponents() != null) {
			for (ReqComponentInfo reqComponentInfo : statementTreeViewInfo.getReqComponents()) {
				BaseDTOAssemblyNode<StatementTreeViewInfo, ReqComponentInfo> reqResult = new BaseDTOAssemblyNode<StatementTreeViewInfo, ReqComponentInfo>(null);
				String reqId = UUIDHelper.genStringUUID(reqComponentInfo.getId());
				reqComponentInfo.setId(reqId);
				reqResult.setNodeData(reqComponentInfo);
				reqResult.setOperation(operation);
				result.getChildNodes().add(reqResult);
				statement.getReqComponentIds().add(reqId);
			}
		}

		BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo> statementResult = new BaseDTOAssemblyNode<StatementTreeViewInfo, StatementInfo>(null);

		statementResult.setNodeData(statement);
		statementResult.setBusinessDTORef(statementTreeViewInfo);
		statementResult.setOperation(operation);
		result.getChildNodes().add(statementResult);

		for (StatementTreeViewInfo subStatement : statementTreeViewInfo.getStatements()) {
			disassembleStatementTreeHelper(subStatement, operation, statementTreeViewInfo.getId(),result);
		}
	}

    /**
     * @deprecated
     *
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
       copyValues(statementInfo, statementTreeViewInfo);
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
     * @param to
     * @param from
     */
    public static void copyValues(final AbstractStatementInfo to, AbstractStatementInfo from) {
        to.setAttributes(from.getAttributes());
        to.setDesc(from.getDesc());
        to.setId(from.getId());
        to.setMetaInfo(from.getMetaInfo());
        to.setName(from.getName());
        to.setOperator(from.getOperator());
        to.setState(from.getState());
        to.setType(from.getType());
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
