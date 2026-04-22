import { CommonModule } from '@angular/common';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Customerservice } from '../../services/customerservice';
import { ResetPwd } from '../../dto/reset-pwd';
import { Router } from '@angular/router';
import { email } from '@angular/forms/signals';
import { HttpClient } from '@angular/common/http';
import { combineLatest, startWith } from 'rxjs';

@Component({
  selector: 'app-reset',
  imports: [ ReactiveFormsModule,CommonModule,FormsModule],
  standalone:true,
  templateUrl: './reset.html',
  styleUrl: './reset.css',
})
export class Reset  implements OnInit{

email:string='';


  resetform!:FormGroup;
  matchesPwd:boolean=false
  isLoading:boolean=false;
 
  mismatchespwd:boolean=false;
  constructor(private fb:FormBuilder,
    private http:HttpClient,
    private customerservice:Customerservice,private route:Router){}

 
  ngOnInit(): void {
     this.email=this.customerservice.emailStore;
    this.resetform=this.fb.group({
        oldPwd:['',[Validators.required]],
        newPwd:['',[Validators.required,]],
        conformPwd: [
        '',
        [
          Validators.required,
        
          Validators.minLength(8),
          Validators.pattern(
            '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$'
          ),
        ],
      ],
      email:[this.email,[Validators.required,Validators.email]]
    },
     {
        validators:this.passwordMatchValidator
      }
  )

 combineLatest([
   this.resetform.controls['newPwd'].valueChanges.pipe(startWith('')),
  this.resetform.controls['conformPwd'].valueChanges.pipe(startWith(''))
 ]).subscribe(([newPwd,conformPwd])=>{

  this.mismatchespwd=newPwd&&conformPwd&&newPwd!==conformPwd;

 })
  
  }
submit() {
  this.isLoading = true;

  if (this.resetform.valid) {
    let resetpwd = this.resetform.value;

    this.http.post('http://localhost:9090/resetpwd', resetpwd)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.isLoading = false;
          this.route.navigateByUrl('/login');
        },
        error: (err) => {
          console.error(err);
          this.isLoading = false;
        }
      });
  }
}

    passwordMatchValidator=(form: FormGroup)=>{
  const newPwd = form.get('newPwd')?.value;
  const confirmPwd = form.get('conformPwd')?.value;

  if (newPwd !== confirmPwd) {
    return { passwordMismatch:true };
  }
  return null;
}
}
