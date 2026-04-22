import { Injectable } from '@angular/core';

declare var Razorpay: any; 

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  
  private Razorpay_Key = "rzp_test_SY6XJ3fnCRzhPv";

  processPayment(orderId: string, amount: number, customerName: string, customerEmail: string, customerPhone: string, successCallback: (response: any) => void): void {

    const options: any = {
      key: this.Razorpay_Key,
      amount: amount,
      currency: "INR",
      name: "GANA",
      description: "Ecommerce Order",
      order_id: orderId, 
      handler: successCallback,
      prefill: {
        name: customerName,
        email: customerEmail,
        contact: customerPhone
      },
      notes: {
        address: 'Customer address'
      },
      theme: {
        color: '#3399cc'
      }
    };

    const rzpl = new Razorpay(options);
    rzpl.open(); 
  }
}