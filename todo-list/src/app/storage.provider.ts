// src/app/storage.provider.ts
import { Storage } from '@ionic/storage-angular';

export function provideIonicStorage() {
  return [
    {
      provide: Storage,
      useFactory: () => new Storage()
    }
  ];
}
