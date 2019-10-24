 
This is a very interesting project for providing a cell phone usage report for the current year for the company.

[A] GIVEN: 
Two csv files are provided with respective comma-delimited data attributes appearing on the first line, corresponding to two different data models, namely:
1) CellPhone.csv
   employeeId,employeeName,purchaseDate,model
   
2) CellPhoneUsageByMonth.csv
   employeeId,date,totalMinutes,totalData


[B] PROJECT REQUIREMENT:
- Generate a report that should contain the following:

1) Header Section
* Report Run Date
* Number of Phones
* Total Minutes
* Total Data
* Average Minutes
* Average Data

2) Details Section
For each company cell phone provide the following information
* Employee Id
* Employee Name
* Model
* Purchase Date
* Minutes Usage
* one column for each month
* Data Usage
* one column for each month

- Report should be printed to a local printer in your computer.

3) Using Java to create this report.

[C] PROCESSES:
To approach this project, I have to look at what the business stakeholder wants to see in the report.  Instead of waiting for answers I had brought them up few days ago, I decided to dive into the project during my Fall break today.  I will use the following processes as my guide:

1) Data Analysis
  * visually look at the content of data of these two csv files.
  * these two files contain data with irregular data format.
  * to save time, I create a java Web application with embedded H2 database to help organize data and generate the cell phone report for the year in an efficient and effective manner.

2) Data Design
  * Choosing H2 as in-memory database for fast query and implementation avoids tedious and time-consuming configuration and setup effort to store data that usually outperforms traditional way of using MySQL and Oracle database.
  * Two database schema will be created in H2 database using sql queries in schema.sql every time when the java Web app is started up.  Similarly, these schema and database data will be destroyed upon the app is shut down.
  * schema.sql is stored in the app resource directory as follows:
  DROP TABLE IF EXISTS CELLPHONE;
CREATE TABLE CELLPHONE(
EMPLOYEEID INT  PRIMARY KEY,
EMPLOYEENAME VARCHAR(100) ,
PURCHASEDATE DATE,
PHONEMODEL VARCHAR(100)
);

DROP TABLE IF EXISTS PHONEUSAGE;
CREATE TABLE PHONEUSAGE(
EMPLOYEEID INT,
USAGEDATE DATE,
MINUTESUSED INT,
DATAUSED DOUBLE
);

3) Application Design
  (a) Technologies:
  * Spring Boot:
    Using Spring Boot to create any java application is the best choice to quickly provide showcase of POC of any business idea, such as the one generating cell phone report.
  * Java 1.8 version:
    This is a de facto must-have standard to run Spring Tool Suite version 3.9.7 or above, which allows us to create java Spring Boot application faster.
  * in-memory H2 database:
    Allowing in-memory embedded H2 database integrated seamlessly with Spring Boot application helps drive software development even faster than before.
  * Spring Framework:
    Using JdbcTemplate plugin available in Spring Framework helps expedite database connection and development in Java application and cut java source codes to the very minimum, even few lines of codes.
  * HTML5, JSP, CSS, Javascript, ...
  
  (b) Assumptions:
    (b.1) Definition of current year
    * Realizing the unknown definition of the current year from the company, I assume the current year is straightly referred to the data from the given files of minutes and data usage.  
    * A simple sql query reveals that the beginning of cell phone usage began on September 21, 2017 and ended on September 20, 2018.  
    * With that said, I assume the fiscal year of the company refers to the month end of the year in which the last phone usage was recorded.  In this case, the current year of the company corresponding to the phone usage is ended on September 30, 2018.
    ASSUMPTION #1: the current/fiscal year of the company ended refers to the month end of the year in which the last phone usage was recorded after consideration of 12 months' period beginning from the FIRST start date of the phone usage.
    
    (b.2) Phone usage Monthly Period:
    It begins on the FIRST start date of the usage from the data file (e.g., CellPhoneUsageByMonth.csv); in this case, it is the 21st of September of 2017.  Likewise, the last day of the phone usage monthly billing cycle will fall on the 20th of October 2017.   
    ASSUMPTION #2: Phone usage Monthly Period will begin from the FIRST start date of one month to one day less than the corresponding start date of the next month.
    
    (b.3) Data File format: 
    ASSUMPTION #3: This application will accept only data files containing data format that matches the given data schema included in the first line of each of the two given data files.  
    
  (c) Input Files:
  * By default, the execution of the application, without any external data file(s) as input argument(s) on the command line, will take the default given data files embedded in the application.
  * If there are external physical data files in the user computer filesystem, the application will accept these files as input data for processing and taking precedence over the embedded given data files.   
  
  (d) The application will automatically generate the cell phone usage report and have it spooled to any printer of your choice or saved into a file in a pdf format.
  
4) Program execution
  (a) Download the java Application war file named as PhoneUsage-0.0.1-SNAPSHOT.war from the repository into any of your computer folder;
  (b) Bring up a window console in Microsoft windows or RedHat or OpenSUSE Leap Konsole or console terminal;
  (c) change your computer folder or directory where you have downloaded the application war file; (e.g, cd your_downloaded_app_directory
  (d) at the your_downloaded_app_directory, type in:
      -- Without any external input files:
       e..g, 
      * for Windows console:
        C:\your_downloaded_app_directory\> java -jar PhoneUsage-0.0.1-SNAPSHOT.war

      * for RedHat or Opensuse Leap terminal:
        linus@localhost:~/your_downloaded_app_directory>  java -jar PhoneUsage-0.0.1-SNAPSHOT.war 
        
      -- With any external input files:
      * copy your external input files to your your_downloaded_app_directory or folder.
        Your external input files are, for example, CellPhone.csv and CellPhoneUsageByMonth.csv
       e.g, 
      * for Windows console: 
        C:\your_downloaded_app_directory\> java -jar PhoneUsage-0.0.1-SNAPSHOT.war --csvfile.fname1=CellPhone.csv --csvfile.fname2=CellPhoneUsageByMonth.csv
        
      * for RedHat or Opensuse Leap terminal:
        linus@localhost:~/your_downloaded_app_directory>  java -jar PhoneUsage-0.0.1-SNAPSHOT.war --csvfile.fname1=CellPhone.csv --csvfile.fname2=CellPhoneUsageByMonth.csv
        
[D] ADDITIONAL NOTES:
* The original given file (CellPhoneUsageByMonth.csv) is corrupted with some invalid data that will crash the program.  I have eliminated these invalid data by design in line with the given data schema.
* I have also provided another set of data with input files named as 2019CellPhone.csv and 2019CellPhoneUsageByMonth.csv for your beta testing.  The only difference between the given files and these two 2019 files is that it covers different billing monthly period from 2018 to 2019.  The number of records in the given data for usage is 2000, but around 1600 in the 2019 data usage file.  However, you may freely use your new external files as you wish, provided that your files format must conform to your original database schema set on the first line given in each file.
* Samples of the yearly cell phone reports generated from the command-line executions of the Web application are also given for you to reference.
* To get the cell phone usage report, go to http://localhost:8080/ on any Web browser of your choice (e.g., FireFox, Google Chrome, or Safari) after running the program in section 3.4 above.

Happy Testing and code review at your leisure !

Author: Linus Kwong
bcontact@gmail.com

