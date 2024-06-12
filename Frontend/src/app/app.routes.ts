import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {NgModule} from "@angular/core";
import {OffersComponent} from "./components/Offer/offers/offers.component";
import {SelectedOfferComponent} from "./components/Offer/selected-offer/selected-offer.component";
import {PaymentComponent} from "./components/payment/payment/payment.component";
import {UserOrdersComponent} from "./components/order/user-orders/user-orders.component";
import {UserPreferencesComponent} from "./components/preferences/user-preferences/user-preferences.component";
import {Top10Component} from "./components/top10/top10.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'offers', component: OffersComponent},
  {path: 'offer', component: SelectedOfferComponent},
  {path: 'orders', component: UserOrdersComponent},
  {path: 'pay', component: PaymentComponent},
  {path: 'preferences', component: UserPreferencesComponent},
  {path: 'top10', component: Top10Component},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
