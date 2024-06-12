import {Component, OnDestroy, OnInit} from '@angular/core';
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
import {AuthService} from "../../../service/auth.service";
import {OrderResponse} from "../../../DTO/response/orderResponse";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Subscription} from "rxjs";
import {SocketService} from "../../../service/socket/socket.service";

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
export class SelectedOfferComponent implements OnInit, OnDestroy {
  public offer: FullOffer | null = null
  public formData: FlightSelectionForm = new FlightSelectionForm()

  private selectedOffer: Offer | null = null

  public offerInfo = ""

  private hotelRemovedSubscription: Subscription = Subscription.EMPTY
  private flightRemovedSubscription: Subscription  = Subscription.EMPTY
  private roomPriceChanged: Subscription  = Subscription.EMPTY
  private flightPriceChanged: Subscription  = Subscription.EMPTY

  constructor(private offerService: OfferService,
              private router: Router,
              private authService: AuthService,
              private snackbar: MatSnackBar,
              private socketService: SocketService) {
  }

  ngOnInit(): void {
    this.fetchOffer()

    const that = this

    this.hotelRemovedSubscription = this.socketService.hotelRemoved$.subscribe(data => {
      that.fetchOffer()
    })

    this.flightRemovedSubscription = this.socketService.flightRemoved$.subscribe(data => {
      that.fetchOffer()
    })

    this.roomPriceChanged = this.socketService.roomPriceChanged$.subscribe(data => {
      that.fetchOffer()
    })

    this.flightPriceChanged = this.socketService.flightPriceChanged$.subscribe(data => {
      that.fetchOffer()
    })
  }

  ngOnDestroy(): void {
    this.hotelRemovedSubscription.unsubscribe()
    this.flightRemovedSubscription.unsubscribe()
    this.roomPriceChanged.unsubscribe()
    this.flightPriceChanged.unsubscribe()
  }

  public goToPayment() {
    const username = this.authService.getUsername()

    if (this.offer !== null && this.selectedOffer !== null && this.formData.departureFlightId !== null
    && this.formData.returnFlightId !== null && this.formData.roomType !== null && username !== null) {
      const orderData: Order = {
          username: username,
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

      this.offerService.clearOfferData()

      this.offerInfo = "Przetwarzanie..."

      this.offerService.makeReservation(orderData).subscribe({
              next: (value: OrderResponse) => {
                console.log(value)

                if (value.response) {
                  this.offerService.saveOrderResponse(value)
                  this.router.navigate(['pay'])
                }
                else {
                  this.offerInfo = "Przepraszamy, oferta już nie jest dostępna."
                }
              },
              error: (err) => {
                this.offerInfo = "Wystąpił błąd."
                console.log(err)
              }
      })
    }
    else {
      console.log("Offer jest null")
      console.log(this.formData)
      console.log(this.offer)
      console.log(this.selectedOffer)
      console.log(username)
    }
  }

  private fetchOffer() {
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
          this.snackbar.open("Oferta została usunięta lub wykupiona", "Zamknij")
          this.router.navigate(['offers'])
        }
      })
    }
    else {
      this.router.navigate(['offers'])
    }
  }

}
