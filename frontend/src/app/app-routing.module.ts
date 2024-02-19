import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {WordListComponent} from './word-list/word-list.component';
import {WordFormComponent} from './word-form/word-form.component';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {ProfileComponent} from "./profile/profile.component";
import {WordComponent} from "./word/word.component";
import {TrainComponent} from "./train/train.component";

const routes: Routes = [
  {path: 'words', component: WordListComponent},
  {path: 'addword', component: WordFormComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'word', component: WordComponent},
  {path: 'train', component: TrainComponent},
  {path: '', redirectTo: 'home', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
