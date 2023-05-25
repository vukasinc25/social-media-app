export interface GroupRequestModel {
  id: number;
  approved: boolean;
  requestDate: Date;
  responseDate: Date;
  isDeleted: boolean;
  groupId: number;
  userId: number;
}
