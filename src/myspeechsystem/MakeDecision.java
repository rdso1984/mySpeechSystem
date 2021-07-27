package myspeechsystem;

import java.io.IOException;

public class MakeDecision {
    
    
    public static void makeDecision (String result){
        
        if(result.equals("load calculator")) {
            System.out.println("Load calculator!");
        }
        if(result.equals("close calculator")) {
            System.out.println("Close calculator!");
        }
        if(result.equals("load calendar")){
            System.out.println("Load calendar");
            try {
                Runtime.getRuntime().exec("cmd /c C:/Windows/notepad.exe"); //ALTERAR O PROGRAMA A ABRIR
            }
            catch(IOException iOException){
                iOException.printStackTrace();
            }
        }
        if("close calendar".equals(result)){
            System.out.println("Close calendar");
        }
        if("load time".equals(result)){
            System.out.println("Load time");
            try {
                Runtime.getRuntime().exec("cmd /c C:/Users/rdso1/Documents/NetBeansProjects/CurrentTime/dist/CurrentTime.jar");
            }
            catch(IOException iOException){
                iOException.printStackTrace();
            }
        }
        if("close time".equals(result)){
            System.out.println("Close time");
        }
        else {
            System.out.println("Invalid option!!");
        }
        
    }
    
}
