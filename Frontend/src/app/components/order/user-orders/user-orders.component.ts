import { Component } from '@angular/core';
import {OfferService} from "../../../service/offer.service";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'app-user-orders',
  standalone: true,
  imports: [],
  templateUrl: './user-orders.component.html',
  styleUrl: './user-orders.component.css'
})
export class UserOrdersComponent {
  orders: any[] = []
  errors: string = ""

  constructor(private offerService: OfferService, private authService: AuthService) {}

  ngOnInit(): void {
    this.fetchOrders()
  }

  public fetchOrders(): void {
    const username: string | null = this.authService.getUsername()

    if (username !== null) {
      this.offerService.getUserOrders('user1').subscribe(response => {
        this.orders = response.orders;
      })
    }
    else {
      this.errors = "Zaloguj się, aby wyświetlić zamówienia."
    }
  }

  public get username() {
    return this.authService.getUsername()
  }
}
