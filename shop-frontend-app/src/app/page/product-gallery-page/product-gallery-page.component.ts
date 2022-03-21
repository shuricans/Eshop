import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";

@Component({
  selector: 'app-product-gallery-page',
  templateUrl: './product-gallery-page.component.html',
  styleUrls: ['./product-gallery-page.component.scss']
})
export class ProductGalleryPageComponent implements OnInit {

  products: Product[] = [];

  page?: Page;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.findAll()
      .subscribe({
        next: res => {
          console.log('Loading products...')
          this.page = res;
          this.products = res.content;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
        }
      })
  }

  goToPage(page: number) {
    this.productService.findAll(page)
      .subscribe({
        next: res => {
          console.log('Loading products...')
          this.page = res;
          this.products = res.content;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
        }
      })
  }

  applyFilter(nameFilter: string) {
    this.productService.findAll(1, nameFilter)
      .subscribe({
        next: res => {
          console.log('Loading products...')
          this.page = res;
          this.products = res.content;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
        }
      })
  }
}
