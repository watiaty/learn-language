import { Component, OnInit, ViewChild } from '@angular/core';
import { Word } from "../word";
import { WordService } from "../_services/word-service.service";
import { map, Observable, startWith } from "rxjs";
import { FormControl } from "@angular/forms";
import { MatSelectChange } from "@angular/material/select";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";

@Component({
  selector: 'app-user-list',
  templateUrl: './word-list.component.html',
  styleUrls: ['./word-list.component.css']
})
export class WordListComponent implements OnInit {
  searchText = new FormControl();
  words: Word[] = [];
  myWords: Word[] = [];
  selectedLang: any;
  visible: String = "words";
  filteredWords: Observable<Word[]>;
  dataSource!: MatTableDataSource<any>;

  constructor(private wordService: WordService) {
    this.filteredWords = this.searchText.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
    this.selectedLang = "EN";
  }

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  private _filter(value: string): Word[] {
    const filterValue = value.toLowerCase();
    return this.words.filter(word => word.word.toLowerCase().startsWith(filterValue)).slice(0, 5);
  }

  ngOnInit() {
    this.wordService.findAll().subscribe(response => {
      this.words = response;
      this.dataSource = new MatTableDataSource(this.words);
      this.dataSource.paginator = this.paginator;
      this.loadWords();
    });
  }

  loadWords() {
    this.filteredWords = this.searchText.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value))
    );
  }

  toggleWords() {
    this.dataSource = new MatTableDataSource(this.words);
    this.dataSource.paginator = this.paginator;
    this.visible = "words";
  }

  toggleMyWords() {
    this.wordService.findUserWords().subscribe({
      next: response => {
        this.myWords = response;
        this.dataSource = new MatTableDataSource(this.myWords);
        this.dataSource.paginator = this.paginator;
      }
    });
    this.visible = "mywords";
  }

  changeLanguage($event: MatSelectChange) {
    console.log($event.value);
    this.selectedLang = $event.value;
  }
}
