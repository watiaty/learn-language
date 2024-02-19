import { Component } from '@angular/core';
import {WordService} from "../_services/word-service.service";
import {Word} from "../word";
import {style} from "@angular/animations";
import {max, min} from "rxjs";

@Component({
  selector: 'app-train',
  templateUrl: './train.component.html',
  styleUrl: './train.component.css'
})
export class TrainComponent {
  rangeValue: number;
  visible: boolean = true;
  words: Word[] = [];
  id: number = 0;
  inputValue: string = '';
  alertVisible: boolean = false;
  translateVisible: boolean = false;

  constructor(private wordService: WordService) {
    this.rangeValue = 10;
  }

  startTrain(wordCount: String) {
    this.visible = false;
    this.wordService.findWordsForTraining(wordCount).subscribe({
        next: response => {
          this.words = response;
        }
      }
    );
  }

  onInputKeyUp() {
    if (this.words[this.id].word === this.inputValue) {
      this.alertVisible = false;
      this.translateVisible = false;
      this.id++;
      this.inputValue = "";
    } else {
      this.alertVisible = true;
    }
  }

  protected readonly alert = alert;
  protected readonly style = style;
  protected readonly max = max;
  protected readonly min = min;
}
