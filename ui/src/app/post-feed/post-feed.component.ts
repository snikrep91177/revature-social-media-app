import { Component, OnInit, OnDestroy, DoCheck } from '@angular/core';
import { PostService } from '../services/post.service';
import { IPost } from '../services/Post';
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';


@Component({
  selector: 'app-post-feed',
  templateUrl: './post-feed.component.html',
  styleUrls: ['./post-feed.component.css'],

})
export class PostFeedComponent implements OnInit, OnDestroy {
  postSub: Subscription;
  posts: IPost[];
  constructor(private postService: PostService, private router: Router) {

  }
  ngOnInit() {
    console.log('feed init called');
    this.postSub = this.postService.posts.subscribe(posts => {
        this.posts = posts;
    });
  }
  refresh() {
    this.postSub = this.postService.posts.subscribe(posts => {
        this.posts = posts;
    });
    console.log('refresh');
    this.router.navigate(['/feed']);
  }

  ngOnDestroy() {
    this.postSub.unsubscribe();
  }

}
