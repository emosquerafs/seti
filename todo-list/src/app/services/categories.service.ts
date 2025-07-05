import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage-angular';
import { Category } from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class CategoriesService {
  private storageKey = 'categories';
  private categories: Category[] = [];

  constructor(private storage: Storage) {
    this.init();
  }

  private async init() {
    await this.storage.create();
    const stored = await this.storage.get(this.storageKey);
    this.categories = stored || []; 
  }

  getAll(): Category[] {
    return [...this.categories];
  }

  async add(category: Category) {
    this.categories.push(category);
    await this.storage.set(this.storageKey, this.categories);
  }

  async update(category: Category) {
    const idx = this.categories.findIndex(c => c.id === category.id);
    if (idx !== -1) {
      this.categories[idx] = category;
      await this.storage.set(this.storageKey, this.categories);
    }
  }

  async remove(id: string) {
    this.categories = this.categories.filter(c => c.id !== id);
    await this.storage.set(this.storageKey, this.categories);
  }
}
