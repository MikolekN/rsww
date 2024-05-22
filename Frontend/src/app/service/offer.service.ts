import { Injectable } from '@angular/core';
import {environment} from "../config/environment";
import {HttpClient} from "@angular/common/http";
import {OfferRequest} from "../DTO/request/offerRequest";
import {OfferResponse, OfferResponseRaw} from "../DTO/response/offerResponse";
import {Offer} from "../components/types/Offer";
import {map, Observable} from "rxjs";
import {FullOfferRequest} from "../DTO/request/fullOfferRequest";
import {FullOfferResponse} from "../DTO/response/fullOfferResponse";
import {Order} from "../components/types/order";
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
      map(response => response.body.countries)
    );
  }

  public saveReservationId(reservationId: string) {
    sessionStorage.setItem('reservationId', reservationId)
  }

  public getReservationId() {
    return sessionStorage.getItem('reservationId')
  }

  public clearReservationId() {
    sessionStorage.removeItem('reservationId');
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
