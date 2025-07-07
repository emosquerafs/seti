import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { TodoTask, TaskPriority } from '../../models/todo-task.model';
import { Category } from '../../models/category.model';

@Component({
  selector: 'app-task-item',
  standalone: true,
  imports: [CommonModule, IonicModule],
  template: `
    <ion-item-sliding>
      <ion-item [class.completed]="task.completed" [class.overdue]="isOverdue">
        <ion-checkbox 
          slot="start" 
          [checked]="task.completed" 
          (ionChange)="onToggleComplete()"
          [color]="getPriorityColor()">
        </ion-checkbox>
        
        <ion-label>
          <h2 [class.line-through]="task.completed">{{ task.title }}</h2>
          <p *ngIf="task.description" class="task-description">{{ task.description }}</p>
          
          <div class="task-meta">
            <ion-chip 
              *ngIf="category" 
              [style.--background]="category.color" 
              [style.--color]="getContrastColor(category.color)">
              <ion-icon *ngIf="category.icon" [name]="category.icon"></ion-icon>
              {{ category.name }}
            </ion-chip>
            
            <ion-chip [color]="getPriorityColor()" outline="true" size="small">
              {{ getPriorityLabel() }}
            </ion-chip>
            
            <ion-chip *ngIf="task.dueDate" [color]="isOverdue ? 'danger' : 'medium'" outline="true" size="small">
              <ion-icon name="calendar-outline"></ion-icon>
              {{ formatDate(task.dueDate) }}
            </ion-chip>
          </div>
        </ion-label>
        
        <ion-button 
          fill="clear" 
          color="primary" 
          slot="end" 
          (click)="onEdit()">
          <ion-icon name="create-outline"></ion-icon>
        </ion-button>
      </ion-item>
      
      <ion-item-options side="end">
        <ion-item-option color="primary" (click)="onEdit()">
          <ion-icon name="create" slot="icon-only"></ion-icon>
        </ion-item-option>
        <ion-item-option color="danger" (click)="onDelete()">
          <ion-icon name="trash" slot="icon-only"></ion-icon>
        </ion-item-option>
      </ion-item-options>
    </ion-item-sliding>
  `,
  styles: [`
    .completed {
      opacity: 0.6;
    }
    
    .line-through {
      text-decoration: line-through;
    }
    
    .task-description {
      color: var(--ion-color-medium);
      font-size: 0.9em;
      margin-top: 4px;
    }
    
    .task-meta {
      display: flex;
      gap: 4px;
      flex-wrap: wrap;
      margin-top: 8px;
    }
    
    .overdue {
      border-left: 4px solid var(--ion-color-danger);
    }
    
    ion-chip {
      margin: 0;
    }
  `]
})
export class TaskItemComponent {
  @Input() task!: TodoTask;
  @Input() category?: Category;
  @Output() toggleComplete = new EventEmitter<TodoTask>();
  @Output() edit = new EventEmitter<TodoTask>();
  @Output() delete = new EventEmitter<string>();

  get isOverdue(): boolean {
    return this.task.dueDate ? 
      this.task.dueDate < new Date() && !this.task.completed : 
      false;
  }

  onToggleComplete() {
    this.toggleComplete.emit(this.task);
  }

  onEdit() {
    this.edit.emit(this.task);
  }

  onDelete() {
    this.delete.emit(this.task.id);
  }

  getPriorityColor(): string {
    const colors = {
      [TaskPriority.LOW]: 'success',
      [TaskPriority.MEDIUM]: 'warning',
      [TaskPriority.HIGH]: 'danger',
      [TaskPriority.URGENT]: 'danger'
    };
    return colors[this.task.priority] || 'medium';
  }

  getPriorityLabel(): string {
    const labels = {
      [TaskPriority.LOW]: 'Baja',
      [TaskPriority.MEDIUM]: 'Media',
      [TaskPriority.HIGH]: 'Alta',
      [TaskPriority.URGENT]: 'Urgente'
    };
    return labels[this.task.priority] || 'Media';
  }

  formatDate(date: Date): string {
    return new Intl.DateTimeFormat('es-ES', {
      day: 'numeric',
      month: 'short'
    }).format(date);
  }

  getContrastColor(hexColor: string): string {
    // Simple contrast calculation
    const r = parseInt(hexColor.substr(1, 2), 16);
    const g = parseInt(hexColor.substr(3, 2), 16);
    const b = parseInt(hexColor.substr(5, 2), 16);
    const brightness = (r * 299 + g * 587 + b * 114) / 1000;
    return brightness > 128 ? '#000000' : '#ffffff';
  }
}
