import { Component } from '@angular/core';
import { Subject } from 'rxjs';
import { LoaderService } from '../loader.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-loader',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './loader.component.html',
  styleUrl: './loader.component.scss'
})
export class LoaderComponent {
  isLoading: Subject<boolean> | undefined;
  //isShowing: boolean = false;
  
  //We can also subscribe() and unsubscribe() to above isLoading subject
  //to avoid memory leaks, but using "async" pipe in html is more cleaner way

  constructor(private loaderService: LoaderService) { }

  ngOnInit(): void {
    this.isLoading = this.loaderService.isLoading;

    // this.airlineService.isLoading.subscribe(res => {
    //   console.log("Value of isLoading ", res);
    //   this.isShowing = res;
    // });
  }

  ngOnDestroy(): void {
    //this.isShowing = false;
  }
}
