import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../service/offer.service";
import {Offer} from "../../types/Offer";
import {MatCard, MatCardContent, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {FullOffer} from "../../types/fullOffer";
import {FullOfferRequest} from "../../../DTO/request/fullOfferRequest";
import {FullOfferResponse} from "../../../DTO/response/fullOfferResponse";
import {NgForOf} from "@angular/common";
import {SingleOfferComponent} from "../single-offer/single-offer.component";

@Component({
  selector: 'app-selected-offer',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardTitle,
    MatCardSubtitle,
    NgForOf,
    SingleOfferComponent
  ],
  templateUrl: './selected-offer.component.html',
  styleUrl: './selected-offer.component.css'
})
export class SelectedOfferComponent implements OnInit {
  public offer: FullOffer | null = null
  constructor(private offerService: OfferService) {
  }

  ngOnInit(): void {
    const selectedOffer: Offer | null = this.offerService.getOfferData()

    if (selectedOffer !== null) {
      const request: FullOfferRequest = {
        hotelUuid: selectedOffer.hotel_uuid,
        dateFrom: selectedOffer.start_date,
        dateTo: selectedOffer.end_date,
        numberOfAdults: selectedOffer.number_of_adults,
        numberOfChildrenUnder10: selectedOffer.number_of_children_under_10,
        numberOfChildrenUnder18: selectedOffer.number_of_children_under_18
      }

      this.offerService.getSelectedOffer(request).subscribe({
        next: (value: FullOfferResponse) => {
          if (value.response) {
            console.log(value)
            this.offer = value.offer
          }
        },
        error: () => {
        }
      })
    }
  }

}
