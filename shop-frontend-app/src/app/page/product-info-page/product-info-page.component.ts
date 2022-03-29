import {Component, Input, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {ActivatedRoute} from "@angular/router";
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
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {

    const routeParams = this.route.snapshot.paramMap;
    const productIdFromRoute = Number(routeParams.get('productId'));

    this.productService.findById(productIdFromRoute)
      .subscribe({
        next: res => {
          console.log(`Loading product with id = ${productIdFromRoute}`)
          this.product = res;
        },
        error: err => {
          console.error(`Error product with id = ${productIdFromRoute}\n${err}`);
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
