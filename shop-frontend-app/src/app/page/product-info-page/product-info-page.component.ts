import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../../model/product";
import {AddLineItemDto} from "../../model/addLineItemDto";
import {CartService} from "../../service/cart.service";
import {NgxSpinnerService} from "ngx-spinner";

@Component({
  selector: 'app-product-info-page',
  templateUrl: './product-info-page.component.html',
  styleUrls: ['./product-info-page.component.scss']
})
export class ProductInfoPageComponent implements OnInit {

  product!: Product;
  qty: number = 1;
  isProductAppending: boolean = false;
  isValidQty: boolean = true;
  isAllImagesReady: boolean = false;
  private countReadyImages: number = 0;
  isProductDataReady: boolean = false;

  constructor(private productService: ProductService,
              private cartService: CartService,
              private route: ActivatedRoute,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit(): void {

    this.showSpinner('spinnerImagesLoading');

    const routeParams = this.route.snapshot.paramMap;
    const productIdFromRoute = Number(routeParams.get('productId'));

    this.productService.findById(productIdFromRoute)
      .subscribe({
        next: res => {
          console.log(`Loading product with id = ${productIdFromRoute}`)
          this.product = res;
          this.isProductDataReady = true;
        },
        error: err => {
          console.error(`Error product with id = ${productIdFromRoute}\n${err}`);
        }
      })
  }

  public addToCart(productId: number) {
    this.isProductAppending = true;
    setTimeout(() => {
      this.isProductAppending = false;
    }, 1000);

    let lineItemDto = new AddLineItemDto(productId, this.qty, '', '');
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

  updateQtyEvent() {
    this.isValidQty = this.qty != null && Number.isInteger(this.qty) && this.qty > 0;
  }

  showSpinner(name: string) {
    this.spinner.show(name);
  }

  hideSpinner(name: string) {
    this.spinner.hide(name);
  }

  pictureReadyEvent() {
    this.countReadyImages++;
    if (this.countReadyImages === this.product.pictures.length) {
      this.isAllImagesReady = true;
      this.hideSpinner('spinnerImagesLoading');
    }
  }
}
