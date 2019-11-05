package com.kborid.library.util;

import java.io.*;

/**
 * IOUtils
 *
 * @description:
 * @date: 2019/10/23
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class IOUtils {
    private IOUtils() {
    }

    public static byte[] toByteArray(InputStream input, int initContainerSize) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(initContainerSize);
        copy(input, bos);
        return bos.toByteArray();
    }

    public static byte[] toByteArray(InputStream input, int initContainerSize, int bufferSize) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(initContainerSize);
        copy(input, bos, bufferSize);
        return bos.toByteArray();
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        return count > 2147483647L ? -1 : (int) count;
    }

    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        return copy(input, output, 4096);
    }

    public static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public static long copyLarge(InputStream input, OutputStream output, byte[] buffer) throws IOException {
        long count;
        int n;
        for (count = 0L; -1 != (n = input.read(buffer)); count += (long) n) {
            output.write(buffer, 0, n);
        }

        return count;
    }


    public static void closeQuietly(OutputStream output) {
        closeQuietly((Closeable) output);
    }

    public static void closeQuietly(InputStream input) {
        closeQuietly((Closeable) input);
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (null != closeable) {
                closeable.close();
            }
        } catch (IOException e) {
            //ignore
        }

    }
}
