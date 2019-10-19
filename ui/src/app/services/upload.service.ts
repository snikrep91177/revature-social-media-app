import { Injectable } from '@angular/core';
import * as AWS from 'aws-sdk';
import * as S3 from 'aws-sdk/clients/s3';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AuthenticationService } from '.';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  currentUser: any;

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {

  }

  uploadFile(file) {
        this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
        const contentType = file.type;
        const bucket = new S3(
          {
              accessKeyId: 'YOUR-ACCESS-KEY-ID',
              secretAccessKey: 'YOUR-SECRET-ACCESS-KEY',
              region: 'YOUR-REGION'
          }
      );
        const params = {
          Bucket: 'group2019',
          Key: file.name,
          Body: file,
          ACL: 'public-read',
          ContentType: contentType
      };
        bucket.upload(params, (err, data) => {
          if (err) {
              console.log('There was an error uploading your file: ', err);
              return false;
          }
          console.log('Successfully uploaded file.', data);
          const payload = new HttpParams().set('imageLink', data).set('username', this.currentUser.username);
          this.http.post<any>(`${environment.apiUrl}/user/addImage`, payload);
          return true;
      });

    }
}
