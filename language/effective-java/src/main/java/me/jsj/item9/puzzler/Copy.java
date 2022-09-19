package me.jsj.item9.puzzler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Copy {
    private static final int BUFFER_SIZE = 8 * 1024;

    static void copy(String src, String dst) throws IOException {
        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);
        try {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while((n = in.read(buf)) >= 0) out.write(buf, 0, n);
        } finally {
            try {
                out.close();
            } catch (IOException e) {

            }

            try {
                in.close();
            } catch (IOException e) {

            }
        }
    }
}
