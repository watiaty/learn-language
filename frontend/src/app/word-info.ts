import {Hashtag} from "./hashtag";
import {Translation} from "./translation";

export class WordInfo {
  id: String;
  word: String;
  transcription: String;
  language: String;
  translations: Translation[];
  hashtags: Hashtag[];

  constructor(id: String, word: String, transcription: String, language: String, translations: Translation[], hashtags: Hashtag[]) {
    this.id = id;
    this.word = word;
    this.transcription = transcription;
    this.language = language;
    this.translations = translations;
    this.hashtags = hashtags;
  }
}
