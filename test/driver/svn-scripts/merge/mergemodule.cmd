set MODULE=%1
set REVISION=%2

set DIR=..\..\..\..\1.0.x\%MODULE%
set URL=https://test.kuali.org/svn/student/%MODULE%/branches/%MODULE%-dev

echo %URL%

svn merge -r %REVISION%:HEAD %URL% %DIR%
