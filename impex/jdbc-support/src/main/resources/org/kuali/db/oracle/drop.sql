-- Drop the user and ignore the Oracle 'user does not exist' error
-- Dropping the user removes all objects associated with this schema (tables, views, indexes, sequences, etc)
-- This is roughly the equivalent to MySQL's: DROP DATABASE [databaseName] IF EXISTS
BEGIN 
  EXECUTE IMMEDIATE 'DROP USER ${database} CASCADE'; 
EXCEPTION WHEN OTHERS THEN 
 IF SQLCODE != ${oracle.error.sqlcode.userDoesNotExist} THEN
   RAISE;
 END IF;
END;
/
