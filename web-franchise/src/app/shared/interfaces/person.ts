import { DocumentType } from './document-type';

export interface Person {
  id?: number;
  idNumber: string;
  firstName: string;
  lastName: string;
  phone: string;
  address: string;
  email: string;
  documentType: DocumentType;
}
