package com.sussysyrup.smitheesfoundry.util;

import java.util.ArrayList;
import java.util.List;

public class TranslationUtil {

    public static boolean fullKeys = false;

    public static void adjustForLanguage(String codeIn)
    {
        if(fullKeysLanguages.contains(codeIn))
        {
            fullKeys = true;
        }
        else
        {
            fullKeys = false;
        }
    }

    static List<String> fullKeysLanguages = new ArrayList<>(){{
        add("ru_ru");
    }};
}
