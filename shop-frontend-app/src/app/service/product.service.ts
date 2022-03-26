import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../model/page";

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {
  }

  public findAll(page?: number, nameFilter?: string) : Observable<Page> {
    let params = new HttpParams();
    params = params.set("page", page != null ? page : 1);
    params = params.set("size", 3);
    if (nameFilter != null) {
      params = params.set("nameFilter", nameFilter);
    }
    return this.http.get<Page>('api/v1/product/all', {params});
  }
}
