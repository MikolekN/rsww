import { Component } from '@angular/core';
import {OfferSearchComponent} from "../offer-search/offer-search.component";
import {SingleOfferComponent} from "../single-offer/single-offer.component";

@Component({
  selector: 'app-offers',
  standalone: true,
  imports: [
    OfferSearchComponent,
    SingleOfferComponent
  ],
  templateUrl: './offers.component.html',
  styleUrl: './offers.component.css'
})
export class OffersComponent {

}
