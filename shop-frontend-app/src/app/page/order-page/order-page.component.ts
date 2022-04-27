import {Component, OnInit} from '@angular/core';
import {Order} from "../../model/order";
import {OrderService} from "../../service/order.service";
import {OrderStatusService} from "../../service/order-status.service";

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];
  isDataReady: boolean = false;

  constructor(private orderService: OrderService,
              private orderStatusService: OrderStatusService) {
  }

  ngOnInit(): void {
    this.updateOrders();
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        let updated = this.orders.find(order => order.id === msg.orderId);
        if (updated) {
          updated.status = msg.status;
        }
      });
  }

  updateOrders() {
    this.orderService.findOrdersByCurrentUser()
      .subscribe({
        next: orders => {
          console.log('Orders successfully loaded.');
          this.isDataReady = true;
          this.orders = orders;
        },
        error: err => {
          console.error(`Error loading Orders ${err}`);
        }
      });
  }

  dateFormat(localDateTime: string): string {
    return new Date(localDateTime).toLocaleString();
  }

  cancelOrder(orderId: number) {
    this.orderService.cancelOrder(orderId)
      .subscribe(() => {
        this.updateOrders();
      });
  }

  setAlertClass(status: string): string {
    return this.orderService.getAlertClass(status);
  }
}
