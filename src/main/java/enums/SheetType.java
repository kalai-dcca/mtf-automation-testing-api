package enums;

public enum SheetType {
    CREATE("Create"),
    UPDATE_PUT("Update-put"),
    UPDATE_PATCH("Update-Patch"),
    DELETE("Delete"),
    SINGLE_RESOURCE("Single-resource"),
    LIST_USERS("List-Users"),
    SINGLE_USER("Single-User");

    public String enumData;

    SheetType(String value) {
        enumData = value;
    }

    public void getEnumData(String value){
        enumData = value;
    }

    public static SheetType getSheetTypeEnum(String value){
        for(final SheetType each : SheetType.values()){
            if(each.enumData.equals(value)){
                return each;
            }
        }
        return null;
    }

}
