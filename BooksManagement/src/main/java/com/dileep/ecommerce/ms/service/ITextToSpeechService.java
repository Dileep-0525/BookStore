	package com.dileep.ecommerce.ms.service;

import java.io.IOException;

public interface ITextToSpeechService {

    byte[] convertTextToAudio(String text) throws IOException;
	
    byte[] convertToAudio(String text, String voice) throws IOException;

}
