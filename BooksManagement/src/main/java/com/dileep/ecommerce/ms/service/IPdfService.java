package com.dileep.ecommerce.ms.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IPdfService {

	BufferedImage extractFirstPage(byte[] pdfBytes) throws IOException;

}
