import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Credentials} from "../../model/credentials";
import {AuthService} from "../../service/auth.service";
import {NavigationEnd, Router} from "@angular/router";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  credentials: Credentials = new Credentials("", "")

  isError: boolean = false;
  isLoggedOut: boolean = false;

  constructor(private auth: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.isLoggedOut = this.router.url.endsWith("logout=")
  }

  reset(): void {
    this.isError = false;
    this.isLoggedOut = false;
  }

  login() {
    this.auth.authenticate(this.credentials)
      .subscribe({
        next: authResult => {
          this.isError = false;
          if (authResult.callbackAfterSuccess) {
            authResult.callbackAfterSuccess();
          }
          if (authResult.redirectUrl) {
            this.router.navigateByUrl(authResult.redirectUrl);
          } else {
            this.router.navigateByUrl('/product');
          }
        },
        error: err => {
          this.isError = true;
          console.log(`Authentication error ${err}`);
        }
      });
  }
}
