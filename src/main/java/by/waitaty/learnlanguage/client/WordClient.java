package by.waitaty.learnlanguage.client;

import by.waitaty.learnlanguage.entity.Word;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface WordClient {
    @GetExchange("/word/find")
    Word findOrCreate(String name, String lang, String transcription);

    @GetExchange("/api/v1/word/findbywordandlang")
    Word findByWordAndLang(@RequestParam("word") String name, @RequestParam("lang") String lang);

    @GetExchange("/findbyid")
    Word findById(Long id);
}
