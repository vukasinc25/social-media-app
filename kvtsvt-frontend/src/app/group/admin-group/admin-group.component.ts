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
  groupUsers: Array<GroupRequestModel> = [];
  groupRequest: GroupRequestModel;
  users: Array<RegisterRequestModel> = [];
  id: number = 0;
  constructor(
    private groupRequestService: GroupRequestService,
    private authService: AuthService,
    private groupService: GroupService,
    private activatedRoute: ActivatedRoute
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];
    console.log(this.id);
    this.groupRequest = {
      id: 0,
      approved: false,
      isBanned: false,
      groupId: 0,
      userId: 0,
    };
  }

  ngOnInit(): void {
    this.getGroupRequests();
    this.getUsersFromGroup();
  }

  getGroupRequests() {
    this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
      for (const request of data) {
        if (!request.approved) {
          this.groupRequests = data;
        }
      }
    });
  }

  getUsersFromGroup() {
    this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
      for (const request of data) {
        if (request.approved) {
          this.groupUsers = data;
        }
      }
    });
  }

  // getUsersFromGroup() {
  //   this.groupRequestService.getAllRequests(this.id).subscribe((data) => {
  //     for (const request of data) {
  //       this.authService.getUser(request.userId).subscribe((user) => {
  //         this.users.push(user);
  //       });
  //     }
  //   });
  // }

  banUser(id: number) {
    this.groupRequest.id = id;
    this.groupRequestService.banGR(this.groupRequest).subscribe(() => {
      this.getUsersFromGroup();
    });
  }

  unbanUser(id: number) {}

  acceptRequest(id: number) {
    this.groupRequest.id = id;
    this.groupRequestService.approveGR(this.groupRequest).subscribe(() => {
      this.ngOnInit();
    });
  }

  declineRequest(id: number) {
    this.groupRequestService.deleteGroup(id).subscribe(() => {
      this.ngOnInit();
    });
  }
}
