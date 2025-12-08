package edu.utsa.cs3443.questlog.service;

import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class FontLoader {

    private FontLoader() { /* no instances */ }

    public static void loadAllFonts() {
        load("/data/fonts/Jersey10-Regular.ttf");
        load("/data/fonts/DotGothic16-Regular.ttf");
    }

    private static void load(String resourcePath) {
        try (InputStream is = FontLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.err.println("Font not found: " + resourcePath);
                return;
            }

            Font font = Font.loadFont(is, 16);
            if (font == null) {
                System.err.println("Failed to load font: " + resourcePath);
            } else {
                System.out.println("Loaded font: " + font.getName() +
                        " / family: " + font.getFamily());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
