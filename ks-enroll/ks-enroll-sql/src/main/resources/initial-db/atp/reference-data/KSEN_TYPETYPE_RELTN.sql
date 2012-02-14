INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-0', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.atp.atp.relation.precedes', 'kuali.atp.type.Fall', 'kuali.atp.type.Spring', 0, 'Fall precedes Spring')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Fall', 0, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Spring', 1, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.contains', 'kuali.atp.type.AY', 'kuali.atp.type.Summer', 2, 'Academic year contains semester')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.SpringBreak' , 0, 'Spring break is a holiday')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('TYPETYPEREL-5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.Holiday', 'kuali.atp.type.Thanksgiving' , 0, 'Thanksgiving is a holiday')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Fall' , 0, 'kuali.atp.type.Fall is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall1' , 0, 'kuali.atp.type.HalfFall1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfFall2' , 0, 'kuali.atp.type.HalfFall2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring1' , 0, 'kuali.atp.type.HalfSpring1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.HalfSpring2' , 0, 'kuali.atp.type.HalfSpring2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A' , 0, 'kuali.atp.type.Mini-mester1A is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B' , 0, 'kuali.atp.type.Mini-mester1B is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2C' , 0, 'kuali.atp.type.Mini-mester2C is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester2D' , 0, 'kuali.atp.type.Mini-mester2D is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session1' , 0, 'kuali.atp.type.Session1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Session2' , 0, 'kuali.atp.type.Session2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG1' , 0, 'kuali.atp.type.SessionG1 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SessionG2' , 0, 'kuali.atp.type.SessionG2 is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Spring' , 0, 'kuali.atp.type.Spring is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SpringBreak' , 0, 'kuali.atp.type.SpringBreak is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Summer' , 0, 'kuali.atp.type.Summer is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.SummerEve' , 0, 'kuali.atp.type.SummerEve is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Winter' , 0, 'kuali.atp.type.Winter is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.termtype.group.member.19', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.group', 'kuali.atp.type.group.term', 'kuali.atp.type.Adhoc' , 0, 'kuali.atp.type.Adhoc is a type of Term')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.1', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.FallSpring', 1, 'AcademicCalendar can contain FallSpring')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.2', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Fall', 2, 'AcademicCalendar can contain Fall')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.3', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall1', 1, 'Fall can contain HalfFall1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.4', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Fall', 'kuali.atp.type.HalfFall2', 2, 'Fall can contain HalfFall2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.5', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Winter', 3, 'AcademicCalendar can contain Winter')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.6', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Spring', 4, 'AcademicCalendar can contain Spring')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.7', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring1', 1, 'Spring can contain HalfSpring1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.8', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.SpringBreak', 2, 'Spring can contain SpringBreak')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.9', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Spring', 'kuali.atp.type.HalfSpring2', 3, 'Spring can contain HalfSpring2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.10', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Session1', 5, 'AcademicCalendar can contain Session1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.11', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1A', 1, 'Session1 can contain Mini-mester1A')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.12', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session1', 'kuali.atp.type.Mini-mester1B', 2, 'Session1 can contain Mini-mester1B')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.13', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Summer', 'kuali.atp.type.Session2', 2, 'Summer can contain Session2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.14', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2C', 1, 'Session2 can contain Mini-mester2C')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.15', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.Session2', 'kuali.atp.type.Mini-mester2D', 2, 'Session2 can contain Mini-mester2D')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.16', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.AcademicCalendar', 'kuali.atp.type.SummerEve', 6, 'AcademicCalendar can contain SummerEve')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.17', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG1', 1, 'SummerEve can contain SessionG1')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values ('kuali.atp.type.type.relation.allowed.18', 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.atp.type.SummerEve', 'kuali.atp.type.SessionG2', 2, 'SummerEve can contain SessionG2')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Lecture', 'kuali.lui.type.activity.offering.lecture', 1, 'A canonical Lecture can only have a lecture as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Lab', 'kuali.lui.type.activity.offering.lab', 1, 'A canonical Lab can only have a lab as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Discussion', 'kuali.lui.type.activity.offering.discussion', 1, 'A canonical Discussion can only have a discussion as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Tutorial', 'kuali.lui.type.activity.offering.tutorial', 1, 'A canonical Tutorial can only have a tutorial as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.WebLecture', 'kuali.lui.type.activity.offering.weblecture', 1, 'A canonical WebLecture can only have a weblecture as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.WebDiscussion', 'kuali.lui.type.activity.offering.webdiscussion', 1, 'A canonical WebDiscussion can only have a webdiscussion as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Directed', 'kuali.lui.type.activity.offering.directed', 1, 'A canonical Directed can only have a directed as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Studio', 'kuali.lui.type.activity.offering.studio', 1, 'A canonical Studio can only have a studio as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Correspond', 'kuali.lui.type.activity.offering.correspond', 1, 'A canonical Correspond can only have a correspond as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Activity', 'kuali.lui.type.activity.offering.activity', 1, 'A canonical Activity can only have a activity as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Colloquium', 'kuali.lui.type.activity.offering.colloquium', 1, 'A canonical Colloquium can only have a colloquium as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Demonstration', 'kuali.lui.type.activity.offering.demonstration', 1, 'A canonical Demonstration can only have a demonstration as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Field', 'kuali.lui.type.activity.offering.field', 1, 'A canonical Field can only have a field as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Homework', 'kuali.lui.type.activity.offering.homework', 1, 'A canonical Homework can only have a homework as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Independ', 'kuali.lui.type.activity.offering.independ', 1, 'A canonical Independ can only have a independ as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Internship', 'kuali.lui.type.activity.offering.internship', 1, 'A canonical Internship can only have a internship as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Private', 'kuali.lui.type.activity.offering.private', 1, 'A canonical Private can only have a private as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Recitation', 'kuali.lui.type.activity.offering.recitation', 1, 'A canonical Recitation can only have a recitation as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.Research', 'kuali.lui.type.activity.offering.research', 1, 'A canonical Research can only have a research as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.SelfPaced', 'kuali.lui.type.activity.offering.selfpaced', 1, 'A canonical SelfPaced can only have a selfpaced as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.CompBased', 'kuali.lui.type.activity.offering.compbased', 1, 'A canonical CompBased can only have a compbased as an offering')
/

INSERT INTO KSEN_TYPETYPE_RELTN (ID, VER_NBR, EFF_DT, EXPIR_DT, TYPETYPE_RELATION_TYPE, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, NAME) values (sys_guid(), 0, to_timestamp('2011-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), to_timestamp('2100-01-01 00:00:00.0','YYYY-MM-DD HH24.MI.SS.FF'), 'kuali.type.type.relation.type.allowed','kuali.lu.type.activity.VideoConf', 'kuali.lui.type.activity.offering.videoconf', 1, 'A canonical VideoConf can only have a videoconf as an offering')
/
