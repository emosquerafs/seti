import { Component, OnInit } from '@angular/core';
import { Procedure } from '../../../shared/interfaces/procedure';
import { Person } from '../../../shared/interfaces/person';
import { ProcedureService } from '../../../shared/services/procedure.service';
import { PersonService } from '../../../shared/services/person.service';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-procedures-list',
  templateUrl: './procedures-list.component.html',
  styleUrls: ['./procedures-list.component.scss']
})
export class ProceduresListComponent implements OnInit {
  procedures: Procedure[] = [];
  persons: Person[] = [];
  procedure: Procedure = {
    registrationNumber: '',
    registrationYear: new Date().getFullYear(),
    name: '',
    description: '',
    submittedById: 0,
    receivedById: 0
  };
  displayDialog = false;
  isEdit = false;
  loading = false;

  constructor(
    private procedureService: ProcedureService,
    private personService: PersonService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadProcedures();
    this.loadPersons();
  }

  loadProcedures(): void {
    this.loading = true;
    this.procedureService.getAll().subscribe({
      next: (data) => {
        this.procedures = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (error) => {
        this.procedures = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar los procedimientos'
        });
        this.loading = false;
      }
    });
  }

  loadPersons(): void {
    this.personService.getAll().subscribe({
      next: (data) => {
        this.persons = Array.isArray(data) ? data : [];
      },
      error: (error) => {
        this.persons = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar las personas'
        });
      }
    });
  }

  openNew(): void {
    this.procedure = {
      registrationNumber: '',
      registrationYear: new Date().getFullYear(),
      name: '',
      description: '',
      submittedById: 0,
      receivedById: 0
    };
    this.isEdit = false;
    this.displayDialog = true;
  }

  editProcedure(procedure: Procedure): void {
    this.procedure = { ...procedure };
    this.isEdit = true;
    this.displayDialog = true;
  }

  deleteProcedure(procedure: Procedure): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el procedimiento ${procedure.name}?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (procedure.id) {
          this.procedureService.delete(procedure.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Exitoso',
                detail: 'Procedimiento eliminado'
              });
              this.loadProcedures();
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error al eliminar el procedimiento'
              });
            }
          });
        }
      }
    });
  }

  saveProcedure(): void {
    if (this.validateProcedure()) {
      if (this.isEdit && this.procedure.id) {
        this.procedureService.update(this.procedure.id, this.procedure).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Procedimiento actualizado'
            });
            this.loadProcedures();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al actualizar el procedimiento'
            });
          }
        });
      } else {
        this.procedureService.create(this.procedure).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Procedimiento creado'
            });
            this.loadProcedures();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al crear el procedimiento'
            });
          }
        });
      }
    }
  }

  validateProcedure(): boolean {
    return !!(
      this.procedure.registrationNumber.trim() &&
      this.procedure.name.trim() &&
      this.procedure.description.trim() &&
      this.procedure.submittedById > 0 &&
      this.procedure.receivedById > 0
    );
  }

  hideDialog(): void {
    this.displayDialog = false;
    this.procedure = {
      registrationNumber: '',
      registrationYear: new Date().getFullYear(),
      name: '',
      description: '',
      submittedById: 0,
      receivedById: 0
    };
  }

  getPersonFullName(personId: number): string {
    const person = this.persons.find(p => p.id === personId);
    return person ? `${person.firstName} ${person.lastName}` : '';
  }
}
