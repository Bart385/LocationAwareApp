package com.ruben.woldhuis.androideindopdrachtapp.Utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class CompressionUtil {
    public static byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        Log.d("COMPRESSION_TAG", "Original: " + data.length + " bytes");
        Log.d("COMPRESSION_TAG", "Compressed: " + output.length + " bytes");
        return output;
    }

    public static String decompress(byte[] data) throws IOException, DataFormatException {
        String encodedString = Base64.getEncoder().encodeToString(data);
        byte[] output = Base64.getDecoder().decode(encodedString);

        Inflater inflater = new Inflater();
        inflater.setInput(output);

        byte[] result = encodedString.getBytes();
        int resultLength = inflater.inflate(result);
        inflater.end();

        System.out.println("Compressed: " + data.length + " bytes");
        System.out.println("Decompressed: " + resultLength + " bytes");
        return new String(result, 0, resultLength, "UTF-8");
    }

}
