export interface TodoTask {
  id: string;
  title: string;
  completed: boolean;
  categoryId?: string;
  createdAt: number;
}
