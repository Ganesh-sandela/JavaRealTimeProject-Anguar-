import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product-service';
import { Product } from '../../dto/product';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

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

  constructor(
    private productservice: ProductService,
    private cd: ChangeDetectorRef,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((param) => {

      const id = param.get("id");

      console.log("Route ID:", id);

      

      this.getproducts(id);
    });
  }

  getproducts(id:any) {

    this.isloading = true;

    console.log("Calling API with:", id);

    this.productservice
      .getproductsCategoryId(id)
      .subscribe((data: any) => {

        console.log("RAW API:", data);

        // 🔥 SAFE FIX (handles all API formats)
        this.products =
          data?.data?.[0]?.products ??
          data?.data ??
          data;

        this.isloading = false;

        console.log("FINAL PRODUCTS:", this.products);

        this.cd.detectChanges();
      });
  }
}