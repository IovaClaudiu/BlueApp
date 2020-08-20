import { Subject } from 'rxjs';
import { environment } from './../../environments/environment';
import { Group } from '../_models/group';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GroupsService {
  addNewGroupSubscription = new Subject<Group>();
  removeGroupSubscription = new Subject<Group>();

  constructor(private http: HttpClient) {}

  getAll() {
    return this.http.get<Group[]>(`${environment.apiUrl}/groups`);
  }

  addGroup(group: Group) {
    return this.http.post<Group>(`${environment.apiUrl}/groups`, group);
  }

  removeGroup(groupName: string) {
    return this.http.delete(`${environment.apiUrl}/groups/${groupName}`);
  }
}
