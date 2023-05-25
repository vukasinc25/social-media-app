import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-all-groups',
  templateUrl: './all-groups.component.html',
  styleUrls: ['./all-groups.component.css'],
})
export class AllGroupsComponent implements OnInit {
  groups: Array<GroupModel> = [];
  constructor(private groupService: GroupService) {}

  ngOnInit() {
    this.groupService.getAllGroups().subscribe(
      (data) => {
        this.groups = data;
      },
      (error) => {
        throwError(error);
      }
    );
  }
}
