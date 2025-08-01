export interface GroupSearchModel {
  // Basic search fields
  name?: string;
  description?: string;
  adminId?: number;
  isSuspended?: boolean;
  
  // PDF description search
  pdfDescription?: string;
  
  // Post count range
  postsFrom?: number;
  postsTo?: number;
  
  // Average likes range
  avgLikesFrom?: number;
  avgLikesTo?: number;
  
  // Boolean query operator
  useAndOperator?: boolean;
  
  // Pagination
  page?: number;
  size?: number;
} 