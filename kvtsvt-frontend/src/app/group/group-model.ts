export interface GroupModel {
  id: number;
  name?: string;
  description?: string;
  adminId: number;
  isSuspended?: boolean;
  suspensionReason?: string;
}
