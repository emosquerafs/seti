import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IonicModule, ModalController } from '@ionic/angular';
import { TodoTask, CreateTaskDto, UpdateTaskDto, TaskPriority } from '../../models/todo-task.model';
import { Category } from '../../models/category.model';
import { QuickCategoryComponent } from './quick-category.component';

@Component({
  selector: 'app-task-form',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, IonicModule],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>{{ task ? 'Editar' : 'Nueva' }} Tarea</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="dismiss()">
            <ion-icon name="close"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <form [formGroup]="taskForm" (ngSubmit)="onSubmit()">
        
        <ion-item>
          <ion-label position="stacked">Título *</ion-label>
          <ion-input 
            formControlName="title"
            placeholder="Ingresa el título de la tarea"
            [class.invalid]="taskForm.get('title')?.invalid && taskForm.get('title')?.touched">
          </ion-input>
        </ion-item>
        <div *ngIf="taskForm.get('title')?.invalid && taskForm.get('title')?.touched" class="error-message">
          El título es requerido
        </div>

        <ion-item>
          <ion-label position="stacked">Descripción</ion-label>
          <ion-textarea 
            formControlName="description"
            placeholder="Descripción opcional"
            rows="3">
          </ion-textarea>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Prioridad</ion-label>
          <ion-select formControlName="priority" interface="popover">
            <ion-select-option value="low">🟢 Baja</ion-select-option>
            <ion-select-option value="medium">🟡 Media</ion-select-option>
            <ion-select-option value="high">🟠 Alta</ion-select-option>
            <ion-select-option value="urgent">🔴 Urgente</ion-select-option>
          </ion-select>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Categoría</ion-label>
          <ion-button 
            expand="block" 
            fill="outline" 
            (click)="openCategorySelector()">
            <ion-icon [name]="getSelectedCategoryIcon()" slot="start"></ion-icon>
            {{ getSelectedCategoryName() }}
          </ion-button>
        </ion-item>

        <ion-item>
          <ion-label position="stacked">Fecha límite</ion-label>
          <ion-datetime 
            formControlName="dueDate"
            presentation="date"
            [min]="today"
            [showDefaultButtons]="true"
            [showClearButton]="true">
          </ion-datetime>
        </ion-item>

        <div class="form-actions">
          <ion-button 
            expand="block" 
            type="submit" 
            [disabled]="taskForm.invalid"
            color="primary">
            <ion-icon name="checkmark" slot="start"></ion-icon>
            {{ task ? 'Actualizar' : 'Crear' }} Tarea
          </ion-button>
          
          <ion-button 
            expand="block" 
            fill="outline" 
            color="medium" 
            (click)="dismiss()">
            Cancelar
          </ion-button>
        </div>
      </form>
    </ion-content>
  `,
  styles: [`
    .form-actions {
      margin-top: 24px;
      display: flex;
      flex-direction: column;
      gap: 12px;
    }
    
    .error-message {
      color: var(--ion-color-danger);
      font-size: 0.875rem;
      padding: 4px 16px;
    }
    
    .invalid {
      --border-color: var(--ion-color-danger);
    }
    
    ion-datetime {
      width: 100%;
    }
  `]
})
export class TaskFormComponent implements OnInit {
  @Input() task?: TodoTask;
  @Input() categories: Category[] = [];
  @Output() save = new EventEmitter<CreateTaskDto | UpdateTaskDto>();

  taskForm!: FormGroup;
  today = new Date().toISOString();

  constructor(
    private fb: FormBuilder,
    private modalCtrl: ModalController
  ) {}

  ngOnInit() {
    this.initForm();
  }

  private initForm() {
    this.taskForm = this.fb.group({
      title: [this.task?.title || '', [Validators.required, Validators.minLength(1)]],
      description: [this.task?.description || ''],
      priority: [this.task?.priority || TaskPriority.MEDIUM],
      categoryId: [this.task?.categoryId],
      dueDate: [this.task?.dueDate?.toISOString()]
    });
  }

  onSubmit() {
    if (this.taskForm.valid) {
      const formValue = this.taskForm.value;
      const taskData = {
        ...formValue,
        dueDate: formValue.dueDate ? new Date(formValue.dueDate) : undefined
      };

      this.modalCtrl.dismiss(taskData, 'save');
    }
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }

  async openCategorySelector() {
    const modal = await this.modalCtrl.create({
      component: QuickCategoryComponent,
      componentProps: {
        categories: this.categories,
        selectedCategoryId: this.taskForm.get('categoryId')?.value
      }
    });

    modal.onDidDismiss().then((result) => {
      if (result.role === 'confirm') {
        this.taskForm.patchValue({ categoryId: result.data });
      }
    });

    await modal.present();
  }

  getSelectedCategoryName(): string {
    const categoryId = this.taskForm.get('categoryId')?.value;
    if (!categoryId) return 'Sin categoría';
    
    const category = this.categories.find(c => c.id === categoryId);
    return category ? category.name : 'Sin categoría';
  }

  getSelectedCategoryIcon(): string {
    const categoryId = this.taskForm.get('categoryId')?.value;
    if (!categoryId) return 'pricetag-outline';
    
    const category = this.categories.find(c => c.id === categoryId);
    return category?.icon || 'pricetag';
  }
}
