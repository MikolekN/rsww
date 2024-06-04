import { Injectable } from '@angular/core';
import {CompatClient, Stomp} from "@stomp/stompjs";
import SockJS from 'sockjs-client';
import {MatSnackBar} from "@angular/material/snack-bar";
import {OfferReservedNotification} from "../../DTO/socket";
import {parseJson} from "@angular/cli/src/utilities/json-file";

@Injectable({
  providedIn: 'root'
})
export class OfferNotificationService {
  constructor(private snackbar: MatSnackBar) {
    this.initializeWebSocketConnection();
  }

  public stompClient: any
  public msg = []
  private wsObj: CompatClient | null = null

  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/socket';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    const that = this;

    console.log("Initialize websocket")


    this.stompClient.connect({}, function(frame: any) {
      that.stompClient.subscribe('/topic/live-reservation', (message: any) => {
        if (message.body) {
          const data: OfferReservedNotification = parseJson(message.body)
          that.snackbar.open(data.message, "Zamknij")
        }
      });
    });
  }

  sendMessage(message: any) {
    this.stompClient.send('/topic/live-reservation' , {}, message);
  }
}
