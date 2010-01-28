package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.AssemblerFilter;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class CreditCourseProposalAssemblerFilter implements AssemblerFilter<Data, Void> {


    @Override
    public void doAssembleFilter(FilterParamWrapper<Void> request, FilterParamWrapper<Data> response, AssembleFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doAssembleFilter(request, response);
    }

    @Override
    public void doDisassembleFilter(FilterParamWrapper<Data> request, FilterParamWrapper<Void> response, DisassembleFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doDisassembleFilter(request, response);
    }

    
    @Override
    public void doGetFilter(FilterParamWrapper<String> id, FilterParamWrapper<Data> response, GetFilterChain<Data, Void> chain) throws AssemblyException {
        //Metadata typeMetadata = chain.getManager().getTarget().getMetadata(CreditCourseProposalAssembler.CREDIT_COURSE_PROPOSAL_DATA_TYPE, "draft");
        
        
        chain.doGetFilter(id, response);
    }

    @Override
    public void doGetMetadataFilter(TypeStateFilterParamWrapper request, FilterParamWrapper<Metadata> response, GetMetadataFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doGetMetadataFilter(request, response);
    }

    @Override
    public void doSaveFilter(FilterParamWrapper<Data> request, FilterParamWrapper<SaveResult<Data>> response, SaveFilterChain<Data, Void> chain) throws AssemblyException {
        Data data = request.getValue();
        Metadata typeMetadata = chain.getManager().getTarget().getMetadata(CreditCourseProposalAssembler.CREDIT_COURSE_PROPOSAL_DATA_TYPE, "draft");
        List<QueryPath> dirtyPaths = AssemblerUtils.findDirtyElements(data);
        
        for(QueryPath path : dirtyPaths) {
            
            Metadata fieldMetadata = AssemblerUtils.get(typeMetadata, path);
            
            if(!fieldMetadata.isCanEdit()) {
                //throw new AssemblyException("User does not have edit permission for field");
            }
            
        }
        chain.doSaveFilter(request, response);
    }

    @Override
    public void doSearchFilter(FilterParamWrapper<SearchRequest> request, AssemblerFilter.FilterParamWrapper<SearchResult> response, SearchFilterChain<Data, Void> chain) {
        chain.doSearchFilter(request, response);
    }

    @Override
    public void doValidateFilter(FilterParamWrapper<Data> request, FilterParamWrapper<List<ValidationResultInfo>> response, ValidateFilterChain<Data, Void> chain) throws AssemblyException {
        chain.doValidateFilter(request, response);
    }

}
