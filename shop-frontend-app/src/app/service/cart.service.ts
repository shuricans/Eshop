import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AllCartDto} from "../model/allCartDto";
import {LineItem} from "../model/lineItem";
import {AddLineItemDto} from "../model/addLineItemDto";

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<AllCartDto> {
    return this.http.get<AllCartDto>('api/v1/cart/all');
  }

  public removeItem(lineItem: LineItem) {
    return this.http.delete('api/v1/cart', {body: lineItem});
  }

  public addToCart(addLineItemDto: AddLineItemDto): Observable<LineItem[]> {
    return this.http.post<LineItem[]>('api/v1/cart', addLineItemDto);
  }

  public removeFromCart(addLineItemDto: AddLineItemDto): Observable<LineItem[]> {
    return this.http.post<LineItem[]>('api/v1/cart/remove', addLineItemDto);
  }

  public removeAll(): Observable<void> {
    return this.http.delete<void>('api/v1/cart/all');
  }
}
