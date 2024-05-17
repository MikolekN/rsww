import {Component, OnInit} from '@angular/core';
import {OfferService} from "../../../service/offer.service";
import {Offer} from "../../types/Offer";
import {MatCard, MatCardContent, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {FullOffer} from "../../types/fullOffer";
import {FullOfferRequest} from "../../../DTO/request/fullOfferRequest";
import {FullOfferResponse} from "../../../DTO/response/fullOfferResponse";
import {NgForOf} from "@angular/common";
import {SingleOfferComponent} from "../single-offer/single-offer.component";
import {FormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {OffersComponent} from "../offers/offers.component";
import {Order} from "../../types/order";

class FlightSelectionForm {
  departureFlightId: string | null = null
  returnFlightId: string | null = null
  roomType: string | null = null
}

@Component({
  selector: 'app-selected-offer',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardTitle,
    MatCardSubtitle,
    NgForOf,
    SingleOfferComponent,
    FormsModule
  ],
  templateUrl: './selected-offer.component.html',
  styleUrl: './selected-offer.component.css'
})
export class SelectedOfferComponent implements OnInit {
  public offer: FullOffer | null = null
  public formData: FlightSelectionForm = new FlightSelectionForm()

  private selectedOffer: Offer | null = null

  constructor(private offerService: OfferService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.selectedOffer = this.offerService.getOfferData()

    if (this.selectedOffer !== null) {
      const request: FullOfferRequest = {
        hotelUuid: this.selectedOffer.hotel_uuid,
        dateFrom: this.selectedOffer.start_date,
        dateTo: this.selectedOffer.end_date,
        numberOfAdults: this.selectedOffer.number_of_adults,
        numberOfChildrenUnder10: this.selectedOffer.number_of_children_under_10,
        numberOfChildrenUnder18: this.selectedOffer.number_of_children_under_18
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

  public goToPayment() {
    if (this.offer !== null && this.selectedOffer !== null && this.formData.departureFlightId !== null
    && this.formData.returnFlightId !== null && this.formData.roomType !== null) {
      const orderData: Order = {
          flightToUuid: this.formData.departureFlightId,
          flightFromUuid: this.formData.returnFlightId,
          hotelUuid: this.selectedOffer.hotel_uuid,
          roomType: this.formData.roomType,
          dateFrom: this.selectedOffer.start_date,
          dateTo: this.selectedOffer.end_date,
          numberOfAdults: this.selectedOffer.number_of_adults,
          numberOfChildrenUnder10: this.selectedOffer.number_of_children_under_10,
          numberOfChildrenUnder18: this.selectedOffer.number_of_children_under_18
      }

      this.offerService.makeReservation(orderData).subscribe({
              next: (value) => {
                  console.log("otrzymano dane")
                  console.log(value)
              },
              error: (err) => {
                console.log(err)
              }
      })

      this.router.navigate(['pay']);
    }
    else {
      console.log("Offer jest null")
        console.log(this.formData)
    }
  }

}
