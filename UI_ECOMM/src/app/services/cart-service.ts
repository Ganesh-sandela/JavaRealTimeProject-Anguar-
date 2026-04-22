import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { AddCart } from '../dto/add-cart';

@Injectable({
  providedIn: 'root',
})
export class CartService {
   cartItems:AddCart[]=[]
  totalPrice:Subject<number>= new Subject<number>();
  totalQuantity:Subject<number>=new Subject<number>();

     
    orderStatus = new BehaviorSubject<string>('');
  razorpayorderId = new BehaviorSubject<string>('');
  orderTrackingNumber = new BehaviorSubject<string>('');
  
  addToCart(thecartItem:AddCart){
    let alreadyCartExistsInCart:boolean=false;
    let ExistsInCartItem!:AddCart;
        if (this.cartItems.length>0) {
      
      for(let cart of this.cartItems){

        if (cart.id===thecartItem.id) {
          alreadyCartExistsInCart=true;
          ExistsInCartItem=cart;
          break;
        }
        
      }
    }
    if (alreadyCartExistsInCart) {
      ExistsInCartItem.quantity++;
    }
    else{
      this.cartItems.push(thecartItem);
    }
 this.computeTotals();
    
  }

  computeTotals(){
    let totalPrice:number=0;
    let totalQuantity:number=0;
    for(let c of this.cartItems){
      totalPrice+=c.unitPrice*c.quantity;
      totalQuantity+=c.quantity;
    }
    this.totalPrice.next(totalPrice);
    this.totalQuantity.next(totalQuantity);
  }


    decrement(item:AddCart){
    item.quantity--;
 if (item.quantity==0) {
  this.remove(item);
 } else {
  this.computeTotals();
 }
  }
  increment(item:AddCart){
// this.addToCart(item);
item.quantity++;
this.computeTotals();
  }
  remove(item:AddCart){
   const itemindex=this.cartItems.findIndex(data=>data.id==item.id);
   if (itemindex >-1) {
    this.cartItems.splice(itemindex,1);
    this.computeTotals();
   }
  }

}
