import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLinkWithHref } from '@angular/router';
import { ProductList } from "./component/product-list/product-list";
import { ProductCategoryMenu } from "./component/product-category-menu/product-category-menu";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ProductList, RouterLinkWithHref, ProductCategoryMenu],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('UI_ECOMM');
}