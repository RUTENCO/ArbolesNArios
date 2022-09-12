package arboles;

import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Arboles {

    /**
     * Comprueba si el string dado como parámetro es un string válido que repreenta
     * un árbol. Si no es válido arrojará una excepción.
     * 
     * @param arbolStr String a validar
     * @throws Exception Si el string no es una representación válida de un árbol.
     */
    public static void validateString(String arbolStr, boolean esBinario) throws Exception {

        if (arbolStr == null) {
            throw new NullPointerException("arbolStr cannot be null");
        }

        if (arbolStr.length() == 0) {
            throw new IllegalArgumentException("Length of arbolStr is zero.");
        }

        char c = arbolStr.charAt(0);

        if (Pattern.matches("\\(|\\)|,", Character.toString(c))) {

            throw new PatternSyntaxException("Malformed tree", arbolStr, 0);

        }

        Vector<String> invalidPatterns = new Vector<String>(0, 1);
        Collections.addAll(invalidPatterns,
                "\\(\\s*\\)", // ()
                "\\(\\s*\\(", // ((
                ",\\s*\\(", // ,(
                ",\\s*,", // ,,
                "\\)\\s*\\(", // )(
                "\\)\\s*[^,\\)]+" // )a
        );

        if (!esBinario) {
            Collections.addAll(invalidPatterns,
                    "\\(\\s*,", // (,
                    ",\\s*\\)" // ,)
            );
        }

        Pattern pattern;
        Matcher matcher;

        for (String p : invalidPatterns) {

            pattern = Pattern.compile(p);
            matcher = pattern.matcher(arbolStr);

            if (matcher.find()) {

                throw new PatternSyntaxException("Malformed tree", arbolStr, matcher.end() - 1);

            }

        }

        int openCount = 0, closeCount = 0;

        for (int i = 0; i < arbolStr.length(); i++) {

            c = arbolStr.charAt(i);

            if (c == '(') {
                openCount++;
            }
            if (c == ')') {
                closeCount++;
            }

        }

        if (openCount != closeCount) {

            throw new Exception("Número de abre paréntesis diferente a número de cierre de paréntesis.");

        }
    }

    /**
     * Válida y construye un árbol y sus subárboles recursivamente a partir del
     * String entregado como parámetro.
     * 
     * @param arbolStr String que representa el árbol a construir.
     * @return {@link ArbolLg} que representa el String dado.
     * @throws Exception Si el String <b>arbolStr</b> tiene patrones inválidos.
     */
    public static ArbolLg consArbolLg(String arbolStr) throws Exception {

        validateString(arbolStr, false);

        return ArbolLg.consArbolLg(arbolStr, new AtomicInteger());

    }

}
