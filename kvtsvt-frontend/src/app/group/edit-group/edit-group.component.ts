import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { throwError } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupAdminModel } from '../group-admin-model';

@Component({
  selector: 'app-edit-group',
  templateUrl: './edit-group.component.html',
  styleUrls: ['./edit-group.component.css'],
})
export class EditGroupComponent {
  editGroupForm: FormGroup;
  groupModel: GroupModel;
  name = new FormControl('');
  description = new FormControl('');
  username: string = '';
  groupId: number = 0;

  constructor(
    private router: Router,
    private groupService: GroupService,
    private authService: AuthService,
    private activateRoute: ActivatedRoute
  ) {
    this.editGroupForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.groupId = this.activateRoute.snapshot.params['id'];
    this.groupModel = {
      id: 0,
      name: '',
      description: '',
      adminId: 0,
    };
  }

  ngOnInit() {
    this.groupService.getGroup(this.groupId).subscribe((data) => {
      this.editGroupForm.get('name')?.setValue(data.name);
      this.editGroupForm.get('description')?.setValue(data.description);
    });
  }

  discard() {
    this.router.navigateByUrl('/');
  }

  editGroup() {
    this.groupModel.id = this.groupId;
    this.groupModel.name = this.editGroupForm.get('name')?.value;
    this.groupModel.description = this.editGroupForm.get('description')?.value;
    this.groupService.editGroup(this.groupModel).subscribe(
      (data) => {
        this.router.navigateByUrl('/');
      },
      (error) => {
        throwError(error);
      }
    );
  }
}
