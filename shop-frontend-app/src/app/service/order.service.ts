import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../model/order";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) {
  }

  public findOrdersByCurrentUser() {
    return this.http.get<Order[]>('/api/v1/order/all');
  }

  public findOrderByIdAndCurrentUser(orderId: number): Observable<Order> {
    return this.http.get<Order>('/api/v1/order/' + orderId);
  }

  public createOrder() {
    return this.http.post<any>('/api/v1/order', {});
  }

  public cancelOrder(orderId: number) {
    return this.http.get('/api/v1/order/cancel/' + orderId);
  }

  public getAlertClass(status: string): string {
    switch (status) {
      case 'CREATED':
        return 'alert-primary';
      case 'PROCESSED':
        return 'alert-info';
      case 'IN_DELIVERY':
        return 'alert-warning';
      case 'DELIVERED':
        return 'alert-success';
      case 'CLOSED':
        return 'alert-secondary';
      case 'CANCELED':
        return 'alert-danger';
      default: return '';
    }
  }
}
