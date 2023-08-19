# Astek
Astek webapp task created by Mateusz Gawroński.

This WebApp has been created with help of Eclipse IDE.
Tested on two machines using:
- Java JDK-11
- Apache Tomcat ver. 9.0

--------------------------------------------------------------
 ------------------------ Setup ------------------------------
 
------------------------- Eclipse ----------------------------
- The easiest way to start the project is to import it into 
Eclipse EE IDE, setup Tomcat server within Eclipse and run
from index.jsp file. 

- While configuring Tomcat server, be sure to set HTTP/1.1 
port (eg. 8080) and Tomcat admin port (eg. 8005). When running
the project on the server, browser tab should open
automatically. If not, navigate to: localhost:8080/Astek or
localhost:8080/Astek/index.jsp. Port number should match one 
specified while setting up Tomcat server. 

------------------------- Tomcat ------------------------------
- Second way of running, without using any IDE, requires
downloading Apache Tomcat from: https://tomcat.apache.org/
as well as Java JRE/JDK-11. After installing Apache Tomcat and
specifying path to JRE or JDK, Astek.rar project folder has to
be extracted to: /YourApacheInstallationFolder/Tomcat/webapps/
Make sure that within .../Tomcat/lib folder there is 
servlet-api.jar file present. It is required when building your
app.

- Because Tomcat requires specific folder and files organisation,
Astek.rar is prepared webapp folder for this type of setup. 

- When finished, simply run Tomcat server from Tomcat/bin folder
and open the browser. Navigate to: localhost:8080/Astek or
localhost:8080/Astek/index.jsp If site fails to load, check
Tomcat command line for other port which might have been used by
the server.

------------------------ Build --------------------------------
- Astek.rar comes in built form, requiring only unzipping in 
destination folder. If recompilation is required, source files
are located in Astek/src folder.

- Eclipse project should be built in a standard manner.

------------------------ Tests JUNIT4--------------------------
- Basic tests can be run from the IDE. Their task is to check
basic performance of Class methods and ensure correct logic of
operations.

Servlets were tested manually on running Tomcat local server.

----------------------------------------------------------------
---------------------- Running Application ---------------------
- Simple application for reimbursement calculations.


------------------------ New claim -----------------------------
- Click "New claim" button to start calculating new claim or 
"Admin Panel" in top right corner for administration tools.

- Trip date has to be specified in order for daily allowance 
calculations. Choose dates in the fields. If start date is a
later date than the end date, application will swap the dates 
after submission. 

- Adding receipt value to reimbursement sum is done through
drop down menu which shows type of receipt and maximum 
reimbursement available for given type. Entering receipt value 
will add the amount to the sum up to maximum specified.

- Daily allowance is limited by the trip duration specified by
trip date. Maximum number of days is which is considered
ranged from: End-Start + 1. Reimbursement is multiplication of
daily allowance x number of days.

- Mileage calculation is performed when user enters distance 
traveled. Sum calculated is added as: Distance x mileage amount.

- Total reimbursement updates after submitting any kind of form.
It can reach to up maximum amount.

- If user wants to calcualte another claim, "Reset Calculation"
button allows for reseting all the values to their defaults.

--------------------------- Admin Panel --------------------------
- To access Admin panel click the "Admin Panel" hyperlink. It will
redirect to the login page and ask for password:

!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


THE PASSWORD IS: admin



!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

- In case of wrong password, user will be redirected to the main 
page. 

- Admin Panel allows to modify all the values for reimbursement 
calculations. 

- New daily allowance sets the new value for: daily allowance x days
formula.

- New mileage sets the new value for: mileage x distance formula.

- Limits is a dynamically created table of available Receipts and
reimbursement limits for all types of reimbursements. 
Each field next to total max allows for modifying given reimbursement
values accordingly. 

- New Receipt can be added by providing name and reimbursement value
in the fields.

- Receipt can also be removed by providing exact name of receipt to
remove. 

- Hitting "Save and Quit" Button fill redirect the user to New Claim
page. 

--------------------------- Known bugs --------------------------------

- When calculating reimbursement and Total maximum is reached, adding
additional receipts/mileage/etc will still substract the amounts from
given type.

-------------------------- Possible Improvements with JavaScript -----------------------
- Javascript functions to monitor user input into the field to check
for correctness of the data and live update of the field. For
example reimbursement value change when different amount of days are
selected. 

- Script to enforce data selection in order to allow input into other
fields.