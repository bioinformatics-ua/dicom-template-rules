/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.dicom;

import java.util.HashMap;

/**
 *
 * @author eriksson
 */
public interface DICOMRuleInterface{
    public void setMatchFields(HashMap<Integer, String> match);
    public HashMap<Integer, String> getMatchFields();
    public void setMapping(HashMap<Integer, Integer> maps);
    public HashMap<Integer, Integer> getMapping();
    public void setI18n(HashMap<String, String> i18ns);
    public HashMap<String, String> getI18n();
    public HashMap<String, HashMap<String, String>> toMap();
}
