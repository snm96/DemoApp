# DemoApp
Demo Application

This is Spring Boot Application that can perform Schduled Tasks by extracting the CRON expression from Database. Currently Below are the two tasks performed by the application.

Reconciliation - 
1. One to One Reconciliation of two input files placed at source paths, insertion of matched and unmatched items in Database using Spring Batch along with details of file from which record got matched or didnt match.(Note - kindly change the source, destination and archival paths in all configuration properties files)
2. We are Providing options to view the matched data, unmatched data and all data using Rest API and paging to limit the data where page size is configured to 500 and also providing option to resolve the records via batch update as an simulation of UI operation. Request URL's can be found in DataModelController.java.(OPEN - unmatched, RESOLVED = matched records)
3. At the end, a Report is generated in form of CSV file with all the Data Available in DATA_MODEL table which can be used as EOD report to summarize state of data at time when report is generated. It can be configured in Configuration table in DB for task name as "reportSchedlue".(All Relevant DB scripts are committed in dbscripts folder).


Employee-Bank Accounts Report - 
1. We are extracting Details of Employees and Bank Accounts that Employess hold by creating One to Many & Many to One relation.
Also there is profile based selection of export path & database connection that can be configured
The result is exported in form of Excel file using POI and is available in DemoSerialization Folder in src/.
Inorder to test the code in Local without importing in eclipse, please follow below mentioned steps:
1. Download BankDemo-0.0.1-SNAPSHOT.jar
2. Open it in WinRar or SecureZip and change the DB Connection details in application.properties file and application-{profile}.properties(Change value of property)
3. Execute the DB scripts present in dbscripts folder in order to have tables and data.
4. Provide export path in application.properties file and application-{profile}.properties(Change value of property)
5. In the same folder of JAR, we can use below mentioned command to run the application in CLI:
      java -jar BankDemo-0.0.1-SNAPSHOT.jar
