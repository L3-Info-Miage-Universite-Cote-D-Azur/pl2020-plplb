package metier;

import org.json.JSONException;
import org.json.JSONObject;

public class UE implements ToJSON{
    private String name;
    private String code;
    private Boolean checked;

    public UE(String name, String code) {
        this.name = name;
        this.code = code;
        this.checked = false;
    }

    public UE(JSONObject json){
        try {
            this.name = (String) json.get("name");
            this.code = (String) json.get("code");
            this.checked = (Boolean) json.get("checked");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUeName() {
        return name;
    }

    public String getUeCode() {
        return code;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    /*JSON*/
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        try {
            json.put("name", getUeName());
            json.put("code", getUeCode());
            json.put("checked", getChecked());
        } catch (JSONException e) {
            e.printStackTrace();}
        return json;
    }
}
