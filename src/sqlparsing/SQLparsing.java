package sqlparsing;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SQLparsing {
    
    //Read file
    public static String ReadFileAsString (String filename)throws Exception {
        String data = "";
        data = new String(Files.readAllBytes(Paths.get(filename)));
        return data;
    }
    
    //convert string to array list char
    public static ArrayList<Character> stringToArrayListChar (String data) {
        ArrayList<Character> listChar = new ArrayList<>();
        
        for (char x: data.toCharArray()) {
            listChar.add(x);
        }
        return listChar;
    }
    
    //collects all index that equals to # to signals new table
    public static ArrayList<Integer> tableIdx (String data) {                   //count ; and # to determine the position of table name
        ArrayList<Integer> idxTable = new ArrayList<>();
        int i = 0;
        int n = 0;
         
        idxTable.add(n);
        n++;
        while(i <= data.length()-1) {                                           //n counts ; and #
            if(data.charAt(i) == ';') {                                         //# indicates new table
                n++;
                i++;
            } else if (data.charAt(i) == '#') {
                idxTable.add(n);
                n++;
                i++;
            } else {
                i++;
            }
        }
        return idxTable;
    }
    
    //convert file to arraylist string
    public static ArrayList<String> dbToArrayListString (String data) {         
        ArrayList<String> db = new ArrayList<>();
        int i = 0;
        String[] dbArray = data.split("#");
        while(i <= dbArray.length-1) {
            for (String x : dbArray[i].split(";")) {
                db.add(x);
            }
            i++;
        }
        return db;
    }
    
    //convert array list char to string
    public static String arrayListCharToString(ArrayList<Character> data) {
        char[] select = new char[data.size()];
        int i = 0;
        for(Character x: data) {
            select[i] = x;
            
            i++;
        }
        String result = select.toString();
        return result;
    }
    
    //check if select is present
    public static boolean checkSelect (ArrayList<Character> listChar) {
        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();
        
        while(!(listChar.get(i).equals(' ')) && (!(listChar.get(i).equals(';'))) && (i != listChar.size())) {
            stringBuilder.append(listChar.get(i));
            i++;
        }
        
        if(stringBuilder.toString().equalsIgnoreCase("select")) {
            return true;
        } else {
            return false;
        }
    }
    
    //idx = 0, false (from not found)
    //idx != 0, true (from found)
    //check if from is present
    public static int checkFrom (ArrayList<Character> listChar) {
        StringBuilder stringBuilder = new StringBuilder();
        int idx = 7;                                                            // 7 is the index that starts the attribute
        
        while((!(listChar.get(idx).equals(' '))) && (!(listChar.get(idx).equals(';'))) && (idx != listChar.size())) {
            idx++;
        }
        
        if (listChar.get(idx).equals(' ')) {
            idx++;
            while((!(listChar.get(idx).equals(' '))) && (!(listChar.get(idx).equals(';'))) && (idx != listChar.size())) {
                stringBuilder.append(listChar.get(idx));
                idx++;
            }
            
            if(!(stringBuilder.toString().equalsIgnoreCase("from"))) {
                idx = 0;
            } else {
                idx++;
            }
        }
        return idx;
    }
    
    //check is semicolon is present
    public static boolean checkSemiColon(ArrayList<Character> listChar) {
        if(listChar.get(listChar.size()-1).equals(';')) {
            return true;
        }
        return false;
    }
    
    //collect table from input
    public static String collectTableStatement(ArrayList<Character> listChar, int idx) {
        ArrayList<Character> arrTable = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        while(!(listChar.get(idx).equals(';'))) {
            stringBuilder.append(listChar.get(idx));
            idx++;
        }
        String check = arrayListCharToString(arrTable);
        return stringBuilder.toString();
    }
    
    //collects all table from file
    public static ArrayList<String> collectTableDb(ArrayList<String> dbArray, ArrayList<Integer> tableIdx) {
                                 
        ArrayList<String> tableDb = new ArrayList<>();                          
        int i = 0;
        
        while(i <= tableIdx.size()-1) {
            tableDb.add(dbArray.get(tableIdx.get(i)));
            i++;
           
        }
        return tableDb;
    }
    
    //collects all attribute from input
    public static String[] collectAttributeStatement (ArrayList<Character> listChar) {
        int idx = 7;
        StringBuilder stringBuilder = new StringBuilder();
        
        while(!(listChar.get(idx).equals(' '))) {
            stringBuilder.append(listChar.get(idx));
            idx++;
        }
        String[] attrStatement = stringBuilder.toString().split(",");
        return attrStatement;
    }
    
    //check if the attribute matches
    public static boolean checkAttribute(String[] attrStatement, ArrayList<Integer> tableIdx, ArrayList<String> dbArray, String table) {
        int i = 0;
        int correct = 0;
        
        while(i <= tableIdx.size()-1 && (!(table.equals(dbArray.get(tableIdx.get(i)))))) {
            i++;
        }
        int pointer = tableIdx.get(i) + 1;
        if(i < tableIdx.size()-1) {
            int max = tableIdx.get(i+1);
            int start = tableIdx.get(i);
            i = 0;
            
            while((i <= attrStatement.length-1) && (pointer <= max)) {
                if(attrStatement[i].equalsIgnoreCase(dbArray.get(pointer))) {
                    correct++;
                    pointer = start;
                    i++;
                }
                pointer= pointer + 1;
            }
        } else {
            int max = dbArray.size()-1;
            int start = tableIdx.get(i);
            i = 0;
            
            while((i <= attrStatement.length-1) && (pointer <= max)) {
                if(attrStatement[i].equalsIgnoreCase(dbArray.get(pointer))) {
                    correct++;
                    pointer = start;
                    i++;
                }
                pointer= pointer + 1;
            }
            
        }
        if(correct == attrStatement.length) {
            return true;
        } else {
            return false;
        }
    }
    
    //check if table is correct
    public static int checkTable(String table, ArrayList<String> listTableDb) {
        int i = 0;
        int x = -1;
        boolean status = false;

        while(i <= listTableDb.size()-1 && (status == false)) {
            if(table.equals(listTableDb.get(i))) {
                x = i;
                status = true;
            }
            i++;
        }
        return x;
    }
    
    //output result
    public static void output(String table, String[] attribute) {
        System.out.println("Table : " + table);
        System.out.print("Attribute : ");
        for(String x : attribute) {
            System.out.print(x + ", ");
        }
    }
    
    public static void main(String[] args) throws Exception{
        String data = ReadFileAsString("db.txt");
        ArrayList<Integer> tableIdx = tableIdx(data);                           //holds all index for new table
        ArrayList<String> dbArray = dbToArrayListString(data);                  //holds string arraylist value for every db element
        ArrayList<String> listTableDb = collectTableDb(dbArray,tableIdx);       //collects all table name in listTableDb

        Scanner reader = new Scanner(System.in);                                                                                                                                  
        System.out.println("Masukan SQL Statement : ");
        String statement = reader.nextLine();
        
        ArrayList<Character> parseStatement = stringToArrayListChar(statement); //convert input into arraylist char
        
        if(checkSelect(parseStatement)) {
            if(checkFrom(parseStatement) != 0) {
                if(checkSemiColon(parseStatement)) {
                    String table = collectTableStatement(parseStatement,checkFrom(parseStatement));
                    if(checkTable(table,listTableDb) != -1) {
                        //int idx = checkTable(table,listTableDb);
                        if(checkAttribute(collectAttributeStatement(parseStatement),tableIdx,dbArray,table)){
                            output(table,collectAttributeStatement(parseStatement));
                        } else {
                            System.out.println("Error Message : Incorrect attribute");
                        }
                    } else {
                        System.out.println("Error Message : Table not found in database");
                    }           
                } else {
                    System.out.println("Error Message : ; not found");
                }
            } else {
                System.out.println("Error Message : from not found");
            }
        } else {
            System.out.println("Error Message : select not found");
        }
    }   
}