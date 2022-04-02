import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductGalleryPageComponent } from './page/product-gallery-page/product-gallery-page.component';
import { ProductInfoPageComponent } from './page/product-info-page/product-info-page.component';
import { NavBarComponent } from './component/nav-bar/nav-bar.component';
import { ProductFilterComponent } from './component/product-filter/product-filter.component';
import { ProductPaginationComponent } from './component/product-pagination/product-pagination.component';
import { ProductGalleryComponent } from './component/product-gallery/product-gallery.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {CartPageComponent} from './page/cart-page/cart-page.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgxSpinnerModule} from "ngx-spinner";
import { ProductCardComponent } from './component/product-card/product-card.component';
import { CartItemComponent } from './component/cart-item/cart-item.component';
import { CarouselPictureComponent } from './component/carousel-picture/carousel-picture.component';
import { LoginPageComponent } from './page/login-page/login-page.component';
import { OrderPageComponent } from './page/order-page/order-page.component';
import {UnauthorizedInterceptor} from "./helper/unauthorized-interceptor";
import { OrderInfoPageComponent } from './page/order-info-page/order-info-page.component';
import { OrderLineItemComponent } from './component/order-line-item/order-line-item.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductGalleryPageComponent,
    ProductInfoPageComponent,
    NavBarComponent,
    ProductFilterComponent,
    ProductPaginationComponent,
    ProductGalleryComponent,
    CartPageComponent,
    ProductCardComponent,
    CartItemComponent,
    CarouselPictureComponent,
    LoginPageComponent,
    OrderPageComponent,
    OrderInfoPageComponent,
    OrderLineItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    NgxSpinnerModule,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: UnauthorizedInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
