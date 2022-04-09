export class OrderLineItem {

  constructor(public id: number,
              public qty: number,
              public price: number,
              public color: string,
              public material: string,
              public productId: number,
              public orderId: number) {
  }
}
