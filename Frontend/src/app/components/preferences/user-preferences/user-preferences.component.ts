import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {SingleOfferComponent} from "../../Offer/single-offer/single-offer.component";
import {AuthService} from "../../../service/auth.service";
import {Offer} from "../../types/Offer";
import {SocketService} from "../../../service/socket/socket.service";
import {Observable, Subscription} from "rxjs";
import {UserOrder} from "../../types/order";
import {SinglePreferenceComponent} from "../single-preference/single-preference.component";

@Component({
  selector: 'app-user-preferences',
  standalone: true,
  imports: [
    NgForOf,
    SingleOfferComponent,
    SinglePreferenceComponent
  ],
  templateUrl: './user-preferences.component.html',
  styleUrl: './user-preferences.component.css'
})
export class UserPreferencesComponent implements OnInit, OnDestroy {
  userPreferences: UserOrder[] | null = null
  private subscription: Subscription | null = null

  constructor(private authService: AuthService,
              private socketService: SocketService) {
  }

  ngOnInit(): void {
    this.subscription = this.socketService.userPreferences$.subscribe(preferences => {
      this.userPreferences = preferences;
      console.log('Updated user preferences:', this.userPreferences);
    })


    const username = this.authService.getUsername()

    if (username !== null) {
      this.socketService.initUserPreferencesEndpoint(username)

      setTimeout(() =>
        {
          this.socketService.sendUserPreferencesRequest(username)
        },
        1000);
    }

  }

  public get username(): string | null {
    return this.authService.getUsername()
  }

  ngOnDestroy(): void {
    if (this.subscription !== null) {
      this.subscription.unsubscribe()
    }
  }

}
