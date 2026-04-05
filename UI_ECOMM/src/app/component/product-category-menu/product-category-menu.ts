import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProductCategory } from '../../dto/product-category';
import { ProductService } from '../../services/product-service';
import { RouterLink, RouterLinkActive } from "@angular/router";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-product-category-menu',
  imports: [RouterLink, CommonModule, RouterLinkActive],
  templateUrl: './product-category-menu.html',
  styleUrl: './product-category-menu.css',
})
export class ProductCategoryMenu implements OnInit {

  category:ProductCategory[]=[];

  constructor(private productservice:ProductService,private cd:ChangeDetectorRef){}

ngOnInit(): void {
  this.getallcategeories();
}
getallcategeories(){
this.productservice.getCategories().subscribe((data)=>{
  this.category=data.data;
  console.log("hiiiiiiiiiiiii",this.category)
 this.cd.detectChanges();
})
}
}
