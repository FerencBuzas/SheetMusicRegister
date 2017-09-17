import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { RouterModule }   from '@angular/router';

import { AppComponent }        from './app.component';
import { DashboardComponent }  from './dashboard.component';
import { BookDetailComponent } from './book/book-detail.component';
import { BooksComponent }     from './book/books.component';
import { BookService }         from './book/book.service';
import { ComposerDetailComponent } from './composer/composer-detail.component';
import { ComposersComponent }     from './composer/composers.component';
import { ComposerService }         from './composer/composer.service';
import { PublisherDetailComponent } from './publisher/publisher-detail.component';
import { PublishersComponent }     from './publisher/publishers.component';
import { PublisherService }         from './publisher/publisher.service';

import { AppRoutingModule }     from './app-routing.module';

@NgModule({
  imports:      [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    BookDetailComponent,
    BooksComponent,
    ComposerDetailComponent,
    ComposersComponent,
    PublisherDetailComponent,
    PublishersComponent
  ],
  providers: [ BookService, ComposerService, PublisherService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { 
}