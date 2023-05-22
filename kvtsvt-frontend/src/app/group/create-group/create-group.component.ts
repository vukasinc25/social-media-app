import { GroupModel } from './../group-model';
import { GroupService } from './../group.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.css'],
})
export class CreateGroupComponent {
  createGroupForm: FormGroup;
  groupModel: GroupModel;
  name = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private groupService: GroupService) {
    this.createGroupForm = new FormGroup({
      name: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required),
    });
    this.groupModel = {
      name: '',
      description: '',
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
        this.router.navigateByUrl('/all-groups');
      },
      (error) => {
        throwError(error);
      }
    );
  }
}
