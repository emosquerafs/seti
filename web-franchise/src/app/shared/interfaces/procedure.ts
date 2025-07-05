import { Person } from './person';

export interface Procedure {
  id?: number;
  registrationNumber: string;
  registrationYear: number;
  name: string;
  description: string;
  submittedById: number;
  submittedBy?: Person;
  receivedById: number;
  receivedBy?: Person;
}
