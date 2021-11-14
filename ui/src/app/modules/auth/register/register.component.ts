import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { StudentForm } from 'src/app/core/models';
import { AuthService } from 'src/app/core/service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  dateOfBirth = new FormControl({ value: '', disabled: true }, [Validators.required])
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  hide = true;
  minDate: Date;
  maxDate: Date;

  constructor(private service: AuthService) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear - 120, 0, 1);
    this.maxDate = new Date();
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    var registrationForm: StudentForm = {
      firstName: this.firstName.value,
      lastName: this.lastName.value,
      dateOfBirth: this.dateOfBirth.value,
      email: this.email.value,
      password: this.password.value
    };
    this.service.register(registrationForm).subscribe(
      res => {
        this.service.getRouter().navigate(['/auth/login']);
      },
      error => {
        console.log("error register");
      })
  }

  sendToLogin(): void {
    this.service.getRouter().navigate(['/auth/login']);
  }


  getEmailsErrorMessage(): string {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }

    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  getErrorMessage(): string {
    return 'You must enter a value';
  }

  isFormValid(): boolean {
    return this.email.valid && this.password.valid;
  }
}
