import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../config/environment";
import {PaymentResponse} from "../DTO/response/PaymentResponse";
import {PaymentDataRequest} from "../DTO/request/PaymentDataRequest";

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private http: HttpClient) { }

  public payForOffer(requestBody: PaymentDataRequest) {
    return this.http.post<PaymentResponse>(environment.API_URL + "/api/payment", requestBody)
  }
}
