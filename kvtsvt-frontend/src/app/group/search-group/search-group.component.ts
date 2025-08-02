import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { GroupService } from '../group.service';
import { GroupModel } from '../group-model';
import { GroupSearchModel } from './search-group-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-group',
  templateUrl: './search-group.component.html',
  styleUrls: ['./search-group.component.css']
})
export class SearchGroupComponent implements OnInit {
  searchForm: FormGroup;
  searchResults: GroupModel[] = [];
  isLoading = false;
  hasSearched = false;

  constructor(
    private formBuilder: FormBuilder,
    private groupService: GroupService,
    private router: Router
  ) {
    this.searchForm = this.formBuilder.group({
      name: [''],
      description: [''],
      pdfContent: [''],
      rules: [''],
      postCountFrom: [''],
      postCountTo: [''],
      avgLikesFrom: [''],
      avgLikesTo: [''],
      operation: ['OR']
    });
  }

  ngOnInit(): void {
  }

  search(): void {
    this.isLoading = true;
    this.hasSearched = true;

    const formValue = this.searchForm.value;
    const searchModel: GroupSearchModel = {
      name: formValue.name || undefined,
      description: formValue.description || undefined,
      pdfContent: formValue.pdfContent || undefined,
      rules: formValue.rules || undefined,
      postCount: formValue.postCountFrom || formValue.postCountTo ? 
        [formValue.postCountFrom ? Number(formValue.postCountFrom) : 0, 
         formValue.postCountTo ? Number(formValue.postCountTo) : 999999] : undefined,
      postAverageLikes: formValue.avgLikesFrom || formValue.avgLikesTo ? 
        [formValue.avgLikesFrom ? Number(formValue.avgLikesFrom) : 0, 
         formValue.avgLikesTo ? Number(formValue.avgLikesTo) : 999999] : undefined,
      operation: formValue.operation
    };

    this.groupService.searchGroups(searchModel).subscribe({
      next: (results) => {
        this.searchResults = results;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error searching groups:', error);
        this.isLoading = false;
        // For now, if the backend doesn't have the search endpoint, we'll use getAllGroups
        this.groupService.getAllGroups().subscribe(groups => {
          this.searchResults = this.filterGroupsLocally(groups, searchModel);
          this.isLoading = false;
        });
      }
    });
  }

  private filterGroupsLocally(groups: GroupModel[], searchModel: GroupSearchModel): GroupModel[] {
    let filteredGroups = groups;

    if (searchModel.name) {
      filteredGroups = filteredGroups.filter(group => 
        group.name?.toLowerCase().includes(searchModel.name!.toLowerCase())
      );
    }

    if (searchModel.description) {
      filteredGroups = filteredGroups.filter(group => 
        group.description?.toLowerCase().includes(searchModel.description!.toLowerCase())
      );
    }



    // Note: Local filtering for PDF description, post count, and average likes
    // would require additional data that might not be available in the basic group model
    // These would typically be handled by the backend search service

    return filteredGroups;
  }

  clearSearch(): void {
    this.searchForm.reset();
    this.searchForm.patchValue({ operation: 'OR' });
    this.searchResults = [];
    this.hasSearched = false;
  }

  viewGroup(groupId: number): void {
    this.router.navigate(['/view-group', groupId]);
  }

  getStatusBadgeClass(group: GroupModel): string {
    if (group.isSuspended) {
      return 'bg-danger';
    }
    return 'bg-success';
  }

  getStatusText(group: GroupModel): string {
    if (group.isSuspended) {
      return 'Suspended';
    }
    return 'Active';
  }
}
