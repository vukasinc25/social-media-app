export interface PostSearchModel {
  title?: string;
  content?: string;
  userId?: number;
  groupId?: number;
  startDate?: Date;
  endDate?: Date;
  sortBy?: 'newest' | 'oldest';
  page?: number;
  size?: number;
} 