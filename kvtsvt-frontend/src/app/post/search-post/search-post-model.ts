export interface PostSearchModel {
  keywords?: string[];
  name?: string;
  title?: string;
  content?: string;
  description?: string;
  pdfContent?: string;
  rules?: string;
  postAverageLikes?: number[];
  postCount?: number[];
  likeCount?: number[];
  commentCount?: number[];
  operation?: string;
} 