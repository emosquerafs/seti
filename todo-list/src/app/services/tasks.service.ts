import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage-angular';
import { BehaviorSubject, Observable } from 'rxjs';
import { TodoTask, CreateTaskDto, UpdateTaskDto, TaskPriority } from '../models/todo-task.model';

@Injectable({ providedIn: 'root' })
export class TasksService {
  private storageKey = 'tasks';
  private tasksSubject = new BehaviorSubject<TodoTask[]>([]);
  public tasks$ = this.tasksSubject.asObservable();

  constructor(private storage: Storage) {
    this.init();
  }

  private async init() {
    try {
      await this.storage.create();
      const stored = await this.storage.get(this.storageKey);
      const tasks = stored ? stored.map((task: any) => ({
        ...task,
        createdAt: new Date(task.createdAt),
        updatedAt: new Date(task.updatedAt),
        dueDate: task.dueDate ? new Date(task.dueDate) : undefined
      })) : [];
      this.tasksSubject.next(tasks);
    } catch (error) {
      console.error('Error initializing tasks service:', error);
      this.tasksSubject.next([]);
    }
  }

  getAll(): TodoTask[] {
    return this.tasksSubject.value;
  }

  getTasks$(): Observable<TodoTask[]> {
    return this.tasks$;
  }

  async add(taskDto: CreateTaskDto): Promise<TodoTask> {
    const task: TodoTask = {
      id: crypto.randomUUID(),
      title: taskDto.title,
      description: taskDto.description,
      completed: false,
      priority: taskDto.priority || TaskPriority.MEDIUM,
      categoryId: taskDto.categoryId,
      dueDate: taskDto.dueDate,
      createdAt: new Date(),
      updatedAt: new Date()
    };

    const tasks = [...this.getAll(), task];
    await this.saveTasks(tasks);
    return task;
  }

  async update(id: string, updateDto: UpdateTaskDto): Promise<TodoTask | null> {
    const tasks = this.getAll();
    const index = tasks.findIndex(t => t.id === id);
    
    if (index === -1) return null;

    const updatedTask = {
      ...tasks[index],
      ...updateDto,
      updatedAt: new Date()
    };

    tasks[index] = updatedTask;
    await this.saveTasks(tasks);
    return updatedTask;
  }

  async remove(id: string): Promise<boolean> {
    const tasks = this.getAll().filter(t => t.id !== id);
    await this.saveTasks(tasks);
    return true;
  }

  async toggleComplete(id: string): Promise<TodoTask | null> {
    const task = this.getAll().find(t => t.id === id);
    if (!task) return null;

    return await this.update(id, { completed: !task.completed });
  }

  getTasksByCategory(categoryId: string): TodoTask[] {
    return this.getAll().filter(task => task.categoryId === categoryId);
  }

  getTasksByPriority(priority: TaskPriority): TodoTask[] {
    return this.getAll().filter(task => task.priority === priority);
  }

  getOverdueTasks(): TodoTask[] {
    const now = new Date();
    return this.getAll().filter(task => 
      task.dueDate && task.dueDate < now && !task.completed
    );
  }

  private async saveTasks(tasks: TodoTask[]): Promise<void> {
    try {
      await this.storage.set(this.storageKey, tasks);
      this.tasksSubject.next(tasks);
    } catch (error) {
      console.error('Error saving tasks:', error);
      throw error;
    }
  }
}
