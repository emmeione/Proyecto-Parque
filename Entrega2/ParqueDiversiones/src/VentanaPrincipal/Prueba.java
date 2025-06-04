package VentanaPrincipal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prueba {
    
////    PUNTO 1
//    public static void palabrasPares(String [] palabras) {
//        for (String palabra : palabras) {
//        	
////        	Siempre el módulo para saber si es par o no :p
//            if (palabra != null && palabra.length() % 2 == 0) {
//                System.out.println(palabra + " " + palabra.length() + " número de caractéres");
//            }
//        }
//    }
//    public static void main(String[] args) {
//        String [] Lista = {"dorito", "esternocleidomastoideo", "parangaricutirimicuaro", 
//        		"osito", "sacreblu", "otorrinolaringología","queso",
//        		"lineal", "ecuacion", "hashmap", "tenkiu", "Miraculous", 
//                 "tercermundista", "cuatro", "palabra", "par", "yogurt", "gato", "naranja", "negro"};
//        
//        palabrasPares(Lista);
//    }

    
//    PUNTO 2
    public static boolean esPalindroma(String palabra) {
        if (palabra == null) {
            return false;
        }

//        Esta parte es para que no se cuenten los espacios como caracteres y omitir mayúrculas 
        String nueva = palabra.replaceAll(" ", "").toLowerCase();
        
//        Aquí creo una palabra haciendo un  recorrido normalito
        String palabraAlDerecho = "";
        for (int i = 0; i < nueva.length(); i++) {
            palabraAlDerecho += nueva.charAt(i);  
        }
        
        String palabraAlReves = "";
        for (int i = nueva.length() - 1; i >= 0; i--) {
            palabraAlReves += nueva.charAt(i);  
        }

            if (palabraAlDerecho.equals(palabraAlReves)) {
                return true;
            }
        

        return false;
    }

    public static void main(String[] args) {
        System.out.println(esPalindroma("olo")); 
        System.out.println(esPalindroma("peso"));         
        System.out.println(esPalindroma("a"));             
    }
}