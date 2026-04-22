import { HttpHandler, HttpInterceptorFn, HttpRequest } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {

  if (req.url.includes('/login')) {
    return next(req);
  }

  console.log(req.url);
  const token = localStorage.getItem("token");
  if (token) {
    const newreq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    })
    return next(newreq);
  } else {
    return next(req);
  }
};
