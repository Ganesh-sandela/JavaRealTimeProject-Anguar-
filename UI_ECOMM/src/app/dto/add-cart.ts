import { Product } from "./product";

export class AddCart {
     public id:number;
  public name:string;
  public imageUrl:string;
  public quantity:number;
  public unitPrice:number;

   constructor(product:Product){
    this.id=product.id!;
    this.name=product.name!;
    this.imageUrl=product.imageUrl!;
    this.unitPrice=product.unitPrice!;
    this.quantity=1;
   }

}
