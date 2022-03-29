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
  private prevQty: number = 0;
  isValidQty: boolean = true;
  qty: number = 0;
  isUpdating: boolean = false;
  isDeleting: boolean = false;

  constructor(private cartService: CartService) {
  }

  ngOnInit(): void {
  }

  @Input()
  set lineItem(value: LineItem | undefined) {
    this._lineItem = value;
    this.qty = value ? value.qty : 0;
    this.initQty = this.qty;
    this.prevQty = this.qty;
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
    this.isUpdating = true;
    this.isDeleting = true;
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

  qtyChangeEvent() {
    let newQty = this.qty;
    this.isValidQty = newQty != null && Number.isInteger(newQty) && newQty > 0;
    if (this.isValidQty) {
      setTimeout(() => {
        if (newQty === this.qty) {
          this.isUpdating = true;
          this.prevQty = newQty;
        }
      }, 300);
      setTimeout(() => {
        if (newQty === this.prevQty && newQty !== this.initQty) {
          console.log(`Call API with new value = ${newQty}`);
          this.updateQty(newQty);
        } else {
          this.isUpdating = false;
        }
      }, 1500);
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

}
