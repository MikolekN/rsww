import {Component, OnInit} from '@angular/core';
import {AuthService} from "./service/auth.service";
import {HeaderComponent} from "./components/header/header.component";
import {FooterComponent} from "./components/footer/footer.component";
import {RouterOutlet} from "@angular/router";
import {SocketService} from "./service/socket/socket.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  constructor(private authService: AuthService,
              private offerNotifyService: SocketService) {}

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }

  ngOnInit(): void {
    this.offerNotifyService.initializeWebSocketConnection()
  }
}
