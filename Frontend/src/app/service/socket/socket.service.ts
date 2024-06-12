import { Injectable } from '@angular/core'
import {Stomp} from "@stomp/stompjs"
import SockJS from 'sockjs-client'
import {MatSnackBar} from "@angular/material/snack-bar"
import {OfferReservedNotification} from "../../DTO/socket"
import {parseJson} from "@angular/cli/src/utilities/json-file"
import {environment} from "../../config/environment"
import {OfferService} from "../offer.service"
import {UserOrder, UserPreferencesResponse} from "../../components/types/order"
import {BehaviorSubject} from "rxjs"
import {HotelRemoved} from "../../DTO/events";

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  constructor(private snackbar: MatSnackBar,
              private offerService: OfferService) {
    this.initializeWebSocketConnection()
  }

  public stompClient: any
  public msg = []

  private userPreferencesSubject = new BehaviorSubject<UserOrder[] | null>(null)
  public userPreferences$ = this.userPreferencesSubject.asObservable()

  private hotelRemovedSubject = new BehaviorSubject<HotelRemoved | null>(null)
  public hotelRemoved$ = this.hotelRemovedSubject.asObservable()

  private flightRemovedSubject = new BehaviorSubject<any>(null)
  public flightRemoved$ = this.flightRemovedSubject.asObservable()

  private roomPriceChangedSubject = new BehaviorSubject<any>(null)
  public roomPriceChanged$ = this.roomPriceChangedSubject.asObservable()

  private flightPriceChangedSubject = new BehaviorSubject<any>(null)
  public flightPriceChanged$ = this.flightPriceChangedSubject.asObservable()

  private serverUrl = environment.socketUrl

  public initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl)
    this.stompClient = Stomp.over(ws)

    console.log("Initialize websocket")

    const that = this

    this.stompClient.connect({}, function(frame: any) {
      console.log('Connected: ' + frame)
      that.initOfferReservedNotification()
      that.initFlightRemovedEvent()
      that.initHotelRemovedEvent()
      that.initRoomPriceChangedEvent()
      that.initFlightPriceChangedEvent()
    }, function(error: any) {
      console.error('Error: ' + error)
    })
  }


  /*
    Reservation notification
   */

  private initOfferReservedNotification() {
    const that = this
    this.stompClient.subscribe('/topic/live-reservation', (message: any) => {
      if (message.body) {
        const data: OfferReservedNotification = parseJson(message.body)
        console.log(data)
        that.displayOfferReservedNotification(data)
      }
    })
  }

  private displayOfferReservedNotification(notification: OfferReservedNotification) {
    const offer = this.offerService.getOfferData()

    if (offer !== null && offer.start_date == notification.start_date && offer.end_date == offer.end_date &&
        offer.hotel_uuid == notification.hotel_uuid) {
      this.snackbar.open(notification.message, "Zamknij")
    }
  }

  /*
    User preferences
   */

  public initUserPreferencesEndpoint(username: string) {
    const that = this
    this.stompClient.subscribe('/topic/user-preferences/' + username, (message: any) => {
      if (message.body) {
        const data: UserPreferencesResponse = parseJson(message.body)
        console.log(data)

        that.userPreferencesSubject.next(data.preferences)
      }
    })
  }
  public sendUserPreferencesRequest(username: string) {
    this.stompClient.send('/app/topic/getUserPreferences' , {}, username)
  }

  /*
   Hotel removed
   */

  private initHotelRemovedEvent() {
    const that = this
    this.stompClient.subscribe('/topic/hotel-removed-event', (message: any) => {
      if (message.body) {
        const data: HotelRemoved = JSON.parse(message.body)
        console.log("Hotel removed")
        console.log(data)
        that.hotelRemovedSubject.next(data)
      }
    })
  }

  /*
   Flight removed
   */

  private initFlightRemovedEvent() {
    const that = this

    this.stompClient.subscribe('/topic/flight-removed-event', (message: any) => {
      if (message.body) {
        const data = JSON.parse(message.body)
        console.log("Flight removed")
        console.log(data)
        that.flightRemovedSubject.next(data)
      }
    })
  }

  /*
  Hotel price changed
   */

  private initRoomPriceChangedEvent() {
    const that = this

    this.stompClient.subscribe('/topic/room-price-changed-event', (message: any) => {
      if (message.body) {
        const data = JSON.parse(message.body)
        console.log("Room price changed")
        console.log(data)
        that.roomPriceChangedSubject.next(data)
      }
    })
  }

  /*
  Flight price changed
   */

  private initFlightPriceChangedEvent() {
    const that = this

    this.stompClient.subscribe('flight-price-changed-event', (message: any) => {
      if (message.body) {
        const data = JSON.parse(message.body)
        console.log("Room price changed")
        console.log(data)
        that.flightPriceChangedSubject.next(data)
      }
    })
  }
}
