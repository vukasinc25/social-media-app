import { Component, OnInit } from '@angular/core';
import { GroupRequestModel } from '../group-request-model';
import { GroupRequestService } from '../group-request.service';
import { RegisterRequestModel } from 'src/app/auth/register/register-request-model';
import { AuthService } from 'src/app/auth/shared/auth.service';
import { GroupService } from '../group.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-admin-group',
  templateUrl: './admin-group.component.html',
  styleUrls: ['./admin-group.component.css'],
})
export class AdminGroupComponent implements OnInit {
  groupRequests: Array<GroupRequestModel> = [];
  users: Array<RegisterRequestModel> = [];
  id: number = 0;
  constructor(
    private groupRequestService: GroupRequestService,
    private authService: AuthService,
    private groupService: GroupService,
    private activatedRoute: ActivatedRoute
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
      this.groupRequests = data;
    });
    // this.groupRequestService.getRequest(this.id).subscribe((data) => {});
  }

  getUsersFromGroup() {
    this.authService.getUsersFromGroup(this.id).subscribe((data) => {
      this.users = data;
    });
  }
}
