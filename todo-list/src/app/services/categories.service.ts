import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage-angular';
import { BehaviorSubject, Observable } from 'rxjs';
import { Category, CreateCategoryDto, UpdateCategoryDto } from '../models/category.model';

@Injectable({ providedIn: 'root' })
export class CategoriesService {
  private storageKey = 'categories';
  private categoriesSubject = new BehaviorSubject<Category[]>([]);
  public categories$ = this.categoriesSubject.asObservable();

  private defaultCategories: CreateCategoryDto[] = [
    { name: 'Personal', color: '#3880ff', icon: 'person' },
    { name: 'Trabajo', color: '#10dc60', icon: 'briefcase' },
    { name: 'Estudios', color: '#ffce00', icon: 'school' },
    { name: 'Hogar', color: '#f04141', icon: 'home' }
  ];

  constructor(private storage: Storage) {
    this.init();
  }

  private async init() {
    try {
      await this.storage.create();
      const stored = await this.storage.get(this.storageKey);
      let categories = stored ? stored.map((cat: any) => ({
        ...cat,
        createdAt: new Date(cat.createdAt)
      })) : [];

      // Si no hay categorías, crear las predeterminadas
      if (categories.length === 0) {
        categories = await this.createDefaultCategories();
      }

      this.categoriesSubject.next(categories);
    } catch (error) {
      console.error('Error initializing categories service:', error);
      this.categoriesSubject.next([]);
    }
  }

  private async createDefaultCategories(): Promise<Category[]> {
    const categories: Category[] = this.defaultCategories.map(dto => ({
      id: crypto.randomUUID(),
      ...dto,
      createdAt: new Date()
    }));

    await this.saveCategories(categories);
    return categories;
  }

  getAll(): Category[] {
    return this.categoriesSubject.value;
  }

  getCategories$(): Observable<Category[]> {
    return this.categories$;
  }

  getById(id: string): Category | undefined {
    return this.getAll().find(cat => cat.id === id);
  }

  async add(categoryDto: CreateCategoryDto): Promise<Category> {
    const category: Category = {
      id: crypto.randomUUID(),
      ...categoryDto,
      createdAt: new Date()
    };

    const categories = [...this.getAll(), category];
    await this.saveCategories(categories);
    return category;
  }

  async update(id: string, updateDto: UpdateCategoryDto): Promise<Category | null> {
    const categories = this.getAll();
    const index = categories.findIndex(c => c.id === id);
    
    if (index === -1) return null;

    const updatedCategory = { ...categories[index], ...updateDto };
    categories[index] = updatedCategory;
    await this.saveCategories(categories);
    return updatedCategory;
  }

  async remove(id: string): Promise<boolean> {
    const categories = this.getAll().filter(c => c.id !== id);
    await this.saveCategories(categories);
    return true;
  }

  private async saveCategories(categories: Category[]): Promise<void> {
    try {
      await this.storage.set(this.storageKey, categories);
      this.categoriesSubject.next(categories);
    } catch (error) {
      console.error('Error saving categories:', error);
      throw error;
    }
  }
}
