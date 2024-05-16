import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../DTO/request/loginRequest";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {UserLoginResponse} from "../DTO/response/UserLoginResponse";
import {environment} from "../config/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient,
              private snackbar: MatSnackBar,
              private router: Router) { }

  public isLoggedIn(): boolean {
    return this.getUsername() != null;
  }

  public logIn(requestBody : LoginRequest) {
    this.http.post<UserLoginResponse>(environment.API_URL + "/api/login", requestBody).subscribe({
      next: (value: UserLoginResponse) => {
        if (value.response) {
          this.snackbar.open(requestBody.username + ' logged in successfully!', 'Close');
          this.saveUserInSession(requestBody.username)
          this.router.navigate(['']);
        }
      },
      error: () => {
       this.snackbar.open('Wrong username or password', 'Close');
      }
    })
  }

  public logOut() {
    sessionStorage.removeItem('username');
    this.snackbar.open('Logged out', 'Close');
  }

  private saveUserInSession(username: string) {
      sessionStorage.setItem('username', username);
  }

  private getUsername(){
    return sessionStorage.getItem('username');
  }

}
