import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { Product } from '../../dto/product';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { CartService } from '../../services/cart-service';
import { AddCart } from '../../dto/add-cart';

@Component({
  selector: 'app-product-list',
  imports: [CommonModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
  standalone: true
})
export class ProductList implements OnInit {

 
  products: Product[] = [];
  isloading: boolean = true;
   categoryid:any;
   searchkeyword:any;
   searchmode:boolean=false
  constructor(
    private productservice: ProductService,
    private cd: ChangeDetectorRef,
    private route: ActivatedRoute,
    private cartservice:CartService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
this.listProducts();
    });
  }

  listProducts(){
    this.searchmode=this.route.snapshot.paramMap.has("keyword");
    if (this.searchmode) {
      this.handleSearchProducts();
    }
    else{
      this.getproducts();
    }
  }

  handleSearchProducts(){
this.searchkeyword=this.route.snapshot.paramMap.get("keyword");
this.productservice.getByNameProducts(this.searchkeyword).subscribe((data)=>{
  this.products=data.data;
})
  }

  getproducts() {
       const hascategoryid:boolean=this.route.snapshot.paramMap.has("id");
   if (hascategoryid) {
    this.categoryid=+this.route.snapshot.paramMap.get("id")!;
    console.log(this.categoryid);
   }
   else{
this.categoryid=1;
   }

    this.isloading = true;
    this.productservice
      .getproductsCategoryId(this.categoryid)
      .subscribe((data: any) => {

        // 🔥 SAFE FIX (handles all API formats)
        this.products =
          data?.data?.[0]?.products ??
          data?.data ??
          data;

        this.isloading = false;

        this.cd.detectChanges();
      });
  }

  addingToCart(theproduct:Product){
   const cartitem=new AddCart(theproduct);
   this.cartservice.addToCart(cartitem);
  }
}