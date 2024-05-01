import { Component } from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private authService: AuthService) {
  }


  public isUserLoggedIn(): boolean {
    return this.authService.isLoggedIn()
  }

  public logOutUser(): void {
    if (this.isUserLoggedIn()) {
      console.log("Logout")
      this.authService.logOut()
    }
  }
}
