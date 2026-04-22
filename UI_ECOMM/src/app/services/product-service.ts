import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConstants } from '../appConstants';
@Injectable({
  providedIn: 'root',
})
export class ProductService {
  
constructor(private http:HttpClient){}

// getproducts():Observable<any>{
//   return this.http.get(AppConstants.Prouct_EndPointUrl/);
// }
getproductsCategoryId(categoryid:any):Observable<any>{
  return this.http.get(`${AppConstants.Product_EndPointUrl}${categoryid}`);
 
}
getCategories():Observable<any>{
  return this.http.get(AppConstants.Category_EndPointUrl);
}
getByNameProducts(keyword:string):Observable<any>{
  return this.http.get(`${AppConstants.SearchProduct_EndPointUrl}/${keyword}`);
}
}
