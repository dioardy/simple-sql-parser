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
    
    public static ArrayList<Integer> tableIdxComponent(ArrayList<String> rawData) {
        ArrayList<Integer> idxTable = new ArrayList<>();
        int i = 1;
        int n = 0;
        String data = rawData.get(0);
        
        while(i <= rawData.size()-1) {
            data = data + rawData.get(i);
            i++;
        }
         
        idxTable.add(n);
        i = 1;
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
    
    public static ArrayList<Integer> tableIdxSpecification(ArrayList<String> rawData) {
        ArrayList<Integer> idxTable = new ArrayList<>();
        int i = 1;
        int n = 0;
        String data = rawData.get(0);
        
        while(i <= rawData.size()-1) {
            data = data + rawData.get(i);
            i++;
        }
         
        idxTable.add(n);
        i = 1;
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
    
    //collects all index that equals to # to signals new table
    public static ArrayList<Integer> tableIdx (String data) {                   //count ; and # to determine the position of table name
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> idxTable = new ArrayList<>();
        int i = 0;
        int n = 0;
         
        temp.add(n);
        n++;
        while(i <= data.length()-1) {                                           //n counts ; and #
            if(data.charAt(i) == ';') {                                         //# indicates new table
                n++;
                i++;
            } else if (data.charAt(i) == '#') {
                temp.add(n);
                n++;
                i++;
            } else {
                i++;
            }
        }
        
        i = 0;
        n = 0;
        while(i < (temp.size()/2)) {
            idxTable.add(temp.get(n));
            n = n + 2;
            i++;
        }
        return idxTable;
    }
    
    public static ArrayList<String> createArrayListTableComponentIdx(String[] dbArray) {
        ArrayList<String> table = new ArrayList<>();
        int total = 0;
        int i = 0;
        
        while(total < dbArray.length/2) {
            table.add(dbArray[i]);
            i = i + 2;
            total++;
        }
        
        total = 0;
        String data;
        while(total <= table.size()-1) {
            data = table.get(total);
            data = '#' + data;
            
            table.remove(total);
            table.add(total,data);
            total++;
        }
        return table;
    }
    
    public static ArrayList<String> createArrayListTableComponent(String[] dbArray) {
        ArrayList<String> table = new ArrayList<>();
        String[] ArrTable = new String[dbArray.length/2];
        int total = 0;
        int i = 0;
        
        while(total < ArrTable.length) {
            ArrTable[total] = (dbArray[i]);
            i = i + 2;
            total++;
        }

        i = 0;
        while(i < ArrTable.length) {
            for(String x: ArrTable[i].split(";")) {
                table.add(x);
            }
            i++;
        }
        
        return table;
    }
    
    public static ArrayList<String> createArrayListTableSpecification(String[] dbArray) {
        ArrayList<String> spec = new ArrayList<>();
        String[] ArrSpec = new String[(dbArray.length/2) + 1];
        int total = 0;
        int i = 1;
        
        while(total < ArrSpec.length-1) {
            ArrSpec[total] = (dbArray[i]);
            i = i + 2;
            total++;
        }
        ArrSpec[ArrSpec.length - 1] = dbArray[dbArray.length-1];
        
        i = 0;
        while(i < ArrSpec.length) {
            for(String x: ArrSpec[i].split(";")) {
                spec.add(x);
            }
            i++;
        }
        
        return spec;
    }
    
    public static ArrayList<String> createArrayListTableSpecificationIdx(String[] dbArray) {
        ArrayList<String> table = new ArrayList<>();
        int total = 0;
        int i = 1;
        
        while(total < (dbArray.length/2)) {
            table.add(dbArray[i]);
            i = i + 2;
            total++;
        }
        table.add(dbArray[dbArray.length-1]);
        
        total = 0;
        String data;
        while(total <= table.size()-1) {
            data = table.get(total);
            data = '#' + data;
            
            table.remove(total);
            table.add(total,data);
            total++;
        }
        return table;
    }
    
    public static ArrayList<Integer> specIdx(int total) {
        ArrayList<Integer> specIdx = new ArrayList<>();
        int i = 0;
        int x = 0;
        
        while(i < total - 1) {
            specIdx.add(x);
            i++;
            x = x + 3;
        }
        return specIdx;
    }
    
    //convert file to arraylist string
    public static String[] dbToArrayListString (String data) {         
        String[] dbArray = data.split("#");
        return dbArray;
    }
    
    public static ArrayList<Integer> tableSpec(String[] data) {
        ArrayList<Integer> db = new ArrayList<>();
        ArrayList<String> dbString = new ArrayList<>();
        String[] specification = new String[(data.length / 2) + 1];
        int i = 0;
        int x = 1;
        
        while(i <= specification.length - 2) {
            specification[i] = data[x];
            i++;
            x = x + 2;
        }
        specification[specification.length-1] = data[data.length-1];
        
        for (String specStr : specification[i].split(";")) {
            dbString.add(specStr);
        }
        int num;
        for (String specInt : dbString) {
            num = Integer.parseInt(specInt);
            db.add(num);
        }
        
        return db;
        
    }
    
    //return arraylist of table name and table attribute
    public static ArrayList<String> tableComp(String[] data) {
        ArrayList<String> db = new ArrayList<>();
        
        String[] component = new String[(data.length / 2)];
        int i = 0;
        int x = 0;
        
        while(i < component.length) {
            component[i] = data[x];
            
            i++;
            x = x + 2;
        }
        
        i = 0;
        
        while(i < component.length) {
            for (String comp : component[i].split(";")) {
                db.add(comp);
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
        
        while(!(listChar.get(i).equals(' ')) && (!(listChar.get(i).equals(';'))) && (i < listChar.size()-1)) {
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
        return idx;                                                             // return first char table idx
    }
    
    //check is semicolon is present
    public static boolean checkSemiColon(ArrayList<Character> listChar) {
        if(listChar.get(listChar.size()-1).equals(';')) {
            return true;
        }
        return false;
    }
    
    //collect table from input
    public static ArrayList<String> collectTableStatement(ArrayList<Character> parseStatement, int idx) {
        ArrayList<String> table = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        while((!(parseStatement.get(idx).equals(';'))) && (!(parseStatement.get(idx).equals(' ')))) {
            stringBuilder.append(parseStatement.get(idx));
            idx++;
        }
        String tbl = stringBuilder.toString();
        table.add(tbl);
        
        if(checkJoin(tbl,parseStatement) > 0) {
            stringBuilder.delete(0,stringBuilder.length());
            idx = checkJoin(tbl,parseStatement);
            
            while((!(parseStatement.get(idx).equals(' ')))) {
                stringBuilder.append(parseStatement.get(idx));
                idx++;
            }
            table.add(stringBuilder.toString());
        }
        return table;
    }
    
    //collects all table from file
    public static ArrayList<String> collectTableDb(ArrayList<String> dbArray, ArrayList<Integer> tableIdx) {
                                 
        ArrayList<String> tableDb = new ArrayList<>();                          
        int i = 0;
        
        while(i < tableIdx.size()) {
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
    //-1, table not found
    //else, return the amount of table found
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
    
    //-1, join false
    //-2, join doesn exist
    //else join true, idx at first char of 2nd table
    public static int checkJoin(String table, ArrayList<Character> parseStatement) {
        int tableLen = table.length();
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();
        
        if((parseStatement.get(checkFrom(parseStatement) + tableLen).equals(';'))) {
            return -2;
        } else {
            
            while ((!(parseStatement.get(checkFrom(parseStatement) + tableLen + i).equals(' '))) && (checkFrom(parseStatement) + tableLen + i != parseStatement.size())) {
                stringBuilder.append(parseStatement.get(checkFrom(parseStatement) + tableLen + i));
                i++;
            }
            if(stringBuilder.toString().equalsIgnoreCase("join")) {
                return checkFrom(parseStatement) + tableLen + i + 1;                
            } else if(stringBuilder.toString().equalsIgnoreCase("where")) {
                return -2;
            }
        }
        
        return -1;
    }
    
    //check if using correct
    //-1, using not found
    //else, return first char of using element
    public static int checkUsing(int idx, ArrayList<Character> parseStatement, ArrayList<String> table) {
        StringBuilder stringBuilder = new StringBuilder();
        idx = idx + table.get(1).length() + 1;
        
        if(idx<parseStatement.size()) {
            while((!(parseStatement.get(idx).equals(';'))) && (!(parseStatement.get(idx).equals(' '))) 
                    && (idx <= parseStatement.size())) {
                
                stringBuilder.append(parseStatement.get(idx));
                idx++;
            }
            if(stringBuilder.toString().equals("using")) {
                return idx+1;
            }
        }
        return -1;
    }
    
    //-1, missing parentheses
    //0, key not foreign key or primary key
    //1, key found
    public static int checkForeignKey(String key, ArrayList<String> table, ArrayList<Integer> tableIdx, ArrayList<String> dbArray) {
        int i;
        boolean status = false;

        if((key.charAt(0) == '(') && (key.charAt(key.length() - 1) == ')')) {
                int jum = 0;
                int idx[] = new int[4];                                         //array idx holds 4 value because the 0 and 2 idx holds the actual idx
                i = 0;                                                          //of table name, while the 1 and 3 index holds the next index so it
                int total = 0;                                                  //knows when to stop searching

                while((i < tableIdx.size() - 1) && status == false) {           //collect idx of table in statement from dbArray
                    if(dbArray.get(tableIdx.get(i)).equalsIgnoreCase(table.get(jum))) {
                        idx[total] = tableIdx.get(i);
                       
                        if(tableIdx.get(tableIdx.size() - 1) == idx[total]) {
                            total++;
                            idx[total] = dbArray.size() - 1;
                            total++;
                            jum++;
                        } else {
                            total++;
                            idx[total] = tableIdx.get(i + 1);
                            total++;
                            jum++;
                        }
                        
                        if(total == idx.length) {
                            status = true;
                        }
                    }
                    i++;
                }
                
                StringBuilder stringBuilder = new StringBuilder(key);
                stringBuilder.deleteCharAt(key.length()-1);
                stringBuilder.deleteCharAt(0);

                if(dbArray.get(idx[0] + 1).equalsIgnoreCase(stringBuilder.toString())) {
                    i = idx[2] + 1;
                    while(idx[3] > i) {
                        if(dbArray.get(i).equalsIgnoreCase(stringBuilder.toString())) {
                            return 1;
                        }
                        i++;
                    }
                } else if (dbArray.get(idx[2] + 1).equalsIgnoreCase(stringBuilder.toString())) {
                    i = idx[0] + 1;
                    while(idx[1] > i) {
                        if(dbArray.get(i).equalsIgnoreCase(stringBuilder.toString())) {
                            return 1;
                        }
                        i++;
                    }
                } else {
                    return 0;
                }
        }
        return -1;
    }
    
    //return foreing key
    public static String collectForeignKey(int idx, ArrayList<Character> parseStatement) {
        StringBuilder stringBuilder = new StringBuilder();
        
        while((!(parseStatement.get(idx).equals(';'))) && (!(parseStatement.get(idx).equals(' ')))
                && (idx <= parseStatement.size())) {
            
            stringBuilder.append(parseStatement.get(idx));
            idx++;
        }
        return stringBuilder.toString();
    }
    
    //check if where exist
    //-1, where is false
    //0, where doesnt exist
    //else, where exist and return first idx of where condition
    public static int checkWhere(ArrayList<Character> parseStatement) {
        int i = checkFrom(parseStatement);
        StringBuilder stringBuilder = new StringBuilder();
        
        while((!(parseStatement.get(i).equals(' '))) && (!(parseStatement.get(i).equals(';')))) {
            i++;
        }
        
        if(parseStatement.get(i).equals(' ')) {
            i++;
            while(!(parseStatement.get(i).equals(' '))) {
                stringBuilder.append(parseStatement.get(i));
                i++;
            }
  
            if(stringBuilder.toString().equalsIgnoreCase("where")) {
                i++;
                return i;
            }
        } else {
            return 0;
        }
        return -1;
    }
    
    //check if where value is a an attribute the table
    public static int checkWhereTable(ArrayList<Character> parseStatement, 
            ArrayList<Integer> tableIdx, ArrayList<String> dbArray, ArrayList<String> table) {
        
        int idx[] = new int[2];
        int i = checkWhere(parseStatement);
        
        
        StringBuilder stringBuilder = new StringBuilder();
        
        while(!(parseStatement.get(i).equals(' '))) {
            stringBuilder.append(parseStatement.get(i));
            i++;
        }
        
        int answ = i++;
        String attr = stringBuilder.toString();
        
        i = 0;
        boolean status = false;
        while(i < tableIdx.size()-1 && status == false) {
            if(table.get(0).equals(dbArray.get(tableIdx.get(i)))) {
                idx[0] = tableIdx.get(i);
                status = true;
            } else {
                i++;
            }
        }
        if(status == true) {
            if(tableIdx.get(i) == tableIdx.size() - 1) {
                idx[1] = dbArray.size() - 1;
            } else {
                idx[1] = tableIdx.get(i + 1);
            }
            i = idx[0] + 1;
            status = false;
            while(i < idx[1] && status == false) {
                if(dbArray.get(i).equalsIgnoreCase(attr)) {
                    answ++;
                    return answ;
                }
                i++;
            }
        }
        return 0;   
    }
    
    //return where value
    public static String checkWhereCondition(ArrayList<Character> parseStatement, 
            ArrayList<Integer> tableIdx, ArrayList<String> dbArray, ArrayList<String> table) {
        
        int i = checkWhereTable(parseStatement,tableIdx,dbArray,table);
        
        StringBuilder stringBuilder = new StringBuilder();
        while(!(parseStatement.get(i).equals(' '))) {
            stringBuilder.append(parseStatement.get(i));
            i++;
        }
        
        if(stringBuilder.toString().equalsIgnoreCase("=")) {
            i++;
            stringBuilder.delete(0, stringBuilder.length());
            
            while(!(parseStatement.get(i).equals(';'))) {
                stringBuilder.append(parseStatement.get(i));
                i++;
            }
            return stringBuilder.toString();
        }
    return "null";
    }
    
    //output result
    public static void output(String table, String[] attribute) {
        System.out.println();
        System.out.println("Table : " + table);
        System.out.print("Attribute : ");
        for(String x : attribute) {
            System.out.print(x + ", ");
        }
        System.out.println();
    }
    
    public static void outputJoin2(ArrayList<String> table, String[] attr) {
        System.out.println();
        System.out.print("Table : ");
        for (String x : table) {
            System.out.print(x + ", ");
        }
        System.out.println();
        System.out.print("Attribute : ");
        for (String y : attr) {
            System.out.print(y + ", ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) throws Exception{
        String data = ReadFileAsString("db.txt");
        
        ArrayList<String> dbArray = createArrayListTableComponent(dbToArrayListString(data));
        ArrayList<Integer> tableIdx = tableIdxComponent(createArrayListTableComponentIdx(dbToArrayListString(data)));
        ArrayList<String> dbArraySpec = createArrayListTableSpecification(dbToArrayListString(data));
        ArrayList<Integer> specIdx = tableIdxSpecification(createArrayListTableSpecificationIdx(dbToArrayListString(data)));
        ArrayList<String> listTableDb = collectTableDb(dbArray,tableIdx);       //collects all table name in listTableDb
        int idxUsing;
        String value;

        Scanner reader = new Scanner(System.in);                                                                                                                                  
        System.out.println("Masukan SQL Statement : ");
        String statement = reader.nextLine();
        
        ArrayList<Character> parseStatement = stringToArrayListChar(statement); //convert input into arraylist char
        
        if(checkSelect(parseStatement)) {
            if(checkFrom(parseStatement) != 0) {
                if(checkSemiColon(parseStatement)) {
                    ArrayList<String> table = collectTableStatement(parseStatement,checkFrom(parseStatement));
                    if(checkJoin(table.get(0),parseStatement) == -2) {                                                         //join doesnt exist
                        if(checkTable(table.get(0),listTableDb) != -1) {
                            if(checkWhere(parseStatement) > 0) {
                                if(checkWhereTable(parseStatement,tableIdx,dbArray,table) != 0) {
                                    if (!(checkWhereCondition(parseStatement,tableIdx,dbArray,table).equalsIgnoreCase("null"))) {
                                        value = checkWhereCondition(parseStatement,tableIdx,dbArray,table);
                                        if(checkAttribute(collectAttributeStatement(parseStatement),tableIdx,dbArray,table.get(0))){
                                            output(table.get(0),collectAttributeStatement(parseStatement));
                                        } else {
                                            System.out.println("Error Message : Incorrect attribute");
                                        }
                                    } else {
                                        System.out.println("Error Message : Incorrect where syntax");
                                    } 
                                } else {
                                    System.out.println("Error Message : Where attribute invalid");
                                }
                            } else if (checkWhere(parseStatement) == 0){
                                if(checkAttribute(collectAttributeStatement(parseStatement),tableIdx,dbArray,table.get(0))){
                                    output(table.get(0),collectAttributeStatement(parseStatement));
                                } else {
                                    System.out.println("Error Message : Incorrect attribute");
                                }
                            } else {
                                System.out.println("Error Message : where not found");
                            }
                        } else {
                            System.out.println("Error Message : Table not found in database");
                        }
                    } else if(checkJoin(table.get(0),parseStatement) > -1) {                                                   //join exist
                        idxUsing = checkUsing(checkJoin(table.get(0),parseStatement),parseStatement,table);
                        if(idxUsing != -1) {
                            String foreignKey = collectForeignKey(idxUsing,parseStatement);
                            if(checkForeignKey(foreignKey,table,tableIdx,dbArray) == 1) {
                                outputJoin2(table,collectAttributeStatement(parseStatement));
                            } else if (checkForeignKey(foreignKey,table,tableIdx,dbArray) == 0) {
                                System.out.println("Error Message : Key is not foreign key and primary key");
                            } else {
                                System.out.println("Error Message : Foreign key missing parenthesis");
                            }
                        }
                    } else {
                        System.out.println("Error Message : Join not found");
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