import { HttpInterceptorFn } from '@angular/common/http';

export const mainInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req);
};
