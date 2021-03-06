import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Book } from './book';
import { BookService } from './book.service';

@Component({
  moduleId: module.id,
  selector: 'my-books',
  templateUrl: 'books.component.html',
  styleUrls: [ 'books.component.css' ]
})

export class BooksComponent implements OnInit {

  books = [];
  selectedBook: Book;
  constructor(private bookService: BookService,
              private router: Router ) {}

  ngOnInit(): void {    // The constructor must be short and fast
    this.bookService.getBooks().then(h => this.books = h);
  }

  onSelect(book: Book): void {
    this.selectedBook = book;
    this.router.navigate(['/bookDetail', this.selectedBook.id]);
  }

  newBook(): void {
    console.log('newBook()');
    this.selectedBook = null;
    this.router.navigate(['/bookDetail', 0]);
  }
}
