import {Component, EventEmitter, Output} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {formatDate, NgIf} from "@angular/common";
import {OfferService} from "../../../service/offer.service";
import {OfferRequest} from "../../../DTO/request/offerRequest";
import {UserLoginResponse} from "../../../DTO/response/UserLoginResponse";

@Component({
  selector: 'app-offer-search',
  standalone: true,
  imports: [
    MatIcon,
    MatFormFieldModule,
    MatInput,
    FormsModule,
    NgIf,
    MatButton
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


  constructor(private offerService: OfferService) {}

  onSubmit() {
    console.log(this.formData);

    const offerRequest: OfferRequest = {
      country: this.formData.country,
      dateFrom: this.formatDate(this.formData.dateFrom),
      dateTo: this.formatDate(this.formData.dateTo),
      numberOfAdults: Number(this.formData.numberOfAdults),
      numberOfChildrenUnder10: Number(this.formData.numberofChildrenUnder10),
      numberOfChildrenUnder18: Number(this.formData.numberofChildrenUnder18)
    }

    console.log(offerRequest)

    this.offerService.getOffers(offerRequest).subscribe(
      {
        next: (value: UserLoginResponse) => {
          if (value.response) {
            console.log(value)
          }
        },
        error: () => {
          console.log("error")
        }
      })
  }

  private formatDate(dateString: string): string {
    const date = new Date(dateString);
    const day = date.getDate();
    const month = date.getMonth() + 1; // Dodajemy 1, ponieważ miesiące są indeksowane od zera
    const year = date.getFullYear();

    // Formatujemy datę jako "dd-mm-rrrr"
    return `${day < 10 ? '0' : ''}${day}-${month < 10 ? '0' : ''}${month}-${year}`;
  }

}
