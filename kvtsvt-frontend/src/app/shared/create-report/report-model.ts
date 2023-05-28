export interface ReportModel {
  id: number;
  reportReason: string;
  isAccepted: boolean;
  byUserId: number;
  groupId?: number;
  reportedUserId?: number;
  postId?: number;
  commentId?: number;
}
