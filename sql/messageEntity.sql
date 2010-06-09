CREATE TABLE KSMG_MESSAGE_T
(
      database_id NUMBER(19),
      message_id VARCHAR2(256),
      locale VARCHAR2(256),
      group_name VARCHAR2(256),
      message_value VARCHAR2(256),
      obj_id VARCHAR(36) default SYS_GUID() NOT NULL,
      ver_nbr NUMBER(8) default 1 NOT NULL,
      CONSTRAINT KSMG_MESSAGE_P1 PRIMARY KEY (database_id),
      CONSTRAINT KSMG_MESSAGE_I1 UNIQUE (locale, group_name, message_id)
)
/
CREATE SEQUENCE KSMG_MESSAGE_S INCREMENT BY 1 START WITH 2020 NOMAXVALUE NOCYCLE NOCACHE ORDER
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgType', 'en', 'org', 'Type')
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgName', 'en', 'org', 'Name')
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgAbbr', 'en', 'org', 'Abbreviation')
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgDesc', 'en', 'org', 'Description')
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgEffDate', 'en', 'org', 'Effective Date')
/
insert into KSMG_MESSAGE_T (database_id, message_id, locale, group_name, message_value) values (KSMG_MESSAGE_S.nextval, 'orgExpDate', 'en', 'org', 'Expiration Date')
/
