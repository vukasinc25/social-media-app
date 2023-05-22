export interface GroupModel {
  id?: string;
  name: string;
  description: string;
  isSuspended?: boolean;
  suspensionReason?: string;
}
