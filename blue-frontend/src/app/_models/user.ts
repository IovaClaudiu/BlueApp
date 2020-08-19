import { Role } from "./role";
export class User {
  id: string;
  email: string;
  firstname: string;
  lastname: string;
  password: string;
  token: string;
  roles: Role[];
}
