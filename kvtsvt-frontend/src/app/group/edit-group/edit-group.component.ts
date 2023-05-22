import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupAdminModel } from '../group-admin-model';

@Component({
  selector: 'app-edit-group',
  templateUrl: './edit-group.component.html',
  styleUrls: ['./edit-group.component.css'],
})
export class EditGroupComponent {
  createGroupForm: FormGroup;
  groupModel: GroupModel;
  groupAdminModel: GroupAdminModel;
  name = new FormControl('');
  description = new FormControl('');
  username: string = '';

  constructor(
    private router: Router,
    private groupService: GroupService,
    private authService: AuthService
  ) {
    this.createGroupForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.groupModel = {
      name: '',
      description: '',
    };
    this.groupAdminModel = {
      userId: 0,
      groupId: 0,
    };
  }

  ngOnInit() {}

  discard() {
    this.router.navigateByUrl('/');
  }

  editGroup() {
    this.groupModel.name = this.createGroupForm.get('name')?.value;
    this.groupModel.description =
      this.createGroupForm.get('description')?.value;
    this.groupService.createGroup(this.groupModel).subscribe(
      (data) => {
        if (data.id !== undefined) {
          this.groupAdminModel.groupId = data.id;
        }
        this.router.navigateByUrl('/all-groups');
      },
      (error) => {
        throwError(error);
      }
    );

    this.username = this.authService.getUserName();
    this.groupService.createGroupAdmin;
  }
}
