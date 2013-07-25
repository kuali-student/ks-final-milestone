package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.program.dto.CoreProgramInfo;
import org.kuali.student.r2.lum.program.service.ProgramService;

/**
 * @author Jim
 */
public class CoreProgramDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private CluService cluService;

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id,ContextInfo contextInfo) throws Exception {
    	if (id==null || id.isEmpty()){
            return findCurrentCoreProgram();
    	} else {
    		return programService.getCoreProgram(id,contextInfo);
    	}

    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties,ContextInfo contextInfo) throws Exception {
        if (dto instanceof CoreProgramInfo) {
            CoreProgramInfo cpInfo = (CoreProgramInfo) dto;
            if (cpInfo.getId() == null && cpInfo.getVersion() != null) {
            	String coreVersionIndId = cpInfo.getVersion().getVersionIndId();
            	cpInfo = programService.createNewCoreProgramVersion(coreVersionIndId, "New core program version", contextInfo);
            } else if (cpInfo.getId() == null) {
                cpInfo = programService.createCoreProgram(cpInfo.getTypeKey(), cpInfo,contextInfo);
            } else {
            	cpInfo = programService.updateCoreProgram(cpInfo.getId(), cpInfo.getTypeKey(), cpInfo, contextInfo);
            }
            return cpInfo;
        } else {
            throw new InvalidParameterException("Only persistence of CoreProgram is supported by this DataService implementation.");
        }
    }

    @Override
	protected List<ValidationResultInfo> validate(Object dto,ContextInfo contextInfo) throws Exception {
		return programService.validateCoreProgram("OBJECT", (CoreProgramInfo)dto,ContextUtils.getContextInfo());
	}
    
    @Override
    protected Class<?> getDtoClass() {
        return CoreProgramInfo.class;
    }

    private CoreProgramInfo findCurrentCoreProgram() throws MissingParameterException, InvalidParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        	    SearchRequestInfo request = new SearchRequestInfo();

        //TODO find a better way to get this search, param and resultcolumn names
        
        CoreProgramInfo core = new CoreProgramInfo();
        String coreProgramId = null;
	    request.setSearchKey("lu.search.mostCurrent.union");

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.getValues().add(ProgramClientConstants.CORE_PROGRAM);
        searchParams.add(qpv1);

        request.setParams(searchParams);

        SearchResultInfo searchResult = null;
        searchResult = cluService.search(request, ContextUtils.getContextInfo());
        if (searchResult.getRows().size() > 0) {
            for(SearchResultRowInfo srrow : searchResult.getRows()){
                List<SearchResultCellInfo> srCells = srrow.getCells();
                if(srCells != null && srCells.size() > 0){
                    for(SearchResultCellInfo srcell : srCells){
                        if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                            coreProgramId = srcell.getValue();
                            break;
                        }
                    }
                }
            }
        }
        if (coreProgramId != null) {
            core = programService.getCoreProgram(coreProgramId, ContextUtils.getContextInfo());
        }
        return core;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    
  
}
