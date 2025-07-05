import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DefaultResponse } from '../interfaces/default-response';
import { Gender } from '../interfaces/gender';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GenderService {
  private uri: string = '/api/shared/gender';

  constructor(private httpClient: HttpClient) {}

  getAllGender(): Observable<DefaultResponse<Gender[]>> {
    return this.httpClient.get<DefaultResponse<Gender[]>>(environment.api_gateway.concat(this.uri));
  }
}
