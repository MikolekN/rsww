export interface OfferChangeEvent {
  uuid: string
  time_stamp: number[]
  content: string
}

export interface OfferChangeResponse {
  uuid: string
  response: boolean
  offer_change_events: OfferChangeEvent[]
}
