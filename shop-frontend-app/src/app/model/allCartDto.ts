import {LineItem} from "./lineItem";

export class AllCartDto {

  constructor(public lineItems: LineItem[],
              public subtotal: number) {
  }
}
