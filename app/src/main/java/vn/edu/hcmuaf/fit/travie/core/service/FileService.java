package vn.edu.hcmuaf.fit.travie.core.service;

import android.content.ContentResolver;
import android.net.Uri;

import com.google.common.io.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FileService {
    public static File writeToFile(ContentResolver resolver, File directory, Uri uri) {
        try {
            String extension = Files.getFileExtension(Objects.requireNonNull(uri.getPath()));
            File file = new File(directory, System.currentTimeMillis() + "." + extension);

            InputStream inputStream = resolver.openInputStream(uri);

            if (inputStream == null) {
                throw new FileNotFoundException("File not found");
            }

            Files.asByteSink(file).writeFrom(inputStream);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
