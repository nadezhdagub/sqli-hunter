package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;

@WebServlet("/hello")
public class MainServlet extends HttpServlet{
    // declare array of commands to check against, can add to this easily
    public static final String stringsToCheck[] = { "select", "drop", "from",
            "exec", "exists", "update", "delete", "insert", "cast", "http",
            "sql", "null", "like", "mysql", "()", "information_schema",
            "sleep", "version", "join", "declare", "having", "signed", "alter",
            "union", "where", "create", "shutdown", "grant", "privileges" };

    // for reference, regex metachars that need escaped <([{\^-=$!|]})?*+.>

    // instantiate each com.example.RegexObj with the expression and a plain english
    // description

    // /* and */
    public static RegexObj regex1 = new RegexObj("(/\\*).*(\\*/)",
            "Found /* and */");

    // -- at the end
    public static RegexObj regex2 = new RegexObj("(--.*)$", "-- at end of sql");

    // ; and at least one " or '
    public static RegexObj regex3 = new RegexObj(";+\"+\'",
            "One or more ; and at least one \" or \'");

    // two or more "
    public static RegexObj regex4 = new RegexObj("\"{2,}+", "Two or more \"");

    // two or more '
    public static RegexObj regex5 = new RegexObj("\'{2,}+", "Two or more \'");

    // anydigit=anydigit
    public static RegexObj regex6 = new RegexObj("\\d=\\d", "anydigit=anydigit");

    // two or more white spaces in a row
    public static RegexObj regex7 = new RegexObj("(\\s\\s)+",
            "two or more white spaces in a row");

    // # at the end
    public static RegexObj regex8 = new RegexObj("(#.*)$", "# at end of sql");

    // two or more %
    public static RegexObj regex9 = new RegexObj("%{2,}+",
            "Two or more \\% signs");

    // admin and one of [; ' " =] before or after admin
    public static RegexObj regex10 = new RegexObj(
            "([;\'\"\\=]+.*(admin.*))|((admin.*).*[;\'\"\\=]+)",
            "admin (and variations like administrator) and one of [; ' \" =] before or after admin");

    // ASCII in hex
    public static RegexObj regex11 = new RegexObj("%+[0-7]+[0-9|A-F]+",
            "ASCII Hex");

    // declare array to hold each regex, can add to this easily
    public static final RegexObj regexes[] = { regex1, regex2, regex3, regex4,
            regex5, regex6, regex7, regex8, regex9, regex10, regex11 };


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String username = req.getParameter("username");
       // /*
        String line = choose(new File("D:\\gitlab\\sqli\\Queries20.txt"));
        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        if (username == null) {
            printWriter.write("Hello, Anonymous" + "<br>");
            printWriter.write("Hello, " +  line.replace(" ", "+") + "<br>");
        }
       //  /*

        else if (username.equals(line)){
            int prediction;
            int stringCounter = 0;
            int condPos = 0;
            int condNeg = 0;
            int truePos = 0;
            int trueNeg = 0;
            int falsePos = 0;
            int falseNeg = 0;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;

            // get the label and store it
            char label = line.charAt(0);

            // increment the true totals accordingly
            if (label == '0') {
                condNeg++;
            } else if (label == '1') {
                condPos++;
                flag1 = true;
            }

            // get the sql string
            String sqlString = line.substring(2);

            // if true is returned, then classify as malware, otherwise
            // benign
            if (sqlHandler(sqlString)) {
                prediction = 1;
                if (Character.getNumericValue(label) == prediction) {
                    // hit
                    truePos++;
                    flag2 = true;
                    printWriter.write("not allowed" + "<br>");
                    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    try {
                        URI uri = new URI("https://base.garant.ru/10108000/");
                        desktop.browse(uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    session.invalidate();
                } else {
                    // false alarm
                    falsePos++;
                    printWriter.write("Hello, " +  "<br>" + line + "<br>");
                }
            } else {
                prediction = 0;
                if (Character.getNumericValue(label) == prediction) {
                    // correctly rejected
                    trueNeg++;
                    printWriter.write("Hello, " +  "<br>" + line + "<br>");
                } else {
                    // missed it
                    falseNeg++;
                    flag3 = true;
                    printWriter.write("not allowed" + "<br>");
                    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                    try {
                        URI uri = new URI("https://base.garant.ru/10108000/");
                        desktop.browse(uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    session.invalidate();
                }
            }
        }
        else {
            printWriter.write("Hello, " +  "<br>" + line + "<br>");
        }
        // */

        printWriter.close();
    }

    public static String choose(File f) throws FileNotFoundException
    {
        Random rand = new Random();
        List<String> wordsList = new ArrayList<>();
        try{
            FileReader fileReader = new FileReader(f);

            // create new buffered reader
            BufferedReader br = new BufferedReader(fileReader);
            String read;
            while ((read = br.readLine()) != null){
                wordsList.add(read);               // читаем все слова из файла и добавляем в коллекцию
            }
            System.out.println(wordsList);
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
        }catch(IOException e){
            System.out.println("I/O error.");
        }

        return wordsList.get(4);
    }


    public static boolean sqlHandler(String sqlString) {

        // use two more bools for returns from sqlStringChecker and sqlRegexChecker
        boolean pass1 = false;
        boolean pass2 = false;

        // call both, pass in the string
        pass1 = sqlStringChecker(sqlString);
        pass2 = sqlRegexChecker(sqlString);

        // if either checker is true return true otherwise return false
        if (pass1 || pass2) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean sqlStringChecker(String sqlToCheck) {

        boolean pass = false;

        System.out.println("\nRunning SQL String Checker");

        // convert to lower case to handle obfuscation with mixed upper and
        // lower case
        sqlToCheck = sqlToCheck.toLowerCase();

        // for each string in stringsToCheck
        for (String command : stringsToCheck) {

            if (sqlToCheck.contains(command)) {

                System.out
                        .printf("SQL string found (%s), predicted label = 1\n",
                                command);

                if (!pass) {
                    pass = true;
                }
            }

        }

        if (!pass) {
            System.out.println("No SQL command found, predicted label = 0");
        }

        return pass;
    }

    public static boolean sqlRegexChecker(String sqlToCheck) {

        System.out.println("\nRunning SQL Regex Checker");

        // bool for each regex
        boolean pass = false;
        // bool to return overall
        boolean overall = false;

        Matcher matcher;

        // convert to lower case to handle obfuscation with mixed upper and
        // lower case
        sqlToCheck = sqlToCheck.toLowerCase();

        // regex checking
        for (RegexObj regex : regexes) {

            // check sqlToCheck vs regex, if pattern returns i.e. regex returns
            // true

            matcher = regex.getRegexPattern().matcher(sqlToCheck);

            pass = matcher.find();

            if (pass) {

                System.out
                        .printf("Malicious input found via regex (%s), predicted label = 1\n",
                                regex.getDescription());

            } else {
                System.out
                        .printf("No malicious input found via regex (%s), predicted label = 0\n",
                                regex.getDescription());

            }

            // if a regex returns true for the first time (i.e. overall is still
            // false), then make overall true
            if ((pass) && (!overall)) {
                overall = true;
            }
        }
        return overall;
    }
}
