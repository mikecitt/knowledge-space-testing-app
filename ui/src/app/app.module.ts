import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProfessorsModule } from './modules/professors/professors.module';
import { StudentsModule } from './modules/students/students.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './shared/material.module';
import { CoreModule } from './core/core.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthModule } from './modules/auth/auth.module';
import { TokenInterceptor } from './core/interceptor/token.interceptor';
import { MiscModule } from './modules/misc/misc.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ProfessorsModule,
    MaterialModule,
    StudentsModule,
    BrowserAnimationsModule,
    CoreModule,
    HttpClientModule,
    AuthModule,
    MiscModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
