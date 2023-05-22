import { PostModel } from './../../post/post-model';
import { Component, OnInit, ViewEncapsulation, Input } from '@angular/core';
import { faComments } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-overview',
  templateUrl: './post-overview.component.html',
  styleUrls: ['./post-overview.component.css'],
})
export class PostOverviewComponent implements OnInit {
  faComments = faComments;
  @Input() posts: PostModel[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {}

  goToPost(id: number): void {
    this.router.navigateByUrl('/view-post/' + id);
  }
}
