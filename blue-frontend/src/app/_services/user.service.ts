import { Injectable, OnInit } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { User } from "@/_models";

@Injectable({ providedIn: "root" })
export class UserService implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit(): void {}

  getAll(token: string) {
    return this.http.get<User[]>(`${config.apiUrl}/users`, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    });
  }

  register(user: User) {
    return this.http.post(`${config.apiUrl}/register`, user);
  }

  delete(id: number) {
    return this.http.delete(`${config.apiUrl}/users/${id}`);
  }
}
