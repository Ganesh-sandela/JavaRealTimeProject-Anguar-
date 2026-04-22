import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnChanges, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { FormGroup } from '@angular/forms';
import { validate } from '@angular/forms/signals';
import { CartService } from '../../services/cart-service';
import { CommonModule } from '@angular/common';
import { Checkoutservice } from '../../services/checkoutservice';
import { OrderItems } from '../../dto/order-items';
import { PurchaseRequest } from '../../dto/purchaserequest';
import { Order } from '../../dto/order';
import { PaymentService } from '../../services/payment-service';
import { Paymentcallback } from '../../dto/paymentcallback';
import { Card } from '../card/card';
import { Customerservice } from '../../services/customerservice';

@Component({
  selector: 'app-checkout',
  imports: [RouterModule, ReactiveFormsModule, FormsModule, CommonModule],
  templateUrl: './checkout.html',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './checkout.css',
})
export class Checkout implements OnInit {

  checkoutform!: FormGroup;
  totalPrice: number = 0;
  totalQuantity: number = 0;
  orderStatusValue: string = '';
  razorpayorderIdValue: string = '';
  orderTrackingNumberValue: string = "";
  result: any = null;
  orderDetailsArray: any[] = [];
  isLoading: boolean = false;

  TotalAdresses: any[] = [];
  addresses: any[] = [];
  Totalorders: any[] = [];

  constructor(private fb: FormBuilder,
    private cartservice: CartService,
    private checkoutservice: Checkoutservice,
    private paymentservice: PaymentService,
    private cd: ChangeDetectorRef,
    private router: Router,
    private customerService: Customerservice) { }


  ngOnInit(): void {

    this.checkoutform = this.fb.group({
      customer: this.fb.group({
        name: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        phno: ['', [Validators.required, Validators.pattern('^[6-9]\\d{9}$')]]
      }),
      address: this.fb.group({
        houseno: ['', [Validators.required, Validators.pattern('^\\d+[A-Za-z]?(\\/\\d+[A-Za-z]?)?$')]],
        city: ['', [Validators.required, Validators.minLength(3)]],
        state: ['', Validators.required],
        zipcode: ['', [Validators.required, Validators.pattern('^\\d{6}$')]]


      })

    });
    this.reviewCartDetails();


    const email = this.customerService.emailStore;

    this.customerService.getDetails(email)?.subscribe(res => {
      const data = res?.data;
      console.log(data,'===========');
      // this.addresses = data?.address ?? [];
      this.Totalorders = data?.order ?? [];
      // this.customerService.setOrder(data?.orderItems ?? []);
      this.addresses = (data?.address ?? []).filter((adr: any, index: number, self: any[]) => {

        return index === self.findIndex((a: any) => {
          return a.houseno === adr.houseno &&
            a.city.toLowerCase() === adr.city.toLowerCase() &&
            a.state.toLowerCase() === adr.state.toLowerCase() &&
            a.zipcode === adr.zipcode
        })

      })

      console.log('adresses...', this.addresses);
      console.log('totalorders....', this.Totalorders);

      this.cd.markForCheck(); // needed for OnPush
    });
    // this.customerService.getDetails(email)?.subscribe(res => {
    //   const data = res?.data;
    //   console.log(data);

    //   this.TotalAdresses=res?.data;
    //   this.addresses = data.address ?? ''
    //   this.Totalorders=data.order ?? ''  
    //   console.log(this.Totalorders);

    //   console.log(this.TotalAdresses);
    // const address = data?.address?.[0];

    // if (!data || !address) return;
    // this.checkoutform.patchValue({
    //   customer: {
    //     name: data.customer?.name ?? '',
    //     email: data.customer?.email ?? '',
    //     phno: data.customer?.phno ?? '',
    //   },
    //   address: {
    //     houseno: address.houseno ?? '',
    //     city: address.city ?? '',
    //     state: address.state ?? '',
    //     zipcode: address.zipcode ?? ''
    //   }
    // });
    // this.cd.markForCheck();
    // });


  }


  get f() { return this.checkoutform.controls; }

  get customer() { return this.checkoutform.get('customer') as any; }

  get address() { return this.checkoutform.get('address') as any; }

  get getName() { return this.checkoutform.get('customer.name') }
  get getEmail() { return this.checkoutform.get('customer.email') }
  get getphone() { return this.checkoutform.get('customer.phno') }
  get gethouseno() { return this.checkoutform.get('address.houseno') }
  get getcity() { return this.checkoutform.get('address.city') }
  get getstate() { return this.checkoutform.get('address.state') }
  get getzipcode() { return this.checkoutform.get('address.zipcode') }

  patchAdr(adr: any) {
    if (!adr) return;

    this.checkoutform.patchValue({
      customer: {
        name: adr.customer?.name ?? '',
        email: adr.customer?.email ?? '',
        phno: adr.customer?.phno ?? ''
      },
      address: {
        houseno: adr.houseno ?? '',
        city: adr.city ?? '',
        state: adr.state ?? '',
        zipcode: adr.zipcode ?? ''
      }
    });

    this.cd.markForCheck();
  }
  onSubmit() {
    this.isLoading = true;
    let order = new Order(this.totalPrice, this.totalQuantity);
    let cartitems = this.cartservice.cartItems;

    let orderItems: OrderItems[] = cartitems.map(item =>
      new OrderItems(
        item.id!,
        item.name!,
        item.imageUrl!,
        item.quantity!,
        item.unitPrice!
      )
    );
    let purchase = new PurchaseRequest();
    purchase.customer = this.checkoutform.controls['customer'].value;
    purchase.address = this.checkoutform.controls['address'].value;
    purchase.order = order;
    purchase.orderItems = orderItems

    this.checkoutservice.createOrder(purchase).subscribe((response) => {
      let responseData = response.data;
      // console.log(responseData);
      // console.log("Order ID:", responseData.razorpayorderId);
      this.paymentservice.processPayment(responseData.razorpayorderId,
        this.totalPrice * 100,
        this.getName?.value,
        this.getEmail?.value,
        this.getphone?.value,
        this.onPaymentSuccess.bind(this));
    })

  }
  onPaymentSuccess(response: any) {
    console.log(response);
    alert("payment done...");
    let paymentCallback = new Paymentcallback(
      response.razorpay_order_id,
      response.razorpay_payment_id,
      response.razorpay_signature
    );
    this.checkoutservice.confirmOrder(paymentCallback).subscribe((data: any) => {

      try {
        this.result = data.data;
        this.razorpayorderIdValue = this.result.razorpayorderId;
        this.orderTrackingNumberValue = this.result.orderTrackingNumber;
        this.orderStatusValue = this.result.orderStatus;

        this.cd.detectChanges();
        this.isLoading = false;
        this.cd.detectChanges();
      } catch (error) {
        console.error(error);
        this.isLoading = false;
      }
    },


    );

  }

  closebtn = () => {
    this.orderStatusValue = '';
    this.router.navigate(['/orders']);
  }
  reviewCartDetails() {
    this.cartservice.totalPrice.subscribe((data) => {
      this.totalPrice = data;
      console.log(data);
    })

    this.cartservice.totalQuantity.subscribe((data) => {
      this.totalQuantity = data;
      console.log(data);
    })
    this.cartservice.computeTotals();
  }
}
