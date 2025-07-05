import { Component, OnInit } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';





import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TasksService } from '../services/tasks.service';
import { CategoriesService } from '../services/categories.service';
import { Category } from '../models/category.model';
import { TodoTask } from '../models/todo-task.model';
import { CategoryCrudComponent } from '../pages/category-crud/category-crud.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule],
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss']
})
export class HomePage implements OnInit {
  tasks: TodoTask[] = [];
  categories: Category[] = [];
  selectedCategoryId: string | null = null;
  newTaskTitle = '';
  loading = true;

  constructor(
    private tasksService: TasksService,
    private categoriesService: CategoriesService,
    private modalCtrl: ModalController
  ) {}

  async ngOnInit() {
    await this.reloadData();
  }

  async reloadData() {
    this.tasks = this.tasksService.getAll();
    this.categories = this.categoriesService.getAll();
    this.loading = false;
  }

  filteredTasks(): TodoTask[] {
    return this.selectedCategoryId
      ? this.tasks.filter(t => t.categoryId === this.selectedCategoryId)
      : this.tasks;
  }

  async addTask() {
    if (!this.newTaskTitle.trim()) return;
    await this.tasksService.add({
      id: crypto.randomUUID(),
      title: this.newTaskTitle,
      completed: false,
      categoryId: this.selectedCategoryId || undefined,
      createdAt: Date.now()
    });
    this.newTaskTitle = '';
    this.reloadData();
  }

  async toggleComplete(task: TodoTask) {
    await this.tasksService.toggleComplete(task.id);
    this.reloadData();
  }

  async removeTask(id: string) {
    await this.tasksService.remove(id);
    this.reloadData();
  }

  async openCategoryCrud() {
    const modal = await this.modalCtrl.create({
      component: CategoryCrudComponent,
    });
    modal.onDidDismiss().then(() => this.reloadData());
    return modal.present();
  }

  getCategoryName(categoryId: string | undefined): string {
  if (!categoryId) return '';
  const category = this.categories.find(c => c.id === categoryId);
  return category ? category.name : '';
}

}
