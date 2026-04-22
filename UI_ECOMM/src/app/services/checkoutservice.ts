import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PurchaseRequest } from '../dto/purchaserequest';
import { AppConstants } from '../appConstants';
import { Observable } from 'rxjs';
import { Paymentcallback } from '../dto/paymentcallback';

@Injectable({
  providedIn: 'root',
})
export class Checkoutservice {

  constructor(private http: HttpClient) {}

  createOrder(request: PurchaseRequest): Observable<any> {
    return this.http.post<any>(AppConstants.Purchase_EndPointUrl,request);
  }

  confirmOrder(Paymentcallback:Paymentcallback):Observable<any>{
    return this.http.put(AppConstants.payment_EndPointUrl,Paymentcallback);
  }
}