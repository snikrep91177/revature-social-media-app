import { Component, OnInit, Input } from '@angular/core';
import { faThumbsUp } from '@fortawesome/free-solid-svg-icons';
import { IPost } from '../services/Post';
import { AuthenticationService, CurrentUserService } from '../services';
import { IUser } from '../services/User';
import { Subscription, Observable, of } from 'rxjs';
import { UserProfile } from '../services/Profile';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../services/post.service';
@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent implements OnInit {
  @Input() post: IPost;
  faThumbsUp = faThumbsUp;
  thumbStyle = 'gray';
  isLiked: boolean;
  subscription: Subscription;
  logUser: IUser;
  currentUser: IUser;
  constructor(private postService: PostService, private userService: AuthenticationService, private fetchUserService: CurrentUserService) {
    this.currentUser = {
      user_id: 0,
      username: '',
      password: '',
      email: '',
      firstName: '',
      lastName: '',
      birthdate: '',
      occupation: '',
      hobbies: ''
    };
  }

  ngOnInit() {
    this.userService.currentUser.subscribe(cUser => {
      this.currentUser = cUser;
    });

    if (this.currentUser && this.post && this.post.rating && this.post.rating.includes(this.currentUser.user_id)) {
      this.isLiked = true;
      this.thumbStyle = 'white';
    }

  }

  liked() {
    if (!this.isLiked) {
      this.isLiked = true;
      this.thumbStyle = 'white';
      this.postService.likePost(this.post.postId);
    }

  }

}
