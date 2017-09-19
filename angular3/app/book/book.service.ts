'use strict';

import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

import { MusicConfig } from '../util/music-config';
import { MusicLogger } from '../util/music-logger';
import { Book } from './book';
import { BOOKS } from './mock-books';

@Injectable()
export class BookService {

    constructor(private http: Http, private logger: MusicLogger) {
        this.logger.info("BookService constructor http: " + http + " logger: " + logger);
    }

    // From: http://www.concretepage.com/angular-2/angular-2-http-get-example
    // Also good: https://medium.com/google-developer-experts/angular-2-introduction-to-new-http-module-1278499db2a0
    //
    getBooks(): Promise<Book[]> {
        var url = MusicConfig.URL_BASE + '/book';
        this.logger.info("getBooks() url=" + url);
        return this.http.get(url).toPromise() // Observable<Response> --> Promise<Response>
    	   .then(this.extractData)            // --> Promise<Book[]>  (as map() with Obs.)
    	   .catch(this.handleErrorPromise);
    }

    private extractData(res: Response) {
        // this.logger... NO!
	    let body = res.json();
        return body;
    }

    private handleErrorPromise(error: Response | any) {
        this.logger.error("handleErrorPromise() ## " +(error.message || error));
    	return Promise.reject(error.message || error);
    }

    getBook(id: number): Promise<Book> {
      return this.getBooks()
        .then(books => books.find(book => book.id === id));
    }
}
