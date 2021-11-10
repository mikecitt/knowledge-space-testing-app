import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { UserLogin } from 'src/app/core/models';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  hide = true;

  constructor(private service: AuthService) { }

  ngOnInit(): void {
  }

  onSubmit(): void {
    var loginForm: UserLogin = {
      username: this.email.value,
      password: this.password.value
    };
    this.service.login(loginForm).subscribe(
      data => {
        this.service.getRouter().navigate(['/'])
      },
      error => {

      }  
    );
  }

  getEmailsErrorMessage(): string {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  getPasswordErrorMessage(): string {
    return 'You must enter a value';
  }

  isFormValid(): boolean {
    return this.email.valid && this.password.valid;
  }
}
