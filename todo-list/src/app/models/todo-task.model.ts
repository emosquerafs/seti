export interface TodoTask {
  id: string;
  title: string;
  description?: string;
  completed: boolean;
  priority: TaskPriority;
  categoryId?: string;
  dueDate?: Date;
  createdAt: Date;
  updatedAt: Date;
}

export enum TaskPriority {
  LOW = 'low',
  MEDIUM = 'medium',
  HIGH = 'high',
  URGENT = 'urgent'
}

export interface CreateTaskDto {
  title: string;
  description?: string;
  priority?: TaskPriority;
  categoryId?: string;
  dueDate?: Date;
}

export interface UpdateTaskDto extends Partial<CreateTaskDto> {
  completed?: boolean;
}
