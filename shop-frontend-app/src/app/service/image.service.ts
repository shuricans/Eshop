import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  public getPicture(picId: number): Observable<Blob> {
    return this.http.get('api/v1/picture/' + picId, {responseType: 'blob'});
  }
}
