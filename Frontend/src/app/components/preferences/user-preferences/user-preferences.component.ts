import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";
import {SingleOfferComponent} from "../../Offer/single-offer/single-offer.component";
import {AuthService} from "../../../service/auth.service";
import {Offer} from "../../types/Offer";

@Component({
  selector: 'app-user-preferences',
  standalone: true,
    imports: [
        NgForOf,
        SingleOfferComponent
    ],
  templateUrl: './user-preferences.component.html',
  styleUrl: './user-preferences.component.css'
})
export class UserPreferencesComponent {
    public offers: Offer[] | null = null
    constructor(private authService: AuthService) {
    }

    public get username(): string | null {
        return this.authService.getUsername()
    }

}
