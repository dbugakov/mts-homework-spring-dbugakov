package ru.siebel.spring.AnimalSpring.Util;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileUtil {

    public static void fileChannelWrite(String newData, String path) {
        try (RandomAccessFile accessFile = new RandomAccessFile(path, "rw")) {
            FileChannel fileChannel = accessFile.getChannel();
            FileChannel.open(Paths.get(path), StandardOpenOption.WRITE).truncate(0).close();
            ByteBuffer buf = ByteBuffer.allocate(10000);
            buf.clear();
            buf.put(newData.getBytes());
            buf.flip();

            while (buf.hasRemaining()) {
                fileChannel.write(buf);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    protected static List<String> fileChannelReadAllLines(String path) {
        List<String> stringList = new ArrayList<>();
        try (RandomAccessFile aFile = new RandomAccessFile(path, "rw")) {
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(10000);
            StringBuilder string = new StringBuilder();
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                buf.flip();
                while (buf.hasRemaining()) {
                    char c = (char) buf.get();
                    if ("\n".equals(String.valueOf(c))) {
                        string.delete(string.length() - 1, string.length());
                        stringList.add(String.valueOf(string));
                        string.delete(0, string.length());
                    } else {
                        string.append(c);
                    }
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return stringList;
    }

    public static String getRandomLineFromFile(String path) {
        List<String> stringList = fileChannelReadAllLines(path);
        Random random = new Random();
        return stringList.get(random.nextInt(stringList.size()));
    }
}