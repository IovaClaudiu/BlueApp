import { Group } from './group';
export class User {
  id: string;
  email: string;
  firstname: string;
  lastname: string;
  password: string;
  token: string;
  group: Group;
  role: string;
}
