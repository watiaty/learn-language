export class Translation {
  id: String;
  translation: String;
  user: boolean;

  constructor(id: String, translation: String, isUser: boolean) {
    this.id = id;
    this.translation = translation;
    this.user = isUser;
  }
}
