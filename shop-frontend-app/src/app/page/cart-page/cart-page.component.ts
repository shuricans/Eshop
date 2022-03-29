import {Component, OnInit} from '@angular/core';
import {CartService} from "../../service/cart.service";
import {AllCartDto} from "../../model/allCartDto";

@Component({
  selector: 'app-cart-page',
  templateUrl: './cart-page.component.html',
  styleUrls: ['./cart-page.component.scss']
})
export class CartPageComponent implements OnInit {

  allCartDto?: AllCartDto;
  isDataLoading!: boolean;
  isEmpty: boolean = true;
  countChildEvents: number = 0;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
    this.updateCart();
  }

  clearCart() {
    this.cartService.removeAll()
      .subscribe(() => {
        this.updateCart();
      });
  }

  public updateCart() {
    // this.isDataLoading = true;
    console.log('Loading cart-items...');
    this.cartService.findAll()
      .subscribe({
        next: res => {
          console.log('Cart-items successfully loaded.');
          this.allCartDto = res;
          this.isDataLoading = false;
          this.isEmpty = this.allCartDto.subtotal === 0;
        },
        error: err => {
          console.error(`Error loading cart ${err}`);
          this.isDataLoading = false;
        }
      });
  }
}
