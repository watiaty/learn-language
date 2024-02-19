package by.waitaty.learnlanguage.service;

import by.waitaty.learnlanguage.entity.Hashtag;

public interface HashtagService {
    Hashtag saveHashtag(Hashtag hashtag);

    void deleteHashtag(Long hashtagId);
}
