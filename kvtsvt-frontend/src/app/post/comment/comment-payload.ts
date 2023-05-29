export interface CommentPayload {
  id: number;
  text: string;
  isDeleted: boolean;
  postId?: number;
  userId: number;
  parentCommentId?: number;
}
