@echo off
if "%JAVA_HOME%"=="" goto error

 %JAVA_HOME%\bin\native2ascii ApplicationResources_zh_CN.txt ApplicationResources_zh_CN.properties
echo ת���ɹ�!
pause
goto end
:error
echo JAVA_HOME ������δ����!
pause
:end