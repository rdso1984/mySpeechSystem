package myspeechsystem;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

public class SpeechRecognize {
    
    private Logger logger = Logger.getLogger(getClass().getName()); //logger to show massage
    private String result; //result to make a action
    
    Thread resourcesThread; //check if the resources -microphone- is ready
    Thread speechThread; //load and run speech recognizer

    private LiveSpeechRecognizer recognizer; //create

    public void startSpeechRecognize() { //method to recognize speech
        logger.log(Level.INFO, "Loading...\n");

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us"); //show the path to Acoustic
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict"); //show the path to Dictionary
        configuration.setGrammarPath("resource:/grammars"); //show path to grammar
        configuration.setGrammarName("grammar"); //show the name of the grammar to use
        configuration.setUseGrammar(true); //indica que quer utilizar a grammar

        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        }
        catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        recognizer.startRecognition(true);

        startResourcesThread(); //Check if microphone is ready
        startSpeechThread(); //Run speech recognize
    }

    private void startResourcesThread() { //check if resources is ready
        if(resourcesThread != null && resourcesThread.isAlive()){
            return;
        }
        
        resourcesThread = new Thread(() -> {
            try { //Check if microphone is available
                if(AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {
                    logger.log(Level.INFO, "Microphone is available. \n");
                }
                else{
                    logger.log(Level.INFO, "Microphone is not available. \n");
                }
                Thread.sleep(350);
            }
            catch (InterruptedException ex) {
                logger.log(Level.WARNING, null, ex);
                resourcesThread.interrupt();
            }
        });
        resourcesThread.start(); //start      
    }
    
    private void startSpeechThread(){
        if(speechThread != null && speechThread.isAlive()) {
            return;
        }
        
        speechThread = new Thread(() -> {
            logger.log(Level.INFO, "You can start to speak... \n");
            
            try {
                while(true){
                    SpeechResult speechResult = recognizer.getResult();
                    if(speechResult != null) {
                        result = speechResult.getHypothesis();
                        System.out.println("You choose: "+ result + "\n");
                        MakeDecision.makeDecision(result);
                    }
                    else {
                        logger.log(Level.INFO, "I can't understand what you choose \n");
                    }
                }
            }
            catch (Exception ex) {
                logger.log(Level.WARNING, null, ex);
            }
        });
        speechThread.start();
    }

}
