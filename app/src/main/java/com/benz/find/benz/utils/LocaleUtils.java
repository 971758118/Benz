package com.benz.find.benz.utils;

import java.util.Locale;


public class LocaleUtils {

    public static String localeToBcp47Language(Locale loc) {
        if(loc == null){
            return "en";
        }
        final char SEP = '-';       // we will use a dash as per BCP 47
        String language = loc.getLanguage();
        String region = loc.getCountry();
        String variant = loc.getVariant();

        // special case for Norwegian Nynorsk since "NY" cannot be a variant as per BCP 47
        // this goes before the string matching since "NY" wont pass the variant checks
        if( language.equals("no") && region.equals("NO") && variant.equals("NY")){
            language = "nn";
            region = "NO";
            variant = "";
        }

        if( language.isEmpty() || !language.matches("\\p{Alpha}{2,8}")){
            language = "und";       // Follow the Locale#toLanguageTag() implementation
            // which says to return "und" for Undetermined
        }else if(language.equals("iw")){
            language = "he";        // correct deprecated "Hebrew"
        }else if(language.equals("in")){
            language = "id";        // correct deprecated "Indonesian"
        }else if(language.equals("ji")){
            language = "yi";        // correct deprecated "Yiddish"
        }

        // ensure valid country code, if not well formed, it's omitted
        if (!region.matches("\\p{Alpha}{2}|\\p{Digit}{3}")) {
            region = "";
        }

        // variant subtags that begin with a letter must be at least 5 characters long
        if (!variant.matches("\\p{Alnum}{5,8}|\\p{Digit}\\p{Alnum}{3}")) {
            variant = "";
        }

        StringBuilder bcp47Tag = new StringBuilder(language);
        if (!region.isEmpty()) {
            bcp47Tag.append(SEP).append(region);
        }
        if (!variant.isEmpty()) {
            bcp47Tag.append(SEP).append(variant);
        }

        return bcp47Tag.toString();
    }


}
