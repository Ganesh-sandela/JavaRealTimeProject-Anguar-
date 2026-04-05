import { Routes } from '@angular/router';
import { ProductList } from './component/product-list/product-list';

export const routes: Routes = [
    {path:"category/:id", component:ProductList},
    { path: '', redirectTo: '/category/1', pathMatch: 'full' },
    {path:"products",component:ProductList}

];
