export interface OfferResponse {
  country: string
  start_date: string[]
  end_date: string[]
  number_of_adults: string
  number_of_children_under_10: string
  number_of_children_under_18: string
  hotel_name: string
  hotel_uuid: string
}

export interface OfferResponseRaw {
  uuid: string
  response: boolean
  offers: OfferResponse[]
}
