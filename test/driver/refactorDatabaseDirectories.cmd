DATABASE=%1

cd ..\..\1.0.x\ks-cfg-dbs

cd %DATABASE%
mkdir src
cd src
mkdir main
cd main
mkdir impex
cd impex
mkdir schema
mkdir data

cd data
copy ..\..\..\..\*.xml .
del schema.xml
cd ..\schema
copy ..\..\..\..\schema.xml .
rename schema.xml %DATABASE%-schema.xml
cd ..\..\..\..
rmdir /q/s doc
del *.* /q
cd ..\..\..\test\driver