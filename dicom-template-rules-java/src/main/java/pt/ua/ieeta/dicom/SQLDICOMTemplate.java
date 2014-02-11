/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.dicom;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.dcm4che2.data.Tag;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DB Schema
 * PRAGMA foreign_keys = ON;
 * CREATE TABLE rules (id integer primary key, rulename text not null);
 * CREATE TABLE matchFields (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * CREATE TABLE i18n (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * CREATE TABLE map (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * 
 * 
 * PRAGMA foreign_keys = ON; CREATE TABLE rules (id integer primary key, rulename text not null); CREATE TABLE matchFields (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id)); CREATE TABLE i18n (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id)); CREATE TABLE map (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 * @author eriksson
 */
public class SQLDICOMTemplate implements DICOMTemplate{
    private final Connection con;
    private int ruleid = -1;
    public SQLDICOMTemplate(Connection con) {
        this.con = con;
    }
    
    
    @Override
    public void addRule(DICOMRuleInterface rule) throws Exception{
        HashMap<String, HashMap<String, String>> toMap = rule.toMap();
        UUID uuid = UUID.randomUUID();
        PreparedStatement st = con.prepareStatement("Insert into rules (rulename) values (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        st.setString(1, uuid.toString());
        st.executeUpdate();
        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        ruleid = rs.getInt(1);
        
        
        for (Map.Entry<String, HashMap<String, String>> entry : toMap.entrySet()) {
            String table = entry.getKey();
            HashMap<String, String> values = entry.getValue();
            
            for (Map.Entry<String, String> pair : values.entrySet()) {
                String key = pair.getKey();
                String value = pair.getValue();
                st = con.prepareStatement("Insert into "+table+" (key, value, ruleid) values (?, ?, ?)");
                st.setString(1, key);
                st.setString(2, value);
                st.setInt(3, ruleid);
                st.executeUpdate();
            }
            
        }
    }
    
    
    public int getInsertedRowId(){
        return this.ruleid;
    }
    
    public static void main(String[] args) {
//        {"matchFields": {
//        "StationName": "Device1",
//        "BodyPartExamined": "SKULL"
//        },
//        "map": {        "BodyPartExamined": "StudyDescription"},
//        "i18n": {
//          "Cranio": "SKULL",
//          "Joelho": "Knee"
//        }
//      },
//      {"matchFields": {
//        "StationName": "Logici7",
//        "BodyPartExamined": "StudyDescription"},
//        "i18n": {
//          "Cranio": "Brain",
//          "Joelho": "Knee"
//        }
//      }
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:rules.db");
            SQLDICOMTemplate template = new SQLDICOMTemplate(con);
        
            DICOMTemplateRuleImp ruleA = new DICOMTemplateRuleImp();

            HashMap<Integer, String> match = new HashMap<>();
            match.put(Tag.StationName, "Device1");
            match.put(Tag.BodyPartExamined, "SKULL");

            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(Tag.BodyPartExamined, Tag.StudyDescription);

            HashMap<String, String> i18n = new HashMap<>();
            i18n.put("Cranio", "SKULL");
            i18n.put("Joelho", "Knee");

            ruleA.setMatchFields(match);
            ruleA.setMapping(map);
            ruleA.setI18n(i18n);

            DICOMTemplateRuleImp ruleB = new DICOMTemplateRuleImp();

            match = new HashMap<>();
            match.put(Tag.StationName, "Logici7");
            match.put(Tag.BodyPartExamined, "StudyDescription");

            i18n = new HashMap<>();
            i18n.put("Cranio", "SKULL");
            i18n.put("Joelho", "Knee");

            ruleB.setMatchFields(match);
            ruleB.setI18n(i18n);

            template.addRule(ruleA);
            System.out.println("Inserted row ID :"+ template.getInsertedRowId());
            template.addRule(ruleB);
            System.out.println("Inserted row ID :"+template.getInsertedRowId());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                   con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(SQLDICOMTemplate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
