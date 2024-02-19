import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() {}

  clean(): void {
    localStorage.clear();
  }

  public getUser(): any {
    const user = localStorage.getItem(`${environment.userString}`);
    if (user) {
      return JSON.parse(user);
    }
    return {};
  }

  public isLoggedIn(): boolean {
    const user = localStorage.getItem(`${environment.userString}`);
    return !!user;

  }
}
