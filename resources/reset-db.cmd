date /t
time /t

cd ..\..\kul-cfg-dbs\impex
rem call ant satellite-init
call ant import

cd ..\..\maven\resources

date /t
time /t
