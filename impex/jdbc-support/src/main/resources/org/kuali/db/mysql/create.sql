CREATE DATABASE IF NOT EXISTS ${database} 
DEFAULT CHARACTER SET '${mysql.character.set}' DEFAULT COLLATE '${mysql.collate}';
/

CREATE DATABASE IF NOT EXISTS ksembedded 
DEFAULT CHARACTER SET 'utf8' DEFAULT COLLATE 'utf8_bin';

GRANT ALL ON ksembedded.* TO 'ksembedded'@'%' IDENTIFIED BY 'ksembedded' WITH GRANT OPTION;
GRANT ALL ON ksembedded.* TO 'ksembedded'@'%' IDENTIFIED BY 'ksembedded' WITH GRANT OPTION;
/

CREATE USER '${databaseUsername}'@'${mysql.host}' IDENTIFIED BY '${databasePassword}'
/

GRANT ALL ON ${database}.* TO '${databaseUsername}'@'${mysql.host}' WITH GRANT OPTION
/