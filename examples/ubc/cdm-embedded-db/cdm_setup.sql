set scan off;

delete KSLU_CLU_SET_JN_CLU
where clu_set_id in ('CLUSET-2', 'CLUSET-1');

 
delete KSLU_CLU_SET_JN_CLU_SET
where clu_set_parent_id in ('CLUSET-2', 'CLUSET-1') or
      clu_set_child_id in ('CLUSET-2', 'CLUSET-1');

delete KSLU_CLU_SET 
where id in ('CLUSET-2', 'CLUSET-1');

-- Change KRU Hiearchy to load UBC Hierarchy
-- update when we switch workflow over to UBC Approval Hierarchy
update KSOR_ORG_HIRCHY
set root_org = '1030'
where id = 'kuali.org.hierarchy.Curriculum';

update KSOR_ORG_HIRCHY
set root_org = '0530b7be-a577-4a61-9bb2-6b7d0e105466'
where id = 'kuali.org.hierarchy.Main'; 

-- Update clusets name, change wording 'cluset' to Course Group
update KSLU_CLU_SET 
set name = replace(name, 'CluSet', 'Course Group')
where lower(name) like '%cluset%';

update KSLU_CLU_SET 
set name = replace(name, 'CLU Set', 'Course Group')
where lower(name) like '%clu set%';

update KSLU_CLU_SET 
set name = replace(name, 'Set', 'Course Group')
where lower(name) like '%set%';

-- remove these ids from the impex files
/**
delete from KSOR_ORG_ORG_RELTN
where id in ('cbadeb64-e2b1-4f0d-98ae-e09b579e8341', 'a464a3da-72db-4117-ae94-67e539e1c46f', '150689d2-06a0-4fd8-80bb-e284f2ed8bbb', '12b873fe-417b-46a3-a2e6-12750bda913e', '5a6242da-bc32-4cf8-90c3-5a57c2df7ca5', 'a4e0e31b-0f80-4b23-9e4b-830b91e221f2', 'd623b9c6-2261-41fc-9606-5627a1393fc6', 'd78abe61-fe88-4612-a99a-c7096a4dc17b', '65c551e3-2d69-4cc2-b88f-1c530504255e', 'd477b965-0a36-46f7-b3b2-e3e39c8d446b',  '981e6378-01df-45d3-9bf4-8a2411e2ecd5', 'd2fe1191-ad31-423d-8657-e672ca7656e5', '3883856a-3704-4399-98fc-1148246ed6fc', '3565ea39-0495-461b-ab81-ff085a6c3cd2', '5e669492-f938-4ade-bb8d-d5a28420f917', '066a112b-b6a8-4641-b616-d56ca52fe974', '07c1d8fa-190f-4347-9e7d-13f7a27172cf', '1d1157dc-f659-43ce-822c-93860176efc6', '2f205586-212d-4352-998a-fc8ac6315fc5', 'f5d8cb42-135f-4c60-a257-d62a049d3df3', 'aeaeddf7-1114-4e10-9caa-b12e4702304d', '20cd5baa-aaa0-4810-b123-1615923bec46');
**/

-- Remove extra ATPs
delete KSAP_DT_RANGE;

delete KSAP_MLSTN;

delete KSAP_ATP
where type not in ('kuali.atp.type.Spring', 'kuali.atp.type.Fall', 'kuali.atp.type.Winter', 'kuali.atp.type.Summer');


-- Remove North and South Campus
delete KSEM_CTX_JN_ENUM_VAL_T
where enum_val_id in ('33', '34');

delete KSEM_ENUM_VAL_T
where id in ('33', '34');


commit;