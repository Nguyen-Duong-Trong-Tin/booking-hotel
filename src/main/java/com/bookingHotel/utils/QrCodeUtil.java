package com.bookingHotel.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

public final class QrCodeUtil {
  private QrCodeUtil() {
  }

  public static byte[] toPng(String content, int size) {
    if (content == null || content.isBlank()) {
      return new byte[0];
    }
    try {
      BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size);
      BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "PNG", outputStream);
      return outputStream.toByteArray();
    } catch (Exception ex) {
      return new byte[0];
    }
  }
}
