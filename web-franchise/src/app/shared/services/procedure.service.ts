import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Procedure } from '../interfaces/procedure';
import { DefaultResponse } from '../interfaces/default-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProcedureService {
  private apiUrl = `${environment.api_gateway}/procedures`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Procedure[]> {
    return this.http.get<DefaultResponse<Procedure[]>>(this.apiUrl)
      .pipe(map(response => response.body));
  }

  getById(id: number): Observable<Procedure> {
    return this.http.get<DefaultResponse<Procedure>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }

  create(procedure: Procedure): Observable<Procedure> {
    return this.http.post<DefaultResponse<Procedure>>(this.apiUrl, procedure)
      .pipe(map(response => response.body));
  }

  update(id: number, procedure: Procedure): Observable<Procedure> {
    return this.http.put<DefaultResponse<Procedure>>(`${this.apiUrl}/${id}`, procedure)
      .pipe(map(response => response.body));
  }

  delete(id: number): Observable<void> {
    return this.http.delete<DefaultResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }
}
