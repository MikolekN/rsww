import {Component, OnInit} from '@angular/core';
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
import {MatOption, MatSelect} from "@angular/material/select";

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
    NgForOf,
    MatSelect,
    MatOption
  ],
  templateUrl: './offer-search.component.html',
  styleUrl: './offer-search.component.css'
})
export class OfferSearchComponent implements OnInit {
  formData = {
    country: '',
    dateFrom: '',
    dateTo: '',
    numberOfAdults: '1',
    numberofChildrenUnder10: '0',
    numberofChildrenUnder18: '0'
  };

  public offers: Offer[] = []
  public loadingText: string = ""
  public countries: string[] = [];

  constructor(private offerService: OfferService) {}

  ngOnInit() {
    this.offerService.getCountries().subscribe(
      (data: string[]) => {
        this.countries = data;
      },
      (error) => {
        console.error('Error fetching countries', error);
      }
    );
  }

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

    this.loadingText = "Wczytywanie ofert..."

    this.offerService.getOffers(offerRequest).subscribe(
      {
        next: (value: OfferResponseRaw) => {
          if (value.response) {
            this.offers = this.offerService.convertArrayToOfferArray(value.offers)
            this.loadingText = "Wczytano oferty:"
          }
        },
        error: () => {
          this.loadingText = "Wystąpił błąd przy wczytywaniu ofert."
        }
      })
  }
}
