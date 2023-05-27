export interface ReactionModel {
  id: number;
  reactionType: String;
  isDeleted: boolean;
  userId: number;
  postId?: number;
  commentId?: number;
}
