import { Component, OnInit } from '@angular/core';
import { CurrentUserService } from '../services/current-user.service';
import { IUser } from '../services/User';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators} from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../services';
import { UserProfile } from '../services/Profile';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private user: IUser;
  email = '';
  password = '';
  username = '';
  user_id: number;
  userProfile: UserProfile;
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;
  error: string;
  success: string;

  constructor(
      private login: CurrentUserService,
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService
      ) {
            if (this.authenticationService.currentUserValue) {
                this.router.navigate(['/']);
            }
            if (this.route.snapshot.queryParams.registered) {
                this.success = 'Registration successful';
            }
       }

  ngOnInit() {
      this.loginForm = this.formBuilder.group({
          username: ['', Validators.required],
          password: ['', Validators.required]
      });

      this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }
  get f() { return this.loginForm.controls; }

  onSubmit() {
      this.submitted = true;

      this.error = null;
      this.success = null;

      if (this.loginForm.invalid) {
          return;
      }
      this.loading = true;
      this.authenticationService.login(this.f.username.value, this.f.password.value)
        .pipe(first())
        .subscribe(
            data => {
                this.router.navigate([this.returnUrl]);
            },
            error => {
                // this.error = error;
                this.loading = false;
            }
        );
  }

  getUser() {
    this.user = {
      password: this.password,
      username: this.username,
      user_id: this.user_id,
      email: '',
      firstName: '',
      lastName: '',
      occupation: '',
      birthdate: '',
      hobbies: ''
    };

  }

}
