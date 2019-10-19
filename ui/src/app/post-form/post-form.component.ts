import { Component, OnInit } from '@angular/core';
import { IPost } from '../services/Post';
import { PostService } from '../services/post.service';
import { Subscription } from 'rxjs';
import { IUser } from '../services/User';
import { AuthenticationService } from '../services';
import { Router } from '@angular/router';

@Component({
    selector: 'app-post-form',
    templateUrl: './post-form.component.html',
    styleUrls: ['./post-form.component.css']
})
export class PostFormComponent implements OnInit {
    postText: string;
    post: IPost;
    subscription: Subscription;
    currentUser: IUser;
    constructor(private postsService: PostService, private userService: AuthenticationService,
                private router: Router) {
        this.subscription = this.userService.currentUser.subscribe(user => {
            if (user) {
                this.currentUser = user;
            } else {
                this.currentUser = null;
            }
        });
    }

    ngOnInit() {
    }

    setPost() {
        this.post = {
            postId: 0,
            user_id: this.currentUser.user_id,
            post: this.postText,
            rating: []
        };

        console.log(this.post);

        this.postsService.addPost(this.post);
        this.router.navigate(['/feed']);
    }
}
