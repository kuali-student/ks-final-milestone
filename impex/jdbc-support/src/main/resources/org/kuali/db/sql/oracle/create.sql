-- Create a user, give them a password and set a quota for the default tablespace
CREATE USER ${schema} IDENTIFIED BY ${oracle.password} DEFAULT TABLESPACE ${oracle.tablespace.default} TEMPORARY TABLESPACE ${oracle.tablespace.temporary} QUOTA ${oracle.tablespace.default.quota} ON ${oracle.tablespace.default}
/

-- Grant permissions as needed
GRANT CREATE PROCEDURE, CREATE SEQUENCE, CREATE SESSION, CREATE SYNONYM, CREATE TABLE, CREATE TRIGGER, CREATE TYPE, CREATE VIEW TO ${schema}
/
