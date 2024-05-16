import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {NgModule} from "@angular/core";
import {OffersComponent} from "./components/Offer/offers/offers.component";
import {SelectedOfferComponent} from "./components/Offer/selected-offer/selected-offer.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'offers', component: OffersComponent},
  {path: 'offer', component: SelectedOfferComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
