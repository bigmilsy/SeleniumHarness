/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dale;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletRequest;

//Database
import java.sql.*;
import java.util.Map;
import java.util.StringJoiner;

//Selenium
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
/**
 *
 * @author dmiller
 */
public class Entry {
    // chromeWebDriver;
    public static final String CHROME_DRIVER_EXE = "c:\\dale\\apps\\SeleniumTrial\\custom_libs\\chromedriver.exe";
    public Entry() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_EXE);
    }
    public String loadWebdriverTest(HashMap<String, String> pars, ServletRequest req) {
        //FileIO fileIO = (FileIO)req.getServletContext().getAttribute("fileIO");
        //WebDriver chromeWebDriver = new ChromeDriver();
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        chromeWebDriver = new ChromeDriver();
        chromeWebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        chromeWebDriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);        
        req.getServletContext().setAttribute("chromeWebDriver", chromeWebDriver);

        chromeWebDriver.quit();
        return "ok";
    }
    public String checkBodyHeader(HashMap<String, String> pars, ServletRequest req) {
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        chromeWebDriver = new ChromeDriver();
        chromeWebDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        chromeWebDriver.manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);        
        req.getServletContext().setAttribute("chromeWebDriver", chromeWebDriver);        
        chromeWebDriver.get("http://localhost:8084/PetToyStore/");
        WebElement txtHeader = chromeWebDriver.findElement(By.id("txtMainTitle"));
        String testString = txtHeader.getText();
        if (testString.equals("Dales Pet Toy Store (Demo)")) {
            //do nothing, continue
        } else {
            return "fail";
        }
        req.getServletContext().setAttribute("chromeWebDriver", chromeWebDriver);
        return "ok";
    }
    public String checkInitSQL(HashMap<String, String> pars, ServletRequest req) {
        Connection connect = null;
        Statement statement = null;
        //PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettoystore","dale","daledale");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from pettoystore.toys");
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }
        return "ok";
    }    
    public String testDBInsert(HashMap<String, String> pars, ServletRequest req) {
        Connection connect = null;
        Statement statement = null;
        //PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettoystore","dale","daledale");
            statement = connect.createStatement();
            statement.executeUpdate("INSERT INTO toys (name, description, img, price) VALUES ('TEST TOY', 'I AM A TEST DESCRIPTION FOR AUTOMATED TESTING.', 'tstTst', 99.66);");    
            //select to check
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from pettoystore.toys where name = 'TEST TOY';");
            resultSet.next();
            String testString = new String(resultSet.getString("name"));
            if (!testString.equals("TEST TOY")) {
                return "fail";
            }
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }
        return "ok";
    }
    public String testDBUpdate(HashMap<String, String> pars, ServletRequest req) {
        Connection connect = null;
        Statement statement = null;
        //PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettoystore","dale","daledale");
            statement = connect.createStatement();
            statement.executeUpdate("UPDATE toys SET name = 'UPDATED TEST TOY' WHERE name = 'TEST TOY';");    
            //select to check
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from pettoystore.toys where name = 'UPDATED TEST TOY';");
            resultSet.next();
            String testString = new String(resultSet.getString("name"));
            if (!testString.equals("UPDATED TEST TOY")) {
                return "fail";
            }
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }    
        return "ok";  
    }
    public String testDBDelete(HashMap<String, String> pars, ServletRequest req) {
        Connection connect = null;
        Statement statement = null;
        //PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;    
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettoystore","dale","daledale");
            statement = connect.createStatement();
            statement.executeUpdate("DELETE FROM toys WHERE name = 'UPDATED TEST TOY';");    
            //select to check
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from pettoystore.toys where name = 'UPDATED TEST TOY';");
            if (resultSet.next() == true) {
                return "fail";
            }
            connect.close();
        } catch (Exception e) {
            System.out.println(e);
            return "fail";
        }    
        return "ok";  
    }
    public String testAPICreate(HashMap<String, String> pars, ServletRequest req) {
        try {
            URL url = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            //parrams
            Map<String,String> arguments = new HashMap<>();
            arguments.put("command", "createToy");
            arguments.put("name", "TEST_TOY_API");
            arguments.put("description", "I AM A TEST DESCRIPTION FROM A TEST API");
            arguments.put("img", "tstTst");
            arguments.put("price", "56.78");            
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                     + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            //send
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }            
        } catch (Exception e) {
            System.out.println("ERROR post to API test: " + e.getMessage());
            return "fail";
        }
        return "ok";
    }
    public String testAPISelect(HashMap<String, String> pars, ServletRequest req) {
        try {            
            //do a getAll and ensure we see the new toy created
            URL url2 = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getAllToys");
            URLConnection urlCon = url2.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            //check to ensure we see the new string
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            String result = stringBuilder.toString();
            if (!result.contains("TEST_TOY_API")) {
                return "fail";
            }        
        } catch (Exception e) {
            System.out.println("ERROR post to API select test: " + e.getMessage());
            return "fail";
        }        
        return "ok";
    }
    public String testAPIUpdate(HashMap<String, String> pars, ServletRequest req) {
                //DONE: 1) Get test toy primary key via API   http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getToyIdByName&name=Tug-Of-War
                //2) Update test toy using the primary key
        String toyId = new String();
        try {            
            //do a getAll and ensure we see the new toy created
            URL url2 = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getToyIdByName&name=TEST_TOY_API");
            URLConnection urlCon = url2.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            //check to ensure we see the new string
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            toyId = stringBuilder.toString();

        } catch (Exception e) {
            System.out.println("ERROR post to API select test: " + e.getMessage());
            return "fail";
        }
        try {
            URL url = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            //parrams
            Map<String,String> arguments = new HashMap<>();
            arguments.put("command", "updateToy");
            arguments.put("id", toyId);
            arguments.put("name", "TEST_TOY_API_UPDATED");
            arguments.put("description", "I AM A TEST DESCRIPTION FROM A TEST API");
            arguments.put("img", "tstTst");
            arguments.put("price", "99.55");            
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                     + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            //send
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }            
        } catch (Exception e) {
            System.out.println("ERROR post to API test: " + e.getMessage());
            return "fail";
        }      
        return "ok";
    }
    public String testAPIDelete(HashMap<String, String> pars, ServletRequest req) {
        String toyId = new String();
        try {            
            //do a getAll and ensure we see the new toy created
            URL url2 = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getToyIdByName&name=TEST_TOY_API_Updated");
            URLConnection urlCon = url2.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));
            //check to ensure we see the new string
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            toyId = stringBuilder.toString();

        } catch (Exception e) {
            System.out.println("ERROR post to API select test: " + e.getMessage());
            return "fail";
        }
        try {
            URL url = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);
            //parrams
            Map<String,String> arguments = new HashMap<>();
            arguments.put("command", "deleteToy");
            arguments.put("id", toyId);
            StringJoiner sj = new StringJoiner("&");
            for(Map.Entry<String,String> entry : arguments.entrySet())
                sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" 
                     + URLEncoder.encode(entry.getValue(), "UTF-8"));
            byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            //send
            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }            
        } catch (Exception e) {
            System.out.println("ERROR post to API test: " + e.getMessage());
            return "fail";
        }         
        System.out.println("Is this the correct toy ID? : " + toyId);
        return "ok";
    }
    public String checkInitAPI(HashMap<String, String> pars, ServletRequest req) {
        try {
            URL url = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getAllToys");
            URLConnection urlCon = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
        } catch (Exception e) {
            System.out.println("ERROR readin URL: " + e.getMessage());
            return "fail";
        }

        return "ok";
    }
    public String getDBData(HashMap<String, String> pars, ServletRequest req) {
        String result = new String();
        try {
            URL url = new URL("http://localhost:8084/PetToyStore/petToyStoreAPI.jsp?command=getAllToys");
            URLConnection urlCon = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }
            result = stringBuilder.toString();
        } catch (Exception e) {
            System.out.println("ERROR readin URL: " + e.getMessage());
            return "fail";
        }

        return result;        
    }
    public String testUseWebsite1(HashMap<String, String> pars, ServletRequest req) {
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        chromeWebDriver.get("http://localhost:8084/PetToyStore/");
        WebElement btnModify = chromeWebDriver.findElement(By.id("btnModify"));
        btnModify.click();
        WebElement txtName = chromeWebDriver.findElement(By.id("txtName"));
        txtName.sendKeys("Test Bone");
        WebElement txtDescription = chromeWebDriver.findElement(By.id("txtDescription"));
        txtDescription.sendKeys("I am a description for a test bone.");
        WebElement txtImg = chromeWebDriver.findElement(By.id("txtImg"));
        txtImg.sendKeys("tstBon");
        WebElement txtPrice = chromeWebDriver.findElement(By.id("txtPrice"));
        txtPrice.sendKeys("70.25");
        req.getServletContext().setAttribute("chromeWebDriver", chromeWebDriver);
        WebElement btnCreate = chromeWebDriver.findElement(By.id("btnCreate"));
        btnCreate.click();
        WebElement btnBack = chromeWebDriver.findElement(By.id("btnBack"));
        btnBack.click();
        //pause for 1 second to allow 
        return "ok";
    }
    public String testWebSelect(HashMap<String, String> pars, ServletRequest req) {
        //pause for 1 second to allow SQL to catch up
        try{Thread.sleep(1000);}catch(Exception e){System.out.println("Error when sleeping " + e.getMessage());}
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        chromeWebDriver.get("http://localhost:8084/PetToyStore/");
        try{Thread.sleep(1000);}catch(Exception e){System.out.println("Error when sleeping " + e.getMessage());}
        String bodyText = chromeWebDriver.findElement(By.tagName("body")).getText();
        
        if (!bodyText.contains("test bone.")) {
          return "fail";
        }        
        return "ok";
    }
    public String testWebDelete(HashMap<String, String> pars, ServletRequest req) {
        String name = pars.get("name");
        String id = new String();
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;            
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost:3306/pettoystore","dale","daledale");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from pettoystore.toys where name = '" + name + "';");
            resultSet.next();
            id = resultSet.getString("id");

        } catch (Exception e) {
            System.out.println("ERROR in getToyIdByName: " + e);
            return "fail";
        }
        
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        chromeWebDriver.get("http://localhost:8084/PetToyStore/modify.jsp");
        WebElement btnDelete = chromeWebDriver.findElement(By.id("btnDelete_" + id));
        btnDelete.click();         
        try{Thread.sleep(1000);}catch(Exception e){System.out.println("Error when sleeping " + e.getMessage());}
        WebElement btnBack = chromeWebDriver.findElement(By.id("btnBack"));
        btnBack.click();     
        return "ok";
    }            
    public String quitWebdriver(HashMap<String, String> pars, ServletRequest req) {
        WebDriver chromeWebDriver = (ChromeDriver)req.getServletContext().getAttribute("chromeWebDriver");
        //WebDriver chromeWebDriver =
        chromeWebDriver.quit();
        //chromeWebDriver = null;
        return "ok";        
    }
}
