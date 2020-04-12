import { Component, OnInit } from '@angular/core';
import { ApiService } from 'src/app/shared/api.service';

@Component({
  selector: 'app-things',
  templateUrl: './things.component.html',
  styleUrls: ['./things.component.scss']
})
export class ThingsComponent implements OnInit {

  constructor(private apiService: ApiService) { }

  lightOn() {
    console.log("light command");

    this.apiService.thingsCommand("light", { commandName: "Dimmer", commandValue: "100" }).subscribe();
  }

  lightOff(){
    console.log("light command");
    this.apiService.thingsCommand("light", {commandName: "Power", commandValue: "OFF"}).subscribe();
  }

  lightDim(){
    console.log("light command");
    this.apiService.thingsCommand("light", {commandName: "Dimmer", commandValue: "50"}).subscribe();
  }



  ngOnInit() {
  }

}
