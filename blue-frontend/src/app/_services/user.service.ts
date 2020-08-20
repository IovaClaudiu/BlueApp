import { Subject, Observable } from 'rxjs';
import { Group } from './../_models/group';
import { environment } from './../../environments/environment';
import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../_models';

@Injectable({ providedIn: 'root' })
export class UserService {
  changeSubject: Subject<User> = new Subject();

  constructor(private http: HttpClient) {}

  getAll(): Observable<Array<User>> {
    return this.http.get<User[]>(`${environment.apiUrl}/users`);
  }

  register(user: User): Observable<User> {
    return this.http.post<User>(`${environment.apiUrl}/register`, user);
  }

  delete(email: string): Observable<any> {
    return this.http.delete(`${environment.apiUrl}/users/${email}`);
  }

  setUserGroup(email: String, group: Group): Observable<any> {
    return this.http.put(`${environment.apiUrl}/users/${email}`, group);
  }
}
