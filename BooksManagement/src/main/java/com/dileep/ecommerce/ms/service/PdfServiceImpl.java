package com.dileep.ecommerce.ms.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements IPdfService {

	private static final int DPI = 300;

	@Override
	public BufferedImage extractFirstPage(byte[] pdfBytes) throws IOException {
		try (PDDocument document = Loader.loadPDF(pdfBytes)) {
			PDFRenderer renderer = new PDFRenderer(document);
			return renderer.renderImageWithDPI(0, DPI);
		}
	}
}