export interface FriendRequestModel {
  id: number;
  approved: boolean;
  requestDate?: Date;
  responseDate?: Date;
  requestFromId: number;
  requestForId: number;
}
