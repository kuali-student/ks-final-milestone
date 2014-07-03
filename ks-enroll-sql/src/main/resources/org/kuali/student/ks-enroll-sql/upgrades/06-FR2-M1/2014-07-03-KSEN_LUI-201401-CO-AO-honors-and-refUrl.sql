-- KSAP-1552: Ref Data for Course Search: 2014 CO/AO honors flags, and course-website-url
--CHEM232 Honors flag
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='70b17334-cc22-46f6-b2aa-6608d234b381'
/
--ENGL391 Honors
update KSEN_LUI_LU_CD set  UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='cf1f256f-bed9-439f-b3e4-ed136ac58895'
/
--ENGL269X honors
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='e04de2db-05f4-4845-ab36-86199e4f9ab8'
/
--PHYS499A
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='957abc70-0eaf-4698-a7a6-ecbef7227ecf'
/
--CHEM231 - A: AO url
update KSEN_LUI set UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-02 21:28:00.00',
REF_URL='https://www.google.com'
where ID='46a787ab-83b3-4abf-bad0-27251882f7d3'
/
--CHEM231 - A: AO honors
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='46a787ab-83b3-4abf-bad0-27251882f7d3'
/
--HIST332 - A: AO honors
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='f242e00a-0f58-4a6e-a789-3ed39c8f4907'
/
--PHYS441 - A: AO honors
update KSEN_LUI_LU_CD set UPDATETIME=TIMESTAMP '2014-07-02 11:19:07.365',updateId='admin',value='true'
where LUI_LUCD_TYPE='kuali.lu.code.honorsOffering'
and LUI_ID='8ee0778c-00bd-4e70-a16e-680d87a0bebe'
/