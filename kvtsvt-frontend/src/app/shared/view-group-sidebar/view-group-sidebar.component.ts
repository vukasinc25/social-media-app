import { GroupAdminModel } from './../../group/group-admin-model';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GroupService } from 'src/app/group/group.service';

@Component({
  selector: 'app-view-group-sidebar',
  templateUrl: './view-group-sidebar.component.html',
  styleUrls: ['./view-group-sidebar.component.css'],
})
export class ViewGroupSidebarComponent implements OnInit {
  groupId: number = 0;
  groupName: string = '';
  groupDescription: string = '';
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private groupService: GroupService
  ) {
    this.groupId = this.activatedRoute.snapshot.params['id'];
  }
  ngOnInit() {
    this.groupService.getGroup(this.groupId).subscribe((data) => {
      if (data.name && data.description) {
        this.groupName = data.name;
        this.groupDescription = data.description;
      }
    });
  }

  goToCreatePost() {
    this.router.navigateByUrl('/create-post');
  }

  goToEditGroup() {
    this.router.navigateByUrl('/edit-group/' + this.groupId);
  }

  goToGroupAdmin() {
    this.router.navigateByUrl('/group-admin/' + this.groupId);
  }

  deleteGroup() {
    this.groupService.deleteGroup(this.groupId).subscribe((data) => {
      this.router.navigateByUrl('/');
    });
  }
}
