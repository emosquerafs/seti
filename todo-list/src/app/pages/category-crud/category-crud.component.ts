import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { IonicModule, ModalController, AlertController } from '@ionic/angular';
import { CategoriesService } from '../../services/categories.service';
import { ToastService } from '../../services/toast.service';
import { Category, CreateCategoryDto } from '../../models/category.model';

@Component({
  selector: 'app-category-crud',
  templateUrl: './category-crud.component.html',
  styleUrls: ['./category-crud.component.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, IonicModule]
})
export class CategoryCrudComponent implements OnInit {
  categories: Category[] = [];
  categoryForm!: FormGroup;
  editingCategory: Category | null = null;
  
  predefinedColors = [
    '#3880ff', '#10dc60', '#ffce00', '#f04141',
    '#7044ff', '#f2a900', '#cd1719', '#32db64',
    '#488aff', '#f53d3d', '#ffc125', '#7e57c2'
  ];

  predefinedIcons = [
    'home', 'briefcase', 'school', 'person', 'car', 'fitness',
    'restaurant', 'medical', 'game-controller', 'musical-notes',
    'camera', 'airplane', 'basket', 'book', 'heart', 'star'
  ];

  constructor(
    private categoriesService: CategoriesService,
    private modalCtrl: ModalController,
    private alertCtrl: AlertController,
    private toastService: ToastService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.initForm();
    this.loadCategories();
  }

  private initForm() {
    this.categoryForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      color: ['#3880ff', Validators.required],
      icon: ['pricetag']
    });
  }

  private loadCategories() {
    this.categories = this.categoriesService.getAll();
  }

  async onSubmit() {
    if (this.categoryForm.valid) {
      const categoryData: CreateCategoryDto = this.categoryForm.value;
      
      try {
        if (this.editingCategory) {
          await this.categoriesService.update(this.editingCategory.id, categoryData);
          await this.toastService.presentSuccessToast('Categoría actualizada');
        } else {
          await this.categoriesService.add(categoryData);
          await this.toastService.presentSuccessToast('Categoría creada');
        }
        
        this.resetForm();
        this.loadCategories();
      } catch (error) {
        await this.toastService.presentErrorToast('Error al guardar la categoría');
      }
    }
  }

  editCategory(category: Category) {
    this.editingCategory = category;
    this.categoryForm.patchValue({
      name: category.name,
      color: category.color,
      icon: category.icon
    });
  }

  async deleteCategory(category: Category) {
    const alert = await this.alertCtrl.create({
      header: 'Confirmar eliminación',
      message: `¿Estás seguro de que quieres eliminar la categoría "${category.name}"?`,
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
              await this.categoriesService.remove(category.id);
              await this.toastService.presentSuccessToast('Categoría eliminada');
              this.loadCategories();
            } catch (error) {
              await this.toastService.presentErrorToast('Error al eliminar la categoría');
            }
          }
        }
      ]
    });

    await alert.present();
  }

  resetForm() {
    this.editingCategory = null;
    this.categoryForm.reset({
      name: '',
      color: '#3880ff',
      icon: 'pricetag'
    });
  }

  dismiss() {
    this.modalCtrl.dismiss();
  }

  selectColor(color: string) {
    this.categoryForm.patchValue({ color });
  }

  selectIcon(icon: string) {
    this.categoryForm.patchValue({ icon });
  }
}
