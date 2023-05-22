import { ReactionType } from './reaction-type';

export interface ReactionModel {
  reactionType: ReactionType;
  postId: number;
  userId: number;
}
