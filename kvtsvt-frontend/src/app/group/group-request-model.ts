export interface GroupRequestModel {
  id: number;
  approved: boolean;
  requestDate?: Date;
  responseDate?: Date;
  isBanned: boolean;
  groupId: number;
  userId: number;
}
