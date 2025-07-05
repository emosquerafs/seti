import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Person } from '../interfaces/person';
import { DefaultResponse } from '../interfaces/default-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private apiUrl = `${environment.api_gateway}/persons`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Person[]> {
    return this.http.get<DefaultResponse<Person[]>>(this.apiUrl)
      .pipe(map(response => response.body));
  }

  getById(id: number): Observable<Person> {
    return this.http.get<DefaultResponse<Person>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }

  create(person: Person): Observable<Person> {
    return this.http.post<DefaultResponse<Person>>(this.apiUrl, person)
      .pipe(map(response => response.body));
  }

  update(id: number, person: Person): Observable<Person> {
    return this.http.put<DefaultResponse<Person>>(`${this.apiUrl}/${id}`, person)
      .pipe(map(response => response.body));
  }

  delete(id: number): Observable<void> {
    return this.http.delete<DefaultResponse<void>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }
}
