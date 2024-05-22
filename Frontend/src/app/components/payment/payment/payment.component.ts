import { Component } from '@angular/core';
import {PaymentService} from "../../../service/payment.service";
import {OfferService} from "../../../service/offer.service";
import {PaymentDataRequest} from "../../../DTO/request/PaymentDataRequest";
import {FullOfferResponse} from "../../../DTO/response/fullOfferResponse";
import {PaymentResponse} from "../../../DTO/response/PaymentResponse";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent {

  public paymentStatus: string = ""

  constructor(private payService: PaymentService,
              private offerService: OfferService,
              private router: Router,
              private snackbar: MatSnackBar) {
  }

  public pay() {
    this.paymentStatus = "Płatność w toku..."

    const offerData = this.offerService.getOfferData()
    const reservationId = this.offerService.getReservationId()

    if (offerData !== null && reservationId !== null) {
      const request: PaymentDataRequest = {
        reservationId: reservationId
      }

      this.payService.payForOffer(request).subscribe({
        next: (value: PaymentResponse) => {
          if (value.response) {
            this.paymentStatus = "Płatność udana! Id płatności: " + value.uuid
          }
          else {
            this.paymentStatus = "Płatność się nie powiodła. Id płatności: " + value.uuid
          }
        },
        error: () => {
          this.paymentStatus = "Płatność się nie powiodła."
        }
      })
    }
    else {
      this.paymentStatus = "Rezerwacja nie udała się."
    }
  }

  public cancel() {
    this.snackbar.open('Rezerwacja anulowana', 'Close');
    this.router.navigate(['offers']);
  }
}
