update KSLU_CLU_IDENT set LNG_NAME = 'Baccalaureate', ST = 'Active', TYPE = 'kuali.lu.type.CreditCourse.identifier.official' where ID='4ab05feb-b550-4db6-9f65-97a74372a137'
/
insert into KSLU_CLU_IDENT (ID, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, OBJ_ID, VER_NBR) values ('82388fd5-6ee3-4936-9497-413Dea7e8098', 'Undergraduate', 'Core Program', 'CORE', 'Active', 'kuali.lu.type.CreditCourse.identifier.official', '643035B45C224BF698E8F32C2D5938C0', '0')
/
update KSLU_CLU set OFFIC_CLU_ID = '82388fd5-6ee3-4936-9497-413Dea7e8098' where ID = '00f5f8c5-fff1-4c8b-92fc-789b891e0849'
/
