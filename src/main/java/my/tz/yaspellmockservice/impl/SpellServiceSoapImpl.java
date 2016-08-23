package my.tz.yaspellmockservice.impl;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import my.tz.yaspellmockservice.contract.*;

public class SpellServiceSoapImpl implements SpellServiceSoap {

    private static final int MAX_ERROR_PERCENT = 5;

    public CheckTextResponse checkText(CheckTextRequest parameters) {
        String textToAnalyze = parameters.getText();
        CheckTextResponse response = new CheckTextResponse();
        SpellResult result = analyzeText(textToAnalyze, parameters.getFormat());
        response.setSpellResult(result);
        return response;
    }

    public CheckTextsResponse checkTexts(CheckTextsRequest parameters) {
        CheckTextsResponse response = new CheckTextsResponse();
        ArrayOfSpellResult allResults = new ArrayOfSpellResult();
        List<String> texts = parameters.getText();
        for (String textToAnalyze:texts) {
            SpellResult result = analyzeText(textToAnalyze, parameters.getFormat());
            allResults.getSpellResult().add(result);
        }
        response.setArrayOfSpellResult(allResults);
        return response;
    }

    private SpellResult analyzeText(String text, String format) {
        String cleanText = text;
        if (format.equals("html")) {
            cleanText = removeHTMLtags(text);
        }
        Pattern lineBreak = Pattern.compile("\r?\n", Pattern.UNICODE_CHARACTER_CLASS);
        Pattern nonWordChars = Pattern.compile("[\\W+]", Pattern.UNICODE_CHARACTER_CLASS);
        Pattern digitsChars = Pattern.compile("\\d+", Pattern.UNICODE_CHARACTER_CLASS);
        List<Integer> startLines = new ArrayList<Integer>();
        Matcher matcher = lineBreak.matcher(text);
        startLines.add(0); // Always present first line
        while(matcher.find()) {
            startLines.add(matcher.end());
        }
        String [] allWords = nonWordChars.split(cleanText);
        List<String> words = new ArrayList<String>();
        for(String s:allWords){
            Matcher onlyDigitWord = digitsChars.matcher(s);
            if (s.length() > 1 && !onlyDigitWord.matches()) {
                words.add(s);
            }
        }
        SpellResult result = new SpellResult();
        int errorWords = 0;
        Random rnd = new Random();
        int errorPercent = rnd.nextInt(MAX_ERROR_PERCENT + 1);
        if (errorPercent > 0 ) {
            errorWords =  (int)Math.ceil((double)(errorPercent * words.size()) / 100);
        }
        Collections.shuffle(words);
        for(int i = 0; i < errorWords; i++) {
            String word = words.get(i);
            int wordPos = text.indexOf(word);
            SpellError e = new SpellError();
            e.setWord(word);
            e.setLen(word.length());
            e.setCode(1);
            e.setPos(wordPos);
            for(int l = 0; l < startLines.size(); l++) {
                if (wordPos >= startLines.get(l) && ((l+1) >= startLines.size() || startLines.get(l+1) > wordPos)) {
                    e.setRow(l);
                    e.setCol(wordPos - startLines.get(l));
                    break;
                }
            }
            e.getS().add(word);
            result.getError().add(e);
        }
        return result;
    }

    private static String removeHTMLtags(String htmlText) {
        String cleanText = htmlText.replaceAll("\\<.*?>","");
        return cleanText;
    }
}
