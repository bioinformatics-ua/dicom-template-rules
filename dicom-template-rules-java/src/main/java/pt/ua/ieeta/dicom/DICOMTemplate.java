/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.ieeta.dicom;

/**
 *
 * @author eriksson
 */
public interface DICOMTemplate {
    public void addRule(DICOMRuleInterface rule) throws Exception;
}
