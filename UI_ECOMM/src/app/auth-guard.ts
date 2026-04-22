import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

export const authGuard: CanActivateFn = (route, state) => {
   const router=inject(Router);
  const token=localStorage.getItem('token');



  if (!token) {
     router.navigate(['/login'])
  return false;
  }
  try {
       
  const decode:any=jwtDecode(token);
  const isExpired=decode.exp*1000<Date.now();
    
    if (isExpired) {
      
      localStorage.clear();
      router.navigate(['/login'])
      return false;
    }
  } catch (error) {
      localStorage.clear();
      router.navigate(['/login'])
      return false;
  }
  return true;
};
