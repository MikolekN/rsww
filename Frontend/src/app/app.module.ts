import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import { MatFormFieldModule } from '@angular/material/form-field';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    NgbModule,
    MatFormFieldModule,
    HttpClientModule,
  ],
  providers: []
})
export class AppModule {

}
