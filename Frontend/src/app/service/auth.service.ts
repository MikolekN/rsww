import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../DTO/loginRequest";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";

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
    this.http.post("http://localhost:8080/api/login", requestBody).subscribe({
      next: value => {
        if (value === true) {
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
