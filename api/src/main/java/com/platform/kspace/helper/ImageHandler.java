package com.platform.kspace.helper;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class ImageHandler {
    public static String saveImage(String path, String imgBase64)
    {
        final String imageName = UUID.randomUUID().toString().replace("-", "");
        String extension = imgBase64.split(";")[0].split("/")[1];
        String imgBase64code = imgBase64.split(",")[1];
        String imagePath = path + imageName+"."+ extension;

        try(OutputStream fos = new FileOutputStream(imagePath))
        {
            byte[] imageByte= Base64.decodeBase64(imgBase64code);

            for(byte b : imageByte)
                fos.write(b);

            fos.flush();
            fos.close();
            return imageName + "." + extension;
        }
        catch(Exception e)
        {
            return "error = "+e;
        }
    }
}
