```sql
 #DB Schema
 PRAGMA foreign_keys = ON;
 CREATE TABLE rules (id integer primary key, rulename text not null);
 CREATE TABLE matchFields (id INTEGER PRIMARY KEY, key text not null, value text not null, ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 CREATE TABLE i18n (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
 CREATE TABLE map (id INTEGER PRIMARY KEY, key text not null, value text not null,  ruleid integer not null, FOREIGN KEY(ruleid) REFERENCES rules(id));
```
 
```java
  
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
 ```
