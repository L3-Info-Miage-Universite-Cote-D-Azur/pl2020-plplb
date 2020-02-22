package metier;

public class UE{
    private String name;
    private String code;
    private Boolean checked;

    public UE(String name, String code) {
        this.name = name;
        this.code = code;
        this.checked = false;
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
}
