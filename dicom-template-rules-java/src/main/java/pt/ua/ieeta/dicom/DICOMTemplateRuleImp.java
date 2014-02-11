/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.dicom;

import java.util.HashMap;
import java.util.Map.Entry;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.Tag;
import org.json.JSONObject;

/**
 *
 * @author eriksson
 */
public class DICOMTemplateRuleImp implements DICOMRuleInterface{
    private final HashMap<String, HashMap<String, String>> rule = new HashMap<>();
    private final BasicDicomObject dicom = new BasicDicomObject();

    public DICOMTemplateRuleImp() {
        rule.put("matchfields", new HashMap<String, String>());
        rule.put("map", new HashMap<String, String>());
        rule.put("i18n", new HashMap<String, String>());
    }
    
    
    @Override
    public void setMatchFields(HashMap<Integer, String> match) {
        HashMap<String, String> matchfields = rule.get("matchfields");
        matchfields.clear();
        for(Entry<Integer, String> e : match.entrySet()){
            matchfields.put(dicom.nameOf(e.getKey()), e.getValue());
        }
    }

    @Override
    public HashMap<Integer, String> getMatchFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setMapping(HashMap<Integer, Integer> maps) {
        HashMap<String, String> map = rule.get("map");
        map.clear();
        for(Entry<Integer, Integer> e : maps.entrySet()){
            map.put(dicom.nameOf(e.getKey()), dicom.nameOf(e.getValue()));
        }
    }

    @Override
    public HashMap<Integer, Integer> getMapping() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setI18n(HashMap<String, String> i18ns) {
        HashMap<String, String> i18n = rule.get("i18n");
        i18n.clear();
        i18n.putAll(i18ns);
    }

    @Override
    public HashMap<String, String> getI18n() {
        return rule.get("i18n");
    }

    public static String mapToString(HashMap rulemap) {
        return new JSONObject(rulemap).toString();
    }
    
    @Override
    public HashMap<String, HashMap<String, String>> toMap() {
        return (HashMap<String, HashMap<String, String>>) rule.clone();
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
//      }
        DICOMTemplateRuleImp jrule = new DICOMTemplateRuleImp();
        
        HashMap<Integer, String> match = new HashMap<>();
        match.put(Tag.StationName, "Device1");
        match.put(Tag.BodyPartExamined, "SKULL");
        
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(Tag.BodyPartExamined, Tag.StudyDescription);
       
        HashMap<String, String> i18n = new HashMap<>();
        i18n.put("Cranio", "SKULL");
        i18n.put("Joelho", "Knee");
        
        jrule.setMatchFields(match);
        jrule.setMapping(map);
        jrule.setI18n(i18n);
        
        System.out.println(DICOMTemplateRuleImp.mapToString(jrule.toMap()));
    }
}
