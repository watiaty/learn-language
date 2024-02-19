import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";
import {Word} from "../word";
import {environment} from "../../environments/environment";
import {WordInfo} from "../word-info";


const URLS = `${environment.wordUrl}`;

@Injectable({
  providedIn: 'root',
})
export class WordService {

  constructor(private http: HttpClient) {

  }

  public findUserWords(): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + 'my');
  }

  public findWordsForTraining(wordCount: String): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + 'train?count=' + wordCount);
  }

  public findAll(): Observable<Word[]> {
    return this.http.get<Word[]>(URLS + 'all');
  }

  public save(word: Word): Observable<Word> {
    return this.http.post<Word>(URLS + 'add', word);
  }

  public addTranslation(id: String): Observable<Word> {
    return this.http.post<Word>(URLS + 'translation/add', id);
  }

  public deleteTranslation(id: String): Observable<Word> {
    return this.http.post<Word>(URLS + 'translation/delete', id);
  }

  findWord(wordName: String) {
    return this.http.get<WordInfo>("http://localhost:8080/api/v1/word" + '?word=' + wordName);
  }

  addTranslationAndWord(id: String, translation: String) {
    return this.http.post<Word>(URLS + 'translation/new', {id, translation});
  }

    deleteTranslationFromWord(id: String) {
      return this.http.post<Word>(URLS + 'translation/fulldelete', id);
    }
}
