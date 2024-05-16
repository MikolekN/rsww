import {Flight} from "./flight";

export interface FullOffer {
  hotel_name: string
  country: string
  stars: number
  rooms: Room[]
  available_flights_to: Flight[]
  available_flights_from: Flight[]
}

export interface Room {
  type: string
  price: number
}
