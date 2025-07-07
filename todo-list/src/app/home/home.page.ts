import { Component, OnInit, OnDestroy } from '@angular/core';
import { IonicModule, ModalController, AlertController, ActionSheetController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subject, takeUntil, combineLatest } from 'rxjs';

import { TasksService } from '../services/tasks.service';
import { CategoriesService } from '../services/categories.service';
import { ToastService } from '../services/toast.service';
import { LoadingService } from '../services/loading.service';

import { Category } from '../models/category.model';
import { TodoTask, CreateTaskDto, UpdateTaskDto, TaskPriority } from '../models/todo-task.model';

import { CategoryCrudComponent } from '../pages/category-crud/category-crud.component';
import { TaskFormComponent } from '../shared/components/task-form.component';
import { TaskItemComponent } from '../shared/components/task-item.component';
import { CategoryStatsComponent } from '../shared/components/category-stats.component';

export type FilterType = 'all' | 'pending' | 'completed' | 'overdue' | 'today';
export type SortType = 'created' | 'title' | 'priority' | 'dueDate';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule, TaskItemComponent, CategoryStatsComponent],
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  
  tasks: TodoTask[] = [];
  filteredTasks: TodoTask[] = [];
  categories: Category[] = [];
  
  selectedCategoryId: string | null = null;
  currentFilter: FilterType = 'all';
  currentSort: SortType = 'created';
  searchQuery = '';
  
  loading = true;
  showStats = false;

  constructor(
    private tasksService: TasksService,
    private categoriesService: CategoriesService,
    private modalCtrl: ModalController,
    private alertCtrl: AlertController,
    private actionSheetCtrl: ActionSheetController,
    private toastService: ToastService,
    private loadingService: LoadingService
  ) {}

  async ngOnInit() {
    await this.initializeData();
    this.setupDataSubscriptions();
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private async initializeData() {
    try {
      await this.loadingService.presentLoading('Cargando datos...');
      
      // Wait a bit for services to initialize
      await new Promise(resolve => setTimeout(resolve, 500));
      
      this.categories = this.categoriesService.getAll();
      this.tasks = this.tasksService.getAll();
      this.applyFiltersAndSorting();
      
    } catch (error) {
      await this.toastService.presentErrorToast('Error al cargar los datos');
    } finally {
      this.loading = false;
      await this.loadingService.dismissLoading();
    }
  }

  private setupDataSubscriptions() {
    combineLatest([
      this.tasksService.getTasks$(),
      this.categoriesService.getCategories$()
    ]).pipe(
      takeUntil(this.destroy$)
    ).subscribe(([tasks, categories]) => {
      this.tasks = tasks;
      this.categories = categories;
      this.applyFiltersAndSorting();
    });
  }

  // Filter and Sort Methods
  applyFiltersAndSorting() {
    let filtered = [...this.tasks];

    // Apply category filter
    if (this.selectedCategoryId) {
      filtered = filtered.filter(task => task.categoryId === this.selectedCategoryId);
    }

    // Apply status filter
    switch (this.currentFilter) {
      case 'pending':
        filtered = filtered.filter(task => !task.completed);
        break;
      case 'completed':
        filtered = filtered.filter(task => task.completed);
        break;
      case 'overdue':
        filtered = filtered.filter(task => 
          task.dueDate && task.dueDate < new Date() && !task.completed
        );
        break;
      case 'today':
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        const tomorrow = new Date(today);
        tomorrow.setDate(tomorrow.getDate() + 1);
        
        filtered = filtered.filter(task => 
          task.dueDate && task.dueDate >= today && task.dueDate < tomorrow
        );
        break;
    }

    // Apply search filter
    if (this.searchQuery.trim()) {
      const query = this.searchQuery.toLowerCase();
      filtered = filtered.filter(task => 
        task.title.toLowerCase().includes(query) ||
        (task.description && task.description.toLowerCase().includes(query))
      );
    }

    // Apply sorting
    this.sortTasks(filtered);
    this.filteredTasks = filtered;
  }

  private sortTasks(tasks: TodoTask[]) {
    tasks.sort((a, b) => {
      switch (this.currentSort) {
        case 'title':
          return a.title.localeCompare(b.title);
        case 'priority':
          const priorityOrder = { urgent: 0, high: 1, medium: 2, low: 3 };
          return priorityOrder[a.priority] - priorityOrder[b.priority];
        case 'dueDate':
          if (!a.dueDate && !b.dueDate) return 0;
          if (!a.dueDate) return 1;
          if (!b.dueDate) return -1;
          return a.dueDate.getTime() - b.dueDate.getTime();
        case 'created':
        default:
          return b.createdAt.getTime() - a.createdAt.getTime();
      }
    });
  }

  // Filter Actions
  async presentFilterOptions() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Filtrar tareas',
      buttons: [
        {
          text: 'Todas las tareas',
          icon: 'list',
          handler: () => this.setFilter('all')
        },
        {
          text: 'Pendientes',
          icon: 'radio-button-off',
          handler: () => this.setFilter('pending')
        },
        {
          text: 'Completadas',
          icon: 'checkmark-circle',
          handler: () => this.setFilter('completed')
        },
        {
          text: 'Vencidas',
          icon: 'alert-circle',
          handler: () => this.setFilter('overdue')
        },
        {
          text: 'Para hoy',
          icon: 'today',
          handler: () => this.setFilter('today')
        },
        {
          text: 'Cancelar',
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
    await actionSheet.present();
  }

  async presentSortOptions() {
    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Ordenar por',
      buttons: [
        {
          text: 'Fecha de creación',
          icon: 'calendar',
          handler: () => this.setSort('created')
        },
        {
          text: 'Título',
          icon: 'text',
          handler: () => this.setSort('title')
        },
        {
          text: 'Prioridad',
          icon: 'flag',
          handler: () => this.setSort('priority')
        },
        {
          text: 'Fecha límite',
          icon: 'time',
          handler: () => this.setSort('dueDate')
        },
        {
          text: 'Cancelar',
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
    await actionSheet.present();
  }

  setFilter(filter: FilterType) {
    this.currentFilter = filter;
    this.applyFiltersAndSorting();
  }

  setSort(sort: SortType) {
    this.currentSort = sort;
    this.applyFiltersAndSorting();
  }

  onCategoryChange() {
    this.applyFiltersAndSorting();
  }

  onSearchChange() {
    this.applyFiltersAndSorting();
  }

  // Task Actions
  async openTaskForm(task?: TodoTask) {
    const modal = await this.modalCtrl.create({
      component: TaskFormComponent,
      componentProps: {
        task,
        categories: this.categories
      }
    });

    modal.onDidDismiss().then(async (result) => {
      if (result.role === 'save' && result.data) {
        try {
          if (task) {
            await this.tasksService.update(task.id, result.data as UpdateTaskDto);
            await this.toastService.presentSuccessToast('Tarea actualizada');
          } else {
            await this.tasksService.add(result.data as CreateTaskDto);
            await this.toastService.presentSuccessToast('Tarea creada');
          }
        } catch (error) {
          await this.toastService.presentErrorToast('Error al guardar la tarea');
        }
      }
    });

    await modal.present();
  }

  async onToggleComplete(task: TodoTask) {
    try {
      await this.tasksService.toggleComplete(task.id);
      const message = task.completed ? 'Tarea marcada como pendiente' : 'Tarea completada';
      await this.toastService.presentSuccessToast(message);
    } catch (error) {
      await this.toastService.presentErrorToast('Error al actualizar la tarea');
    }
  }

  async onDeleteTask(taskId: string) {
    const alert = await this.alertCtrl.create({
      header: 'Confirmar eliminación',
      message: '¿Estás seguro de que quieres eliminar esta tarea?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        },
        {
          text: 'Eliminar',
          role: 'destructive',
          handler: async () => {
            try {
              await this.tasksService.remove(taskId);
              await this.toastService.presentSuccessToast('Tarea eliminada');
            } catch (error) {
              await this.toastService.presentErrorToast('Error al eliminar la tarea');
            }
          }
        }
      ]
    });

    await alert.present();
  }

  // Category Actions
  async openCategoryCrud() {
    const modal = await this.modalCtrl.create({
      component: CategoryCrudComponent
    });
    await modal.present();
  }

  toggleStats() {
    this.showStats = !this.showStats;
  }

  // Utility Methods
  getCategoryById(categoryId: string): Category | undefined {
    return this.categories.find(c => c.id === categoryId);
  }

  getFilterLabel(): string {
    const labels = {
      all: 'Todas',
      pending: 'Pendientes',
      completed: 'Completadas',
      overdue: 'Vencidas',
      today: 'Hoy'
    };
    return labels[this.currentFilter];
  }

  getSortLabel(): string {
    const labels = {
      created: 'Fecha',
      title: 'Título',
      priority: 'Prioridad',
      dueDate: 'Vencimiento'
    };
    return labels[this.currentSort];
  }

  getTaskCounts() {
    return {
      total: this.tasks.length,
      pending: this.tasks.filter(t => !t.completed).length,
      completed: this.tasks.filter(t => t.completed).length,
      overdue: this.tasks.filter(t => 
        t.dueDate && t.dueDate < new Date() && !t.completed
      ).length
    };
  }

  // TrackBy function for performance
  trackByTaskId(index: number, task: TodoTask): string {
    return task.id;
  }

  // Empty state methods
  getEmptyStateIcon(): string {
    if (this.searchQuery.trim()) return 'search-outline';
    if (this.currentFilter === 'completed') return 'checkmark-circle-outline';
    if (this.currentFilter === 'overdue') return 'alert-circle-outline';
    if (this.currentFilter === 'today') return 'today-outline';
    return 'list-outline';
  }

  getEmptyStateTitle(): string {
    if (this.searchQuery.trim()) return 'No se encontraron tareas';
    if (this.currentFilter === 'completed') return '¡Genial!';
    if (this.currentFilter === 'overdue') return '¡Perfecto!';
    if (this.currentFilter === 'today') return 'Día libre';
    return 'Lista vacía';
  }

  getEmptyStateMessage(): string {
    if (this.searchQuery.trim()) return `No hay tareas que coincidan con "${this.searchQuery}"`;
    if (this.currentFilter === 'completed') return 'No tienes tareas completadas aún';
    if (this.currentFilter === 'overdue') return 'No tienes tareas vencidas';
    if (this.currentFilter === 'today') return 'No tienes tareas programadas para hoy';
    return 'Comienza agregando tu primera tarea';
  }
}
