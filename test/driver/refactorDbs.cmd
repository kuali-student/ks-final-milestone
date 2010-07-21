set DATABASE=ks-core-db
call refactorDatabaseDirectories %DATABASE%

set DATABASE=ks-lum-db
call refactorDatabaseDirectories %DATABASE%

set DATABASE=ks-rice-db
call refactorDatabaseDirectories %DATABASE%

set DATABASE=ks-embedded-db
call refactorDatabaseDirectories %DATABASE%
