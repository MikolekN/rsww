import {Component, OnInit} from '@angular/core';
import {OfferChangeEvent, OfferChangeResponse} from "../types/top10";
import {NgForOf} from "@angular/common";
import {SinglePreferenceComponent} from "../preferences/single-preference/single-preference.component";
import {MatCard, MatCardContent} from "@angular/material/card";
import {SocketService} from "../../service/socket/socket.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-top10',
  standalone: true,
  imports: [
    NgForOf,
    SinglePreferenceComponent,
    MatCard,
    MatCardContent
  ],
  templateUrl: './top10.component.html',
  styleUrl: './top10.component.css'
})
export class Top10Component implements OnInit {
  offerChanges: OfferChangeEvent[] | null = null
  private subscription: Subscription = Subscription.EMPTY

  constructor(private socketService: SocketService) {}

  ngOnInit(): void {
    this.socketService.getLastChangesRequestREST().subscribe({
      next: (value: OfferChangeResponse) => {
        this.offerChanges = value.offer_change_events
      }
    })

    this.subscription = this.socketService.tenLastChanges$.subscribe(changes => {
      this.offerChanges = changes
      console.log('Updated changes:', this.offerChanges)
    })
  }


}
