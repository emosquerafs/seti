import { Injectable } from '@angular/core';
import { LoadingController } from '@ionic/angular';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private loadingElement: HTMLIonLoadingElement | null = null;

  constructor(private loadingController: LoadingController) {}

  async presentLoading(message: string = 'Cargando...') {
    if (this.loadingElement) {
      return;
    }

    this.loadingElement = await this.loadingController.create({
      message,
      spinner: 'circular'
    });

    await this.loadingElement.present();
  }

  async dismissLoading() {
    if (this.loadingElement) {
      await this.loadingElement.dismiss();
      this.loadingElement = null;
    }
  }

  async executeWithLoading<T>(
    operation: () => Promise<T>, 
    message: string = 'Procesando...'
  ): Promise<T> {
    try {
      await this.presentLoading(message);
      const result = await operation();
      return result;
    } finally {
      await this.dismissLoading();
    }
  }
}
