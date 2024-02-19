import {NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {WordListComponent} from "./word-list/word-list.component";
import {WordFormComponent} from "./word-form/word-form.component";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ProfileComponent} from './profile/profile.component';
import {HttpRequestInterceptor} from "./_helpers/http.interceptor";
import {WordComponent} from './word/word.component';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {
  MatAutocomplete,
  MatAutocompleteModule,
  MatAutocompleteTrigger,
  MatOption
} from "@angular/material/autocomplete";
import {MatFormField, MatInput, MatInputModule} from "@angular/material/input";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";
import {MatAnchor, MatButton, MatFabAnchor, MatFabButton, MatIconButton} from "@angular/material/button";
import {MatSelect} from "@angular/material/select";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef,
  MatHeaderRow,
  MatHeaderRowDef,
  MatRow,
  MatRowDef,
  MatTable
} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {TrainComponent} from "./train/train.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIcon} from "@angular/material/icon";
import {MatSlider, MatSliderThumb} from "@angular/material/slider";
import {MatCard, MatCardContent} from "@angular/material/card";

@NgModule({
  declarations: [
    AppComponent,
    WordListComponent,
    WordFormComponent,
    LoginComponent,
    RegisterComponent,
    ProfileComponent,
    WordComponent,
    TrainComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    MatAutocomplete,
    MatOption,
    MatPaginatorModule,
    MatAutocompleteTrigger,
    MatAutocompleteModule,
    MatInput,
    ReactiveFormsModule,
    MatFormField,
    MatInputModule,
    MatFormFieldModule,
    MatSlideToggleModule,
    MatButtonToggleGroup,
    MatButtonToggle,
    MatAnchor,
    MatSelect,
    BrowserAnimationsModule,
    MatTable,
    MatHeaderRow,
    MatRow,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatRowDef,
    MatHeaderRowDef,
    MatCellDef,
    MatHeaderCellDef,
    MatPaginator,
    MatIcon,
    MatIconButton,
    MatFabButton,
    MatButton,
    MatSlider,
    MatSliderThumb,
    MatCardContent,
    MatCard,
    MatFabAnchor
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpRequestInterceptor,
      multi: true
    },
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
