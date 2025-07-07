import { Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  constructor(private toastController: ToastController) {}

  async presentToast(message: string, color: 'success' | 'warning' | 'danger' | 'primary' = 'primary', duration: number = 2000) {
    const toast = await this.toastController.create({
      message,
      duration,
      color,
      position: 'bottom',
      buttons: [
        {
          text: 'Cerrar',
          role: 'cancel'
        }
      ]
    });
    await toast.present();
  }

  async presentSuccessToast(message: string) {
    await this.presentToast(message, 'success');
  }

  async presentErrorToast(message: string) {
    await this.presentToast(message, 'danger', 3000);
  }

  async presentWarningToast(message: string) {
    await this.presentToast(message, 'warning');
  }
}
