import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { NavbarComponent } from './navbar/navbar.component';
import { LoginComponent } from './login/login.component';
import { CurrentUserService } from './services/current-user.service';

import { SidebarComponent } from './sidebar/sidebar.component';
import { PostFormComponent } from './post-form/post-form.component';
import { PostService } from './services/post.service';
import { UsercardComponent } from './usercard/usercard.component';

import { ResetPasswordComponent } from './reset-password/reset-password.component';

// import { fakeBackendProvider } from './_helpers';
import { AuthGuard } from './_helpers';
import { JwtInterceptor, ErrorInterceptor } from './_helpers';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ProfileComponent } from './profile/profile.component';
import { PostFeedComponent } from './post-feed/post-feed.component';
import { PostCardComponent } from './post-card/post-card.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';


@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    NavbarComponent,
    LoginComponent,
    ResetPasswordComponent,
    SidebarComponent,
    PostFormComponent,
    UsercardComponent,
    EditProfileComponent,
    ProfileComponent,
    PostFeedComponent,
    PostCardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    InfiniteScrollModule,
    FontAwesomeModule,
    RouterModule.forRoot([
      { path: 'register', component: RegisterComponent },
      { path: 'resetpassword', component: ResetPasswordComponent},
      { path: 'login', component: LoginComponent },
      { path: 'post', component: PostFormComponent, canActivate: [AuthGuard]},
      { path: 'editProfile', component: EditProfileComponent, canActivate: [AuthGuard]},
      { path: 'profile/:id', component: ProfileComponent, canActivate: [AuthGuard], runGuardsAndResolvers: 'paramsChange'},
      { path: 'feed', component: PostFeedComponent, canActivate: [AuthGuard] },
    ], { onSameUrlNavigation: 'reload' })
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  CurrentUserService, PostService, /*fakeBackendProvider*/],
  bootstrap: [AppComponent]
})
export class AppModule { }
