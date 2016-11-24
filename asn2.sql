DROP DATABASE asn2;

CREATE DATABASE asn2;

GRANT ALL PRIVILEGES ON asn2.* TO joealbert@localhost IDENTIFIED BY 'joealb';
GRANT ALL PRIVILEGES ON asn2.* TO joealbert@'%' IDENTIFIED BY 'joealb';

USE asn2;

CREATE TABLE Employee
(employeeNumber    int            AUTO_INCREMENT
,employeeID        VARCHAR(25)    NOT NULL
,employeeName      VARCHAR(25)    NOT NULL 
,password          VARCHAR(25)    NOT NULL
,CONSTRAINT PKemployeeNumber PRIMARY KEY (employeeNumber)
)DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci
;

CREATE TABLE TimesheetLog
(timesheetID   	   int            AUTO_INCREMENT 
,employeeNumber    int    
,endDate		   DATE
,weekNumber        int            NOT NULL
,CONSTRAINT PKtimesheetLog PRIMARY KEY(timesheetID)
,CONSTRAINT FKtimesheetLog FOREIGN KEY(employeeNumber) REFERENCES Employee(employeeNumber)
ON DELETE SET NULL 
ON UPDATE cascade
)DEFauLT CHARACTER SET utf8 COLLATE utf8_unicode_ci
;

CREATE TABLE Timesheet
(timesheetRow int            AUTO_INCREMENT
,projectID    int		     
,wp           VARCHAR(25)    
,saturday     NUMERIC        
,sunday       NUMERIC        
,monday       NUMERIC         
,tuesday      NUMERIC         
,wednesday    NUMERIC         
,thursday     NUMERIC         
,friday       NUMERIC         
,notes        VARCHAR(100)    
,timesheetID  int            
,CONSTRAINT   PKtimesheet    PRIMARY KEY(timesheetRow)
)DEFauLT CHARACTER SET utf8 COLLATE utf8_unicode_ci
; 
 

INSERT INTO Employee VALUES(NULL,"EmployeeSmith", "Smith", "default");

INSERT INTO TimesheetLog VALUES(NULL,last_insert_id(), '2016-11-18' ,47);

INSERT INTO Timesheet VALUES(NULL, 321, "zxc", 6, 5, 4, 3, 2, 1, 0, "This is a placeholder.", last_insert_id());

INSERT INTO Employee VALUES(NULL,"EmployeeJohn", "John", "default");

INSERT INTO TimesheetLog VALUES(NULL,last_insert_id(), '2016-11-25' ,48);

INSERT INTO Timesheet VALUES(NULL, 123, "abc", 0, 1, 2, 3, 4, 5, 6, "This is a placeholder.", last_insert_id());

INSERT INTO Employee VALUES(NULL,"Admin", "Admin", "default");

INSERT INTO TimesheetLog VALUES(NULL,last_insert_id(), '2016-11-25' ,48);

INSERT INTO Timesheet VALUES(NULL, 123, "admin", 0, 0, 0, 0, 0, 0, 0, "This is a placeholder.", last_insert_id());
