import {Injectable} from '@angular/core';
import {Credentials} from "../model/credentials";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {AuthResult} from "../model/auth-result";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private currentUser?: Credentials;

  public redirectUrl?: string;

  public callbackAfterSuccess?: () => void;

  constructor(private http: HttpClient, private router: Router) {
    let data = localStorage.getItem('current_user');
    if (data) {
      this.currentUser = JSON.parse(data);
    }
  }

  authenticate(credentials: Credentials): Observable<AuthResult> {

    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    return this.http.get('/api/v1/login', {headers: headers})
      .pipe(
        map(resp => {
          if ('username' in resp) {
            this.currentUser = resp as Credentials;
            localStorage.setItem('current_user', JSON.stringify(resp));
            const authResult = new AuthResult(this.currentUser, this.redirectUrl, this.callbackAfterSuccess);
            this.redirectUrl = undefined;
            this.callbackAfterSuccess = undefined;
            return authResult;
          }
          throw new Error('Authentication error');
        })
      )
  }

  logout() {
    if (this.isAuthenticated()) {
      this.currentUser = undefined;
      localStorage.removeItem("current_user");
      this.http.get('/api/v1/logout', {})
        .subscribe({
          next: () => {
            console.log(`Success logout.`);
            this.router.navigateByUrl('/login?logout');
          },
          error: err => {
            console.error(err);
          }
        })
    }
  }

  public isAuthenticated(): boolean {
    return !!this.currentUser;
  }
}
