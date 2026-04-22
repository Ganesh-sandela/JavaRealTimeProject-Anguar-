import { Component } from '@angular/core';
import { SearchList } from "../search-list/search-list";
import { CartStatus } from "../cart-status/cart-status";
import { RouterLink, RouterOutlet } from "@angular/router";
import { ProductCategoryMenu } from "../product-category-menu/product-category-menu";

@Component({
  selector: 'app-layout',
  imports: [SearchList, CartStatus, RouterLink, ProductCategoryMenu, RouterOutlet],
  templateUrl: './layout.html',
  styleUrl: './layout.css',
})
export class Layout {

}
