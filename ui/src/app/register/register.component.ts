import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { CurrentUserService, AuthenticationService } from '../services';
import { isBuffer } from 'util';
import { IUser } from '../services/User';
import { UserProfile } from '../services/Profile';
import { RegisterForm } from '../services/registerForm';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
  })
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    error: string;
    regUser: IUser;
    regProfile: UserProfile;
    tForm: RegisterForm;


    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: CurrentUserService
    ) {
        this.regUser = {
            user_id: 0,
            username: '',
            password: '',
            email: '',
            firstName: '',
            lastName: '',
            occupation: '',
            birthdate: '',
            hobbies: ''
        };
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) {
            this.router.navigate(['/']);
        }
    }

    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            firstName: ['', Validators.required],
            lastName: ['', Validators.required],
            email: ['', Validators.required],
            username: ['', Validators.required],
            password: ['', [Validators.required, Validators.minLength(6)]]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }
        this.tForm = this.registerForm.value;
        console.log(this.tForm);
        this.regUser.username = this.tForm.username;
        this.regUser.password = this.tForm.password;
        this.regUser.firstName = this.tForm.firstName;
        this.regUser.lastName = this.tForm.lastName;
        this.regUser.email = this.tForm.email;
        console.log(this.regUser);

        this.loading = true;
        this.userService.register(  this.regUser.username,
                                    this.regUser.password,
                                    this.regUser.firstName,
                                    this.regUser.lastName,
                                    this.regUser.email)
            .pipe(first())
            .subscribe(
                data => {
                    this.router.navigate(['/login'], { queryParams: { registered: true }});
                },
                error => {
                    // this.error = error;
                    this.loading = false;
                });
    }
}
