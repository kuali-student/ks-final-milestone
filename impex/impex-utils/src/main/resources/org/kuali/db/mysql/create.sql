CREATE DATABASE IF NOT EXISTS ${database} 
DEFAULT CHARACTER SET '${mysql.character.set}' 
DEFAULT COLLATE '${mysql.collate}';
/

GRANT ALL ON ${database}.* 
TO '${databaseUsername}'@'%' 
IDENTIFIED BY '${databasePassword}' 
WITH GRANT OPTION
/
