import {Component, OnInit} from '@angular/core';
import {CartService} from "../../service/cart.service";
import {AllCartDto} from "../../model/allCartDto";
import {LineItem} from "../../model/lineItem";

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  allCartDto?: AllCartDto;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.reloadCart();
  }

  clearCart() {
    this.cartService.removeAll()
      .subscribe(() => {
        this.reloadCart()
      });
  }

  removeItem(lineItem: LineItem) {
    this.cartService.removeItem(lineItem)
      .subscribe(() => {
        this.reloadCart()
      });
  }

  private reloadCart() {
    this.cartService.findAll()
      .subscribe({
        next: res => {
          console.log('Loading cart...')
          this.allCartDto = res;
        },
        error: err => {
          console.error(`Error loading cart ${err}`);
        }
      });
  }
}
