import {Component, EventEmitter, Output} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {FormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-offer-search',
  standalone: true,
  imports: [
    MatIcon,
    MatFormFieldModule,
    MatInput,
    FormsModule,
    NgIf,
    MatButton
  ],
  templateUrl: './offer-search.component.html',
  styleUrl: './offer-search.component.css'
})
export class OfferSearchComponent {
  formData = {
    country: '',
    dateFrom: '',
    dateTo: '',
    numberOfAdults: '',
    numberofChildrenUnder10: '',
    numberofChildrenUnder18: ''
  };

  onSubmit() {
    // Do something with the form data, like submitting it to a server
    console.log(this.formData);
  }

}
