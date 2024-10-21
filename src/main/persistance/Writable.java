// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistance;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as a JSON Object
    JSONObject  toJson();
}
