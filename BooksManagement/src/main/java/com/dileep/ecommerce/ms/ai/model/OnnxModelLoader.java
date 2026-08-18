package com.dileep.ecommerce.ms.ai.model;

import ai.onnxruntime.*;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OnnxModelLoader {

	private final OrtEnvironment environment;

	private OrtSession session;

	public OnnxModelLoader(OrtEnvironment environment) {
		this.environment = environment;
	}

//    @PostConstruct
//    public void loadModel() throws Exception {
//
//        File modelFile =
//                new ClassPathResource("models/clip-image.onnx")
//                        .getFile();
//
//        session = environment.createSession(modelFile.getAbsolutePath());
//
//        System.out.println("Model Loaded Successfully");
//    }

	@PostConstruct
	public void loadModel() throws Exception {

		File modelFile = new ClassPathResource("models/clip-image.onnx").getFile();

		session = environment.createSession(modelFile.getAbsolutePath());

		System.out.println("====================================");
		System.out.println("MODEL LOADED");
		System.out.println("====================================");

		System.out.println("Inputs:");

		session.getInputInfo().forEach((name, info) -> {
			System.out.println(name);
			System.out.println(info.getInfo());
		});

		System.out.println();

		System.out.println("Outputs:");

		session.getMetadata().getCustomMetadata().forEach((k, v) ->
		System.out.println(k + " : " + v));

//		session.getOutputInfo().forEach((name, info) -> {
//			System.out.println(name);
//			System.out.println(info.getInfo());
//		});

	}

	public OrtSession getSession() {
		return session;
	}
}