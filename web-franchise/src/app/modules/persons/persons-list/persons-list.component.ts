import { Component, OnInit } from '@angular/core';
import { Person } from '../../../shared/interfaces/person';
import { DocumentType } from '../../../shared/interfaces/document-type';
import { PersonService } from '../../../shared/services/person.service';
import { DocumentTypeService } from '../../../shared/services/document-type.service';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-persons-list',
  templateUrl: './persons-list.component.html',
  styleUrls: ['./persons-list.component.scss']
})
export class PersonsListComponent implements OnInit {
  persons: Person[] = [];
  documentTypes: DocumentType[] = [];
  person: Person = {
    idNumber: '',
    firstName: '',
    lastName: '',
    phone: '',
    address: '',
    email: '',
    documentType: { code: '', description: '' }
  };
  displayDialog = false;
  isEdit = false;
  loading = false;

  constructor(
    private personService: PersonService,
    private documentTypeService: DocumentTypeService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadPersons();
    this.loadDocumentTypes();
  }

  loadPersons(): void {
    this.loading = true;
    this.personService.getAll().subscribe({
      next: (data) => {
        this.persons = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (error) => {
        this.persons = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar las personas'
        });
        this.loading = false;
      }
    });
  }

  loadDocumentTypes(): void {
    this.documentTypeService.getAll().subscribe({
      next: (data) => {
        this.documentTypes = Array.isArray(data) ? data : [];
      },
      error: (error) => {
        this.documentTypes = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar los tipos de documento'
        });
      }
    });
  }

  openNew(): void {
    this.person = {
      idNumber: '',
      firstName: '',
      lastName: '',
      phone: '',
      address: '',
      email: '',
      documentType: { code: '', description: '' }
    };
    this.isEdit = false;
    this.displayDialog = true;
  }

  editPerson(person: Person): void {
    this.person = { ...person };
    this.isEdit = true;
    this.displayDialog = true;
  }

  deletePerson(person: Person): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar a ${person.firstName} ${person.lastName}?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (person.id) {
          this.personService.delete(person.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Exitoso',
                detail: 'Persona eliminada'
              });
              this.loadPersons();
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error al eliminar la persona'
              });
            }
          });
        }
      }
    });
  }

  savePerson(): void {
    if (this.validatePerson()) {
      if (this.isEdit && this.person.id) {
        this.personService.update(this.person.id, this.person).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Persona actualizada'
            });
            this.loadPersons();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al actualizar la persona'
            });
          }
        });
      } else {
        this.personService.create(this.person).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Persona creada'
            });
            this.loadPersons();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al crear la persona'
            });
          }
        });
      }
    }
  }

  validatePerson(): boolean {
    return !!(
      this.person.idNumber.trim() &&
      this.person.firstName.trim() &&
      this.person.lastName.trim() &&
      this.person.email.trim() &&
      this.person.documentType.id
    );
  }

  hideDialog(): void {
    this.displayDialog = false;
    this.person = {
      idNumber: '',
      firstName: '',
      lastName: '',
      phone: '',
      address: '',
      email: '',
      documentType: { code: '', description: '' }
    };
  }
}
