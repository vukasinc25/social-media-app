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
  selectedFile: File | null = null;

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

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      this.selectedFile = file;
    } else {
      alert('Please select a valid PDF file');
      event.target.value = '';
    }
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  createGroup() {
    this.groupModel.name = this.createGroupForm.get('name')?.value;
    this.groupModel.description =
      this.createGroupForm.get('description')?.value;

    this.groupService.createGroup(this.groupModel, this.selectedFile || undefined).subscribe(
      (data) => {
        this.groupId = data?.id ?? 0;
        this.userId = this.authService.getUserId();
        this.createGroupAdmin();
        this.createGroupRequestForAdmin();
        this.router.navigateByUrl('/');
      },
      (error) => {
        throwError(error);
      }
    );
  }

  createGroupAdmin() {
    this.groupAdminModel.groupId = this.groupId;
    this.groupAdminModel.userId = this.userId;
    this.groupAdminModel.isDeleted = false;
    console.log('Creating group admin for group ID:', this.groupId, 'and user ID:', this.userId);
    this.groupService.createGroupAdmin(this.groupAdminModel).subscribe(
      (data) => {
        console.log('Group admin created successfully');
      },
      (error) => {
        console.error('Error creating group admin:', error);
        // Don't throw error here to prevent the entire group creation from failing
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
