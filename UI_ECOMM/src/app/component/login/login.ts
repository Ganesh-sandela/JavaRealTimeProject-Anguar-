import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { Customerservice } from '../../services/customerservice';
import { LoginClass } from '../../dto/loginClass';
@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule, RouterModule, ReactiveFormsModule],
  standalone: true,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {

  loginform!: FormGroup;
  isLoading:boolean=false;

  constructor(private fb: FormBuilder,private customerservice:Customerservice,private route:Router) {}

  ngOnInit(): void {
    this.loginform = this.fb.group({
      email: ['', [Validators.required, Validators.email]],

      pwd: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(
            '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$'
          ),
        ],
      ],
    }
);
  }

  onsubmit() {
    this.isLoading=true
    if (this.loginform.valid) {
      let email=this.loginform.get('email')?.value;
      let oldPwd=this.loginform.get('pwd')?.value;
      let login=new  LoginClass(email,oldPwd);
      this.customerservice.login(login).subscribe({
        next:(data)=>{
          
        console.log(data);
        localStorage.setItem("token",data.data.token);
        this.isLoading=false;
        this.customerservice.emailStore=email;
        this.route.navigateByUrl('/products');
        },
        error:(err)=>{
          this.isLoading=false;
          console.log(err);
          console.log(err.error.msg);
          this.loginform.setErrors({
            invalidcreditionals:true
          })}
        })
      }

    else {
      console.log("Form Invalid");
      this.isLoading=false
      alert("Form Invalid");
    }
  }
}