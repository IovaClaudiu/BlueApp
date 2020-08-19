import { Groups } from "./groups";
import { Role } from "./role";
export class User {
  id: string;
  email: string;
  firstname: string;
  lastname: string;
  password: string;
  token: string;
  groups: Groups[];
  role: string;
}
