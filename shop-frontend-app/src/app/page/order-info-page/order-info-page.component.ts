import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Order} from "../../model/order";
import {OrderService} from "../../service/order.service";
import {ProductService} from "../../service/product.service";
import {OrderStatusService} from "../../service/order-status.service";

@Component({
  selector: 'app-order-info-page',
  templateUrl: './order-info-page.component.html',
  styleUrls: ['./order-info-page.component.scss']
})
export class OrderInfoPageComponent implements OnInit {

  order!: Order;
  idDataReady: boolean = false;

  constructor(private orderService: OrderService,
              private productService: ProductService,
              private orderStatusService: OrderStatusService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.updateOrder();
    this.orderStatusService.onMessage('/order_out/order')
      .subscribe(msg => {
        if (this.order.id === msg.orderId) {
          this.order.status = msg.status;
        }
      });
  }

  updateOrder() {
    const routeParams = this.route.snapshot.paramMap;
    const orderId = Number(routeParams.get('orderId'));

    this.orderService.findOrderByIdAndCurrentUser(orderId)
      .subscribe({
        next: res => {
          console.log(`Loading order with id = ${orderId}`);
          this.order = res;
          this.idDataReady = true;
        },
        error: err => {
          console.error(`Error loading order with id = ${orderId}\n${err}`);
        }
      });
  }

  cancelOrder() {
    this.orderService.cancelOrder(this.order.id)
      .subscribe(() => {
        this.updateOrder();
      });
  }

  dateFormat(localDateTime: string): string {
    return new Date(localDateTime).toLocaleString();
  }

  setAlertClass(status: string): string {
    return this.orderService.getAlertClass(status);
  }
}
