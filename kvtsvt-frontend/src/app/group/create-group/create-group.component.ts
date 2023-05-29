import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupAdminModel } from '../group-admin-model';
import { GroupRequestService } from '../group-request.service';
import { GroupRequestModel } from '../group-request-model';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css'],
})
export class CreateGroupComponent {
  createGroupForm: FormGroup;
  groupModel: GroupModel;
  groupRequest: GroupRequestModel;
  groupAdminModel: GroupAdminModel;
  name = new FormControl('');
  description = new FormControl('');
  username: string = '';
  userId: number = 0;
  groupId: number = 0;

  constructor(
    private router: Router,
    private groupService: GroupService,
    private authService: AuthService,
    private groupRequestService: GroupRequestService
  ) {
    this.createGroupForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.groupRequest = {
      id: 0,
      approved: false,
      isBanned: false,
      groupId: 0,
      userId: 0,
    };
    this.groupModel = {
      id: 0,
      name: '',
      description: '',
      adminId: 0,
    };
    this.groupAdminModel = {
      id: 0,
      isDeleted: false,
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {}

  discard() {
    this.router.navigateByUrl('/');
  }

  createGroup() {
    this.groupModel.name = this.createGroupForm.get('name')?.value;
    this.groupModel.description =
      this.createGroupForm.get('description')?.value;

    this.groupService.createGroup(this.groupModel).subscribe(
      (data) => {
        this.groupId = data?.id ?? 0;
        this.router.navigateByUrl('/');
        this.createGroupAdmin();
        this.createGroupRequestForAdmin();
      },
      (error) => {
        throwError(error);
      }
    );
  }

  createGroupAdmin() {
    this.userId = this.authService.getUserId();
    this.groupAdminModel.groupId = this.groupId;
    this.groupAdminModel.userId = this.userId;
    this.groupAdminModel.isDeleted = false;
    console.log(this.groupId);
    this.groupService.createGroupAdmin(this.groupAdminModel).subscribe(
      (data) => {
        console.log('created group admin');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  createGroupRequestForAdmin() {
    this.groupRequest.approved = true;
    this.groupRequest.groupId = this.groupId;
    this.groupRequest.userId = this.userId;

    this.groupRequestService.createGroupRequest(this.groupRequest).subscribe();
  }
}
