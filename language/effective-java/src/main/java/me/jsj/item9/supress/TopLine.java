package me.jsj.item9.supress;

import java.io.FileReader;
import java.io.IOException;

public class TopLine {
    public static String firstLineOfFileUsingTryFinally(String path) throws IOException {
        BadBufferedReader br = new BadBufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }

    public static String firstLineOfFileUsingTryWithResources(String path) throws IOException {
        try (BadBufferedReader br = new BadBufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }
}
