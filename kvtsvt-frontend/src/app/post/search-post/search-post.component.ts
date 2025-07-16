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
      content: [''],
      userId: [''],
      groupId: [''],
      startDate: [''],
      endDate: [''],
      sortBy: ['newest']
    });
  }

  ngOnInit(): void {
  }

  search(): void {
    this.isLoading = true;
    this.hasSearched = true;

    const searchModel: PostSearchModel = {
      content: this.searchForm.value.content || undefined,
      userId: this.searchForm.value.userId ? Number(this.searchForm.value.userId) : undefined,
      groupId: this.searchForm.value.groupId ? Number(this.searchForm.value.groupId) : undefined,
      startDate: this.searchForm.value.startDate ? new Date(this.searchForm.value.startDate) : undefined,
      endDate: this.searchForm.value.endDate ? new Date(this.searchForm.value.endDate) : undefined,
      sortBy: this.searchForm.value.sortBy
    };

    this.postService.searchPosts(searchModel).subscribe({
      next: (results) => {
        this.searchResults = results;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error searching posts:', error);
        this.isLoading = false;
        // For now, if the backend doesn't have the search endpoint, we'll use getAllPosts
        this.postService.getAllPosts().subscribe(posts => {
          this.searchResults = this.filterPostsLocally(posts, searchModel);
          this.isLoading = false;
        });
      }
    });
  }

  private filterPostsLocally(posts: PostModel[], searchModel: PostSearchModel): PostModel[] {
    let filteredPosts = posts;

    if (searchModel.content) {
      filteredPosts = filteredPosts.filter(post => 
        post.content.toLowerCase().includes(searchModel.content!.toLowerCase())
      );
    }

    if (searchModel.userId) {
      filteredPosts = filteredPosts.filter(post => post.userId === searchModel.userId);
    }

    if (searchModel.groupId) {
      filteredPosts = filteredPosts.filter(post => post.groupId === searchModel.groupId);
    }

    if (searchModel.startDate) {
      filteredPosts = filteredPosts.filter(post => 
        new Date(post.creationDate) >= searchModel.startDate!
      );
    }

    if (searchModel.endDate) {
      filteredPosts = filteredPosts.filter(post => 
        new Date(post.creationDate) <= searchModel.endDate!
      );
    }

    // Sort posts
    if (searchModel.sortBy === 'newest') {
      filteredPosts.sort((a, b) => new Date(b.creationDate).getTime() - new Date(a.creationDate).getTime());
    } else {
      filteredPosts.sort((a, b) => new Date(a.creationDate).getTime() - new Date(b.creationDate).getTime());
    }

    return filteredPosts;
  }

  clearSearch(): void {
    this.searchForm.reset({ sortBy: 'newest' });
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
