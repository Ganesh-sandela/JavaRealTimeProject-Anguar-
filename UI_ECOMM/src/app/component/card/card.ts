import { Component, Input, OnChanges, OnInit } from '@angular/core';
import { Checkout } from '../checkout/checkout';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-card',
  imports: [CommonModule],
  standalone:true,
  templateUrl: './card.html',
  styleUrl: './card.css',
})
export class Card implements OnChanges {

  @Input()orderTrackingNumber:string='';
  @Input() razorpayorderId:string='';
  @Input() orderStatus:string='';


  
ngOnChanges(): void {
  console.log("first",this.orderTrackingNumber);
  console.log("second",this.razorpayorderId);
  console.log("third",this.orderStatus);
}




}
