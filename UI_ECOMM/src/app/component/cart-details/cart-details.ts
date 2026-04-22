import { Component, OnInit } from '@angular/core';
import { AddCart } from '../../dto/add-cart';
import { CartService } from '../../services/cart-service';
import { CommonModule } from '@angular/common';
import { CartStatus } from '../cart-status/cart-status';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-cart-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './cart-details.html',
  styleUrl: './cart-details.css',
})
export class CartDetails implements OnInit {

  cartitems: AddCart[] = [];
  totalPrice: number = 0;
  totalQuantity: number = 0;

  constructor(private cartservice: CartService) {}

  ngOnInit(): void {
    this.cartListDetails();   
  }

  decrement(item:AddCart){
    this.cartservice.decrement(item);
  }
  increment(item:AddCart){
   this.cartservice.increment(item);
  }
  remove(item:any){
    this.cartservice.remove(item);
  }
  cartListDetails() {

    this.cartitems = this.cartservice.cartItems;

    this.cartservice.totalPrice.subscribe((res) => {
      this.totalPrice = res;   
    });

    this.cartservice.totalQuantity.subscribe((data) => {
      this.totalQuantity = data;   
    });

    this.cartservice.computeTotals();
  }
}