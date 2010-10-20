CREATE TABLE KSEM_ENUM_T 
    ( 
        ENUM_KEY VARCHAR2(255) NOT NULL, 
        DESCR VARCHAR2(255), 
        EFF_DT TIMESTAMP(6), 
        EXPIR_DT TIMESTAMP(6), 
        NAME VARCHAR2(255), 
        OBJ_ID VARCHAR2(36) default SYS_GUID() NOT NULL,
        VER_NBR NUMBER(8) default 1 NOT NULL,
        CONSTRAINT KSEM_ENUM_TP1 PRIMARY KEY (ENUM_KEY) 
    )
/
CREATE TABLE KSEM_ENUM_VAL_T 
    ( 
        ID VARCHAR2(255) NOT NULL, 
        ABBREV_VAL VARCHAR2(255), 
        CD VARCHAR2(255), 
        EFF_DT TIMESTAMP(6), 
        ENUM_KEY VARCHAR2(255), 
        EXPIR_DT TIMESTAMP(6), 
        SORT_KEY NUMBER(10), 
        VAL VARCHAR2(255),
        OBJ_ID VARCHAR2(36) default SYS_GUID() NOT NULL,
        VER_NBR NUMBER(8) default 1 NOT NULL,
        CONSTRAINT KSEM_ENUM_VAL_TP1 PRIMARY KEY (ID),
        CONSTRAINT KSEM_ENUM_VAL_TF1 FOREIGN KEY (ENUM_KEY) REFERENCES KSEM_ENUM_T (ENUM_KEY)
    )
/
CREATE SEQUENCE KSEM_ENUM_VAL_ID_S INCREMENT BY 1 START WITH 2020 NOMAXVALUE NOCYCLE NOCACHE ORDER
/

insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.enum.type.cip2010', 'CIP 2010', null, null, 'CIP Codes 2010', 1, 'FFC5781CDBC54534814882D543BEE556')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.enum.type.cip2000', 'CIP 2000', null, null, 'CIP Codes 2000', 1, 'FE04B55CD7B04474806C1E0971808955')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.enum.lu.program.level', 'Program Level', null, null, 'Program Level', 1, 'CA7FBC7D601E4FBDA208FE576A53101C')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.code.UniversityClassification', 'University Classification', null, null, 'University Classification', 1, '4C92FD95E82B4003A1F5B43AE1139DF8')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.finalExam.status', 'Final Exam Status', null, null, 'Final Exam Status', 1, 'E9E5BD07AE4A42109558D26FF770E07E')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.atptype.duration', 'ATP Durations', {ts '2000-01-01 00:00:00'}, {ts '2020-01-01 00:00:00'}, 'ATP Durations', 1, '03F18B857B0042ABBFAD1FB37DE9298C')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.campusLocation', 'Campus Location Enumeration', {ts '2000-01-01 00:00:00'}, {ts '2020-01-01 00:00:00'}, 'Campus Location Enumeration', 1, '95252826337D4F92A672FE12C14F6410')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.fee.feeType', 'Learning Unit Fee Type', {ts '2000-01-01 00:00:00'}, {ts '2020-01-01 00:00:00'}, 'Learning Unit Fee Type', 1, '46C073D6D6C941F6A73AD43EB310094F')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.fee.rateType', 'Learning Unit Rate Type', {ts '2000-01-01 00:00:00'}, {ts '2200-01-01 00:00:00'}, 'Learning Unit Rate Type', 1, '809046E6D0F9462388C74A7E5F9FF7E5')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.lu.subjectArea', 'Subject Area Enumeration', {ts '2000-01-01 00:00:00'}, {ts '2200-01-01 00:00:00'}, 'Subject Area Enumeration', 1, '5D13CD9FC83B48E0BE168D09D67E34B3')
/
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, VER_NBR, OBJ_ID) values ('kuali.state', 'KS Data States', {ts '2000-01-01 00:00:00'}, null, 'KS Data States', 1, '9C8E260A455D45DBAB952142EA760C94')
/


insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('1', 'ROBT', 'ROBT', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 1, 'Robotics', 1, '401977EB7E1F4533B807419857C76483')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('10', 'BOTA', 'BOTA', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 10, 'Botany', 1, '1BF3099FF84446CCB51A6B98D7650732')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('14', 'CSCI', 'CSCI', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 14, 'CompSci', 1, 'BA1D4AD3363F4201BBF404C0D4D2A7A8')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('15', 'EDUC', 'EDUC', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 15, 'Education', 1, '21104A768FAF4B43B36F82CF5D53FA13')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('16', 'ENGL', 'ENGL', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 16, 'English', 1, '90CE9F443A2F4B81A01E02E49096E2FD')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('25', 'MENG', 'MENG', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 25, 'Mechanical', 1, 'FB7B305494DA42959C8E6EC88C861363')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('26', 'MUSC', 'MUSC', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 26, 'Music', 1, '614A5A2876794C8CB730ECE6A537DB3B')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('27', 'PHIL', 'PHIL', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 27, 'Philosophy', 1, 'C29DA9DE8D6B476AA18A52D71A34F24E')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('28', 'POLI', 'POLI', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 28, 'PolySci', 1, '43DD3DD4E4F548B88E1771F1DB03DFF4')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('29', 'PSYC', 'PSYC', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 29, 'Psychology', 1, '984FEED826CF42A59DD0053F5F4034AD')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('17', 'FINA', 'FINA', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 17, 'Finance', 1, '6F7C05563E4F49249562569E18E5159F')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('18', 'FREN', 'FREN', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 18, 'French', 1, 'EDCF06BBB71B4DA5B34D9B34DD3B3729')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('19', 'GEOG', 'GEOG', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 19, 'Geography', 1, '0A80E3FDF0FD4932B26DFE187BA11C95')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('2', 'ROBT', 'ROBT', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 2, 'CompSci', 1, 'C61EB78ADE934962B2E8CB1DCCA6FF1D')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('20', 'GEOL', 'GEOL', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 20, 'Geology', 1, '7B14E09118C74EA4A6DBE6FC5D1C2A8C')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('23', 'LING', 'LING', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 23, 'Linguistics', 1, '03B3C8E735B94418A972E1DB6DDD999D')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('24', 'MARK', 'MARK', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 24, 'Marketing ', 1, 'EC464A804F324AD0A66B528F536E7AD3')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('11', 'CHEE', 'CHEE', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 11, 'Chemical', 1, '4F36D3E014F84226855FCF99F446B0A3')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('12', 'CHEM', 'CHEM', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 12, 'Chemistry', 1, 'F80C71ACC4FB4FA7955D3FA8617044C0')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('13', 'CIVI', 'CIVI', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 13, 'Civil', 1, '65305155CBD84730A08BE749CAD9D38D')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('4', 'AACH', 'AACH', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 4, 'Architecture', 1, '878841D1AC374C2182CA188147B6E698')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('40', 'draft.public', 'draft.public', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 40, 'Entered as draft but not yet submitted', 1, '576C154BA9F64FB38EC5EE80CE785E63')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('41', 'submitted', 'submitted', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 41, 'Submitted but not yet approved', 1, 'D62803C4F1C74A4482C846E6155AFEAC')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('42', 'withdrawn', 'withdrawn', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 42, 'Withdrawn (anytime before active)', 1, '5D24C389851F47C98CF8F0F1ABD93337')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('30', 'PUAD', 'PUAD', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 30, 'PubAdmin', 1, 'FDF7DCDA035940DFBC4DD7AA42FC0D43')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('31', 'SOCI', 'SOCI', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 31, 'Sociology', 1, 'F6818F0526C9427D9A813D9E6B7DA4D1')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('32', 'SOWK', 'SOWK', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 32, 'Social Work', 1, '93820C9D160B4C6DB1A90D1277FA30CD')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('33', 'North', 'NORTH', {ts '2000-01-01 00:00:00'}, 'kuali.lu.campusLocation', null, 31, 'North', 1, '0B0664FC55994549A9BD5CEF66D7BEB6')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('34', 'South', 'SOUTH', {ts '2000-01-01 00:00:00'}, 'kuali.lu.campusLocation', null, 32, 'South', 1, '19720600348B45CEA47B93204F66588A')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('38', 'template', 'template', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 38, 'A template holds configuration for defaults for creating a new course', 1, '7A34A95409774612B68E5DA6CD13C483')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('39', 'draft.private', 'draft.private', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 39, 'Exploratory/Private scratch pad', 1, '155D6F3216F448D985D7D3BEE3622530')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('46', 'retired', 'retired', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 46, 'Retired/No longer active', 1, 'C5E53AB860A1433CB90EDA73BDEFB5B5')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('5', 'ACCT', 'ACCT', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 5, 'Accounting', 1, 'CC16A2449F284DC8B1E7FEAA83411683')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('43', 'approved', 'approved', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 43, 'Approved', 1, 'ED32867B9E194C44B37D58E2491AAA33')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('45', 'Active', 'Active', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 45, 'Active and ready to be published', 1, '9BEBFD77A71C4E45BFE67D65C71E9F29')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('7', 'BENG', 'BENG', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 7, 'Biomed', 1, '13F62DCC819E4984867D91515D12D44F')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('60', 'AAST', 'AAST', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 60, 'Asian American Studies', 1, '87575AD3BC3D4AAEB57AE76382EF3C14')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('64', 'Extended', 'EXTENDED', {ts '2000-01-01 00:00:00'}, 'kuali.lu.campusLocation', null, 64, 'Extended', 1, '361B1A6E9D9A442E8D0C34D72B049DF1')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('65', 'All', 'ALL', {ts '2000-01-01 00:00:00'}, 'kuali.lu.campusLocation', null, 65, 'All', 1, '03EF07D611EE4F25AE26B37409496DC2')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('8', 'BIOC', 'BIOC', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 8, 'Biochemistry', 1, '110FE0DBCEA04CC084C35B6B1E8A15AA')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('9', 'BIOL', 'BIOL', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 9, 'Biology', 1, '75742347B9AA439E9052DF8AAD25E880')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('21', 'HIST', 'HIST', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 21, 'History', 1, 'D6300242AC494ABCB6A59F5448E6AAB1')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('22', 'INTB', 'INTB', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 22, 'InternationalBusiness', 1, '6F94441EFA4C47AF90BF80FF363083C5')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('3', 'ROBT', 'ROBT', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 3, 'Mechanical', 1, 'DFB4B34DC9B74D079A7D0650456229C7')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('59', 'AASP', 'AASP', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 59, 'African American Studies', 1, 'F330083424D548389B91372FA8D3C78E')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('44', 'rejected', 'rejected', {ts '2000-01-01 00:00:00'}, 'kuali.state', null, 44, 'Not approved (rejected)', 1, '4AF05B01FB3D4779BA959DD91EE91A54')
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, ENUM_KEY, EXPIR_DT, SORT_KEY, VAL, VER_NBR, OBJ_ID) values ('6', 'ARTS', 'ARTS', {ts '2000-01-01 00:00:00'}, 'kuali.lu.subjectArea', null, 6, 'FineArts', 1, '8EB8F283DF894FEDBEE0C32A0FFED275')
/


