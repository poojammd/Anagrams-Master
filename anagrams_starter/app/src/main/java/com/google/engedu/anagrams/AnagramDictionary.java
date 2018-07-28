/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.provider.UserDictionary;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AnagramDictionary {
    //TODO: Add Comments for al data structures
    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet=new HashSet<>();
    //private ArrayList<String> wordList=new ArrayList<>(); //Used till Milestone 1. After which ArrayList is replaced by Hashmaps
    private HashMap<String,ArrayList<String>> lettersToWord=new HashMap<>();    //Maps all possible words that can be formed using the given  letters
    private HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<>();
    private int wordLength=DEFAULT_WORD_LENGTH;



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);//Reads the words from the dictionary
        String line;
        while((line = in.readLine()) != null)//Enters the  loop only if something is entered
             {
            String word = line.trim();//Removes any blank spaces from the word
            wordSet.add(word);
            //wordList.add(word);
            String sorted=sortLetters(word);
            int length=word.length();
            if (lettersToWord.containsKey(sorted))//If the sorted word is a key of the hashmap
            {
                lettersToWord.get(sorted).add(word);//Add the word to the arraylist (value) of hashmap whose key is got from the sorted letters of the  word
            }
            else{

                // if the key isn"t found new arraylist(value of hashmap) is created with the sorted letters of the word as the key


                ArrayList<String> value = new ArrayList<>();
                value.add(word);
                lettersToWord.put(sorted,value);
            }
            if (sizeToWords.containsKey(length))
            {
                sizeToWords.get(word.length()).add(word);
            }
            else{


                ArrayList<String> valueLength = new ArrayList<>();
                valueLength.add(word);
                sizeToWords.put(length,valueLength);
            }



        }
       /* Scanner s = new Scanner(new File("words.txt"));
        ArrayList<String> wordList = new ArrayList<String>();
        while (s.hasNext()){
            wordList.add(s.next());
        }
        s.close();*/
    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word)&& !word.contains(base))//if the wordset contains the word without the substring of base word then it is a good word
            return true;
        else
            return false;
    }


    public String sortLetters(String word)
    {
        char st[]= word.toCharArray();
        Arrays.sort(st);
        return new String(st);

    }




    public ArrayList<String> getAnagrams(String targetWord) {
      /* String sortedtargetedword= sortLetters(targetWord);
       int length=targetWord.length();
        ArrayList<String> result = new ArrayList<String>();
        for (String arrayword : wordList)
        {
            if (arrayword.len/gth()==length)
            {
                if(sortedtargetedword.equals(sortLetters(arrayword)))
                {
                    result.add(arrayword);
                }
            }
        }
        return result;*/
      String sortedstring=sortLetters(targetWord);
      return lettersToWord.get(sortedstring);//Getting the anagrams from the sorted version of the given word using the hashmap
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char i='a';i<='z';i++)
        {
            ArrayList<String> list =getAnagrams(word+i);
            if (list!=null)
                 result.addAll(list);//add all anagrams with one more letter to the list
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char i='a';i<='z';i++)
        {
            ArrayList<String> list =getAnagramsWithOneMoreLetter(word+i);
            if (list!=null)
                result.addAll(list);
        }
        return result;
    }

    public String pickGoodStarterWord() {


            //String word = "";
            String word = sizeToWords.get(wordLength).get(random.nextInt(sizeToWords.get(wordLength).size()));//getting a random word from th arraylist of the hashmap
            int noOfAnagrams = getAnagramsWithOneMoreLetter(word).size();
            while (noOfAnagrams < MIN_NUM_ANAGRAMS )//checking if the word has the min no_ of anagrams
            // if not searching for a new random word of the same arraylist having same wordlength
            {
                word = sizeToWords.get(wordLength).get(random.nextInt(sizeToWords.get(wordLength).size()));
                noOfAnagrams = getAnagramsWithOneMoreLetter(word).size();
            }
            /*while (word.length() != wordLength)
            {
                 word = wordList.get(random.nextInt(wordList.size()));
            }*/
             if (++wordLength > MAX_WORD_LENGTH)
             {
                wordLength = MAX_WORD_LENGTH;
             }







            return word;



           /* String word = wordList.get(random.nextInt(wordList.size()));
            if (lettersToWord.get(sortLetters(word)).size()>MIN_NUM_ANAGRAMS && word.length()<=MAX_WORD_LENGTH)
                return word;*/

    }
}
