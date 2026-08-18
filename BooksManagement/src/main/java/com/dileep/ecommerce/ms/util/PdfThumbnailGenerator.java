package com.dileep.ecommerce.ms.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PdfThumbnailGenerator {

	private static final int DPI = 150;
	private static final int THUMB_WIDTH = 200;
	private static final int THUMB_HEIGHT = 280;

	/**
	 * Generates a thumbnail from the first page of the PDF.
	 *
	 * @param pdfFile Uploaded PDF
	 * @return Thumbnail as PNG bytes
	 * @throws IOException
	 */
//	public byte[] generateThumbnail(MultipartFile pdfFile) throws IOException {

	public byte[] generateThumbnail(byte[] pdfBytes) throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfBytes)) {

			PDFRenderer renderer = new PDFRenderer(document);

			// Render first page at 150 DPI
			BufferedImage originalImage = renderer.renderImageWithDPI(0, DPI);

			// Resize to thumbnail
			BufferedImage thumbnail = resize(originalImage, THUMB_WIDTH, THUMB_HEIGHT);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			ImageIO.write(thumbnail, "png", outputStream);

			return outputStream.toByteArray();
		}
	}

	private BufferedImage resize(BufferedImage originalImage, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = resizedImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.drawImage(originalImage, 0, 0, width, height, null);
		graphics.dispose();
		return resizedImage;
	}

	public BufferedImage extractFirstPage(byte[] pdfBytes) throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfBytes)) {
			PDFRenderer renderer = new PDFRenderer(document);
			return renderer.renderImageWithDPI(0, 300);
		}
	}

}
