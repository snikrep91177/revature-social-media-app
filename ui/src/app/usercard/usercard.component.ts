import { Component, OnInit, OnDestroy } from '@angular/core';
import { IUser } from '../services/User';
import { Subscription } from 'rxjs';
import { AuthenticationService } from '../services';

@Component({
  selector: 'app-usercard',
  templateUrl: './usercard.component.html',
  styleUrls: ['./usercard.component.css']
})
export class UsercardComponent implements OnInit, OnDestroy {
  subscription: Subscription;
  currentUser: IUser;
  userImage: string;
  constructor(private userService: AuthenticationService) {
    this.subscription = this.userService.currentUser.subscribe(user => {
      if (user) {
        this.currentUser = user;
        this.userImage = 'https://images.pexels.com/photos/2461991/pexels-photo-2461991.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500';
      } else {
        this.currentUser = null;
      }
    });
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    // unsubscribe to ensure no memory leaks
    this.subscription.unsubscribe();
  }
}
