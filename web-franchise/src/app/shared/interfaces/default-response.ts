export interface DefaultResponse<T> {
  error: boolean;
  message: string;
  httpStatus: string;
  body: T;
}
