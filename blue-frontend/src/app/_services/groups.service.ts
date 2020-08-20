import { environment } from './../../environments/environment';
import { Groups } from './../_models/groups';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GroupsService {
  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Groups[]>(`${environment.apiUrl}/groups`);
  }
}
