package persistence;

import org.json.JSONObject;

//Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

//represents Writable interface for model classes to inherit
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}