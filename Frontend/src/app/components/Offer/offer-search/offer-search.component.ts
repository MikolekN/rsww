import {Component} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {OfferService} from "../../../service/offer.service";
import {OfferRequest} from "../../../DTO/request/offerRequest";
import {OfferResponseRaw} from "../../../DTO/response/offerResponse";
import {Offer} from "../../types/Offer";
import {SingleOfferComponent} from "../single-offer/single-offer.component";

@Component({
  selector: 'app-offer-search',
  standalone: true,
  imports: [
    MatIcon,
    MatFormFieldModule,
    MatInput,
    FormsModule,
    NgIf,
    MatButton,
    SingleOfferComponent,
    NgForOf
  ],
  templateUrl: './offer-search.component.html',
  styleUrl: './offer-search.component.css'
})
export class OfferSearchComponent {
  formData = {
    country: '',
    dateFrom: '',
    dateTo: '',
    numberOfAdults: '',
    numberofChildrenUnder10: '',
    numberofChildrenUnder18: ''
  };

  public offers: Offer[] = []

  constructor(private offerService: OfferService) {}

  onSubmit() {
    console.log(this.formData);

    const offerRequest: OfferRequest = {
      country: this.formData.country,
      dateFrom: this.formData.dateFrom,
      dateTo: this.formData.dateTo,
      numberOfAdults: Number(this.formData.numberOfAdults),
      numberOfChildrenUnder10: Number(this.formData.numberofChildrenUnder10),
      numberOfChildrenUnder18: Number(this.formData.numberofChildrenUnder18)
    }

    console.log(offerRequest)

    this.offerService.getOffers(offerRequest).subscribe(
      {
        next: (value: OfferResponseRaw) => {
          if (value.response) {
            console.log(value)
            this.offers = this.offerService.convertArrayToOfferArray(value.offers)
          }
        },
        error: () => {
          console.log("error")
        }
      })
  }
}
