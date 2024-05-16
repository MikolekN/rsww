import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../config/environment";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public payForOffer(requestBody: PaymentRequest) {
    return this.http.post<boolean>(environment.API_URL + "/api/offers", requestBody)
  }
}
