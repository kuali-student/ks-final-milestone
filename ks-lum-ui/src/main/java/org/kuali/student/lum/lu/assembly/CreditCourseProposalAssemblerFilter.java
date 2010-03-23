package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.core.assembly.PassThroughAssemblerFilter;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;

public class CreditCourseProposalAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {


    @Override
    public void doSaveFilter(FilterParamWrapper<Data> request, FilterParamWrapper<SaveResult<Data>> response, SaveFilterChain<Data, Void> chain) throws AssemblyException {
        Data data = request.getValue();
        CreditCourseProposalHelper help = CreditCourseProposalHelper.wrap(data);
        String idType = "proposalId";
        String proposalId = help.getProposal().getId();
        String state = help.getState();
        Metadata typeMetadata = chain.getManager().getTarget().getMetadata(idType, proposalId, CreditCourseProposalAssembler.CREDIT_COURSE_PROPOSAL_DATA_TYPE, state);
        if(typeMetadata != null && !typeMetadata.isCanEdit()) {
            throw new AssemblyException("Document is read only");
        }
        
        List<QueryPath> dirtyPaths = AssemblerUtils.findDirtyElements(data);
        
        for(QueryPath path : dirtyPaths) {
            
            Metadata fieldMetadata = AssemblerUtils.get(typeMetadata, path);
            
            if(!fieldMetadata.isCanEdit()) {
                throw new AssemblyException("User does not have edit permission for field");
            }
            
        }
        chain.doSaveFilter(request, response);
        
    }

}
