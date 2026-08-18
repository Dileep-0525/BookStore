package com.dileep.ecommerce.ms.ai.preprocess;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;

import org.springframework.stereotype.Component;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtEnvironment;

@Component
public class ImagePreprocessor {

	private static final int IMAGE_WIDTH = 224;
	private static final int IMAGE_HEIGHT = 224;

	/**
	 * CLIP Mean
	 */
	private static final float[] MEAN = { 0.48145466f, 0.4578275f, 0.40821073f };

	/**
	 * CLIP Standard Deviation
	 */
	private static final float[] STD = { 0.26862954f, 0.26130258f, 0.27577711f };

	private final OrtEnvironment environment;

	public ImagePreprocessor(OrtEnvironment environment) {
		this.environment = environment;
	}

	public OnnxTensor preprocess(BufferedImage image) throws Exception {
		BufferedImage resized = resize(image);
		float[] pixels = normalize(resized);
		return OnnxTensor.createTensor(environment, FloatBuffer.wrap(pixels),
				new long[] { 1, 3, IMAGE_HEIGHT, IMAGE_WIDTH });
	}

	/**
	 * Resize image to 224x224
	 */
	private BufferedImage resize(BufferedImage original) {
		BufferedImage resized = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = resized.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.drawImage(original, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
		graphics.dispose();
		return resized;
	}

	/**
	 * Convert BufferedImage into normalized float array (NCHW)
	 */
	private float[] normalize(BufferedImage image) {
		float[] pixels = new float[3 * IMAGE_WIDTH * IMAGE_HEIGHT];

		int redOffset = 0;
		int greenOffset = IMAGE_WIDTH * IMAGE_HEIGHT;
		int blueOffset = IMAGE_WIDTH * IMAGE_HEIGHT * 2;

		for (int y = 0; y < IMAGE_HEIGHT; y++) {
			for (int x = 0; x < IMAGE_WIDTH; x++) {
				int rgb = image.getRGB(x, y);
				float red = ((rgb >> 16) & 0xFF) / 255.0f;
				float green = ((rgb >> 8) & 0xFF) / 255.0f;
				float blue = (rgb & 0xFF) / 255.0f;
				int index = y * IMAGE_WIDTH + x;
				pixels[redOffset + index] = (red - MEAN[0]) / STD[0];
				pixels[greenOffset + index] = (green - MEAN[1]) / STD[1];
				pixels[blueOffset + index] = (blue - MEAN[2]) / STD[2];
			}
		}
		return pixels;
	}

}