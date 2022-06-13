# Salary-Management
> This project aims to build an MVP employee salary management webapp to manage employees' salaries

## Getting started
### User Story 1
After downloading the project into a local repository, run the [Springboot Application](upload/src/main/java/com/cognizant/upload/UploadApplication.java) as a Java application. This application will then start normally on port: ```8080```

Next, start the frontend application using ```Node.js command prompt```. This can be done via the following commands:
```
cd <folder_containing_upload_frontend>
ng serve
```
Once successful, the following message will be seen in the command prompt:
```
** Angular Live Development Server is listening on localhost:4200, open your browser on http://localhost:4200/ **
```

Opening your brower on ```http://localhost:4200``` will lead to the following page:

![image](https://user-images.githubusercontent.com/51468261/173272942-1cce73d7-e4d3-4cc5-b605-78a622940f5f.png)

To test out the features of the application, feel free to make use of the [CSV files](/csv_files) and see the respective success/error messages. Uploaded CSV files will have the values updated in the ```H2 database```. This database can be accessed via ```http://localhost8080/h2-console``` with the following login details:

![image](https://user-images.githubusercontent.com/51468261/173273500-6dc09f8b-8473-48ee-86b4-a8644733ea1d.png)

Upon connecting, run the statement as shown below to see the values that were updated into the database:

![image](https://user-images.githubusercontent.com/51468261/173273666-16404981-5339-46b9-9026-a8fd08cf826e.png)


### User Story 2
First, run the [Springboot Application](dashboard/src/main/java/com/cognizant/dashboard/DashboardApplication.java) as a Java application. This application will then start normally on port: ```8081```

Next, start the frontend application using ```Node.js command prompt```. This can be done via the following commands:
```
cd <folder_containing_dashboard_frontend>
ng serve
```

Once successful, the following message will be seen in the command prompt:
```
** Angular Live Development Server is listening on localhost:4200, open your browser on http://localhost:4200/ **
```

Opening your brower on ```http://localhost:4200``` will lead to the following page:

![image](https://user-images.githubusercontent.com/51468261/173276094-7c4c9113-759b-453b-a677-811e54aa4184.png)

Upon inputing values into the respective **compulsory** fields, click on the blue ```Filter``` button to display the filtered results. Similarly, details of the database can either be found from the [data.sql](dashboard/src/main/resources/data.sql) file or from the ```h2 database```, which is accessible via ```http:localhost:8081/h2-console```. 

Apart from the JDBC URL which is changed to ```jdbc:h2:mem:EmployeeDashboard```, all other login details are the same as those in User Story 1.


### Notes
## User Story 1
Acceptance critera:
- [x] Successful uploading our good test data file with comments, both new and existing records
- [x] Unsuccessful upload of an empty file
- [x] Unsuccessful upload of files with partial number of incorrect number of columns (both too many and too few)
- [ ] Unsuccessful upload of files with some but not all rows with incorrectly formatted salaries
- [x] Unsuccessful upload of files with some but not all rows with salary < 0.0
- [x] Upload 2 files concurrently and receive expected results, or failure.

possbile to use put the method csvtoemployees into employeeservice? so can assess flag repository

* CSV file should have the following column headers (in order for [CSVhelper](/upload/src/main/java/com/cognizant/upload/helper/CSVHelper.java) to read and parse fields):  
  * ```index```
  * ```login```
  * ```name```
  * ```salary```


![postmanupload](https://user-images.githubusercontent.com/51468261/172776730-56511678-633a-4a9f-9ec9-765099a27aab.png)

![h2upload](https://user-images.githubusercontent.com/51468261/172776895-1a07fb90-2fb4-4332-be97-09cc12d8cdff.png)

Update existing entry if employeeId exists in database:

![before](https://user-images.githubusercontent.com/51468261/172818276-eac9a40b-4136-42b9-aaf7-eae1643e1df9.png)
![after](https://user-images.githubusercontent.com/51468261/172818439-b428faad-f0f6-4609-93cc-a0603ad629e4.png)


## User Story 2

assumption: strictly either + or - in url, column names no capitalisation

todo: need take care of possible exceptions when calling the getUsers (out of list index etc)

To build the Angular application, it is required to install the pagination dependency. Go to Node.js command prompt, cd to the folder of interest and type in the following command: ```npm i ngx-pagination```
