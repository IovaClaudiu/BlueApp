import { Subject, Observable } from 'rxjs';
import { environment } from './../../environments/environment';
import { Group } from '../_models/group';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GroupsService {
  addNewGroupSubscription: Subject<Group> = new Subject<Group>();
  removeGroupSubscription: Subject<Group> = new Subject<Group>();

  constructor(private http: HttpClient) {}

  getGroups(): Observable<Array<Group>> {
    return this.http.get<Group[]>(`${environment.apiUrl}/groups`);
  }

  addGroup(group: Group): Observable<Group> {
    return this.http.post<Group>(`${environment.apiUrl}/groups`, group);
  }

  removeGroup(groupName: string): Observable<any> {
    return this.http.delete(`${environment.apiUrl}/groups/${groupName}`);
  }
}
