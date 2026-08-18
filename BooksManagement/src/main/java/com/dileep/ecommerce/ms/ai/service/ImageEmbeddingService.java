package com.dileep.ecommerce.ms.ai.service;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dileep.ecommerce.ms.ai.model.OnnxModelLoader;
import com.dileep.ecommerce.ms.ai.preprocess.ImagePreprocessor;

import ai.onnxruntime.OnnxTensor;
import ai.onnxruntime.OrtSession;

@Service
public class ImageEmbeddingService {

    private final OnnxModelLoader modelLoader;
    private final ImagePreprocessor imagePreprocessor;

    public ImageEmbeddingService(OnnxModelLoader modelLoader,
                                 ImagePreprocessor imagePreprocessor) {
        this.modelLoader = modelLoader;
        this.imagePreprocessor = imagePreprocessor;
    }

    public float[] generateEmbedding(BufferedImage image) throws Exception {

        OnnxTensor tensor = imagePreprocessor.preprocess(image);

        OrtSession session = modelLoader.getSession();

        try (OrtSession.Result result =
                     session.run(Map.of("pixel_values", tensor))) {

            float[][] embeddings =
                    (float[][]) result.get("image_embeds").get().getValue();

            return embeddings[0];
        }
        finally {
            tensor.close();
        }
    }
}