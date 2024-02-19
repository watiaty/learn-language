import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";
import {StorageService} from "../_services/storage.service";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  currentUser: any;

  constructor(private authService: AuthService, private storageService: StorageService) { }

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.currentUser = this.storageService.getUser();
    }
  }

  onSubmit(): void {
    const { username, password } = this.form;

    this.authService.login(username, password).subscribe({
      next: response => {
        if (response) {
          localStorage.setItem(`${environment.accessTokenString}`, response.access_token);
          localStorage.setItem(`${environment.refreshTokenString}`, response.refresh_token);
          localStorage.setItem(`${environment.userString}`, JSON.stringify(response.user))

          this.isLoginFailed = false;
          this.isLoggedIn = true;
        }
      },
      error: err => {
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });
  }
}
