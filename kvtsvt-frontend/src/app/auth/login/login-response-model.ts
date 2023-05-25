export interface LoginResponse {
  userId: number;
  authenticationToken: string;
  refreshToken: string;
  expiresAt: Date;
  username: string;
  role: string;
}
