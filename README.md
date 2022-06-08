# Salary-Management

### Instructions


### Notes
## User Story 1
- [ ] include @Transactional? (since whole upload is considered a single transaction)
- [ ] any row starting with # must be ignored
- [ ] all 4 columns must be filled (no empty forms)
- [ ] check if number of columns is 4
- [ ] check formatting of individual columns
- [ ] id and login unique
- [ ] swap id feature?
- [ ] support concurrent upload of files?

* CSV file should have the following column headers (in order for [CSVhelper](/upload/src/main/java/com/cognizant/upload/helper/CSVHelper.java) to read and parse fields):  
  * ```index```
  * ```login```
  * ```name```
  * ```salary```

## User Story 2
