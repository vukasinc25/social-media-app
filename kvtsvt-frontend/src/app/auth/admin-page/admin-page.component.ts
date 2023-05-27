import { Component, OnInit } from '@angular/core';
import { GroupAdminModel } from 'src/app/group/group-admin-model';
import { GroupModel } from 'src/app/group/group-model';
import { GroupService } from 'src/app/group/group.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css'],
})
export class AdminPageComponent implements OnInit {
  groupAdmins: Array<GroupAdminModel> = [];
  groups: Array<GroupModel> = [];
  group: GroupModel;

  constructor(private groupService: GroupService) {
    this.group = {
      id: 0,
      name: '',
      description: '',
      adminId: 0,
    };
  }

  ngOnInit() {
    this.getAllGroupAdmins();
    this.getAllGroups();
  }

  getAllGroupAdmins() {
    return this.groupService.getAllGroupAdmins().subscribe((data) => {
      this.groupAdmins = data;
    });
  }

  removeAdmin(id: number) {
    this.groupService.removeGroupAdmin(id).subscribe(() => {
      this.getAllGroupAdmins();
    });
  }

  getAllGroups() {
    return this.groupService.getAllGroups().subscribe((data) => {
      this.groups = data;
    });
  }

  suspendGroup(id: number, reason: string) {
    this.group.id = id;
    this.group.suspensionReason = reason;

    this.groupService.deleteGroup(this.group).subscribe(() => {
      this.groupService.getAllGroupAdmins().subscribe((groupAdmins) => {
        for (const groupAdmin of groupAdmins) {
          if (groupAdmin.groupId === this.group.id) {
            this.removeAdmin(groupAdmin.id);
          }
        }
      });
      this.ngOnInit();
    });
  }
}
