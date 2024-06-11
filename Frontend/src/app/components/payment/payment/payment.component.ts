import {Component, OnDestroy, OnInit} from '@angular/core';
import {PaymentService} from "../../../service/payment.service";
import {OfferService} from "../../../service/offer.service";
import {PaymentDataRequest} from "../../../DTO/request/PaymentDataRequest";
import {PaymentResponse} from "../../../DTO/response/PaymentResponse";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {interval, startWith, Subject, switchMap, takeUntil} from "rxjs";

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [],
  templateUrl: './payment.component.html',
  styleUrl: './payment.component.css'
})
export class PaymentComponent implements OnInit, OnDestroy {

  public reservationStatus = "Ładowanie..."
  public paymentStatus: string = ""
  private destroy$ = new Subject<void>()

  public paymentButtonDisabled: boolean = false

  private stopPolling$ = new Subject<void>()

  constructor(private payService: PaymentService,
              private offerService: OfferService,
              private router: Router,
              private snackbar: MatSnackBar) {
  }

  ngOnInit(): void {
    const orderResponseId = this.offerService.getOrderResponse().reservationId
    this.startPolling(orderResponseId)
  }

  ngOnDestroy() {
    this.destroy$.next()
    this.destroy$.complete()
  }

  private startPolling(orderResponseId: string) {
    interval(2000)
      .pipe(
        startWith(0),
        takeUntil(this.stopPolling$),
        switchMap(() => this.offerService.getOrderStatus(orderResponseId))
      )
      .subscribe({
        next: (value) => {
          if (value.order.tripId !== null) {
            this.reservationStatus = "Czas na rezerwację minął (1 min).";
            this.paymentButtonDisabled = true;
            this.stopPolling();
          } else if (value.order.payed) {
            this.reservationStatus = "Rezerwacja pomyślnie opłacona.";
            this.paymentButtonDisabled = true;
            this.stopPolling();
          } else {
            this.reservationStatus = "Rezerwacja w toku.";
          }
        },
        error: (err) => {
          console.log(err);
        }
      })
  }

  private stopPolling() {
    this.stopPolling$.next();
    this.stopPolling$.complete();
  }

  public pay() {
    this.paymentStatus = "Płatność w toku..."

    const reservationId = this.offerService.getOrderResponse().reservationId

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

  public cancel() {
    this.snackbar.open('Rezerwacja anulowana', 'Close');
    this.router.navigate(['offers']);
  }
}
