import { Component, Inject } from '@angular/core';
import { ApiService } from '../shared/api.service';
import { LoginModel } from '../models/login.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginModel: LoginModel = new LoginModel();
  status = '';

  constructor(private apiService: ApiService,
              private router: Router) { }

  login() {
    this.apiService.login(this.loginModel).subscribe(
      (data: any) => {
        this.status = 'Success';
        this.router.navigate(['fpw-app', 'all']);
      }, (err: any) => {
        this.status = '' + err.status + ' ' + err.statusText;
        console.log(err);
        this.loginModel = new LoginModel();
      }, () => {
        console.log('login is done');
      });

  }
}
