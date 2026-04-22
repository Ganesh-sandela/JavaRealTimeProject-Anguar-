import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AddCart } from '../../dto/add-cart';
import { Subject } from 'rxjs';
import { CartService } from '../../services/cart-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-cart-status',
  imports: [RouterLink],
  templateUrl: './cart-status.html',
  styleUrl: './cart-status.css',
})
export class CartStatus implements OnInit {

  totalPrice:number=0;
  totalQuantity:number=0;
  constructor(private cartservice:CartService,private cd:ChangeDetectorRef){}

updateCartStatus(){
  this.cartservice.totalPrice.subscribe((data)=>{
    this.totalPrice=data;
  })
  this.cartservice.totalQuantity.subscribe((data)=>{
    this.totalQuantity=data
    this.cd.detectChanges();
  })
}

ngOnInit(): void {
  this.updateCartStatus();
} 

}
