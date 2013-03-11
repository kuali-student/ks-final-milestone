package org.kuali.rice.krms.service.impl;

import org.apache.strutsel.taglib.bean.ELSizeTag;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.container.PageGroup;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.impl.repository.AgendaBoService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.impl.repository.RuleBoService;
import org.kuali.rice.krms.keyvalues.RuleInstructionKeyValues;
import org.kuali.student.enrollment.class1.krms.builder.AgendaComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.AgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.rice.krms.keyvalues.RequisiteAgendaTypeKeyValues;
import org.kuali.rice.krms.keyvalues.RuleTypeKeyValues;
import org.kuali.rice.krms.service.AgendaManagementViewHelperService;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.krms.util.KSKRMSConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleManagementService ruleManagementService;
    private static TemplateRegistry templateRegistry;
    private AgendaEditor agendaEditor;

    private PageGroup page;

    @Override
    public void performInitialization(View view, Object model) {
        AgendaManagementForm form = (AgendaManagementForm) model;
        String agendaId = "10008";

        page = ComponentUtils.copy((PageGroup) form.getView().getPage());

        AgendaComponentBuilder agendaComponentBuilder = new AgendaComponentBuilder(form);
        form.getView().setPage(page);
        super.performInitialization(view, model);
    }

    @Override
    public TemplateInfo getTemplateForType(String type) {
        return this.getTemplateRegistry().getTemplateForType(type);
    }

    @Override
    public String getTermSpecNameForType(String type) {
        return this.getTemplateRegistry().getTermSpecNameForType(type);
    }

    @Override
    public String getOperationForType(String type) {
        return this.getTemplateRegistry().getOperationForType(type);
    }

    @Override
    public String getValueForType(String type) {
        return this.getTemplateRegistry().getValueForType(type);
    }

    @Override
    public ComponentBuilder getComponentBuilderForType(String type) {
        return this.getTemplateRegistry().getComponentBuilderForType(type);
    }

    private RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new RulePreviewTreeBuilder();
            previewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return previewTreeBuilder;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }
}
