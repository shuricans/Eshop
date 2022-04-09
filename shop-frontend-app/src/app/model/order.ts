import {OrderLineItem} from "./orderLineItem";

export class Order {

  constructor(public id: number,
              public price: number,
              public date: string,
              public status: string,
              public login: string,
              public orderLineItems: OrderLineItem[]) {
  }
}
