import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from '../auth/shared/auth.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { SearchModel } from '../auth/shared/search-model';
import { RegisterRequestModel } from '../auth/register/register-request-model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  faUser = faUser;
  isLoggedIn!: boolean;
  username!: string;
  id: number = 0;

  searchForm: FormGroup;
  searchResults: Array<RegisterRequestModel> = [];
  searchQuery: SearchModel;
  @ViewChild('searchResultsModal') searchResultsModal!: TemplateRef<any>;

  constructor(
    private router: Router,
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private modalService: NgbModal
  ) {
    this.searchForm = this.formBuilder.group({
      firstname: [''],
      lastname: [''],
    });
    this.searchQuery = {
      firstname: '',
      lastname: '',
    };
  }

  ngOnInit() {
    this.authService.loggedIn.subscribe(
      (data: boolean) => (this.isLoggedIn = data)
    );
    this.authService.username.subscribe(
      (data: string) => (this.username = data)
    );
    this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUserName();
    this.id = this.authService.getUserId();
  }

  search(): void {
    // const searchQuery = this.searchForm.value.firstname;
    this.searchQuery.firstname = this.searchForm.value.firstname;
    this.searchQuery.lastname = this.searchForm.value.lastname;
    this.authService.searchUsers(this.searchQuery).subscribe((data) => {
      this.searchResults = data;
    });

    this.openSearchResultsModal();
  }

  openSearchResultsModal(): void {
    const modalRef = this.modalService.open(this.searchResultsModal);
    // Additional modal configurations if needed
  }

  goToUserProfile() {
    this.router.navigateByUrl('/user-profile/' + this.id);
  }

  changePassword() {
    this.router.navigateByUrl('/change-password');
  }

  adminPage() {
    this.router.navigateByUrl('/admin');
  }

  logout() {
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigateByUrl('');
  }

  openProfile(id: number) {
    this.router.navigateByUrl('/user-profile/' + id);
  }
}
