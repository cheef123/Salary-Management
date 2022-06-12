# Salary-Management

### Instructions


### Notes
## User Story 1
- [ ] include @Transactional? (since whole upload is considered a single transaction)
- [x] any row starting with # must be ignored
- [x] all 4 columns must be filled (no empty forms)
- [x] check if number of columns is 4
- [x] check formatting of individual columns
- [x] id and login unique
- [ ] swap id feature?
- [ ] support concurrent upload of files?

Acceptance critera:
- [x] Successful uploading our good test data file with comments, both new and existing records
- [x] Unsuccessful upload of an empty file
- [x] Unsuccessful upload of files with partial number of incorrect number of columns (both too many and too few)
- [ ] Unsuccessful upload of files with some but not all rows with incorrectly formatted salaries
- [x] Unsuccessful upload of files with some but not all rows with salary < 0.0
- [ ] Upload 2 files concurrently and receive expected results, or failure.

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
