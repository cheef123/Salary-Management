import { AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { DashboardService } from '../dashboard.service';
import { Employee } from './employee';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  results: Employee[] = [];
  totalRecords: number;
  page: number = 1;
  columns = ["id", "name", "login", "salary"];
  minSalary: number;
  maxSalary: number;
  offset: number;
  limit: number;
  orderby: string;
  sort: string;


  constructor(private service: DashboardService) { }

  ngOnInit(): void {
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
    this.getUsers();
  }



}
