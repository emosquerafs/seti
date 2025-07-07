import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IonicModule, ModalController } from '@ionic/angular';
import { Category, CreateCategoryDto } from '../../models/category.model';
import { CategoriesService } from '../../services/categories.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-quick-category',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, IonicModule],
  template: `
    <ion-header>
      <ion-toolbar>
        <ion-title>Seleccionar Categoría</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="dismiss()">
            <ion-icon name="close"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding">
      <!-- Quick category selection -->
      <div class="categories-grid">
        <div 
          class="category-option"
          [class.selected]="selectedCategoryId === null"
          (click)="selectCategory(null)">
          <ion-icon name="close-circle-outline"></ion-icon>
          <span>Sin categoría</span>
        </div>
        
        <div 
          *ngFor="let category of categories"
          class="category-option"
          [class.selected]="selectedCategoryId === category.id"
          [style.--category-color]="category.color"
          (click)="selectCategory(category.id)">
          <ion-icon [name]="category.icon || 'pricetag'"></ion-icon>
          <span>{{ category.name }}</span>
        </div>
      </div>

      <!-- Quick create new category -->
      <ion-card>
        <ion-card-header>
          <ion-card-title>Crear Nueva Categoría</ion-card-title>
        </ion-card-header>
        
        <ion-card-content>
          <form [formGroup]="quickCategoryForm" (ngSubmit)="createQuickCategory()">
            <ion-item>
              <ion-label position="stacked">Nombre</ion-label>
              <ion-input 
                formControlName="name"
                placeholder="Nombre de la categoría">
              </ion-input>
            </ion-item>
            
            <div class="quick-colors">
              <div 
                *ngFor="let color of quickColors"
                class="quick-color-option"
                [class.selected]="quickCategoryForm.get('color')?.value === color"
                [style.background-color]="color"
                (click)="selectQuickColor(color)">
              </div>
            </div>
            
            <ion-button 
              expand="block" 
              type="submit" 
              [disabled]="quickCategoryForm.invalid"
              color="primary">
              <ion-icon name="add" slot="start"></ion-icon>
              Crear y Seleccionar
            </ion-button>
          </form>
        </ion-card-content>
      </ion-card>

      <div class="actions">
        <ion-button 
          expand="block" 
          fill="outline" 
          [disabled]="!hasSelection()"
          (click)="confirmSelection()">
          Confirmar Selección
        </ion-button>
      </div>
    </ion-content>
  `,
  styles: [`
    .categories-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
      gap: 12px;
      margin-bottom: 24px;
    }

    .category-option {
      display: flex;
      flex-direction: column;
      align-items: center;
      padding: 16px 8px;
      border: 2px solid var(--ion-color-light);
      border-radius: 12px;
      cursor: pointer;
      transition: all 0.3s ease;
      background: white;
      text-align: center;
    }

    .category-option:hover {
      border-color: var(--ion-color-primary);
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    .category-option.selected {
      border-color: var(--category-color, var(--ion-color-primary));
      background: var(--category-color, var(--ion-color-primary));
      color: white;
    }

    .category-option ion-icon {
      font-size: 1.5rem;
      margin-bottom: 8px;
    }

    .category-option span {
      font-size: 0.875rem;
      font-weight: 500;
    }

    .quick-colors {
      display: flex;
      gap: 8px;
      margin: 16px 0;
      justify-content: center;
    }

    .quick-color-option {
      width: 32px;
      height: 32px;
      border-radius: 50%;
      cursor: pointer;
      border: 3px solid transparent;
      transition: all 0.3s ease;
    }

    .quick-color-option:hover {
      transform: scale(1.1);
    }

    .quick-color-option.selected {
      border-color: var(--ion-color-dark);
      transform: scale(1.1);
    }

    .actions {
      margin-top: 24px;
    }

    ion-card {
      --border-radius: 12px;
    }
  `]
})
export class QuickCategoryComponent {
  @Input() categories: Category[] = [];
  @Input() selectedCategoryId: string | null = null;
  @Output() categorySelected = new EventEmitter<string | null>();

  quickCategoryForm!: FormGroup;
  
  quickColors = [
    '#3880ff', '#10dc60', '#ffce00', '#f04141',
    '#7044ff', '#f2a900', '#cd1719', '#32db64'
  ];

  constructor(
    private fb: FormBuilder,
    private modalCtrl: ModalController,
    private categoriesService: CategoriesService,
    private toastService: ToastService
  ) {
    this.initQuickForm();
  }

  private initQuickForm() {
    this.quickCategoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      color: [this.quickColors[0], Validators.required]
    });
  }

  selectCategory(categoryId: string | null) {
    this.selectedCategoryId = categoryId;
  }

  selectQuickColor(color: string) {
    this.quickCategoryForm.patchValue({ color });
  }

  async createQuickCategory() {
    if (this.quickCategoryForm.valid) {
      try {
        const categoryData: CreateCategoryDto = {
          ...this.quickCategoryForm.value,
          icon: 'pricetag'
        };
        
        const newCategory = await this.categoriesService.add(categoryData);
        this.categories.push(newCategory);
        this.selectedCategoryId = newCategory.id;
        
        await this.toastService.presentSuccessToast('Categoría creada');
        this.quickCategoryForm.reset({
          name: '',
          color: this.quickColors[0]
        });
      } catch (error) {
        await this.toastService.presentErrorToast('Error al crear la categoría');
      }
    }
  }

  hasSelection(): boolean {
    return this.selectedCategoryId !== undefined;
  }

  confirmSelection() {
    this.modalCtrl.dismiss(this.selectedCategoryId, 'confirm');
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }
}
