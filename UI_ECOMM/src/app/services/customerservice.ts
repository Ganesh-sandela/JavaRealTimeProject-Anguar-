import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, shareReplay, Subject } from 'rxjs';
import { AppConstants } from '../appConstants';
import { Coustomer } from '../dto/coustomer';
import { ResetPwd } from '../dto/reset-pwd';
import { LoginClass } from '../dto/loginClass';
import { observableToBeFn } from 'rxjs/internal/testing/TestScheduler';

@Injectable({
  providedIn: 'root',
})
export class Customerservice {


  public userDetails = new Map<string, Observable<any>>();

  emailStore = '';
  nameStore = '';
  phnoStore = '';
   ordersSubject = new BehaviorSubject<any[]>([]);
  orders$ = this.ordersSubject.asObservable();


  constructor(private http: HttpClient) { }
  register(customer: Coustomer): Observable<any> {
    return this.http.post(AppConstants.Register_EndPoinUrl, customer);
  }

  resetPwd(resetpwd: ResetPwd): Observable<any> {
    return this.http.post(AppConstants.ResetPwd_EndPointUrl, resetpwd);
  }

  login(login: LoginClass): Observable<any> {
    return this.http.post(AppConstants.Login_EndPoinUrl, login);
  }
  forgot(email: string): Observable<any> {
    return this.http.post(
      `${AppConstants.Forgot_EndPointUrl}?email=${email}`,
      {}
    );
  }

  getDetails(email: any): Observable<any> | undefined {

    if (!this.userDetails.has(email)) {
      const userdataObj = this.http.post(AppConstants.getAllDetails_EndPointUrl, { email: email })
        .pipe(shareReplay(1));
      this.userDetails.set(email, userdataObj);

    }
    return this.userDetails.get(email);

  }


//   setOrder(orders:any[]){
//     console.log(orders,'00000000000000000000000');
// this.ordersSubject.next([...orders]);
//   }


  loadOrders(email: string) {
  this.http.post<any>(AppConstants.getAllDetails_EndPointUrl, { email })
    .subscribe(res => {
      const orders = res?.data?.orderItems ?? [];
      this.ordersSubject.next(orders);
    });
}
}
