import { Injectable } from '@angular/core';
import {environment} from "../config/environment";
import {HttpClient} from "@angular/common/http";
import {OfferRequest} from "../DTO/request/offerRequest";
import {OfferResponse, OfferResponseRaw} from "../DTO/response/offerResponse";
import {Offer} from "../components/types/Offer";
import {map, Observable} from "rxjs";
import {FullOfferRequest} from "../DTO/request/fullOfferRequest";
import {FullOfferResponse} from "../DTO/response/fullOfferResponse";
import {Order, UserOrderApiResponse} from "../components/types/order";
import {OrderResponse} from "../DTO/response/orderResponse";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http: HttpClient) { }

  public getOffers(requestBody: OfferRequest): Observable<OfferResponseRaw> {
    return this.http.post<OfferResponseRaw>(environment.API_URL + "/api/offers", requestBody);
  }

  public getSelectedOffer(requestBody: FullOfferRequest): Observable<FullOfferResponse> {
    return this.http.post<FullOfferResponse>(environment.API_URL + "/api/offer", requestBody);
  }

  public makeReservation(requestBody: Order): Observable<OrderResponse> {
    return this.http.post<OrderResponse>(environment.API_URL + "/api/order", requestBody);
  }

  public getCountries(): Observable<string[]> {
    return this.http.get<any>(environment.API_URL + "/api/countries").pipe(
      map(response => response.countries)
    )
  }

  public getOrderStatus(orderUUID: string): Observable<any> {
    return this.http.post<any>(`${environment.API_URL}/api/order/${orderUUID}`, {})
  }

  public getUserOrders(username: string): Observable<UserOrderApiResponse> {
    const body = { username: username };
    return this.http.post<UserOrderApiResponse>(environment.API_URL + "/api/orders", body);
  }

  public saveOrderResponse(reservation: OrderResponse) {
    sessionStorage.setItem('orderResponse', JSON.stringify(reservation))
  }

  public getOrderResponse(): OrderResponse {
    const offerData = sessionStorage.getItem('orderResponse');
    return offerData ? JSON.parse(offerData) : null;
  }

  public clearOrderResponse() {
    sessionStorage.removeItem('orderResponse');
  }

  public clearOrder() {
    sessionStorage.removeItem('order');
  }

  public saveOrder(order: Order) {
    sessionStorage.setItem('order', JSON.stringify(order))
  }

  public getOrder(): OrderResponse {
    const offerData = sessionStorage.getItem('order');
    return offerData ? JSON.parse(offerData) : null;
  }

  public saveOfferData(offer: Offer) {
    sessionStorage.setItem('offerData', JSON.stringify(offer));
  }

  public getOfferData(): Offer | null {
    const offerData = sessionStorage.getItem('offerData');
    return offerData ? JSON.parse(offerData) : null;
  }

  public clearOfferData() {
    sessionStorage.removeItem('offerData');
  }

  public convertArrayToOfferArray(offerResponses: OfferResponse[]): Offer[] {
    return offerResponses.map(offerResponse => this.convertToOffer(offerResponse));
  }

  public convertToOffer(offerResponse: OfferResponse): Offer {
    return {
      country: offerResponse.country,
      start_date: this.parseDF(offerResponse.start_date[0]) + "-" + this.parseDF(offerResponse.start_date[1]) + "-" + this.parseDF(offerResponse.start_date[2]),
      end_date: this.parseDF(offerResponse.end_date[0]) + "-" + this.parseDF(offerResponse.end_date[1]) + "-" + this.parseDF(offerResponse.end_date[2]),
      number_of_adults: offerResponse.number_of_adults,
      number_of_children_under_10: offerResponse.number_of_children_under_10,
      number_of_children_under_18: offerResponse.number_of_children_under_18,
      hotel_name: offerResponse.hotel_name,
      hotel_uuid: offerResponse.hotel_uuid
    };
  }

  private parseDF(data: string): string {
    if (data.toString().length == 1) {
      return "0" + data;
    }
    return data;
  }

}
