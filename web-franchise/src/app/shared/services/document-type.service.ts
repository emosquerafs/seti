import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentType } from '../interfaces/document-type';
import { DefaultResponse } from '../interfaces/default-response';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DocumentTypeService {
  private apiUrl = `${environment.api_gateway}/document-types`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<DocumentType[]> {
    return this.http.get<DefaultResponse<DocumentType[]>>(this.apiUrl)
      .pipe(map(response => response.body));
  }

  getById(id: number): Observable<DocumentType> {
    return this.http.get<DefaultResponse<DocumentType>>(`${this.apiUrl}/${id}`)
      .pipe(map(response => response.body));
  }

  create(documentType: DocumentType): Observable<DocumentType> {
    return this.http.post<DefaultResponse<DocumentType>>(this.apiUrl, documentType)
      .pipe(map(response => response.body));
  }

  update(id: number, documentType: DocumentType): Observable<DocumentType> {
    return this.http.put<DefaultResponse<DocumentType>>(`${this.apiUrl}/${id}`, documentType)
      .pipe(map(response => response.body));
  }

  delete(id: number): Observable<void> {
    console.log(`Deleting document type with ID: ${id}`);
    console.log(`API URL: ${this.apiUrl}/${id}`);
    
    return this.http.delete<DefaultResponse<void>>(`${this.apiUrl}/${id}`)

      .pipe(map(response => response.body));
  }
}
