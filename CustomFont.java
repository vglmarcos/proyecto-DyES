import java.awt.*;
import java.io.InputStream;

public class CustomFont {
    private static Font font = null;

    public CustomFont(){
        String fontName = "font/MagmaWave Caps.otf";

        try {
            InputStream is = getClass().getResourceAsStream(fontName);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
        }
        catch (Exception ex) {
            System.err.println(fontName + "  No se cargo la fuente");
            font = new Font("Arial", Font.PLAIN, 14);
        }
    }

    public Font MyFont (int estilo, float tamanio){
        Font tfont = font.deriveFont(estilo, tamanio);
        return tfont;
    }
}