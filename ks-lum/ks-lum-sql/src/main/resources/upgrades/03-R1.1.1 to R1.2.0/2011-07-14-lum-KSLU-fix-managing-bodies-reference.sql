UPDATE
    KSLU_CLU_ADMIN_ORG
SET
    ORG_ID='65'
WHERE
    CLU_ID='REFERENCEPROG-BSCI-GENB'
AND TYPE IN('kuali.adminOrg.type.CurriculumOversightUnit',
    'kuali.adminOrg.type.StudentOversightUnit','kuali.adminOrg.type.DeploymentUnit',
    'kuali.adminOrg.type.FinancialResourcesUnit','kuali.adminOrg.type.FinancialControlUnit')
/
    