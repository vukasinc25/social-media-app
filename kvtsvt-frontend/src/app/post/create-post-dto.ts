export interface CreatePostDto {
  id?: number;
  title: string;
  content: string;
  userId: number;
  groupId: number;
}
