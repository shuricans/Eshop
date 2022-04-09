import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {LineItem} from "../../model/lineItem";
import {CartService} from "../../service/cart.service";
import {AddLineItemDto} from "../../model/addLineItemDto";

@Component({
  selector: '[app-cart-item]',
  templateUrl: './cart-item.component.html',
  styleUrls: ['./cart-item.component.scss']
})
export class CartItemComponent implements OnInit {

  @Output() updated = new EventEmitter();

  private _lineItem?: LineItem;
  private _index?: number;

  private initQty: number = 0;
  isValidQty: boolean = true;
  qty: number = 1;
  isUpdating: boolean = false;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  @Input()
  set lineItem(value: LineItem | undefined) {
    this._lineItem = value;
    this.qty = value ? value.qty : 1;
    this.initQty = this.qty;
  }

  get lineItem(): LineItem | undefined {
    return this._lineItem;
  }

  @Input()
  set index(value: number | undefined) {
    this._index = value;
  }

  get index(): number | undefined {
    return this._index;
  }

  deleteLineItem() {
    console.log('deleteLineItem');
    this.isUpdating = true;
    if (this._lineItem) {
      this.cartService.removeItem(this._lineItem)
        .subscribe({
          next: () => {
            this.updated.emit();
          },
          error: () => {
            console.error('Unsuccessful removal');
          }
        });
    }
  }

  private updateQty(newQty: number) {
    if (this._lineItem) {
      let lineItemDto = new AddLineItemDto(this._lineItem.productId, 0, '', '');
      if (newQty < this.initQty) {
        lineItemDto.qty = this.initQty - newQty;
        this.cartService.removeFromCart(lineItemDto)
          .subscribe({
            next: () => {
              console.log(`Product decreased by ${lineItemDto.qty}`);
              this.updated.emit();
            },
            error: err => {
              console.error(`Product decrease error.\n${err}`);
              this.isUpdating = false;
            }
          });
      } else {
        lineItemDto.qty = newQty - this.initQty;
        this.cartService.addToCart(lineItemDto)
          .subscribe({
            next: () => {
              console.log(`Product increased by ${lineItemDto.qty}`);
              this.updated.emit();
            },
            error: err => {
              console.error(`Product increase error.\n${err}`);
              this.isUpdating = false;
            }
          });
      }
      this.initQty = newQty;
    }
  }

  focusOut() {
    // get new quantity value
    let newQty = this.qty;
    // is valid?
    this.isValidQty = newQty != null && Number.isInteger(newQty) && newQty > 0;
    // if is valid & not equals initial value
    if (this.isValidQty && newQty !== this.initQty) {
      this.updateQty(newQty);
    }
    this.isUpdating = false;
  }

  focusIn() {
    this.isUpdating = true;
  }
}
