-- Drop the user and ignore the error Oracle spits out if the user does not exist
-- Dropping the user removes all objects associated with this schema (tables, views, indexes, sequences, etc)
-- This is roughly the equivalent to MySQL's: DROP DATABASE ${schema} IF EXISTS
BEGIN 
  EXECUTE IMMEDIATE 'DROP USER ${schema} CASCADE'; 
EXCEPTION WHEN OTHERS THEN 
 IF SQLCODE != -01918 THEN
   RAISE;
 END IF;
END;
/
