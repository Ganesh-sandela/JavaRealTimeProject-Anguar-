import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLinkWithHref } from '@angular/router';
import { ProductList } from "./component/product-list/product-list";
import { ProductCategoryMenu } from "./component/product-category-menu/product-category-menu";
import { SearchList } from "./component/search-list/search-list";
import { CartStatus } from './component/cart-status/cart-status';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ProductList, RouterLinkWithHref, ProductCategoryMenu, SearchList,CartStatus],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('UI_ECOMM');
}