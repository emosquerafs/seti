import { Person } from './person';

export interface Employee {
  id?: number;
  personId: number;
  person?: Person;
  department: string;
  hireDate: string;
}
