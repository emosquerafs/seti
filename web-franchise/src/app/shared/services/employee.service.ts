import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Employee } from '../interfaces/employee';
import { DefaultResponse } from '../interfaces/default-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrl = `${environment.api_gateway}/employees`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Employee[]> {
    return this.http.get<DefaultResponse<Employee[]>>(this.apiUrl)
      .pipe(map(response => response.body));
  }

  getById(id: number): Observable<Employee> {
    return this.http.get<DefaultResponse<Employee>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }

  create(employee: Employee): Observable<Employee> {
    return this.http.post<DefaultResponse<Employee>>(this.apiUrl, employee)
      .pipe(map(response => response.body));
  }

  update(id: number, employee: Employee): Observable<Employee> {
    return this.http.put<DefaultResponse<Employee>>(`${this.apiUrl}/${id}`, employee)
      .pipe(map(response => response.body));
  }

  delete(id: number): Observable<void> {
    return this.http.delete<DefaultResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }
}
