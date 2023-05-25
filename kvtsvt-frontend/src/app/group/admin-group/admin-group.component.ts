import { Component, OnInit } from '@angular/core';
import { GroupRequestModel } from '../group-request-model';
import { GroupRequestService } from '../group-request.service';

@Component({
  selector: 'app-admin-group',
  templateUrl: './admin-group.component.html',
  styleUrls: ['./admin-group.component.css'],
})
export class AdminGroupComponent implements OnInit {
  groupRequests: Array<GroupRequestModel> = [];

  constructor(private groupRequestService: GroupRequestService) {}

  ngOnInit(): void {
    this.groupRequestService.getAllRequests().subscribe((data) => {
      this.groupRequests = data;
    });
  }
}
