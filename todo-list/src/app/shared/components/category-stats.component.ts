import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { TodoTask } from '../../models/todo-task.model';
import { Category } from '../../models/category.model';

interface CategoryStats {
  category: Category | null;
  total: number;
  completed: number;
  pending: number;
  completionRate: number;
}

@Component({
  selector: 'app-category-stats',
  standalone: true,
  imports: [CommonModule, IonicModule],
  template: `
    <ion-card *ngIf="stats.length > 0">
      <ion-card-header>
        <ion-card-title>
          <ion-icon name="analytics-outline"></ion-icon>
          Estadísticas por Categoría
        </ion-card-title>
      </ion-card-header>
      
      <ion-card-content>
        <div class="stats-grid">
          <div 
            *ngFor="let stat of stats" 
            class="stat-card"
            [class.no-category]="!stat.category">
            
            <div class="stat-header">
              <ion-chip 
                *ngIf="stat.category"
                [style.--background]="stat.category.color"
                [style.--color]="'white'">
                <ion-icon [name]="stat.category.icon || 'pricetag'"></ion-icon>
                {{ stat.category.name }}
              </ion-chip>
              <ion-chip 
                *ngIf="!stat.category"
                color="medium">
                <ion-icon name="pricetag-outline"></ion-icon>
                Sin categoría
              </ion-chip>
            </div>
            
            <div class="stat-numbers">
              <div class="stat-item">
                <span class="number">{{ stat.total }}</span>
                <span class="label">Total</span>
              </div>
              <div class="stat-item">
                <span class="number">{{ stat.completed }}</span>
                <span class="label">Completadas</span>
              </div>
              <div class="stat-item">
                <span class="number">{{ stat.pending }}</span>
                <span class="label">Pendientes</span>
              </div>
            </div>
            
            <div class="progress-section">
              <div class="progress-bar">
                <div 
                  class="progress-fill"
                  [style.width.%]="stat.completionRate"
                  [style.background]="stat.category?.color || 'var(--ion-color-medium)'">
                </div>
              </div>
              <span class="progress-text">{{ stat.completionRate }}% completado</span>
            </div>
          </div>
        </div>
      </ion-card-content>
    </ion-card>
  `,
  styles: [`
    .stats-grid {
      display: grid;
      gap: 16px;
    }

    .stat-card {
      padding: 16px;
      border: 1px solid var(--ion-color-light);
      border-radius: 12px;
      background: white;
    }

    .stat-card.no-category {
      border-left: 4px solid var(--ion-color-medium);
    }

    .stat-header {
      margin-bottom: 12px;
    }

    .stat-header ion-chip {
      margin: 0;
    }

    .stat-numbers {
      display: grid;
      grid-template-columns: repeat(3, 1fr);
      gap: 8px;
      margin-bottom: 16px;
    }

    .stat-item {
      display: flex;
      flex-direction: column;
      align-items: center;
      text-align: center;
    }

    .stat-item .number {
      font-size: 1.5rem;
      font-weight: 700;
      color: var(--ion-color-dark);
      margin-bottom: 4px;
    }

    .stat-item .label {
      font-size: 0.75rem;
      color: var(--ion-color-medium);
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }

    .progress-section {
      display: flex;
      flex-direction: column;
      gap: 4px;
    }

    .progress-bar {
      height: 6px;
      background: var(--ion-color-light);
      border-radius: 3px;
      overflow: hidden;
    }

    .progress-fill {
      height: 100%;
      transition: width 0.3s ease;
      border-radius: 3px;
    }

    .progress-text {
      font-size: 0.875rem;
      color: var(--ion-color-medium);
      text-align: center;
    }

    @media (min-width: 768px) {
      .stats-grid {
        grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
      }
    }
  `]
})
export class CategoryStatsComponent implements OnChanges {
  @Input() tasks: TodoTask[] = [];
  @Input() categories: Category[] = [];

  stats: CategoryStats[] = [];

  ngOnChanges(changes: SimpleChanges) {
    if (changes['tasks'] || changes['categories']) {
      this.calculateStats();
    }
  }

  private calculateStats() {
    const categoryMap = new Map<string | null, CategoryStats>();

    // Initialize stats for all categories
    this.categories.forEach(category => {
      categoryMap.set(category.id, {
        category,
        total: 0,
        completed: 0,
        pending: 0,
        completionRate: 0
      });
    });

    // Add "no category" option
    categoryMap.set(null, {
      category: null,
      total: 0,
      completed: 0,
      pending: 0,
      completionRate: 0
    });

    // Calculate stats for each task
    this.tasks.forEach(task => {
      const categoryId = task.categoryId || null;
      const stat = categoryMap.get(categoryId);
      
      if (stat) {
        stat.total++;
        if (task.completed) {
          stat.completed++;
        } else {
          stat.pending++;
        }
      }
    });

    // Calculate completion rates and filter out empty categories
    this.stats = Array.from(categoryMap.values())
      .filter(stat => stat.total > 0)
      .map(stat => ({
        ...stat,
        completionRate: stat.total > 0 ? Math.round((stat.completed / stat.total) * 100) : 0
      }))
      .sort((a, b) => b.total - a.total); // Sort by total tasks descending
  }
}
