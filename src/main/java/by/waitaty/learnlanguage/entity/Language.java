package by.waitaty.learnlanguage.entity;

import lombok.Getter;

@Getter
public enum Language {
    RU("RUSSIAN"),
    PL("POLISH"),
    EN("ENGLISH"),
    AR("ARABIC"),
    BE("BELARUSIAN"),
    BG("BULGARIAN"),
    CS("CZECH"),
    CY("WELSH"),
    DA("DANISH"),
    DE("GERMAN"),
    EL("GREEK"),
    EO("ESPERANTO"),
    ES("SPANISH"),
    ET("ESTONIAN"),
    FI("FINNISH"),
    FR("FRENCH"),
    GA("IRISH"),
    GD("SCOTTISH"),
    HU("HUNGARIAN"),
    HY("ARMENIAN"),
    ID("INDONESIAN"),
    IS("ICELANDIC"),
    IT("ITALIAN"),
    JA("JAPANESE"),
    KO("KOREAN"),
    LT("LITHUANIAN"),
    LV("LATVIAN"),
    MK("MACEDONIAN"),
    MN("MONGOLIAN"),
    MO("MOLDAVIAN"),
    NE("NEPALI"),
    NL("DUTCH"),
    NN("NORWEGIAN"),
    PT("PORTUGUESE"),
    RO("ROMANIAN"),
    SK("SLOVAK"),
    SL("SLOVENIAN"),
    SQ("ALBANIAN"),
    SR("SERBIAN"),
    SV("SWEDISH"),
    TH("THAI"),
    TR("TURKISH"),
    UK("UKRAINIAN"),
    VI("VIETNAMESE"),
    YI("YIDDISH"),
    ZH("CHINESE");

    Language(String id) {
        this.id = id;
    }

    private final String id;
}
