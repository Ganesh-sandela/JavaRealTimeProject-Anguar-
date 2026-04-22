import { Routes } from '@angular/router';
import { ProductList } from './component/product-list/product-list';
import { CartDetails } from './component/cart-details/cart-details';
import { Checkout } from './component/checkout/checkout';
import { Login } from './component/login/login';
import { Forgot } from './component/forgot/forgot';
import { Layout } from './component/layout/layout'; 
import { Register } from './component/register/register';
import { Reset } from './component/reset/reset';
import { authGuard } from './auth-guard';

export const routes: Routes = [

  { path: 'login', component: Login },
  { path: 'forgot', component: Forgot },
  {path:'register',component:Register},
  {path:'resetPwd',component:Reset},
 
  {
    path: 'products',
    component: Layout,
   canActivateChild:[authGuard],
   loadChildren:()=>
   import('./product.routes')
    .then(m => m.product_routes)

  },

 
  { path: '**', redirectTo: 'login' },
  { path: '', redirectTo: 'login',pathMatch:'full' }
  

];