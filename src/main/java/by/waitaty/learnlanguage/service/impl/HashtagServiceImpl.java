package by.waitaty.learnlanguage.service.impl;

import by.waitaty.learnlanguage.entity.Hashtag;
import by.waitaty.learnlanguage.repository.HashtagRepository;
import by.waitaty.learnlanguage.service.HashtagService;
import org.springframework.stereotype.Service;

@Service
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;

    public HashtagServiceImpl(HashtagRepository hashtagRepository) {
        this.hashtagRepository = hashtagRepository;
    }

    @Override
    public Hashtag saveHashtag(Hashtag hashtag) {
        return hashtagRepository.save(hashtag);
    }

    @Override
    public void deleteHashtag(Long hashtagId) {
        hashtagRepository.deleteById(hashtagId);
    }
}
