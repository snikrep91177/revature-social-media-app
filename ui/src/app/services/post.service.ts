import { Injectable } from '@angular/core';
import { IPost } from './Post';
import { Observable, BehaviorSubject } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { IUser } from './User';
import { AuthenticationService } from '.';
import { Router } from '@angular/router';
import { delay } from 'rxjs/operators';
import { switchMap } from "rxjs/operators";

@Injectable({
    providedIn: 'root'
})
export class PostService {
    posts: Observable<IPost[]>;
    currentUser: IUser;

    constructor(private http: HttpClient, private userService: AuthenticationService, private route: Router) {
        this.posts = this.http.get<IPost[]>(`${environment.apiUrl}/post/getAllPosts`);
        userService.currentUser.subscribe(cUser => this.currentUser = cUser);
    }

    addPost(post: IPost) {
        const payload = new HttpParams()
            .set('postText', post.post)
            .set('user_id', this.currentUser.user_id.toString());

        this.http.post(`${environment.apiUrl}/post/newPost`, payload).subscribe(data => {
            this.posts = this.http.get<IPost[]>(`${environment.apiUrl}/post/getAllPosts`);
            delay(5000);
            this.route.navigate(['/feed']);
        });

    }

    getPosts(): Observable<IPost[]> {
        return this.posts;
    }

    likePost(postId: number) {
        const payload = new HttpParams()
            .set('postId', postId.toString())
            .set('user_id', this.currentUser.user_id.toString());
        this.http.post<IPost>(`${environment.apiUrl}/post/likePost`, payload).subscribe(post => {
            console.log('Post liked ' + post);
            
        });
    }

}
