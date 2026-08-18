package com.dileep.ecommerce.ms.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.config.TtsProperties;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PiperTextToSpeechService implements ITextToSpeechService {

	private final TtsProperties properties;

//	@PostConstruct
//	public void init() {
//		System.out.println("Piper Path      : " + properties.piperPath());
//		System.out.println("Model Directory : " + properties.modelDirectory());
//		System.out.println("Default Voice   : " + properties.defaultVoice());
//	}

	@Override
	public byte[] convertTextToAudio(String text) throws IOException {
		// Execute Piper
		return convertToAudio(text, properties.defaultVoice());

	}

	@Override
	public byte[] convertToAudio(String text, String voice) throws IOException {
		if (text == null || text.isBlank()) {
			throw new IllegalArgumentException("Text cannot be null or blank.");
		}
		if (voice == null || voice.isBlank()) {
			voice = properties.defaultVoice();
		}
		System.out.println(properties.modelDirectory());
		Path modelPath = Paths.get(properties.modelDirectory(), voice + ".onnx");
		if (!Files.exists(modelPath)) {
			throw new IllegalArgumentException("Voice model not found : " + modelPath);
		}
		Path tempFile = Files.createTempFile("tts-", ".wav");

		List<String> command = List.of(
		        properties.piperPath(), "--model", modelPath.toString(),
		        "--output_file",
		        tempFile.toString()
		);
		
//		List<String> command = List.of(properties.piperPath(), "--model", modelPath.toString(), "--output_file", "-");
		try {
			ProcessBuilder builder = new ProcessBuilder(command);
			Process process = builder.start();
			// Send text to Piper
			try (OutputStream os = process.getOutputStream()) {
				os.write(text.getBytes(StandardCharsets.UTF_8));
				os.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
				os.flush();
			}
			// Read generated WAV audio
			int exitCode = process.waitFor();
			if (exitCode != 0) {
			    String error = new String(
			            process.getErrorStream().readAllBytes(),
			            StandardCharsets.UTF_8);
			    throw new RuntimeException(error);
			}
			byte[] audio = Files.readAllBytes(tempFile);
			Files.deleteIfExists(tempFile);
			return audio;
		} catch (IOException e) {
			throw new RuntimeException("Unable to start Piper.", e);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Piper execution interrupted.", e);
		}
	}

}