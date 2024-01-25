export interface Flight {
    id: number;
    comapany:string;
    departure: string;
    arrival: string;
    date: string;
    departure_time:string;
    arrival_time:string;
    travelClass:string;
    foods:Food[];
    seats:Seat[];
    discountCode:string;
    price: number;
  }
  export interface Seat{
    name:string;
    isAvailable:boolean;
    price:number;
    childrenPrice:number;
  }

  export interface Food{
    name:string;
    quantity:number;
    price:number;
  }