import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../model/page";
import {ProductFilterDto} from "../model/productFilterDto";
import {Product} from "../model/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  public findAll(productFilter?: ProductFilterDto, page?: number): Observable<Page> {
    let params = new HttpParams();
    params = params.set("page", page != null ? page : 1);
    params = params.set("size", 3);

    if (productFilter?.nameFilter != null) {
      params = params.set('nameFilter', productFilter?.nameFilter);
    }
    if (productFilter?.minPrice != null) {
      params = params.set('minPrice', productFilter?.minPrice);
    }
    if (productFilter?.maxPrice != null) {
      params = params.set('maxPrice', productFilter?.maxPrice);
    }
    if (productFilter?.categoryId != null && productFilter.categoryId !== -1) {
      params = params.set('categoryId', productFilter?.categoryId);
    }

    return this.http.get<Page>('api/v1/product/all', {params});
  }

  public findById(productId: string): Observable<Product> {
    return this.http.get<Product>('api/v1/product/' + productId);
  }
}
