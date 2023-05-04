package cantwe.alldisagree.CWADForge.util;

import cantwe.alldisagree.CWADForge.Main;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

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

    public static MutableText getGeneratedTranslation(String singleKey, String compoundKey, Object... compounds)
    {
        if(fullKeys)
        {

            boolean hasKey = Language.getInstance().hasTranslation(singleKey);

            if(hasKey) {
                return Text.translatable(singleKey);
            }
            if(!hasKey && Main.config.isTranslationMode())
            {
                return Text.translatable(singleKey);
            }
        }
        return Text.translatable(compoundKey, compounds);
    }
}
