import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {CartService} from "../../service/cart.service";
import {AddLineItemDto} from "../../model/addLineItemDto";

@Component({
  selector: 'app-product-gallery',
  templateUrl: './product-gallery.component.html',
  styleUrls: ['./product-gallery.component.scss']
})
export class ProductGalleryComponent implements OnInit {

  @Input() products: Product[] = [];

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  public addToCart(productId: number) {
    let lineItemDto = new AddLineItemDto(productId, 1, '', '');
    this.cartService.addToCart(lineItemDto)
      .subscribe({
        next: () => {
          console.log(`Adding product with id = ${productId} to cart.`)
        },
        error: err => {
          console.error(`Error adding product with id = ${productId}\n${err}`);
        }
      })
  }
}
