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
      adminId: [''],
      isSuspended: ['']
    });
  }

  ngOnInit(): void {
  }

  search(): void {
    this.isLoading = true;
    this.hasSearched = true;

    const searchModel: GroupSearchModel = {
      name: this.searchForm.value.name || undefined,
      description: this.searchForm.value.description || undefined,
      adminId: this.searchForm.value.adminId ? Number(this.searchForm.value.adminId) : undefined,
      isSuspended: this.searchForm.value.isSuspended !== '' ? this.searchForm.value.isSuspended === 'true' : undefined
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

    if (searchModel.adminId) {
      filteredGroups = filteredGroups.filter(group => group.adminId === searchModel.adminId);
    }

    if (searchModel.isSuspended !== undefined) {
      filteredGroups = filteredGroups.filter(group => group.isSuspended === searchModel.isSuspended);
    }

    return filteredGroups;
  }

  clearSearch(): void {
    this.searchForm.reset();
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
