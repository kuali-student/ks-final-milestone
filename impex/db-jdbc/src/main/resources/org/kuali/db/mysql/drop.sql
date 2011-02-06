DROP DATABASE IF EXISTS ${database} 
/

GRANT USAGE ON *.* TO '${databaseUsername}'@'%' IDENTIFIED BY '${databasePassword}'
/

DROP USER ${databaseUsername}
/
