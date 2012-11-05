package org.kuali.student.lum.program.server.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.transform.AbstractDataFilter;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.lum.program.client.ProgramConstants;

/**
 * Add/remove the related CredentialPrograms titles to/from Program data (for display
 * purposes only, as the data is obviously not persisted).
 * 
 * @author Jim
 */
public class CoreCredentialProgramFilter extends AbstractDataFilter {

    private CluService cluService;

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

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    private Data findCredentialTitles(String coreProgramId) throws MissingParameterException,
            InvalidParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        SearchRequestInfo request = new SearchRequestInfo();

        //TODO find a better way to get search, param and resultcolumn names

        Data result = new Data();

        request.setSearchKey("lu.search.luByRelation");

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        SearchParamInfo qpv1 = new SearchParamInfo();
        qpv1.setKey("lu.queryParam.luOptionalRelatedCluId");
        qpv1.getValues().add(coreProgramId);
        SearchParamInfo qpv2 = new SearchParamInfo();
        qpv2.setKey("lu.queryParam.luOptionalRelationType");
        qpv2.getValues().add(ProgramConstants.HAS_CORE_PROGRAM);

        searchParams.add(qpv1);
        searchParams.add(qpv2);

        request.setParams(searchParams);

        SearchResultInfo searchResult = null;
        searchResult = cluService.search(request, ContextUtils.getContextInfo());
        if (searchResult.getRows().size() > 0) {
            for (SearchResultRowInfo srrow : searchResult.getRows()) {
                List<SearchResultCellInfo> srCells = srrow.getCells();
                if (srCells != null && srCells.size() > 0) {
                    for (SearchResultCellInfo srcell : srCells) {
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
