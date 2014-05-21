-- Creating maintenance document for the ref data proposals
insert into KRNS_DOC_HDR_T (DOC_HDR_ID,FDOC_DESC,OBJ_ID,VER_NBR) VALUES('3011','Intro to Biology',SYS_GUID(),1)
/
insert into KRNS_DOC_HDR_T (DOC_HDR_ID,FDOC_DESC,OBJ_ID,VER_NBR) VALUES('3012','Writing for Teachers',SYS_GUID(),1)
/
insert into KRNS_DOC_HDR_T (DOC_HDR_ID,FDOC_DESC,OBJ_ID,VER_NBR) VALUES('3013','Modify: Special Projects',SYS_GUID(),1)
/
insert into KRNS_DOC_HDR_T (DOC_HDR_ID,FDOC_DESC,OBJ_ID,VER_NBR) VALUES('3014','Modify: Biology of Cancer',SYS_GUID(),1)
/
insert into KRNS_MAINT_DOC_T (DOC_HDR_ID,OBJ_ID,VER_NBR) VALUES ('3011',SYS_GUID(),1)
/
insert into KRNS_MAINT_DOC_T (DOC_HDR_ID,OBJ_ID,VER_NBR) VALUES ('3012',SYS_GUID(),1)
/
insert into KRNS_MAINT_DOC_T (DOC_HDR_ID,OBJ_ID,VER_NBR) VALUES ('3013',SYS_GUID(),1)
/
insert into KRNS_MAINT_DOC_T (DOC_HDR_ID,OBJ_ID,VER_NBR) VALUES ('3014',SYS_GUID(),1)
/
-- Update ref proposal to use the workflow/maintenace doc id.
update KREW_DOC_HDR_T set DOC_HDR_ID='3011' where DOC_HDR_ID='KS-KREW-DOC-HDR-1000'
/
update KREW_DOC_HDR_T set DOC_HDR_ID='3012' where DOC_HDR_ID='KS-KREW-DOC-HDR-1001'
/
update KREW_DOC_HDR_T set DOC_HDR_ID='3013' where DOC_HDR_ID='KS-KREW-DOC-HDR-1002'
/
update KREW_DOC_HDR_T set DOC_HDR_ID='3014' where DOC_HDR_ID='KS-KREW-DOC-HDR-1003'
/