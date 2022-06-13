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

To test out the features of the application, a few [CSV files](/csv_files) were used to see the respective success/error messages. Uploaded CSV files will have the values updated in the ```H2 database```. This database can be accessed via ```http://localhost8080/h2-console``` with the following login details:

![image](https://user-images.githubusercontent.com/51468261/173273500-6dc09f8b-8473-48ee-86b4-a8644733ea1d.png)

Upon connecting, run the statement as shown below to see the values that were updated into the database:

![image](https://user-images.githubusercontent.com/51468261/173273666-16404981-5339-46b9-9026-a8fd08cf826e.png)


### User Story 2
First, run the [Springboot Application](dashboard/src/main/java/com/cognizant/dashboard/DashboardApplication.java) as a Java application. This application will then start normally on port: ```8081```

To build the Angular application, it is required to install the pagination dependency. Go to Node.js command prompt, cd to the folder of interest and type in the following command: 
```
cd <folder_containing_dashboard_frontend>
npm i ngx-pagination
```

Upon completion, start the frontend application using ```Node.js command prompt```. This can be done via the following commands:
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


## Notes
### User Story 1
**Acceptance critera:**
- [x] Successful uploading our good test data file with comments, both new and existing records (using files from harrypotter_update1 to harrypotter_update4)
- [x] Unsuccessful upload of an empty file (using [harrypotter_empty.csv](csv_files/harrypotter_empty.csv))
- [x] Unsuccessful upload of files with partial number of incorrect number of columns (using [harrypotter_column.csv](csv_files/harrypotter_colum.csv))
- [x] Unsuccessful upload of files with some but not all rows with incorrectly formatted salaries (using [harrypotter_wrongsalaryformat.csv](csv_files/harrypotter_wrongsalaryformat.csv))
- [x] Unsuccessful upload of files with some but not all rows with salary < 0.0 (using [harrypotter.csv](csv_files/harrypotter.csv))
- [x] Upload 2 files concurrently and receive expected results, or failure. (This is done using a [flag repository](upload/src/main/java/com/cognizant/upload/repository/ConcurrentFlagRepository.java) to check if there is an ongoing upload occuring)

**Assumptions:**
* CSV file should have the following column headers (in order for [CSVhelper](/upload/src/main/java/com/cognizant/upload/helper/CSVHelper.java) to read and parse fields):  
  * ```index```
  * ```login```
  * ```name```
  * ```salary```

### User Story 2
**Acceptance critera:**
- [x] When click on next page, it should be able to display records, with no overlappedrecords in page 1 and page 2
- [x] When performing a search for min salary of 0 and max salary of 4000, it should onlyreturn records with salary between 0 <= salary <= 4000
- [x] When sorting by name in ascending order, it should display in ascending order by name
- [x] When sorting by salary in ascending order, it should display in ascending order by salary
- [x] When sorting by login descending order, it should display in descending order by login

**Assumptions:** (Due to timeconstraints, custom exceptions were not thrown)
* Strictly either ```+``` or ```-``` in url for RequestParams ```sort``` 
* Column names are in small letters
* ```Offset``` value must not be larger than ```limit``` value
* Minimum salary must not be < 0
* In the frontend application, all fields must be filled with the correct format before clicking on the filter button


