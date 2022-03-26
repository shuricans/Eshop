import {Component, OnInit} from '@angular/core';
import {ProductService} from "../../service/product.service";
import {Product} from "../../model/product";
import {Page} from "../../model/page";
import {ProductFilterDto} from "../../model/productFilterDto";
import {Category} from "../../model/category";
import {CategoryService} from "../../service/category.service";
import {NgxSpinnerService} from "ngx-spinner";

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

  categoriesIsLoading: boolean = true;

  constructor(private productService: ProductService,
              private categoryService: CategoryService,
              private spinner: NgxSpinnerService) {
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

    this.showSpinner("spin-filter");

    this.categoryService.findAll()
      .subscribe({
        next: res => {
          console.log('Loading categories...')
          this.categories = res;
          this.hideSpinner("spin-filter");
          this.categoriesIsLoading = false;
        },
        error: err => {
          console.error(`Error loading categories ${err}`);
          this.hideSpinner("spin-filter");
          this.categoriesIsLoading = false;
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

  showSpinner(name: string) {
    this.spinner.show(name);
  }

  hideSpinner(name: string) {
    this.spinner.hide(name);
  }
}
