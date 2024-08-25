import { Router, NavigationEnd } from '@angular/router';


export class AppUtils {

    static scrollToTop(router: Router) {
        router.events.subscribe((evt) => {
            if (evt instanceof NavigationEnd) {
                console.log('scroll to top');
                console.log(evt);
                window.scrollTo(0, 0);
                let elmnt = document.getElementById("content-view-area");
                elmnt.scrollIntoView(); 
            }
        })


    }


}