CREATE TABLE KSMG_MESSAGE 
    ( 
        DATABASEID VARCHAR2(255) NOT NULL, 
        GROUPNAME VARCHAR2(255), 
        ID VARCHAR2(255), 
        LOCALE VARCHAR2(255), 
        VALUE VARCHAR2(255), 
        VER_NBR NUMBER(8) DEFAULT 1 NOT NULL, 
        OBJ_ID VARCHAR2(36) DEFAULT SYS_GUID() NOT NULL,  
        CONSTRAINT KSMG_MESSAGEP1 PRIMARY KEY (DATABASEID),
        CONSTRAINT KSMG_MESSAGE_I1 UNIQUE (locale, groupname, id)
    )
/
CREATE SEQUENCE KSMG_MESSAGE_DATABASE_ID_S INCREMENT BY 1 START WITH 2020 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgType', 'en', 'org', 'Type')
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgName', 'en', 'org', 'Name')
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgAbbr', 'en', 'org', 'Abbreviation')
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgDesc', 'en', 'org', 'Description')
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgEffDate', 'en', 'org', 'Effective Date')
/
insert into KSMG_MESSAGE (databaseid, id, locale, groupname, value) values (KSMG_MESSAGE_DATABASE_ID_S.nextval, 'orgExpDate', 'en', 'org', 'Expiration Date')
/
