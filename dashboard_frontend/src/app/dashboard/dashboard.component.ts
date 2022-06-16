import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DashboardService } from '../dashboard.service';
import { Employee } from './employee';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // for filtered result
  results: Employee[] = [];
  // for latest results after edit/delete
  allresults: Employee[] = [];
  empDetail: FormGroup;
  empObject: Employee = new Employee();
  totalRecords: number;
  page: number = 1;
  columns = ["id", "name", "login", "salary"];
  minSalary: number;
  maxSalary: number;
  offset: number;
  limit: number;
  orderby: string;
  sort: string;


  constructor(private service: DashboardService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.empDetail = this.formBuilder.group({
      id: [''],
      name: [''],
      login: [''],
      salary: ['']
    });
  }

  getUsers() {
    this.service.getUsers(this.minSalary, this.maxSalary, this.offset, this.limit, this.orderby, this.sort).subscribe(data => {
      console.log(data);
      console.log("length:" + data.length)
      this.results = data;
      this.totalRecords = data.length;
    })
  }

  submit(filter) {
    this.minSalary = filter.minSalary;
    this.maxSalary = filter.maxSalary;
    this.offset = filter.offset;
    this.limit = filter.limit;
    this.orderby = filter.orderby;
    if (filter.sort == "Ascending") {
      this.sort = "+"
    } else {
      this.sort = "-"
    }
    console.log(filter);
    this.page = 1;
    this.getUsers();
  }


  deleteEmployee() {
    this.empObject.id = this.empDetail.value.id;
    this.empObject.name = this.empDetail.value.name;
    this.empObject.login = this.empDetail.value.login;
    this.empObject.salary = this.empDetail.value.salary;

    console.log(this.empObject);
    this.service.deleteEmployee(this.empObject).subscribe(res => {
    }, err => {
      console.log("hi");
      console.log(err);

    });
  }

  getAllEmployees() {
    this.service.getAllEmployees().subscribe(res => {
      this.allresults = res;
    }, err => {
      console.log("Internal Server Error");
    });

  }

  editEmployee(emp: Employee) {
    this.empDetail.controls['id'].setValue(emp.id);
    this.empDetail.controls['name'].setValue(emp.name);
    this.empDetail.controls['login'].setValue(emp.login);
    this.empDetail.controls['salary'].setValue(emp.salary);
    console.log(this.empDetail);
  }

  updateEmployee() {
    this.empObject.id = this.empDetail.value.id;
    this.empObject.name = this.empDetail.value.name;
    this.empObject.login = this.empDetail.value.login;
    this.empObject.salary = this.empDetail.value.salary;

    this.service.updateEmployee(this.empObject).subscribe(res => {
      let obj = this.results.find((o, i) => {
        if (o.id === this.empObject.id) {
          this.results[i] = { id: this.empObject.id, name: this.empObject.name, login: this.empObject.login, salary: this.empObject.salary };
          return true; // stop searching
        }
      });
    }, err => {
      console.log(err);
    });
  }

}
