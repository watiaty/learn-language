import {Component, OnInit} from '@angular/core';
import {StorageService} from "../_services/storage.service";
import {UserService} from "../_services/user.service";
import {User} from "../user";
import {Router} from "@angular/router";
import {environment} from "../../environments/environment";
import {FormControl} from "@angular/forms";
import {ThemePalette} from "@angular/material/core";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: any;
  user: User;
  colorControl = new FormControl('native' as ThemePalette);

  constructor(private storageService: StorageService, private userService: UserService, private router: Router) {
    this.user = new User();
  }

  onSubmit() {
    this.userService.save(this.user).subscribe(
      response => {
        console.log('Сохранено успешно', response);
        localStorage.setItem(`${environment.userString}`, JSON.stringify(response))
        this.gotoProfile();
      },
      error => {
        console.error('Ошибка при сохранении', error);
      }
    );
  }

  gotoProfile() {
    this.router.navigate(['/profile']);
  }

  ngOnInit(): void {
    this.currentUser = this.storageService.getUser();
    this.user.firstName = this.currentUser.firstName;
    this.user.lastName = this.currentUser.lastName;
    this.user.email = this.currentUser.email;
    this.user.nativeLang = this.currentUser.nativeLang;
    this.user.learningLangs = this.currentUser.learningLang;
  }
}
