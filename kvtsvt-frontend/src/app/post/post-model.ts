export interface PostModel {
  id: number;
  title: string;
  content: string;
  isDeleted: boolean;
  userId: number;
  groupId: number;
  creationDate: Date;
}
