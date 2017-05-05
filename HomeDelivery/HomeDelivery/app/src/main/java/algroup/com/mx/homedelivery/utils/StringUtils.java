package algroup.com.mx.homedelivery.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.util.regex.Pattern;

import algroup.com.mx.homedelivery.business.Persona;


/**
 * Created by MAMM on 19/04/15.
 */
public class StringUtils {
    private final static String  SEPARADOR = "||";

    public static String obtenerNombreCompleto( Persona persona ){
        StringBuilder nombreCompleto = new StringBuilder();
        if( persona != null){
            nombreCompleto.append( persona.getNombre() ).
                    append( " " ).
                    append( persona.getApellidoPaterno() ).
                    append( " " ).
                    append( persona.getApellidoMaterno() );
        }
        return nombreCompleto.toString();
    }


    public static boolean isOnlyIntegerPositive( String value ){
        return value.matches("^[0-9]\\d*$");
    }

    public static boolean isDecimal( String value ){
        return value.matches("-?(\\d+)?+(\\.\\d+)?");
    }

    public static String transformarPrecioADecimalConCentavos( String value ){
        DecimalFormatSymbols simbolo=new DecimalFormatSymbols();
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        DecimalFormat formateador = new DecimalFormat("########0.00" , simbolo);
        String valueFormated = formateador.format( Double.parseDouble( value ) );
        return valueFormated;
    }

    public static String transformarPrecio100APrecioDecimal( String precio100 ){
        int precio = Integer.parseInt( precio100 );
        BigDecimal bd = new BigDecimal( precio );
        return transformarPrecioADecimalConCentavos("" + bd.divide( new BigDecimal(100) ));

    }


    public static String cadenaVaciaPorNull( String cadena ){
        return cadena != null?cadena:"";
    }

    public static String removeTilde(String dato) {
        String newDato = dato;
        String[] characterSpecial = Const.VOWELS[0];
        String[] characterNormal = Const.VOWELS[1];
        for (int i = 0; i < characterSpecial.length; i++) {
            String letra = characterSpecial[i];
            int posicion = newDato.indexOf(letra);
            if (posicion > 0) {
                newDato = newDato.substring(0, posicion) + characterNormal[i]
                        + newDato.substring(posicion + 1, newDato.length());
            }
        }
        return newDato;
    }

    /**
     * Función que elimina acentos y caracteres especiales de
     * una cadena de texto.
     * @param input cadena a atransformar
     * @return cadena de texto limpia de acentos y caracteres especiales.
     */
    public static String removerCaracteresNoASCII(String input) {
        // Descomposición canónica
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Nos quedamos únicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\P{ASCII}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    public static String armarKeyPaqueteParaMapa( String codigoPaquete , String desParrilla  ){
        StringBuilder sb = new StringBuilder();
        sb.append( codigoPaquete );
        sb.append( SEPARADOR );
        sb.append( desParrilla );
        return sb.toString();
    }

//    public static String armarKeyPaqueteParaMapa( String codigoPaquete , String desParrilla , int idEspecialidad ){
//        StringBuilder sb = new StringBuilder();
//        sb.append( codigoPaquete );
//        sb.append( SEPARADOR );
//        sb.append( desParrilla );
//        sb.append( SEPARADOR );
//        sb.append( idEspecialidad );
//        return sb.toString();
//    }

//    public static String armarKeyPaqueteParaMapa( String codigoPaquete , int idParrilla , int idEspecialidad ){
//
//        StringBuilder sb = new StringBuilder();
//        sb.append( codigoPaquete );
//        sb.append( SEPARADOR );
//        sb.append( idParrilla );
//        sb.append( SEPARADOR );
//        sb.append( idEspecialidad );
//        return sb.toString();
//    }

//    public static String armarKeyPaqueteParaMapa( String codigoPaquete , int idEspecialidad ){
//
//        StringBuilder sb = new StringBuilder();
//        sb.append( codigoPaquete );
//        sb.append( SEPARADOR );
//        sb.append( idEspecialidad );
//        return sb.toString();
//    }

}
