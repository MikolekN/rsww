import {Component, Input} from '@angular/core';
import {Offer} from "../../types/Offer";
import {MatCard, MatCardContent} from "@angular/material/card";
import {OfferService} from "../../../service/offer.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-single-offer',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent
  ],
  templateUrl: './single-offer.component.html',
  styleUrl: './single-offer.component.css'
})
export class SingleOfferComponent {
  @Input() offer: Offer | null = null

  constructor(private offerService: OfferService,
              private router: Router) {
  }

  public selectOffer() {
    if (this.offer !== null) {
      this.offerService.saveOfferData(this.offer)
      this.router.navigate(['offer']);
    }
    else {
      console.log("Nie ma takiej oferty")
    }
  }
}
