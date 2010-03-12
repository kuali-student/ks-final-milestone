package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.core.assembly.PassThroughAssemblerFilter;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.util.AssemblerUtils;

public class CreditCourseProposalAssemblerFilter extends PassThroughAssemblerFilter<Data, Void> {


    @Override
    public void doSaveFilter(FilterParamWrapper<Data> request, FilterParamWrapper<SaveResult<Data>> response, SaveFilterChain<Data, Void> chain) throws AssemblyException {
        Data data = request.getValue();
        Metadata typeMetadata = chain.getManager().getTarget().getMetadata(null, null, CreditCourseProposalAssembler.CREDIT_COURSE_PROPOSAL_DATA_TYPE, "draft");
        List<QueryPath> dirtyPaths = AssemblerUtils.findDirtyElements(data);
        
        for(QueryPath path : dirtyPaths) {
            
            Metadata fieldMetadata = AssemblerUtils.get(typeMetadata, path);
            
            if(!fieldMetadata.isCanEdit()) {
                //throw new AssemblyException("User does not have edit permission for field");
            }
            
        }
        chain.doSaveFilter(request, response);
    }

}
