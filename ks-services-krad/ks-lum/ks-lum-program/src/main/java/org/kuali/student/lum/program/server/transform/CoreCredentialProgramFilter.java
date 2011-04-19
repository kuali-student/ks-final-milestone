package org.kuali.student.lum.program.server.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.assembly.transform.AbstractDataFilter;
import org.kuali.student.core.assembly.transform.DataBeanMapper;
import org.kuali.student.core.assembly.transform.DefaultDataBeanMapper;
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
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.dto.CoreProgramInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;
import org.kuali.student.lum.program.service.ProgramService;

/**
 * Add/remove the related CredentialPrograms titles to/from Program data (for display purposes only, as the data is obviously
 * not persisted).
 * 
 * @author Jim
 */
public class CoreCredentialProgramFilter extends AbstractDataFilter {

    private MetadataServiceImpl metadataService;
    private ProgramService programService;
    private LuService luService;
    private final DataBeanMapper mapper = new DefaultDataBeanMapper();
    private final Metadata credPgmMetadata = null;

    /**
     * Remove CredentialPrograms titles
     */
    @Override
    public void applyInboundDataFilter(Data data, Metadata metadata,
                                       Map<String, Object> properties) throws Exception {
        // remove the list of CredentialPrograms from the data passed in
        data.remove(new Data.StringKey(ProgramConstants.CREDENTIAL_PROGRAMS));
    }

    /**
     * Add the related CredentialPrograms' longTitle
     */
    @Override
    public void applyOutboundDataFilter(Data data, Metadata metadata,
                                        Map<String, Object> properties) throws Exception {

        String coreProgramId = data.get(ProgramConstants.ID);
        Data credPgmData = findCredentialTitles(coreProgramId);

        if (credPgmData != null) {
            // Add the Credential Programs' titles to the data passed in
            data.set(ProgramConstants.CREDENTIAL_PROGRAMS, credPgmData);
        }
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    public void setLuService(LuService luService) {
        this.luService = luService;
    }

    private Data findCredentialTitles(String coreProgramId) throws MissingParameterException, InvalidParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        	    SearchRequest request = new SearchRequest();

        //TODO find a better way to get search, param and resultcolumn names

        Data result = new Data();

	    request.setSearchKey("lu.search.luByRelation");

        List<SearchParam> searchParams = new ArrayList<SearchParam>();
        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalRelatedCluId");
        qpv1.setValue(coreProgramId);
        SearchParam qpv2 = new SearchParam();
        qpv1.setKey("lu.queryParam.luOptionalRelationType");
        qpv1.setValue(ProgramConstants.HAS_CORE_PROGRAM);

        searchParams.add(qpv1);
        searchParams.add(qpv2);

        request.setParams(searchParams);

        SearchResult searchResult = luService.search(request);
        if (searchResult.getRows().size() > 0) {
            for(SearchResultRow srrow : searchResult.getRows()){
                List<SearchResultCell> srCells = srrow.getCells();
                if(srCells != null && srCells.size() > 0){
                    for(SearchResultCell srcell : srCells){
                        if (srcell.getKey().equals("lu.resultColumn.luOptionalLongName")) {
                            result.add(srcell.getValue());
                        }
                    }
                }
            }
        }
        return result;
    }

}
