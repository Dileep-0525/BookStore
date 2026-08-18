package com.dileep.ecommerce.ms.service;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class ThumbnailService {

	private static final int THUMB_WIDTH = 250;
	private static final int THUMB_HEIGHT = 350;

	public byte[] createThumbnail(BufferedImage originalImage) throws IOException {
		BufferedImage thumbnail = resize(originalImage);
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			ImageIO.write(thumbnail, "png", outputStream);
			return outputStream.toByteArray();
		}
	}

	private BufferedImage resize(BufferedImage originalImage) {
		BufferedImage resizedImage = new BufferedImage(THUMB_WIDTH, THUMB_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = resizedImage.createGraphics();
		try {
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.drawImage(originalImage, 0, 0, THUMB_WIDTH, THUMB_HEIGHT, null);
		} finally {
			graphics.dispose();
		}

		return resizedImage;
	}
}