import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ProductFilterDto} from "../../model/productFilterDto";
import {Category} from "../../model/category";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.scss']
})
export class ProductFilterComponent implements OnInit {

  productFilter: ProductFilterDto = new ProductFilterDto();

  @Output() applyFilterEvent = new EventEmitter<ProductFilterDto>();

  @Input() categories: Category[] = [];

  categoryId: number = -1;

  constructor() {
  }

  ngOnInit(): void {
  }

  applyFilter() {
    this.applyFilterEvent.emit(this.productFilter);
  }

  setCategory(categoryId: number) {
    this.categoryId = categoryId;
    this.productFilter.categoryId = categoryId;
    this.applyFilter();
  }

  clearFilter() {
    this.productFilter = new ProductFilterDto();
    this.categoryId = -1;
    this.applyFilter();
  }
}
