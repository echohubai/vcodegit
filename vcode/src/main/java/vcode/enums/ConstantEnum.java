package vcode.enums;

/**
 * Created by hubai on 2017/8/4.
 * 常量枚举类
 */
public enum ConstantEnum {
    TOKENCONSTAN("CMB"),VALIDATECONSTANT("POWERED BY CMB"),
    SLIDEAPPID("Slide Verify"),PUZZLEAPPID("Puzzle Verify"),
    APPID("AppID");
    private int failedlimit;
    private int state;
    private String stateInfo;
    private String constant;
    ConstantEnum(String constant){
        this.constant= constant;
    }
    ConstantEnum(int failedlimit){
        this.failedlimit=failedlimit;
    }
    ConstantEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }
     public int getState() {
        return state;
    }
      public int getFailedlimit(){
        return failedlimit;
    }
     public String getStateInfo() {
        return stateInfo;
    }
    public String getConstant(){
        return constant;
    }
    @Override
    public String toString() {
        return String.valueOf ( this.constant );
    }
}
