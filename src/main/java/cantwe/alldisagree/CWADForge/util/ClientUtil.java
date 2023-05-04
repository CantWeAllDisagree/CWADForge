package cantwe.alldisagree.CWADForge.util;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.PreLaunch;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

@Environment(EnvType.CLIENT)
public class ClientUtil {

    @Environment(EnvType.CLIENT)
    public static BufferedImage getImageFromID(Identifier identifier)
    {
        try(BufferedInputStream inputStream = openBufferedInputStream(identifier))
        {
            BufferedImage image = getImageFromStream(inputStream);
            inputStream.close();
            return image;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Environment(EnvType.CLIENT)
    public static BufferedImage getImageFromStream(BufferedInputStream stream)
    {
        if(stream != null)
        {
            try {
                BufferedImage image = ImageIO.read(stream);
                stream.close();
                return image;
            } catch (Exception e)
            {
                Main.LOGGER.error(e.toString());
            }
        }
        return null;
    }

    @Environment(EnvType.CLIENT)
    public static BufferedInputStream openBufferedInputStream(Identifier id)  {
        BufferedInputStream inputStream = new BufferedInputStream(PreLaunch.classLoader.getResourceAsStream(ResourceType.CLIENT_RESOURCES.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath()));
            if (inputStream != null) {
                return inputStream;
            } else {
                return null;
            }
    }

    @Environment(EnvType.CLIENT)
    public static BufferedImage copyImage(BufferedImage bi)
    {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);

        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
