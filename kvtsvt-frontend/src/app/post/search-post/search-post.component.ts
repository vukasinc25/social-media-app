import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PostService } from '../post.service';
import { PostModel } from '../post-model';
import { PostSearchModel } from './search-post-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-post',
  templateUrl: './search-post.component.html',
  styleUrls: ['./search-post.component.css']
})
export class SearchPostComponent implements OnInit {
  searchForm: FormGroup;
  searchResults: PostModel[] = [];
  isLoading = false;
  hasSearched = false;

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostService,
    private router: Router
  ) {
    this.searchForm = this.formBuilder.group({
      title: [''],
      content: [''],
      pdfContent: [''],
      likeCountMin: [''],
      likeCountMax: [''],
      commentCountMin: [''],
      commentCountMax: [''],
      operation: ['AND']
    });
  }

  ngOnInit(): void {}

  search(): void {
    this.isLoading = true;
    this.hasSearched = true;

    const likeCount: number[] = [];
    if (this.searchForm.value.likeCountMin !== '' && this.searchForm.value.likeCountMin !== null) {
      likeCount.push(Number(this.searchForm.value.likeCountMin));
    }
    if (this.searchForm.value.likeCountMax !== '' && this.searchForm.value.likeCountMax !== null) {
      likeCount.push(Number(this.searchForm.value.likeCountMax));
    }

    const commentCount: number[] = [];
    if (this.searchForm.value.commentCountMin !== '' && this.searchForm.value.commentCountMin !== null) {
      commentCount.push(Number(this.searchForm.value.commentCountMin));
    }
    if (this.searchForm.value.commentCountMax !== '' && this.searchForm.value.commentCountMax !== null) {
      commentCount.push(Number(this.searchForm.value.commentCountMax));
    }

    const searchModel: PostSearchModel = {
      title: this.searchForm.value.title || undefined,
      content: this.searchForm.value.content || undefined,
      pdfContent: this.searchForm.value.pdfContent || undefined,
      likeCount: likeCount.length > 0 ? likeCount : undefined,
      commentCount: commentCount.length > 0 ? commentCount : undefined,
      operation: this.searchForm.value.operation || 'AND'
    };

    this.postService.searchPosts(searchModel).subscribe({
      next: (results) => {
        console.log(results);
        this.searchResults = results.content;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error searching posts:', error);
        this.isLoading = false;
        this.searchResults = [];
      }
    });
  }

  clearSearch(): void {
    this.searchForm.reset({ operation: 'AND' });
    this.searchResults = [];
    this.hasSearched = false;
  }

  viewPost(postId: number): void {
    this.router.navigate(['/view-post', postId]);
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString();
  }
}
