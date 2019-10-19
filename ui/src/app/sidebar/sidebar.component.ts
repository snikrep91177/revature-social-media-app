import { Component, OnInit, OnDestroy } from '@angular/core';
import { IUser } from '../services/User';
import { CurrentUserService } from '../services/current-user.service';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../services';
import { first } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
    selector: 'app-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit, OnDestroy {
    subscription: Subscription;
    currentUser: IUser;
    userList: IUser[];
    constructor(private route: Router, private userService: CurrentUserService, private authenticationService: AuthenticationService) {
        this.subscription = this.authenticationService.currentUser.subscribe(user => {
            if (user) {
                this.currentUser = user;
            } else {
                this.currentUser = null;
            }
            // console.log('CurrentUser in sidebar ' + this.currentUser);
        });
    }

    ngOnInit() {
        if (this.currentUser) {
            const temp = this.authenticationService.getusers().pipe(first()).subscribe(userList => {
                if (userList) {
                    this.userList = userList;
                } else {
                    this.userList = null;
                }
            });
        }

    }
    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    }

}


// $(document).ready(function () {

//     $("#sidebar").mCustomScrollbar({
//          theme: "minimal"
//     });

//     $('#sidebarCollapse').on('click', function () {
//         $('#sidebar').toggleClass('active');
//     });

// });

// $(document).ready(function () {

//     $("#sidebar").mCustomScrollbar({
//          theme: "minimal"
//     });

//     $('#sidebarCollapse').on('click', function () {
//         // open or close navbar
//         $('#sidebar').toggleClass('active');
//         // close dropdowns
//         $('.collapse.in').toggleClass('in');
//         // and also adjust aria-expanded attributes we use for the open/closed arrows
//         // in our CSS
//         $('a[aria-expanded=true]').attr('aria-expanded', 'false');
//     });

// });
