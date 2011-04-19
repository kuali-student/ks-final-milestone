package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.server.gwt.AbstractDataService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.client.ProgramClientConstants;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * @author Jim
 */
public class CoreProgramDataService extends AbstractDataService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;
    private LuService luService;

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected Object get(String id) throws Exception {
    	if (id==null || id.isEmpty()){
            return findCurrentCoreProgram();
    	} else {
    		return programService.getCoreProgram(id);
    	}

    }

    @Override
    protected Object save(Object dto, Map<String, Object> properties) throws Exception {
        if (dto instanceof CoreProgramInfo) {
            CoreProgramInfo cpInfo = (CoreProgramInfo) dto;
            if (cpInfo.getId() == null && cpInfo.getVersionInfo() != null) {
            	String coreVersionIndId = cpInfo.getVersionInfo().getVersionIndId();
            	cpInfo = programService.createNewCoreProgramVersion(coreVersionIndId, "New core program version");
            } else if (cpInfo.getId() == null) {
                cpInfo = programService.createCoreProgram(cpInfo);
            } else {
                cpInfo = programService.updateCoreProgram(cpInfo);
            }
            return cpInfo;
        } else {
            throw new InvalidParameterException("Only persistence of CoreProgram is supported by this DataService implementation.");
        }
    }

    @Override
    protected Class<?> getDtoClass() {
        return CoreProgramInfo.class;
    }

    private CoreProgramInfo findCurrentCoreProgram() throws MissingParameterException, InvalidParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        	    SearchRequest request = new SearchRequest();

        //TODO find a better way to get this search, param and resultcolumn names
        
        CoreProgramInfo core = new CoreProgramInfo();
        String coreProgramId = null;
	    request.setSearchKey("lu.search.mostCurrent.union");

        List<SearchParam> searchParams = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalType");
        qpv1.setValue(ProgramClientConstants.CORE_PROGRAM);
        searchParams.add(qpv1);

        request.setParams(searchParams);

        SearchResult searchResult = luService.search(request);
        if (searchResult.getRows().size() > 0) {
            for(SearchResultRow srrow : searchResult.getRows()){
                List<SearchResultCell> srCells = srrow.getCells();
                if(srCells != null && srCells.size() > 0){
                    for(SearchResultCell srcell : srCells){
                        if (srcell.getKey().equals("lu.resultColumn.cluId")) {
                            coreProgramId = srcell.getValue();
                            break;
                        }
                    }
                }
            }
        }
        if (coreProgramId != null) {
            core = programService.getCoreProgram(coreProgramId);
        }
        return core;
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }
}
