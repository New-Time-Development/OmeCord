package de.sage.util;

import java.io.IOException;

import com.darkprograms.speech.translator.GoogleTranslate;
import com.ibm.icu.text.Transliterator;

public class Übersetzer {
	
	static String textR;

	@SuppressWarnings("unused")
	public static String übersetzer(String text, String lan) {
		  String LANGUAGE_COMBINATION = "Any-" + lan; // Any language to English
		  String LANGUAGE_COMBINATION_NO_ACCENTS = LANGUAGE_COMBINATION+"; nfd; [:nonspacing mark:] remove; nfc";
		
		try {

			String originalLocale = GoogleTranslate.detectLanguage(text);

			/*System.out.println("Original Locale\t: " + originalLocale);
			System.out.println("Original Text\t: " + text);
			String unicodeCodes = StringEscapeUtils.escapeJava(text);
			System.out.println("Unicode codes\t: " + unicodeCodes);*/
		
			// Translator
			
			// Transliterator
			//Transliterator transliterator = Transliterator.getInstance(LANGUAGE_COMBINATION);
			//String result1 = transliterator.transliterate(text);

			
			
			textR = GoogleTranslate.translate(lan, text);
			
			Transliterator transliteratorNoAccent = Transliterator.getInstance(LANGUAGE_COMBINATION_NO_ACCENTS);
			String result2 = transliteratorNoAccent.transliterate(text);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return textR + " || *translated*";
	}

}