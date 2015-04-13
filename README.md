dicom-template-rules
====================

DICOM Template Rules

## Structure

This is a library of template rules that can be used in any java project that use the template rules. 
It is based in JSON format and they support:

* direct match 
* range operations 
* transformations 

## Example 

```
{"matchFields": {
        "StationName": "Fugi1",
        "InstitututionName": "Inst1"},
        "Sensitivity": {
          "Operation": "Range",  
          "Values" : {"0-400": "High Exposure", "400-800": "Normal Exposure", "800-1600": "Low Exposure"}
        },
      }
      
```
