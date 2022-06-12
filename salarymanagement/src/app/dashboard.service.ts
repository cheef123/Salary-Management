import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Employee } from './dashboard/employee';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private httpClient: HttpClient) { }

  getUsers(minSalary: number, maxSalary: number, offset: number, limit: number, orderby: string, sort: string): Observable<Employee[]> {
    const httpParams = new HttpParams({
      fromObject: {
        minSalary: minSalary,
        maxSalary: maxSalary,
        offset: offset,
        limit: limit,
        sort: sort + orderby
      }
    })

    return this.httpClient.get<Employee[]>('http://localhost:8081/users', { params: httpParams });

  }

}


