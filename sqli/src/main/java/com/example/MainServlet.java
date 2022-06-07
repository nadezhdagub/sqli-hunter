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
    public static final String stringsToCheck[] = { "select", "drop", "from",
            "exec", "exists", "update", "delete", "insert", "cast", "http",
            "sql", "null", "like", "mysql", "()", "information_schema",
            "sleep", "version", "join", "declare", "having", "signed", "alter",
            "union", "where", "create", "shutdown", "grant", "privileges" };

    public static RegexObj regex1 = new RegexObj("(/\\*).*(\\*/)",
            "Found /* and */");

    public static RegexObj regex2 = new RegexObj("(--.*)$", "-- at end of sql");

    public static RegexObj regex3 = new RegexObj(";+\"+\'",
            "One or more ; and at least one \" or \'");

    public static RegexObj regex4 = new RegexObj("\"{2,}+", "Two or more \"");

    public static RegexObj regex5 = new RegexObj("\'{2,}+", "Two or more \'");

    public static RegexObj regex6 = new RegexObj("\\d=\\d", "anydigit=anydigit");

    public static RegexObj regex7 = new RegexObj("(\\s\\s)+",
            "two or more white spaces in a row");

    public static RegexObj regex8 = new RegexObj("(#.*)$", "# at end of sql");

    public static RegexObj regex9 = new RegexObj("%{2,}+",
            "Two or more \\% signs");

    public static RegexObj regex10 = new RegexObj(
            "([;\'\"\\=]+.*(admin.*))|((admin.*).*[;\'\"\\=]+)",
            "admin (and variations like administrator) and one of [; ' \" =] before or after admin");

    public static RegexObj regex11 = new RegexObj("%+[0-7]+[0-9|A-F]+",
            "ASCII Hex");

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

            char label = line.charAt(0);

            if (label == '0') {
                condNeg++;
            } else if (label == '1') {
                condPos++;
                flag1 = true;
            }

            String sqlString = line.substring(2);

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

            BufferedReader br = new BufferedReader(fileReader);
            String read;
            while ((read = br.readLine()) != null){
                wordsList.add(read);
            }
            System.out.println(wordsList);
        }catch(FileNotFoundException e){
            System.out.println("File not found.");
        }catch(IOException e){
            System.out.println("I/O error.");
        }

        return wordsList.get(33);
    }


    public static boolean sqlHandler(String sqlString) {

        boolean pass1 = false;
        boolean pass2 = false;

        pass1 = sqlStringChecker(sqlString);
        pass2 = sqlRegexChecker(sqlString);

        if (pass1 || pass2) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean sqlStringChecker(String sqlToCheck) {

        boolean pass = false;

        System.out.println("\nRunning SQL String Checker");

        sqlToCheck = sqlToCheck.toLowerCase();

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

        boolean pass = false;
        boolean overall = false;

        Matcher matcher;


        sqlToCheck = sqlToCheck.toLowerCase();

        for (RegexObj regex : regexes) {

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

            if ((pass) && (!overall)) {
                overall = true;
            }
        }
        return overall;
    }
}
