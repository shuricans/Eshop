import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Router} from "@angular/router";
import {Product} from "../../model/product";
import {AddLineItemDto} from "../../model/addLineItemDto";
import {CartService} from "../../service/cart.service";

@Component({
  selector: 'app-product-info-page',
  templateUrl: './product-info-page.component.html',
  styleUrls: ['./product-info-page.component.scss']
})
export class ProductInfoPageComponent implements OnInit {

  product!: Product;
  qty: number = 1;

  constructor(private productService: ProductService,
              private cartService: CartService,
              private router: Router) {
  }

  ngOnInit(): void {
    let productId = this.router.url.split('/')[2];
    console.log(`productId = ${productId}`);
    this.productService.findById(productId)
      .subscribe({
        next: res => {
          console.log(`Loading product with id = ${productId}`)
          this.product = res;
        },
        error: err => {
          console.error(`Error product with id = ${productId}\n${err}`);
        }
      })
  }

  public addToCart(productId: number) {
    let lineItemDto = new AddLineItemDto(productId, this.qty, '', '');
    this.cartService.addToCart(lineItemDto)
      .subscribe({
        next: res => {
          console.log(`Adding product with id = ${productId} to cart.`)
        },
        error: err => {
          console.error(`Error adding product with id = ${productId}\n${err}`);
        }
      })
  }
}
