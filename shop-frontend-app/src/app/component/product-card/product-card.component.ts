import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {AddLineItemDto} from "../../model/addLineItemDto";
import {CartService} from "../../service/cart.service";
import {NgxSpinnerService} from "ngx-spinner";
import {ImageService} from "../../service/image.service";

@Component({
  selector: '[app-product-card]',
  templateUrl: './product-card.component.html',
  styleUrls: ['./product-card.component.scss']
})
export class ProductCardComponent implements OnInit {

  private _product?: Product;

  imageToShow: any;
  isImageLoading?: boolean;
  spinnerImageLoadingName!: string;

  isProductAppending?: boolean;

  constructor(private cartService: CartService,
              private imageService: ImageService,
              private spinner: NgxSpinnerService) {
  }

  ngOnInit(): void {
    this.getImageFromService();
  }

  @Input()
  set product(value: Product | undefined) {
    this._product = value;
    this.spinnerImageLoadingName = "spinImg" + this._product?.pictures[0];
  }

  get product(): Product | undefined {
    return this._product;
  }

  public addToCart(productId: number) {
    this.isProductAppending = true;
    setTimeout(() => {
      this.isProductAppending = false;
    }, 1000);
    let lineItemDto = new AddLineItemDto(productId, 1, '', '');
    this.cartService.addToCart(lineItemDto)
      .subscribe({
        next: res => {
          console.log(res);
          console.log(`Adding product with id = ${productId} to cart.`);
        },
        error: err => {
          console.error(`Error adding product with id = ${productId}\n${err}`);
        }
      });
  }

  showSpinner(name: string) {
    this.spinner.show(name);
  }

  hideSpinner(name: string) {
    this.spinner.hide(name);
  }

  private createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  private getImageFromService() {
    this.isImageLoading = true;
    if (this._product) {
      let picId = this._product.pictures[0];
      let spinName = "spinImg" + picId;
      this.showSpinner(spinName);
      this.imageService.getPicture(picId)
        .subscribe({
          next: (data) => {
            this.createImageFromBlob(data);
            this.isImageLoading = false;
            this.hideSpinner(spinName);
          },
          error: err => {
            this.isImageLoading = false;
            this.hideSpinner(spinName);
            console.error(err);
          }
        });
    } else {
      console.error('product is undefined');
    }
  }
}
