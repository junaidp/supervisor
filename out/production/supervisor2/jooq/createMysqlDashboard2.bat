pause  This will destroy any existing data in the dashboard2 database
SET MARIADBDIR=c:\wbcsupervisor\tools\mariadb\bin
SET CLEAN_DIR=..\installdir\dbclean

%MARIADBDIR%\mysql  -uroot  <  %CLEAN_DIR%\clean-dashboard.sql
