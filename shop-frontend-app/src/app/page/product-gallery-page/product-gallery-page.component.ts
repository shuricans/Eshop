import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";
import {ProductFilterDto} from "../../model/productFilterDto";
import {Category} from "../../model/category";
import {CategoryService} from "../../service/category.service";

@Component({
  selector: 'app-product-gallery-page',
  templateUrl: './product-gallery-page.component.html',
  styleUrls: ['./product-gallery-page.component.scss']
})
export class ProductGalleryPageComponent implements OnInit {

  products: Product[] = [];

  categories: Category[] = [];

  page?: Page;

  productFilter?: ProductFilterDto;

  constructor(private productService: ProductService,
              private categoryService: CategoryService) {
  }

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

    this.categoryService.findAll()
      .subscribe({
        next: res => {
          console.log('Loading categories...')
          this.categories = res;
        },
        error: err => {
          console.error(`Error loading categories ${err}`);
        }
      })
  }

  goToPage($event: number) {
    this.productService.findAll(this.productFilter, $event)
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

  applyFilter($event: ProductFilterDto) {
    this.productFilter = $event;
    this.productService.findAll(this.productFilter, 1)
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
