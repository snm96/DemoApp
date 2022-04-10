# DemoApp
Demo Application

This is Spring Boot Application that can perform Schduled Tasks by extracting the CRON expression from Database. Currently we are extracting Details of Employees and Bank Accounts that Employess hold by creating One to Many & Many to One relation.

Also there is profile based selection of export path & database connection that can be configured

The result is exported in form of Excel file using POI and is available in DemoSerialization Folder in src/.

Inorder to test the code in Local without importing in eclipse, please follow below mentioned steps:
1. Download BankDemo-0.0.1-SNAPSHOT.jar
2. Open it in WinRar or SecureZip and change the DB Connection details in application.properties file and application-{profile}.properties(Change value of property)
3. Execute the DB scripts present in dbscripts folder in order to have tables and data.
4. Provide export path in application.properties file and application-{profile}.properties(Change value of property)
5. In the same folder of JAR, we can use below mentioned command to run the application in CLI:
      java -jar BankDemo-0.0.1-SNAPSHOT.jar
