import { Component, OnInit } from '@angular/core';
import { Employee } from '../../../shared/interfaces/employee';
import { Person } from '../../../shared/interfaces/person';
import { EmployeeService } from '../../../shared/services/employee.service';
import { PersonService } from '../../../shared/services/person.service';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-employees-list',
  templateUrl: './employees-list.component.html',
  styleUrls: ['./employees-list.component.scss']
})
export class EmployeesListComponent implements OnInit {
  employees: Employee[] = [];
  persons: Person[] = [];
  employee: Employee = {
    personId: 0,
    department: '',
    hireDate: ''
  };
  displayDialog = false;
  isEdit = false;
  loading = false;

  constructor(
    private employeeService: EmployeeService,
    private personService: PersonService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadPersons();
  }

  loadEmployees(): void {
    this.loading = true;
    this.employeeService.getAll().subscribe({
      next: (data) => {
        this.employees = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (error) => {
        this.employees = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar los empleados'
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
    this.employee = {
      personId: 0,
      department: '',
      hireDate: new Date().toISOString().split('T')[0]
    };
    this.isEdit = false;
    this.displayDialog = true;
  }

  editEmployee(employee: Employee): void {
    this.employee = { ...employee };
    this.isEdit = true;
    this.displayDialog = true;
  }

  deleteEmployee(employee: Employee): void {
    const personName = employee.person ? 
      `${employee.person.firstName} ${employee.person.lastName}` : 
      'este empleado';
    
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar a ${personName}?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (employee.id) {
          this.employeeService.delete(employee.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Exitoso',
                detail: 'Empleado eliminado'
              });
              this.loadEmployees();
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error al eliminar el empleado'
              });
            }
          });
        }
      }
    });
  }

  saveEmployee(): void {
    if (this.validateEmployee()) {
      if (this.isEdit && this.employee.id) {
        this.employeeService.update(this.employee.id, this.employee).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Empleado actualizado'
            });
            this.loadEmployees();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al actualizar el empleado'
            });
          }
        });
      } else {
        this.employeeService.create(this.employee).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Empleado creado'
            });
            this.loadEmployees();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al crear el empleado'
            });
          }
        });
      }
    }
  }

  validateEmployee(): boolean {
    return !!(
      this.employee.personId > 0 &&
      this.employee.department.trim() &&
      this.employee.hireDate
    );
  }

  hideDialog(): void {
    this.displayDialog = false;
    this.employee = {
      personId: 0,
      department: '',
      hireDate: ''
    };
  }

  getPersonFullName(personId: number): string {
    const person = this.persons.find(p => p.id === personId);
    return person ? `${person.firstName} ${person.lastName}` : '';
  }
}
