import { Component, OnInit } from '@angular/core';
import { DocumentType } from '../../../shared/interfaces/document-type';
import { DocumentTypeService } from '../../../shared/services/document-type.service';
import { MessageService, ConfirmationService } from 'primeng/api';

@Component({
  selector: 'app-document-types-list',
  templateUrl: './document-types-list.component.html',
  styleUrls: ['./document-types-list.component.scss']
})
export class DocumentTypesListComponent implements OnInit {
  documentTypes: DocumentType[] = [];
  documentType: DocumentType = { code: '', description: '' };
  displayDialog = false;
  isEdit = false;
  loading = false;

  constructor(
    private documentTypeService: DocumentTypeService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadDocumentTypes();
  }

  loadDocumentTypes(): void {
    this.loading = true;
    this.documentTypeService.getAll().subscribe({
      next: (data) => {
        this.documentTypes = Array.isArray(data) ? data : [];
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading document types:', error);
        this.documentTypes = []; // Ensure it's always an array
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error al cargar los tipos de documento'
        });
        this.loading = false;
      }
    });
  }

  openNew(): void {
    this.documentType = { code: '', description: '' };
    this.isEdit = false;
    this.displayDialog = true;
  }

  editDocumentType(documentType: DocumentType): void {
    this.documentType = { ...documentType };
    this.isEdit = true;
    this.displayDialog = true;
  }

  deleteDocumentType(documentType: DocumentType): void {
    this.confirmationService.confirm({
      message: `¿Está seguro de eliminar el tipo de documento ${documentType.description}?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        if (documentType.id) {
          this.documentTypeService.delete(documentType.id).subscribe({
            next: () => {
              this.messageService.add({
                severity: 'success',
                summary: 'Exitoso',
                detail: 'Tipo de documento eliminado'
              });
              this.loadDocumentTypes();
            },
            error: () => {
              this.messageService.add({
                severity: 'error',
                summary: 'Error',
                detail: 'Error al eliminar el tipo de documento'
              });
            }
          });
        }
      }
    });
  }

  saveDocumentType(): void {
    if (this.documentType.code.trim() && this.documentType.description.trim()) {
      if (this.isEdit && this.documentType.id) {
        this.documentTypeService.update(this.documentType.id, this.documentType).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Tipo de documento actualizado'
            });
            this.loadDocumentTypes();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al actualizar el tipo de documento'
            });
          }
        });
      } else {
        this.documentTypeService.create(this.documentType).subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Exitoso',
              detail: 'Tipo de documento creado'
            });
            this.loadDocumentTypes();
            this.hideDialog();
          },
          error: () => {
            this.messageService.add({
              severity: 'error',
              summary: 'Error',
              detail: 'Error al crear el tipo de documento'
            });
          }
        });
      }
    }
  }

  hideDialog(): void {
    this.displayDialog = false;
    this.documentType = { code: '', description: '' };
  }
}
