import {Component, Input, OnInit} from '@angular/core';
import {OrderLineItem} from "../../model/orderLineItem";
import {Observable} from "rxjs";
import {Product} from "../../model/product";
import {ProductService} from "../../service/product.service";

@Component({
  selector: '[app-order-line-item]',
  templateUrl: './order-line-item.component.html',
  styleUrls: ['./order-line-item.component.scss']
})
export class OrderLineItemComponent implements OnInit {

  private _orderLineItem!: OrderLineItem;
  private _index?: number;
  product?: Observable<Product>;

  constructor(private productService: ProductService) {
  }

  @Input()
  set orderLineItem(value: OrderLineItem) {
    this._orderLineItem = value;
  }

  get orderLineItem(): OrderLineItem {
    return this._orderLineItem;
  }

  @Input()
  set index(value: number | undefined) {
    this._index = value;
  }

  get index(): number | undefined {
    return this._index;
  }

  ngOnInit(): void {
    this.product = this.fetchProductById(this._orderLineItem.productId);
  }

  private fetchProductById(productId: number): Observable<Product> {
    return this.productService.findById(productId);
  }
}
