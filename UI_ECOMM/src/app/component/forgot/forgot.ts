import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { email, FormField } from "@angular/forms/signals";
import { Router, RouterLink } from "@angular/router";
import { Customerservice } from '../../services/customerservice';

@Component({
  selector: 'app-forgot',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, FormField, RouterLink],
  standalone: true,
  templateUrl: './forgot.html',
  styleUrl: './forgot.css',
})
export class Forgot implements OnInit {

  forgotform!: FormGroup;
  isLoading: boolean = false;
  constructor(private fb: FormBuilder, private route: Router, private customerservice: Customerservice) { }



  ngOnInit(): void {
    this.forgotform = this.fb.group({
      email: ["", [Validators.required, Validators.email]]
    })
  }

  submit() {
    if (this.forgotform.valid) {
      const email = this.forgotform.value.email;
      this.route.navigate([],
        {
          queryParams: { email: email },
          queryParamsHandling: 'merge'
        }
      );
      this.isLoading = true;
      this.customerservice.forgot(email).subscribe({
        next: (res) => {
          console.log(res);
          this.isLoading = false;
          let value=this.forgotform.get('email')?.setValue('');
        },
        error: (err) => {
          console.log(err);
          this.isLoading = false;
        }
      });

    }


  }
}
