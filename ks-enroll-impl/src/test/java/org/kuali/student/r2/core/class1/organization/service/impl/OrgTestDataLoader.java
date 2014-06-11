package org.kuali.student.r2.core.class1.organization.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/11/14
 * Time: 3:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrgTestDataLoader {

    private OrganizationService organizationService;

    public OrgTestDataLoader(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    public void loadData(){
        this.loadOrg("1", "kuali.org.CorporateEntity", "Kuali University System", "KUSystem");
        this.loadOrg("3", "kuali.org.Office", "Chancellor''s Office", "ChancellorsOffice");
        this.loadOrg("4", "kuali.org.CorporateEntity", "Kuali University", "KU");
        this.loadOrg("5", "kuali.org.Board", "Board of Trustees", "Trustees");
        this.loadOrg("6", "kuali.org.Office", "Office of the President", "PresidentsOffice");
        this.loadOrg("7", "kuali.org.AdvisoryGroup", "President''s Cabinet for College-Wide Planning", "PresidentsCabinet");
        this.loadOrg("8", "kuali.org.AdvisoryGroup", "President''s Council for College-Wide Planning", "PresidentsCouncil");
    }

    public void loadOrg(String id, String typeKey, String longName, String shortName){

        OrgInfo orgInfo = new OrgInfo();
        orgInfo.setId(id);
        orgInfo.setTypeKey(typeKey);
        orgInfo.setLongName(longName);
        orgInfo.setShortName(shortName);

        try {
            organizationService.createOrg(orgInfo.getTypeKey(), orgInfo, this.getContextInfo());
        } catch (DataValidationErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DoesNotExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (OperationFailedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PermissionDeniedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadOnlyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private ContextInfo getContextInfo(){
        ContextInfo context = new ContextInfo();
        context.setPrincipalId(OrgTestDataLoader.class.getSimpleName());
        context.setCurrentDate(new Date());
        return context;
    }

    public OrganizationService getOrganizationService() {
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }
}
