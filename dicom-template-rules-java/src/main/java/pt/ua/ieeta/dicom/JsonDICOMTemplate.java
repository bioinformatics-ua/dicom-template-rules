/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.dicom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.dcm4che2.data.Tag;

/**
 * 
 * @author eriksson
 */
public class JsonDICOMTemplate implements DICOMTemplate{
    private final HashMap<String, List<HashMap<String, HashMap<String, String>>>> template = new HashMap<>();

    public JsonDICOMTemplate() {
        template.put("rules", new LinkedList<HashMap<String, HashMap<String, String>>>());
    }
    
    
    @Override
    public void addRule(DICOMRuleInterface rule) {
        List<HashMap<String, HashMap<String, String>>> rules = template.get("rules");
        rules.add(rule.toMap());
    }

    public String toJson(){
        return DICOMTemplateRuleImp.mapToString(template);
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
        
        JsonDICOMTemplate template = new JsonDICOMTemplate();
        
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
        template.addRule(ruleB);
        
        System.out.println(template.toJson());
    }
}
