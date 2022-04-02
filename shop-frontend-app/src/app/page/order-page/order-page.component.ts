import {Component, OnInit} from '@angular/core';
import {Order} from "../../model/order";
import {OrderService} from "../../service/order.service";

@Component({
  selector: 'app-order-page',
  templateUrl: './order-page.component.html',
  styleUrls: ['./order-page.component.scss']
})
export class OrderPageComponent implements OnInit {

  orders: Order[] = [];

  constructor(private orderService: OrderService) {
  }

  ngOnInit(): void {

    this.orderService.findOrdersByCurrentUser()
      .subscribe({
        next: orders => {
          console.log('Orders successfully loaded.');
          this.orders = orders;
        },
        error: err => {
          console.error(`Error loading Orders ${err}`);
        }
      });
  }
}
