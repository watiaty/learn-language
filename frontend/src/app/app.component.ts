import { Component } from '@angular/core';
import {StorageService} from "./_services/storage.service";
import {AuthService} from "./_services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title: string;
  role: string = '';
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  constructor(private storageService: StorageService, private authService: AuthService) {
    this.title = 'Spring Boot - Angular Application';
  }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();

    if (this.isLoggedIn) {

      const user = this.storageService.getUser();
      this.username = user.username;
      this.role = user.role;

      this.showAdminBoard = this.role.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.role.includes('ROLE_MODERATOR');
    }
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: res => {
        console.log(res);
        this.storageService.clean();

        window.location.reload();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
