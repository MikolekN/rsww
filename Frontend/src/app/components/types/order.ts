export interface Order {
  username: string
  flightToUuid: string
  flightFromUuid: string
  hotelUuid: string
  roomType: string
  dateFrom: string
  dateTo: string
  numberOfAdults: string
  numberOfChildrenUnder10: string
  numberOfChildrenUnder18 : string
}

export interface UserOrder {
  reservationId: string;
  user: string;
  payed: boolean;
  reservationTime: number[];
  startFlightReservation: any;
  endFlightReservation: any;
  startFlightId: string;
  endFlightId: string;
  hotelReservation: any;
  tripId: any;
  hotelId: string;
  price: number;
}

export interface UserPreferencesResponse {
  uuid: string
  response: boolean
  preferences: UserOrder[]
}

export interface UserOrderApiResponse {
  uuid: any;
  response: boolean;
  orders: Order[];
}
