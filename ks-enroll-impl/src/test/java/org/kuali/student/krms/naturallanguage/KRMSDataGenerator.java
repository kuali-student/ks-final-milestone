package org.kuali.student.krms.naturallanguage;

import org.kuali.rice.krms.api.repository.category.CategoryDefinitionContract;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplateContract;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsageContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.term.TermDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinitionContract;
import org.kuali.rice.krms.api.repository.type.KrmsTypeAttributeContract;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinitionContract;

import java.util.List;
import java.util.Map;
import org.kuali.rice.krms.api.repository.term.TermDefinition;

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

    public static TermSpecificationDefinitionContract createTermSpecificationDefinition(final String name, final String namespace, final String type, final String description, final List<? extends CategoryDefinitionContract> categories, final List<String> contextIds, final String id, final boolean isActive, final Long verNumber){
        return new TermSpecificationDefinitionContract() {

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getNamespace() {
                return namespace;
            }

            @Override
            public String getType() {
                return type;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public List<? extends CategoryDefinitionContract> getCategories() {
                return categories;
            }

            @Override
            public List<String> getContextIds() {
                return contextIds;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public boolean isActive() {
                return isActive;
            }

            @Override
            public Long getVersionNumber() {
                return verNumber;
            }
        };
    }

    public static PropositionDefinitionContract createPropositionDefinition(final String description, final String typeId, final String ruleId, final String propTypeCode, final List<? extends PropositionParameterContract> parameters, final String compOpCode, final Integer compSeqNo, final List<? extends PropositionDefinitionContract> compComponents, final String id, final Long verNumber){
        return new PropositionDefinitionContract() {
            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getTypeId() {
                return typeId;
            }

            @Override
            public String getRuleId() {
                return ruleId;
            }

            @Override
            public String getPropositionTypeCode() {
                return propTypeCode;
            }

            @Override
            public List<? extends PropositionParameterContract> getParameters() {
                return parameters;
            }

            @Override
            public String getCompoundOpCode() {
                return compOpCode;
            }

            @Override
            public Integer getCompoundSequenceNumber() {
                return compSeqNo;
            }

            @Override
            public List<? extends PropositionDefinitionContract> getCompoundComponents() {
                return compComponents;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public Long getVersionNumber() {
                return verNumber;
            }
        };
    }

    public static PropositionParameterContract createPropositionParameter(final String propId, final String value, final String parameterTypeCode, final Integer sequenceNumber, final String id, final Long versionNumber){
        return new  PropositionParameterContract(){


            @Override
            public String getPropId() {
                return propId;
            }


            @Override
            public String getValue() {
                return value;
            }


            @Override
            public String getParameterType() {
                return parameterTypeCode;
            }


            @Override
            public Integer getSequenceNumber() {
                return sequenceNumber;
            }


            @Override
            public String getId() {
                return id;
            }


            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }

            @Override
            public TermDefinition getTermValue() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
            
        };
    }

    public static KrmsTypeDefinitionContract createKrmsTypeDefinition(final String name, final String namespace, final String servicename, final List<? extends KrmsTypeAttributeContract> attributes, final String id, final boolean active, final Long versionNumber) {
        return new KrmsTypeDefinitionContract() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getNamespace() {
                return namespace;
            }

            @Override
            public String getServiceName() {
                return servicename;
            }

            @Override
            public List<? extends KrmsTypeAttributeContract> getAttributes() {
                return attributes;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public boolean isActive() {
                return active;
            }

            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }
        };
    }

    public static NaturalLanguageUsageContract createNaturalLanguageUsage(final String description, final String name, final String namespace, final String id, final boolean active,final Long versionNumber){
        return new NaturalLanguageUsageContract() {
            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getNamespace() {
                return namespace;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public boolean isActive() {
                return active;
            }

            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }
        };
    }

    public static NaturalLanguageTemplateContract createNaturalLanguageTemplate(final String languageCode, final String naturalLanguageUsageId, final String typeId, final String template, final Map<String, String> attributes, final String id, final boolean active, final Long versionNumber){
        return new NaturalLanguageTemplateContract() {
            @Override
            public String getLanguageCode() {
                return languageCode;
            }

            @Override
            public String getNaturalLanguageUsageId() {
                return naturalLanguageUsageId;
            }

            @Override
            public String getTypeId() {
                return typeId;
            }

            @Override
            public String getTemplate() {
                return template;
            }

            @Override
            public Map<String, String> getAttributes() {
                return attributes;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public boolean isActive() {
                return active;
            }

            @Override
            public Long getVersionNumber() {
                return versionNumber;
            }
        };
    }
}
