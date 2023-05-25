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
  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private groupService: GroupService
  ) {
    this.groupId = this.activatedRoute.snapshot.params['id'];
  }
  ngOnInit() {}

  goToCreatePost() {
    this.router.navigateByUrl('/create-post');
  }

  goToEditGroup() {
    this.router.navigateByUrl('/edit-group/' + this.groupId);
  }

  deleteGroup() {
    this.groupService.deleteGroup(this.groupId);
  }
}
