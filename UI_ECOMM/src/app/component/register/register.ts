import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { Router, RouterLink } from "@angular/router";
import { Coustomer } from '../../dto/coustomer';
import { email } from '@angular/forms/signals';
import { Customerservice } from '../../services/customerservice';
import { Reset } from "../reset/reset";
import { finalize } from 'rxjs';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, FormsModule, CommonModule, RouterLink],
  templateUrl: './register.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styleUrl: './register.css',
})
export class Register implements OnInit {

  signup!: FormGroup
  isLoading: boolean = false;
  emaildata: string = '';
  constructor(private fb: FormBuilder,
    private customerservice: Customerservice,
    private route: Router
  ) { }

  ngOnInit(): void {
    this.signup = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      phno: ['', [Validators.required, Validators.pattern('^[6-9]\\d{9}$')]]
    });
  }


  submit = () => {
    this.isLoading = true
    
    if (this.signup.valid) {
      let formdata = this.signup.value;
      let customer = new Coustomer(formdata.name, formdata.email, Number(formdata.phno));
      console.log(customer)
      this.customerservice.register(customer).pipe(
        finalize(()=>{
          this.isLoading=false
        })
      ).subscribe({
        next: (data) => {
          this.customerservice.nameStore=formdata.name;
          this.customerservice.phnoStore=formdata.phno;
          this.customerservice.emailStore = formdata.email;
          this.isLoading=false;
          this.route.navigateByUrl('/resetPwd');
        },
        error: (err) => {
          this.isLoading = false;
          console.log(err);
          console.log(err.error.msg);

          const emailcontrol = this.signup.get('email');
          if (emailcontrol) {
            emailcontrol.setErrors({
              ...emailcontrol.errors, EmailNotUnique: true
            })
          }
        }
      })
    }
    else{
      if (this.signup.invalid) {
        this.isLoading=false;
        return;
      }
    }

  }
}
