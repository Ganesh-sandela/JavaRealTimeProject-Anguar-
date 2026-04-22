import { Component } from '@angular/core';
import { ActivatedRoute, Router, RouteReuseStrategy } from '@angular/router';

@Component({
  selector: 'app-search-list',
  imports: [],
  templateUrl: './search-list.html',
  styleUrl: './search-list.css',
})
export class SearchList {
constructor(private router: Router,private route:ActivatedRoute) {}

doSearch(value: string) {
  this.router.navigate(['search', value],{
    relativeTo:this.route
  });
}
}
