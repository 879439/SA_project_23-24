import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router'; // Import RouterModule instead of RouterOutlet
import { LoaderService } from './loader.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule], // Use RouterModule instead of RouterOutlet
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'] // Use 'styles' instead of 'styleUrl'
})
export class AppComponent {
  constructor(public loaderService: LoaderService) {}
  title = 'frontend';
}
