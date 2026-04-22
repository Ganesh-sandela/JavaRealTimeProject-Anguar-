import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { Customerservice } from '../../services/customerservice';
import { CommonModule } from '@angular/common';
import { map, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-yourorders',
  imports: [CommonModule],
  standalone: true,
  changeDetection:ChangeDetectionStrategy.OnPush,
  templateUrl: './yourorders.html',
  styleUrl: './yourorders.css',
})
export class Yourorders implements OnInit {

order$!: Observable<any[]>;
  customerservice = inject(Customerservice);


  route=inject(Router)
ngOnInit(): void {
  const email = this.customerservice.emailStore;

  this.customerservice.loadOrders(email);

  this.order$ = this.customerservice.orders$.pipe(
    map(orders =>
      orders!.map(data => ({
        image: data.imageUrl,
        name: data.name,
        price: data?.unitPrice ?? data?.order?.totalPrice ?? 0
      }))
    )
  );
}
onClick(){
 this.route.navigateByUrl('/products')
}
}