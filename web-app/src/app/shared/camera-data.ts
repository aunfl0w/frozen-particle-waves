import { BehaviorSubject, Subject } from 'rxjs';
import { PictureUpdateMessage, CameraInfo } from '../models/index';

export class CameraData {
  
    private pictureUpdateMessageList: PictureUpdateMessage[] = [];
    private urlHistory :string[] = [];
    private urlSubject$ = new BehaviorSubject<string>(`api/camera/${this.cameraInfo.id}/image`);

    constructor(private cameraInfo: CameraInfo){

    }

    tryUpdate(message: PictureUpdateMessage){
        if (message.updateCamera === this.cameraInfo.id){
            this.pictureUpdateMessageList.push(message);
            const nextUrl = `api/camera/${this.cameraInfo.id}/image/${message.imageID}`;
            this.urlHistory.unshift(nextUrl);
            if (this.urlHistory.length > 500)
                this.urlHistory.pop();

            this.urlSubject$.next(nextUrl);
        }
    }

    addURLHistory(timestamp: string){ 
        const nextUrl = `api/camera/${this.cameraInfo.id}/image/${timestamp}`;
        this.urlHistory.unshift(nextUrl);
    }

    getURLHistory(): string[] {
        return this.urlHistory;
    }
    

    getURL() : Subject<string> {
        return this.urlSubject$;
    }

    getId() : string {
        return this.cameraInfo.id;
    }

    getDescription(): string {
        return this.cameraInfo.description;
    }
    
}