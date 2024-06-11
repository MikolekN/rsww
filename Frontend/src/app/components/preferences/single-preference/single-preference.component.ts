import {Component, Input} from '@angular/core';
import {UserOrder} from "../../types/order";
import {OfferService} from "../../../service/offer.service";
import {Router} from "@angular/router";
import {MatCard, MatCardContent} from "@angular/material/card";

@Component({
  selector: 'app-single-preference',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent
  ],
  templateUrl: './single-preference.component.html',
  styleUrl: './single-preference.component.css'
})
export class SinglePreferenceComponent {
  @Input() userOrder: UserOrder | null = null

  constructor(private offerService: OfferService,
              private router: Router) {
  }
}
