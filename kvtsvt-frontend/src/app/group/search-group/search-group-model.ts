export interface GroupSearchModel {
  // Basic search fields
  name?: string;
  description?: string;
  
  // PDF content search
  pdfContent?: string;
  
  // Rules search
  rules?: string;
  
  // Post average likes range
  postAverageLikes?: number[];
  
  // Post count range
  postCount?: number[];
  
  // Boolean query operator
  operation?: string;
  
  // Pagination
  page?: number;
  size?: number;
} 