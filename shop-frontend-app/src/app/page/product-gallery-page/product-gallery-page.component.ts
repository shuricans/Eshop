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


  productsLoading: boolean = true;
  categoriesLoading: boolean = true;

  constructor(private productService: ProductService,
              private categoryService: CategoryService) {
  }

  ngOnInit(): void {
    console.log('Loading products...');
    this.productsLoading = true;
    this.productService.findAll()
      .subscribe({
        next: res => {
          console.log('Products successfully loaded.');
          this.page = res;
          this.products = res.content;
          this.productsLoading = false;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
          this.productsLoading = false;
        }
      });
    console.log('Loading categories...');
    this.categoriesLoading = true;
    this.categoryService.findAll()
      .subscribe({
        next: res => {
          console.log('Categories successfully loaded.');
          this.categories = res;
          this.categoriesLoading = false;
        },
        error: err => {
          console.error(`Error loading categories ${err}`);
          this.categoriesLoading = false;
        }
      });
  }

  goToPage($event: number) {
    console.log('Loading products...');
    this.productService.findAll(this.productFilter, $event)
      .subscribe({
        next: res => {
          console.log('Categories successfully loaded.');
          this.page = res;
          this.products = res.content;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
        }
      });
  }

  applyFilter($event: ProductFilterDto) {
    console.log('Loading products...');
    this.productFilter = $event;
    this.productService.findAll(this.productFilter, 1)
      .subscribe({
        next: res => {
          console.log('Categories successfully loaded.');
          this.page = res;
          this.products = res.content;
        },
        error: err => {
          console.error(`Error loading products ${err}`);
        }
      });
  }
}
