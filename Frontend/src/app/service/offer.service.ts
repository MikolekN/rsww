import { Injectable } from '@angular/core';
import {UserLoginResponse} from "../DTO/response/UserLoginResponse";
import {environment} from "../config/environment";
import {HttpClient, HttpParams} from "@angular/common/http";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Router} from "@angular/router";
import {OfferRequest} from "../DTO/request/offerRequest";
import {OfferResponse, OfferResponseRaw} from "../DTO/response/offerResponse";
import {Offer} from "../components/types/Offer";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http: HttpClient,
              private snackbar: MatSnackBar,
              private router: Router) { }

  public getOffers(requestBody: OfferRequest): Observable<OfferResponseRaw> {
    // Wykonaj zapytanie HTTP GET z przekazanymi parametrami
    return this.http.get<OfferResponseRaw>(environment.API_URL + "/api/offers");
  }

  public convertOfferResponseToOffer(offerResponse: OfferResponse): Offer {
    const offer: Offer = {
      country: offerResponse.country,
      start_date: offerResponse.start_date[0] + "-" + offerResponse.start_date[1] + "-" + offerResponse.start_date[2],
      end_date: offerResponse.end_date[0] + "-" + offerResponse.end_date[1] + "-" + offerResponse.end_date[2],
      number_of_adults: offerResponse.number_of_adults,
      number_of_children_under_10: offerResponse.number_of_children_under_10,
      number_of_children_under_18: offerResponse.number_of_children_under_18,
      hotel_name: offerResponse.hotel_name,
      hotel_uuid: offerResponse.hotel_uuid
    }

    return offer
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

}
