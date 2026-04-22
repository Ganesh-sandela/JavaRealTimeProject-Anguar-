import { Route, Routes } from "@angular/router";
import { ProductList } from "./component/product-list/product-list";
import { CartDetails } from "./component/cart-details/cart-details";
import { Checkout } from "./component/checkout/checkout";
import { Yourorders } from "./component/yourorders/yourorders";


export const product_routes:Routes=[

     {
        path:'',
        children: [
      { path: 'category/:id', component: ProductList },
      // { path: 'products', component: ProductList },
      // { path: 'search', component: ProductList },
      { path: 'search/:keyword', component: ProductList },
      { path: 'cart-details', component: CartDetails },
      { path: 'checkout', component: Checkout },
      {path:'yourorders',component:Yourorders},

      // default inside layout
      { path: '', redirectTo: 'category/1', pathMatch: 'full' }
    ]
     }
    ]