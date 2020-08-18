import { Injectable, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { User } from "@/_models";

@Injectable({ providedIn: "root" })
export class UserService implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  getAll() {
    return this.http.get<User[]>(`${config.apiUrl}/users`);
  }

  register(user: User) {
    return this.http.post(`${config.apiUrl}/register`, user);
  }

  delete(email: string) {
    return this.http.delete(`${config.apiUrl}/user/${email}`);
  }
}
