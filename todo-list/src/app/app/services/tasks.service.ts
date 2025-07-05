import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage-angular';
import { Task } from '../models/task.model';

@Injectable({ providedIn: 'root' })
export class TasksService {
  private storageKey = 'tasks';
  private tasks: Task[] = [];

  constructor(private storage: Storage) {
    this.init();
  }

  private async init() {
    await this.storage.create();
    const stored = await this.storage.get(this.storageKey);
    this.tasks = stored || [];
  }

  getAll(): Task[] {
    return [...this.tasks];
  }

  async add(task: Task) {
    this.tasks.push(task);
    await this.storage.set(this.storageKey, this.tasks);
  }

  async update(task: Task) {
    const index = this.tasks.findIndex(t => t.id === task.id);
    if (index !== -1) {
      this.tasks[index] = task;
      await this.storage.set(this.storageKey, this.tasks);
    }
  }

  async remove(id: string) {
    this.tasks = this.tasks.filter(t => t.id !== id);
    await this.storage.set(this.storageKey, this.tasks);
  }

  async toggleComplete(id: string) {
    const task = this.tasks.find(t => t.id === id);
    if (task) {
      task.completed = !task.completed;
      await this.storage.set(this.storageKey, this.tasks);
    }
  }

  // Métodos extra para filtrar por categoría, etc.
}
