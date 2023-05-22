import { GroupModel } from './../../group/group-model';
import { GroupService } from './../../group/group.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-group-sidebar',
  templateUrl: './group-sidebar.component.html',
  styleUrls: ['./group-sidebar.component.css'],
})
export class GroupSidebarComponent {
  groups: Array<GroupModel> = [];
  displayViewAll: boolean;

  constructor(private groupService: GroupService) {
    this.groupService.getAllGroups().subscribe((data) => {
      if (data.length > 3) {
        this.groups = data.splice(0, 3);
        this.displayViewAll = true;
      } else {
        this.groups = data;
      }
    });
  }

  ngOnInit(): void {}
}
