package cantwe.alldisagree.CWADForge.resource.client.util;

import cantwe.alldisagree.CWADForge.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

public class RecolourUtil {

        public static InputStream recolour(BufferedImage image, ColourScheme colourScheme) {

                ColorModel cm = image.getColorModel();
                boolean bool = image.isAlphaPremultiplied();
                WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());

                BufferedImage bi = new BufferedImage(cm, raster, bool, null);
                Color color;
                for(int h = 0; h < bi.getHeight(); h++) {
                        for (int w = 0; w < bi.getWidth(); w++) {
                                int rgb = bi.getRGB(w, h);

                                int alpha = (rgb >> 24) & 0xff;

                                if (alpha < 254) {
                                        continue;
                                }

                                color = new Color(rgb);

                                if (colourScheme.template().contains(color)) {
                                        List<Color> colourGrey = colourScheme.colours().stream().toList();
                                        List<Color> colourTemp = colourScheme.template().stream().toList();
                                        bi.setRGB(w, h, colourGrey.get(colourTemp.indexOf(color)).getRGB());
                                }
                        }
                }

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                        ImageIO.write(bi, "png", os);
                        InputStream is = new ByteArrayInputStream(os.toByteArray());

                        return is;
                } catch (Exception e)
                {
                        Main.LOGGER.error(e.toString());
                        return null;
                }
        }
}
