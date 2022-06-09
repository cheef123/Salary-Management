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

* CSV file should have the following column headers (in order for [CSVhelper](/upload/src/main/java/com/cognizant/upload/helper/CSVHelper.java) to read and parse fields):  
  * ```index```
  * ```login```
  * ```name```
  * ```salary```


![postmanupload](https://user-images.githubusercontent.com/51468261/172776730-56511678-633a-4a9f-9ec9-765099a27aab.png)

![h2upload](https://user-images.githubusercontent.com/51468261/172776895-1a07fb90-2fb4-4332-be97-09cc12d8cdff.png)


## User Story 2
