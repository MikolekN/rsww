import { Injectable } from '@angular/core';
import {CompatClient, Stomp} from "@stomp/stompjs";
import SockJS from 'sockjs-client';
import {MatSnackBar} from "@angular/material/snack-bar";
import {OfferReservedNotification} from "../../DTO/socket";
import {parseJson} from "@angular/cli/src/utilities/json-file";
import {environment} from "../../config/environment";
import {OfferService} from "../offer.service";

@Injectable({
  providedIn: 'root'
})
export class OfferNotificationService {
  constructor(private snackbar: MatSnackBar,
              private offerService: OfferService) {
    this.initializeWebSocketConnection();
  }

  public stompClient: any
  public msg = []

  private wsObj: CompatClient | null = null
  private serverUrl = environment.socketUrl;

  public initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);

    console.log("Initialize websocket")

    this.initOfferReservedNotification()

  }

  private initOfferReservedNotification() {
    const that = this

    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe('/topic/live-reservation', (message: any) => {
        if (message.body) {
          const data: OfferReservedNotification = parseJson(message.body)
          console.log(data)
          that.displayOfferReservedNotification(data)
        }
      });
    });
  }

  private displayOfferReservedNotification(notification: OfferReservedNotification) {
    const offer = this.offerService.getOfferData()

    if (offer !== null && offer.start_date == notification.start_date && offer.end_date == offer.end_date &&
        offer.hotel_uuid == notification.hotel_uuid) {
      this.snackbar.open(notification.message, "Zamknij")
    }
  }

  sendMessage(message: any) {
    this.stompClient.send('/topic/live-reservation' , {}, message);
  }
}
