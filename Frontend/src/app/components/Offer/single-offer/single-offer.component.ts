import {Component, Input} from '@angular/core';
import {Offer} from "../../types/Offer";
import {MatCard, MatCardContent} from "@angular/material/card";

@Component({
  selector: 'app-single-offer',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent
  ],
  templateUrl: './single-offer.component.html',
  styleUrl: './single-offer.component.css'
})
export class SingleOfferComponent {
  @Input() offer: Offer | null = null
}
