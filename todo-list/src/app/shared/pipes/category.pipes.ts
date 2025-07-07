import { Pipe, PipeTransform } from '@angular/core';
import { Category } from '../../models/category.model';

@Pipe({
  name: 'categoryName',
  standalone: true
})
export class CategoryNamePipe implements PipeTransform {
  transform(categoryId: string | undefined, categories: Category[]): string {
    if (!categoryId) return 'Sin categoría';
    
    const category = categories.find(c => c.id === categoryId);
    return category ? category.name : 'Sin categoría';
  }
}

@Pipe({
  name: 'categoryColor',
  standalone: true
})
export class CategoryColorPipe implements PipeTransform {
  transform(categoryId: string | undefined, categories: Category[]): string {
    if (!categoryId) return 'var(--ion-color-medium)';
    
    const category = categories.find(c => c.id === categoryId);
    return category ? category.color : 'var(--ion-color-medium)';
  }
}

@Pipe({
  name: 'categoryIcon',
  standalone: true
})
export class CategoryIconPipe implements PipeTransform {
  transform(categoryId: string | undefined, categories: Category[]): string {
    if (!categoryId) return 'pricetag-outline';
    
    const category = categories.find(c => c.id === categoryId);
    return category?.icon || 'pricetag';
  }
}
