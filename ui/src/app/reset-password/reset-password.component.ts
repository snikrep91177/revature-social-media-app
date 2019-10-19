import { Component, OnInit } from '@angular/core';
import { IUser } from '../services/User';
import { CurrentUserService } from '../services/current-user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { UserProfile } from '../services/Profile';

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
    email = '';
    user_id: number;
    userProfile: UserProfile;
    passwordForm: FormGroup;

    constructor(private resetpass: CurrentUserService,
                private formBuilder: FormBuilder,
                private http: HttpClient) { }

    ngOnInit() {
        this.passwordForm = this.formBuilder.group({
            email: ['', Validators.required]
        });
    }

    onSubmit() {

        // stop here if form is invalid
        if (this.passwordForm.invalid) {
            return;
        }

        this.resetpass.resetPw(this.email);

    }

}


