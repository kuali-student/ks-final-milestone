package org.kuali.student.krms.naturallanguage;

import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinitionContract;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.impl.repository.PropositionBo;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/02/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class KRMSDataGenerator {

    public PropositionDefinition getPropositionForId(String id){

        //INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES
        //        ('TEST-REQCOMP-1', 'CREATEID', null, 'UPDATEID', null, 1, null, 'ACTIVE', null, null, 'kuali.reqComponent.type.test')
        //INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE,VER_NBR) VALUES ('TEST-FIELD-1', 'kuali.reqComponent.field.type.org.id', '50',0)


        //if("TEST-PROP-1".equals(id)){
            // Proposition for rule 2
            //PropositionDefinition.Builder propositionDefBuilder1 =
            //        PropositionDefinition.Builder.create(null, PropositionType.SIMPLE.getCode(), parentRule.getId(),
            //                null, Collections.<PropositionParameter.Builder>emptyList());
            //propositionDefBuilder1.setDescription("Test Proposition 1");

            // PropositionParams for rule 2
            //List<PropositionParameter.Builder> propositionParams1 = params.build();


            // set the parent proposition so the builder will not puke
            //for (PropositionParameter.Builder propositionParamBuilder : propositionParams1) {
            //    propositionParamBuilder.setProposition(propositionDefBuilder1);
            //}

            //propositionDefBuilder1.setParameters(propositionParams1);

            //return propositionDefBuilder1.build();
        //}
        return null;
    }

    public KrmsTypeDefinition getKrmsTypeForId(String id){
        return null;
    }


    public static TermParameterDefinitionContract createTermParameterDefinition(final String id,final String name,final String value, final String termID, final Long versionNumber) {
         return new TermParameterDefinitionContract(){


            @Override
            public String getTermId() {
                return termID;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }
        };

    }

    public static TermDefinitionContract createTermDefinition(final TermSpecificationDefinitionContract termSpecification, final String description, final List<? extends TermParameterDefinitionContract> parameters, final String id, final Long versionNumber){
        return new TermDefinitionContract() {
            @Override
            public TermSpecificationDefinitionContract getSpecification() {
                return termSpecification;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public List<? extends TermParameterDefinitionContract> getParameters() {
                return parameters;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }
        };
    }
}
