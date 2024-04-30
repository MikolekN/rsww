import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {LoginFormComponent} from "./components/login-form/login-form.component";
import {NgModule} from "@angular/core";
import {OffersComponent} from "./components/Offer/offers/offers.component";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: LoginFormComponent},
  {path: 'offers', component: OffersComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
