import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoaderInterceptor } from "./loader.interceptor"

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideClientHydration(), provideHttpClient(withFetch()), provideAnimations(), { provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptor, multi: true }]
};
